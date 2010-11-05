/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 
* Titolo: CollisionDetector.java
* Versione: 0.5 Rev.:
*/

package com.tabuto.j2dgf.collision;

import com.tabuto.j2dgf.Sprite;
import com.tabuto.j2dgf.SpriteGroup;

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
 * a {@link #CollisionAction(Sprite, Sprite)}method for the collided sprites
 * 
 * @author tabuto83
 * 
 * @version 0.4.0
 * 
 * 
 * @see CollisionManager
 * @see CollisionBoundDetector
 */

public abstract class CollisionDetector {

	/** Boolean variable. Is true if the collisionDetector checks two {@linkplain SpriteGroup}*/
	protected boolean twoGroups;
	/**SpriteGroups */
	protected SpriteGroup group1;
	protected SpriteGroup group2;
	
	/**Represent the DISTANCE when the CollisionDetector calls {@link #CollisionAction(Sprite, Sprite)} */
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
	 * @param  pg1 {@link SpriteGroup}
	 */
	
	public CollisionDetector(SpriteGroup pg1)
	{
		group1 = pg1;
		twoGroups = false;
		DISTANCE = 0;
	}
	
	/**
	 * CONSTRUCTOR
	 * <p>
	 * The Constructor of CollisionDetector for collision between elements of two groups.   
	 * @param  pg1  {@link SpriteGroup}
	 * @param  pg2  {@link SpriteGroup}
	 */
	public CollisionDetector (SpriteGroup pg1, SpriteGroup pg2)
	{
		group1 = pg1;
		group2 = pg2;
		twoGroups = true;
		DISTANCE = 0;
		
	}
	
	/**
	 * Called when a collision is detected. Calls the routines to do when two Sprite of their own groups collides.
	 * <p>
	 * @param hashSprite1 <code>int</code> Hash code of first collided Sprite 
	 * @param hashSprite2 <code>int</code> Hash code of second collided Sprite 
	 */
	public abstract void CollisionAction(int hashSprite1, int hashSprite2);
	
	//public abstract void CollisionAction(int HashSprite1, int HashSprite2);
	
	/**
	 * Check the sprite group in search of collisions. When a collision occour it calls a CollisionAction method.
	 */
	public void checkCollision()
	{
		int count=1;
		//Check if this class have to control one or two groups
		if (twoGroups && group1.isActive() && group2.isActive() )
		{
			for(int i=0; i< group1.getSize(); i++ )
				for (int j=0; i < group2.getSize();j++)
				  if ( group1.getSprite(i).isCollide(group2.getSprite(j),DISTANCE ) )
					  
				     {
					  CollisionAction(group1.getSprite(i).hashCode(), group2.getSprite(j).hashCode() );
					  
					  int defaultSpeed1 = group1.getSprite(i).getSpeed();
					  int defaultSpeed2 = group2.getSprite(j).getSpeed();
					  group1.getSprite(i).setSpeed(1);
					  group2.getSprite(j).setSpeed(1);
					  group1.getSprite(i).move();
					  group2.getSprite(j).move();
					  group1.getSprite(i).setSpeed(defaultSpeed1);
					  group2.getSprite(j).setSpeed(defaultSpeed2);
					  }
					
		}
		else
			if (group1.isActive())
		{   
			for (int i=0; i< group1.getSize();i++)
				for (int j=count;j<group1.getSize();j++)
				{
					if (j!=i)
					if ( 
						group1.getSprite(i).isCollide( group1.getSprite(j),DISTANCE ) 
							
					    )
						
						
					{  
						if (group1.getSprite(i).isActive() && group1.getSprite(j).isActive() )
						CollisionAction(group1.getSprite(i).hashCode(), group1.getSprite(j).hashCode() );
						int defaultSpeed1 = group1.getSprite(i).getSpeed();
						int defaultSpeed2 = group1.getSprite(j).getSpeed();
						
						group1.getSprite(i).setSpeed(1);
						group1.getSprite(j).setSpeed(1);
						group1.getSprite(i).move();
						group1.getSprite(j).move();
						group1.getSprite(i).setSpeed(defaultSpeed1);
						group1.getSprite(j).setSpeed(defaultSpeed2);
						count++;
					}
				}
		}
	}
	

	
}



