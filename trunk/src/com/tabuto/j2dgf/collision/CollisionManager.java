/**
* @author Francesco di Dio
* Date: 29 Novembre 2010
* Titolo: CollisionManager.java
* Versione: 0.7.2 Rev.:
*/

/*
 * Copyright (c) 2010 Francesco di Dio.
 * tabuto83@gmail.com 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */




/*
 * Questa classe non fa altro che gestire una serie di CollisionDetector, 
 * Ha un metodo Check che passa in rassegna tutti i collisionDetector registrati 
 */

package com.tabuto.j2dgf.collision;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * class <code>CollisionManager</code>
 * <p>
 * This class manage a vector of CollisionDetector. <p>
 * Add Every CollisionDetector to this class and CollisionManager schedule the CollisionDetector threads
 * to run their only task: CheckCollision methods.
 * This class extends Observable and implements Observer 'cause it is observing the Collision Detector and
 * allows to observe when a collision occurs by throws the message "CollisionDetector.ClassName" 
 * using the {@link Observable#notifyObservers(Object)}
 * 
 *
 * @author tabuto83
 * 
 * @version 0.7.0
 * 
 * @see Vector
 * @see CollisionDetector
 * @see CollisionBoundDetector
 */

public class CollisionManager extends Observable implements Serializable, Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6235330134986494263L;
	/**
	 * The collisionDetector Vector
	 */
	private Vector<CollisionDetector> CollisionsList; 
	
	/**
	 * The Collision Manager Thread executor service
	 */
	transient public ExecutorService executor; 

	private int DEFAULT_MAX_COLLISIONDETECTOR_THREADS = 10;
	
	/**
	 * public CollisionManager()
	 * <p>
	 * The constructor instances the CollisionDetector Vector
	 */
	public CollisionManager()
	{
		CollisionsList = new Vector<CollisionDetector>();
		executor = Executors.newFixedThreadPool(DEFAULT_MAX_COLLISIONDETECTOR_THREADS);
	}
	
	/**
	 * public CollisionManager()
	 * <p>
	 * The constructor instances the CollisionDetector Vector
	 * @param maxPoolThreadsNumber the max number of Collision Thread it can manage
	 */
	public CollisionManager(int maxPoolThreadsNumber)
	{
		CollisionsList = new Vector<CollisionDetector>();
		executor = Executors.newFixedThreadPool(maxPoolThreadsNumber);
	}
	
	/**
	 * Add a collision detector on the CollisionManager
	 * @param  cd {@link CollisionDetector}
	 */
	public void addCollision(CollisionDetector cd)
	{
		CollisionsList.add(cd);
		cd.addObserver(this);
		executor.execute(cd);
	}
	
	/**
	 * Delete all CollisionDetector from CollisionManager
	 */
	public void clearCollisions()
	{
		CollisionsList.clear();
	}
	
	/**
	 * Delete a collisionDetector from CollisionManager
	 * @param cd {@link CollisionDetector}
	 */
	public void delCollision(CollisionDetector cd)
	{
		CollisionsList.remove(cd);
		cd.deleteObserver(this);
	}
	
	/**
	 * Start to execute all the CollisionDetector registered.
	 * Use it to load a previously saved CollisionManager
	 */
	public void execute()
	{
		executor = Executors.newFixedThreadPool(10);
		
		int max= CollisionsList.size();
		for (int i=0; i<max;i++)
		executor.execute(CollisionsList.get(i));
	}
	

	/**
	 * Stop the CollisionManager threads
	 */
	public void pause()
	{
	
		int max= CollisionsList.size();
		for (int i=0; i<max;i++)
			//CollisionsList.get(i).checkCollision();
			CollisionsList.get(i).pause();
	}

	/**
	 * Resume the CollisionManager
	 */
	public void resume()
	{
		int max= CollisionsList.size();
		for (int i=0; i<max;i++)
			//CollisionsList.get(i).checkCollision();
			CollisionsList.get(i).resume();
	}

	/**
	 * Shutdown all the CollisionDetector thread
	 */
	public void shutdown()
	{
		clearCollisions();
		executor.shutdown();
	}

	/**
	 * Start the COllisionManager's Thread
	 */
	public void start()
	{
		try
		{
			int max= CollisionsList.size();
			for (int i=0; i<max;i++)
				//CollisionsList.get(i).checkCollision();
				CollisionsList.get(i).run();
				//Thread.sleep(5);
		}
		catch (Exception e)
	    { 
			//System.out.println("Collision Manager error: ");
			e.printStackTrace();
	    }
	}

	/**
	 * CollisionManager is observable and (at the same time) observe all the CollisionDetector
	 * registered on it.
	 * When a CollisionDetector execute a CollisionAction send to all his observer a message
	 * contains the CollisionDetector class name. This message is passed at all CollisionManager 
	 * Observers by this method.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
	
		this.setChanged();
		this.notifyObservers(arg1);
		
	}
	
}
