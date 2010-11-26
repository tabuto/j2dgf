/**
* @author Francesco di Dio
* Date: 19 Novembre 2010
* Titolo: CollisionManager.java
* Versione: 0.6.5 Rev.:
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

/**
 * 
 * class <code>CollisionManager</code>
 * <p>
 * This class manage a vector of CollisionDetector. <p>
 * Add Every CollisionDetector to this class, and the method {@link #RunCollisionManager()} checks every  {@linkplain CollisionDetector}
 * using the method {@linkplain CollisionDetector#checkCollision()}
 * This class extends Observable and implements Observer 'couse it is observing the Collision Detector and
 * allows to observe when a collision occours by throws the message "CollisionDetector.ClassName" 
 * using the {@link Observable#notifyObservers(Object)}
 * 
 *
 * @author tabuto83
 * 
 * @version 0.6.5
 * 
 * @see Vector
 */

public class CollisionManager extends Observable implements Serializable, Runnable, Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6235330134986494263L;
	/**
	 * The collisionDetector Vector
	 */
	private Vector<CollisionDetector> CollisionsList; 
	
	/**
	 * The Collision Manager Thread
	 */
	private Thread t;
	
	/**
	 * Cuurrent CollisionManager's state
	 */
	boolean running = true;
	
	/**
	 * CONSTRUCTOR 
	 * <p>
	 * public CollisionManager()
	 * <p>
	 * The constructor instances the CollisionDetector Vector
	 */
	public CollisionManager()
	{
		CollisionsList = new Vector<CollisionDetector>();
		
		t = new Thread(this); 
	}
	
	
	/**
	 * Add a collision detector on the CollisionManager
	 * @param  cd {@link CollisionDetector}
	 */
	public void addCollision(CollisionDetector cd)
	{
		CollisionsList.add(cd);
		cd.addObserver(this);
	}
	
	/**
	 * Delete all CollisionDetector from CollisionManager
	 */
	public void clearCollision()
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
 * @return the Running CollisionManager state
 */
public boolean isRunning(){return running;}
	
	/**
	 * Calls every {@link com.tabuto.j2dgf.Sprite} in all CollisionDetector.
	 * registered in this <code>CollisionManager</code>.
	 */
public void run() 
{
		
	while(running)
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
				System.out.println("Collision Manager thread error: ");
				e.printStackTrace();
		    }
	}
}

/**
 * Start the COllisionManager's Thread
 */
public void start()
{
	t.start();
}

/**
 * Stop the CollisionManager
 */
public void stop()
{
	running=false;
}

	@Override
	public void update(Observable arg0, Object arg1) {
		
	
		this.setChanged();
		this.notifyObservers(arg1);
		
	}
	
}
