/**
* @author Francesco di Dio
* Date: 09/gen/2011 14.11.29
* Titolo: TimeCollisionDetector.java
* Versione: 0.7.3 Rev.a:
*/


/*
 * Copyright (c) 2011 Francesco di Dio.
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
  * Inserisci qui breve descrizione in italiano della classe
  */

package com.tabuto.j2dgf.collision;

import com.tabuto.j2dgf.Group;
import com.tabuto.j2dgf.Sprite;

/**
 * Abstract class extends CollisionDetectot to perform action every check time.
 * Elapsed time between collisions checked is the contructor parameter time.
 * 
 * @author tabuto83
 * @see CollisionDetector
 *
 */
public abstract class TimeCollisionDetector extends CollisionDetector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	/**
	 * time in ms using to perform the action every 'ms' time
	 */
	int elapsedTime;
	
	public TimeCollisionDetector(Group<? extends Sprite> pg1, int time) {
		super(pg1);
		elapsedTime = time;
		
	}
	
	public synchronized void checkCollision()
	{
		if(ACTIVE)
		{
			int count=1;
			//Check if this class have to control one or two groups
			
				if (group1.isActive() && group1.size()>0 && !twoGroups)
				{   
					group1.trimToSize();
					for (int i=0; i< group1.size();i++)
							try{
												setChanged();
												notifyObservers(getName());
												CollisionAction(i);
												
												int defaultSpeed1 = group1.get(i).getSpeed();
													
												 group1.get(i).setSpeed(1);
												
												 group1.get(i).move();
												
												 group1.get(i).setSpeed(defaultSpeed1);
											
												 //Thread.sleep(elapsedTime);
												count++;	
									
							}//endTRY
							
							catch (IndexOutOfBoundsException e)
							{
								continue;
							}
					}//END IF
					
			}//endIF
	}
	
	/**
	 * Called when a collision is detected. Calls the routines to do on every Sprite when time check
	 * <p>
	 * @param indexG1 <code>int</code> ID number of Sprite 
	 */
	public abstract void CollisionAction(int i);
	
	
	/**
	 * CollisionDetector Thread task. Simply {@link CollisionDetector#checkCollision()}
	 * when it is ACTIVE
	 */
	@Override
	public void run() {
		while(ACTIVE)
		{
			try
			{
				checkCollision();
				Thread.sleep(elapsedTime);
			}
			catch (Exception e)
		      { 
				System.out.println("Collision Detector thread error: ");
				e.printStackTrace();
		      }
		}
		
	}

}
