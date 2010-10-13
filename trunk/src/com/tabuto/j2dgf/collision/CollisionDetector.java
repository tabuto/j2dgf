package com.tabuto.j2dgf.collision;


import com.tabuto.j2dgf.Sprite;
import com.tabuto.j2dgf.SpriteGroup;

/**
* @author Francesco di Dio
* Date: 11 Ottobre 2010 
* Titolo: CollisionDetector.java
* Versione: 0.1 Rev.:
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
 * @version 0.1.0
 * 
 * 
 * @see com.tabuto.j2dgame.collision.CollisionManager
 * @see com.tabuto.j2dgame.collision.CollisionBoundDetector
 */

public abstract class CollisionDetector {

	/** Boolean variable. Is true if the collisionDetector checks two {@linkplain com.tabuto.j2dgame.SpriteGroup}*/
	protected boolean twoGroups;
	/**SpriteGroups */
	protected SpriteGroup group1;
	protected SpriteGroup group2;
	
	/**Represent the DISTANCE when the CollisionDetector calls {@link #CollisionAction(Sprite, Sprite)} */
	protected double DISTANCE;
	
	/** Set the DISTANCE variable @see {@link CollisionDetector#DISTANCE} */
	public void setDistance(double d){DISTANCE = d;}
	
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
		
	}
	
	/**
	 * Called when a collision is detected.
	 * <p>
	 * @param  s1 {@link Sprite}
	 * @param  s2 {@link Sprite}
	 */
	
	public void CollisionAction(Sprite s1, Sprite s2)
	{
		
		
	}
	
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
				  if ( getDistance( group1.getSprite(i) , group2.getSprite(j) ) <= (group1.getSprite(i).r + group1.getSprite(j).r + DISTANCE) )
					  {
					  CollisionAction(group1.getSprite(i), group2.getSprite(j) );
					  group1.getSprite(i).move();
					  group2.getSprite(j).move();
					  }
					
		}
		else
			if (group1.isActive())
		{   
			for (int i=0; i< group1.getSize();i++)
				for (int j=count;j<group1.getSize();j++)
				{
					if (j!=i)
					if ( getDistance( group1.getSprite(i) , group1.getSprite(j) ) <= (group1.getSprite(i).r + group1.getSprite(j).r + DISTANCE) )
					{  
						if (group1.getSprite(i).isActive() && group1.getSprite(j).isActive() )
						CollisionAction(group1.getSprite(i), group1.getSprite(j) );
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
	
	/**
	 * Return the distance between two sprite
	 * @param  p1 {@link Sprite}
	 * @param  p2 {@link Sprite}
	 * @return distance int 
	 */
	public double getDistance( Sprite p1, Sprite p2 )
	
	{
		   double temp1 =  (  p1.xc - p2.xc ) *  ( p1.xc - p2.xc );
	       double temp2 =  (  p1.yc - p2.yc ) *  (  p1.yc - p2.yc );
		   double distance = Math.sqrt( temp1 + temp2 );
		   return distance;
	}
	
}



