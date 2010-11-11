/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 
* Titolo: CollisionDetector.java
* Versione: 0.6 Rev.:
*/

package com.tabuto.j2dgf.collision;

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
 * 
 * @author tabuto83
 * 
 * @version 0.6.0
 * 
 * 
 * @see CollisionManager
 * @see CollisionBoundDetector
 */

public class CollisionDetector  {

	/** Boolean variable. Is true if the collisionDetector checks two {@link Group}*/
	protected boolean twoGroups;
	/**SpriteGroups */
	protected Group<? extends Sprite> group1 = new Group();
	protected Group<? extends Sprite> group2 = new Group();
	
	/**Represent the DISTANCE when the CollisionDetector calls {@link #CollisionAction(int, int)} */
	protected double DISTANCE;
	
	/** Set the DISTANCE variable @see {@link CollisionDetector#DISTANCE} */
	public void setDistance(double d){if(DISTANCE>=0)DISTANCE = d;}
	
	/**Get the DISTANCE variable @see {@link CollisionDetector#DISTANCE} */
	public double getDistance(){return DISTANCE;}
	
	
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
		
	}
	
	
	/**
	 * Called when a collision is detected. Calls the routines to do when two Sprite of their own groups collides.
	 * <p>
	 * @param indexG1 <code>int</code> Hash code of first collided Sprite 
	 * @param indexG2 <code>int</code> Hash code of second collided Sprite 
	 */
	public void CollisionAction( int indexG1, int indexG2){};
	
	//public  void CollisionAction(Group<?> G1, int indexG1, 
			                   // Group<?> G2, int indexG2){}
	
	
	/**
	 * Check the sprite group in search of collisions. When a collision occurs it calls a CollisionAction method.
	 */
	public void checkCollision()
	{
		int count=1;
		//Check if this class have to control one or two groups
		if (twoGroups && group1.isActive() && group2.isActive() )
		{
			for(int i=0; i< group1.size(); i++ )
				for (int j=0; j < group2.size();j++)
				  if ( group1.get(i).isCollide(group2.get(j),DISTANCE ) )
					  
				     {
					  CollisionAction( i,j);
					  
					  int defaultSpeed1 = group1.get(i).getSpeed();
					  int defaultSpeed2 = group2.get(j).getSpeed();
					  group1.get(i).setSpeed(1);
					  group2.get(j).setSpeed(1);
					  group1.get(i).move();
					  group2.get(j).move();
					  group1.get(i).setSpeed(defaultSpeed1);
					  group2.get(j).setSpeed(defaultSpeed2);
					  }
					
		}
		else
			if (group1.isActive())
		{   
			for (int i=0; i< group1.size();i++)
				for (int j=count;j<group1.size();j++)
				{
					if (j!=i)
					if ( 
						group1.get(i).isCollide( group1.get(j),DISTANCE ) 
							
					    )
						
						
					{  
						if (group1.get(i).isActive() && group1.get(j).isActive() )
						CollisionAction(i, j );
						int defaultSpeed1 = group1.get(i).getSpeed();
						int defaultSpeed2 = group1.get(j).getSpeed();
						
						group1.get(i).setSpeed(1);
						group1.get(j).setSpeed(1);
						group1.get(i).move();
						group1.get(j).move();
						group1.get(i).setSpeed(defaultSpeed1);
						group1.get(j).setSpeed(defaultSpeed2);
						count++;
					}
				}
		}
	}
	

	
}


