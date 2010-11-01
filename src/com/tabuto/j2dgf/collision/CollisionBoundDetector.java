package com.tabuto.j2dgf.collision;

import java.awt.Dimension;

import com.tabuto.j2dgf.Sprite;
import com.tabuto.j2dgf.SpriteGroup;

/**
* @author Francesco di Dio
* Date: 11 Ottobre 2010 
* Titolo: CollisionBoundDetector.java
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
 * Questa classe estende COllisioDetector e gestisce le collisioni con i bordi del playfield
 * 
 */

/**
 * class <code>CollisionBoundDetector</code> extends CollisionDetector
 * <p>
 * This Class extends CollisionDetector to implements a Collision Boundary Detector.<br>
 * Example of Use:
 * <pre>
 * CollisionBoundDetector CBD = new CollisionBoundDetector(playersGroup, PlayFieldDimension);
 * CBD.useReflection(); //Simulate an elastic collision with the boundary;
 * 
 * CollisionManager CM = new CollisionManager();
 * CM.add(CBD);
 * 
 * CollisionManager.runCollisionManager();
 * </pre>
 * 
 * @author tabuto83
 * 
 * @version 0.1.0
 * 
 * @see com.tabuto.j2dgame.collision.CollisionManager
 * @see com.tabuto.j2dgame.collision.CollisionDetector
 * 
 */
public class CollisionBoundDetector extends CollisionDetector {

	/**
	 * Dimension of the playfield
	 */
Dimension dim = new Dimension();

private double w; //Weight
private double h; //heigth
	/**
	 * Flag for Use a Reflection collision with the boundary
	 */
boolean Reflection=false;

 	/**
 	 * Flag for Use a PassThrough with the boundary
 	 */
boolean Through=false;

	/**
	 * Flag for use a Bounch collision with the boundary
	 */
boolean Bounce = false;
	/**
	 * CONSTRUCTOR
	 * <p>
	 * CollisionBoundDetector constructor
	 * @param  sp1 {@link SpriteGroup}
	 * @param  d {@link Dimension}
	 */
	public CollisionBoundDetector(SpriteGroup sp1, Dimension d)
	{
		super(sp1);
		this.w = d.getWidth();
		this.h = d.getHeight();
		
	}
	
	/** 
	 * Check the position of each Sprite in the SpriteGroup registered in 
	 * this CollisionDetector, if a Sprite collide with the boundary, 
	 * the method calls a CollisionAction.
	 * @see com.tabuto.j2dgame.Sprite
	 * @see com.tabuto.j2dgame.collision.CollisionDetector
	 * @see com.tabuto.j2dgame.collision.CollisionDetector#CollisionAction(Sprite, Sprite)
	 */
	public void checkCollision()
	{
		
		for (int i=0; i< group1.getSize();i++)
			{
			if (group1.getSprite(i).isActive())
			 if (  group1.getSprite(i).getX() - group1.getSprite(i).getWest() < 0 || 
					 group1.getSprite(i).getX() + group1.getSprite(i).getEast() > this.w ||
					   group1.getSprite(i).getY() - group1.getSprite(i).getNorth() < 0 ||
					     group1.getSprite(i).getY() + group1.getSprite(i).getSouth() > this.h
				)
				 {
				 this.CollisionAction(group1.group.get(i) );
				 group1.getSprite(i).move();
				 }
			}
	}
	
	/** 
	 * Calls the right method to change Sprite direction or
	 * position with {@link #Reflection(Sprite)}or {@link #Through(Sprite)} methods
	 * @param  s {@link Sprite}
	 */
	public void CollisionAction(Sprite s)
	{
		if (Reflection)
			Reflection(s);
		else if (Through)
			Through(s);
		else if (Bounce)
			Bounce(s);
	}
	
	public void CollisionAction(int hash1,int hash2){};
	
	/**
	 * Change the Sprite angle to simulate an elastic collision
	 * with the boundary
	 * @param s {@link Sprite}
	 */
	protected void Reflection(Sprite s)
	
	{

  	  int wall=0;
  	  
  	    if (s.getX() - s.getWest() < 0  ) wall=3;
	    if (s.getX() + s.getEast() > this.w) wall=2;
	    if (s.getY() - s.getNorth() < 0 ) wall=1;
	    if (s.getY() + s.getSouth() > this.h) wall=0;
  	  
  	  switch (wall)
	    {   
	      case 0:
	      case 1: s.setAngleRadians( 2.0*Math.PI - s.getAngle() ); break;
	      case 2: s.setAngleRadians( Math.PI + s.getAngle() ); break;
	      case 3:  s.setAngleRadians( Math.PI - s.getAngle() ); break;
	    }
	}
	
	/**
	 * Change the Sprite position to simulate a pass through the boundary
	 * @param  s {@link Sprite}
	 */
	protected void Through(Sprite s)
	{  
	
	if ( s.getX() < 0  ) s.setX(this.w - s.getWest());
	if ( s.getX()  > this.w  ) s.setX( 0 + s.getEast());
	if ( s.getY() < 0  ) s.setY(this.h - s.getSouth() );
	if ( s.getY()  > this.h  ) s.setY( 0 + s.getNorth() );
	  
	}
	
	//TODO: A collision that simulate a bounce
	public void Bounce(Sprite s)
	{

		  	  
	}
	
	/**
	 * Set true the flag {@link #Reflection} in order to use the method {@link #Reflection(Sprite)}
	 */
	public void useReflection()
	{
		Reflection = true;
		Through = false;
	}
	
	/**
	 * Set true the flag {@link #Through} in order to use the method {@link #Through(Sprite)}
	 */
	public void usePassThrough()
	{
		Reflection = false;
		Through = true;
	}
	
	/**
	 * Set true the flag {@link CollisionBoundDetector#Bounce in order to use the method {@link #Bounce(Sprite) }
	 */
	public void useBounce()
	{
		Reflection = false;
		Through = false;
		Bounce = true;
	}
	
}

