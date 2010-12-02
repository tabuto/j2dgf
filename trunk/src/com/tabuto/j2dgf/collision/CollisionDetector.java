/**
* @author Francesco di Dio
* Date: 29 Novembre 2010 
* Titolo: CollisionDetector.java
* Versione: 0.7.0 Rev.:
*/

package com.tabuto.j2dgf.collision;

import java.io.Serializable;
import java.util.Observable;

import com.tabuto.j2dgf.Group;
import com.tabuto.j2dgf.Sprite;

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
 * Questa classe astratta non fa altro che gestire uno o due SpriteGroup, possiede un metodo che
 * controlla le posizione reciproche di tutti gli sprite, e in caso di collisione 
 * richiama il metodo CollisionAction che viene sovrascritto per effettuare delle operazioni specifiche;
 * 
 */

/**
 * abstract class <code>CollisionDetector</code>
 * <p>
 * The CollisionDetector class provide to check the position of each sprite 
 * registered in one or two SpriteGroup.
 * <p>
 *  When a collision is checked, the class calls
 * a {@link #CollisionAction(int, int)}method for the collided sprites
 * This class extends Observable class 'couse let its observer to know when a collision occurs
 * <p>
 * To use this Class simply extends it and override the 
 * {@link CollisionDetector#CollisionAction(int, int)}. After that register this class on
 * the CollisionManager present in the Game2D.
 * 
 * @author tabuto83
 * 
 * @version 0.7.0
 * 
 * @see CollisionManager
 * @see CollisionBoundDetector
 * @see Game2D
 */

//@SuppressWarnings("unchecked")
public class CollisionDetector extends Observable implements Serializable, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2577969994903303062L;

	/** Boolean variable. Is true if the collisionDetector checks two {@link Group}*/
	protected boolean twoGroups;
	/**SpriteGroups we want to check */
	protected Group<? extends Sprite> group1 = new Group();
	/**SpriteGroups we want to check*/
	protected Group<? extends Sprite> group2 = new Group();
	
	/**Represent the DISTANCE when the CollisionDetector calls {@link #CollisionAction(int, int)} */
	protected double DISTANCE;
	
	/**
	 * The current State of this CollisionDetector, if true the CollisionDetector scan the SpriteGroup
	 * in search of Collision, if false, does it not!
	 */
	protected boolean ACTIVE;
	
	private transient Thread t;
	
	/**
	 * A random sleep time to let other collisionDetectors threads to execute!
	 */
	protected int RandomSleep;
	
	/**
	 * Collision String Name is used by {@link CollisionDetector#setName()} methods to
	 * store the Class Name that extends a CollisionDetector. 
	 * So, if you write a MonsterVsWall class that extends CollisionDetector, 
	 * the setName methods set Name as "MonsterVsWall". 
	 * This name is passed to the Observers of this class when a collision occurs.
	 * See the Java Observer and Observable Class.
	 * 
	 * @see Observable
	 */
	protected String Name;
	/**
	 * CONSTRUCTOR
	 * <p>
	 * CollisionDetector(SpriteGroup pg1)
	 * <p>
	 * The Constructor of CollisionDetector for collision between the same group elements.  
	 * @param  pg1 {@link Group}
	 */
	
	
	public CollisionDetector(Group<? extends Sprite> pg1)
	{
		group1 = pg1;
		
		twoGroups = false;
		DISTANCE = 0;
		ACTIVE=true;
		setName();
		t = new Thread(this); 
		RandomSleep = (int) (Math.random()*15);
	}
	
	/**
	 * CONSTRUCTOR
	 * <p>
	 * The Constructor of CollisionDetector for collision between elements of two groups.   
	 * @param  pg1  {@link Group}
	 * @param  pg2  {@link Group}
	 */
	public CollisionDetector (Group<? extends Sprite> pg1, Group<? extends Sprite> pg2)
	{
		group1 = pg1;
		group2 = pg2;
		twoGroups = true;
		DISTANCE = 0;
		ACTIVE=true;
		setName();
		t = new Thread(this); 
		RandomSleep = (int) (Math.random()*15);
	}	
	
	/**
	 * Active the CollisionDetector
	 */
	public void active(){ACTIVE=true;}
	
	/**
	 * Check the sprite group in search of collisions. 
	 * When a collision occurs it calls a CollisionAction method.
	 */
	public synchronized void checkCollision()
	{
		if(ACTIVE)
		{
			int count=1;
			//Check if this class have to control one or two groups
			if (twoGroups && group1.isActive() && group2.isActive() && group1.size()>0 && group2.size()>0 )
				{
					group1.trimToSize();
					group2.trimToSize();
					for(int i=0; i< group1.size(); i++ )
						for (int j=0; j < group2.size();j++)
							try{
								if ( group1.get(i).isCollide(group2.get(j),DISTANCE ) )
								{
								 setChanged();
								 notifyObservers(getName());
								 CollisionAction( i,j);
						  
								  int defaultSpeed1 = group1.get(i).getSpeed();
								  int defaultSpeed2 = group2.get(j).getSpeed();
								  group1.get(i).setSpeed(1);
								  group2.get(j).setSpeed(1);
								  group1.get(i).move();
								 
								  group2.get(j).move();
								  group1.get(i).setSpeed(defaultSpeed1);
								  group2.get(j).setSpeed(defaultSpeed2);
								  Thread.sleep(5);
								}//ENDIF
							}//try
			
						  catch (InterruptedException e) 
							  {
									//e.printStackTrace();
							  		continue;
							  }
						  catch (IndexOutOfBoundsException e)
							  {
								continue;
							  }	 
				} //ENDIF
			else
				if (group1.isActive() && group1.size()>0 && !twoGroups)
				{   
					group1.trimToSize();
					for (int i=0; i< group1.size();i++)
						for (int j=count;j<group1.size();j++)
							try{
									if (j!=i)
										if ( group1.get(i).isCollide( group1.get(j),DISTANCE ) ) 
											if (group1.get(i).isActive() && group1.get(j).isActive() )
											{
												setChanged();
												notifyObservers(getName());
												CollisionAction(i, j );
												
												int defaultSpeed1 = group1.get(i).getSpeed();
												int defaultSpeed2 = group1.get(j).getSpeed();
													
												 group1.get(i).setSpeed(1);
												 group1.get(j).setSpeed(1);
												 group1.get(i).move();
												 group1.get(j).move();
												 group1.get(i).setSpeed(defaultSpeed1);
												 group1.get(j).setSpeed(defaultSpeed2);
												 Thread.sleep(5);
												count++;	
											  }
							}//endTRY
							catch (InterruptedException e) 
							{
								//e.printStackTrace();
						  		continue;
							}
							catch (IndexOutOfBoundsException e)
							{
								continue;
							}
					}//END IF
					
			}//endIF
		}//endMethod
		
	
	
	/**
	 * Called when a collision is detected. Calls the routines to do when two Sprite of their own groups collides.
	 * <p>
	 * @param indexG1 <code>int</code> Hash code of first collided Sprite 
	 * @param indexG2 <code>int</code> Hash code of second collided Sprite 
	 */
	public void CollisionAction( int indexG1, int indexG2){};
	
	
	/**
	 * Deactive the CollisionDetector
	 */
	public void deactive(){ACTIVE=false;}

	/**Get the DISTANCE variable @see {@link CollisionDetector#DISTANCE} */
	public double getDistance(){return DISTANCE;}
	
	/**
	 * @return The Class name
	 * 
	 * @see CollisionDetector#Name
	 * @see CollisionDetector#setName
	 */
	public String getName()
	{
		return Name;
	}
	
	/**
	 * @return true if this CollisionDetector is Active
	 */
	public boolean isActive(){return ACTIVE;}

	/**
	 * Set the ACTIVE state of this class as {@code false}
	 */
	public void pause()
	{
		ACTIVE=false;
	}
	
	/**
	 * Set the ACTIVE state of this class as {@code true}
	 */
	public void resume()
	{
		ACTIVE=true;
	}
	
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
				Thread.sleep(RandomSleep);
			}
			catch (Exception e)
		      { 
				System.out.println("Collision Manager thread error: ");
				e.printStackTrace();
		      }
		}
		
	}
	
	/** Set the DISTANCE variable 
	 * @see CollisionDetector#DISTANCE
	 */
	public void setDistance(double d){if(DISTANCE>=0)DISTANCE = d;}
	
	/**
	 * Collision String Name is used by {@link CollisionDetector#setName()} methods to
	 * store the Class Name that extends a CollisionDetector. 
	 * So, if you write a MonsterVsWall class that extends CollisionDetector, 
	 * the setName methods set Name as "MonsterVsWall". 
	 * This name is passed to the Observers of this class when a collision occurs.
	 * See the Java Observer and Observable Class.
	 */
	public void setName()
	{
		String t = this.getClass().getCanonicalName();
		Name=t.substring( t.lastIndexOf('.')+1 );
	}

	/**
	 * Start the CollisionDetector Thread
	 */
	public void start()
	{
		t.start();
	}
	


	
}



