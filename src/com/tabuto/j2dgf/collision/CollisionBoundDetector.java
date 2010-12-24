/**
* @author Francesco di Dio
* Date: 29 Novembre 2010 
* Titolo: CollisionBoundDetector.java
* Versione: 0.7.2 Rev.:
*/

package com.tabuto.j2dgf.collision;

import java.awt.Dimension;
import java.io.Serializable;

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
 * </pre>
 * 
 * @author tabuto83
 * 
 * @version 0.7.0
 * 
 * @see CollisionManager
 * @see CollisionDetector
 * 
 */
public class CollisionBoundDetector extends CollisionDetector implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6755271373855764312L;
	/**
	 * Dimension of the playfield
	 */
private Dimension dim = new Dimension();

	/**
	 * Edges type
	 */
private enum Edges  {SOUTH, NORTH, EAST, WEST,NULL};

/**
 * An <code>enum</code> for Use a Collision Bound routines 
 */
private enum CollisionsBoundType {NULL, REFLECTION, THROUGH, BOUNCE};

/**
 * The actual CollisionBoundType
 */
private CollisionsBoundType collisionType = CollisionsBoundType.NULL;
	

	/**
	 * CONSTRUCTOR
	 * <p>
	 * CollisionBoundDetector constructor
	 * @param  sp1 {@link Group}
	 * @param  d {@link Dimension}
	 */
	public CollisionBoundDetector(Group<? extends Sprite> sp1, Dimension d)
	{
		super(sp1);
		this.dim=d;
	}
	
	/** 
	 * Check the position of each Sprite in the SpriteGroup registered in 
	 * this CollisionDetector, if a Sprite collide with the boundary, 
	 * the method calls a CollisionAction.
	 * @see Sprite
	 * @see CollisionDetector
	 */
	public void checkCollision() 
	{
		
		for (int i=0; i< group1.size();i++)
			{
			try{
				if (group1.get(i).isActive())
					 if (  group1.get(i).getX() - group1.get(i).getWest() < 0 || 
							 group1.get(i).getX() + group1.get(i).getEast() > this.dim.width ||
							   group1.get(i).getY() - group1.get(i).getNorth() < 0 ||
							     group1.get(i).getY() + group1.get(i).getSouth() > this.dim.height
				         )
				 {
						 this.CollisionAction(group1.get(i) );
						 group1.get(i).move();
				 }
				}
		
	catch (IndexOutOfBoundsException e)
		{
			continue;
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
		
		switch(collisionType)
		{
		case REFLECTION:  Reflection(s);
		case THROUGH: Through(s);
		case BOUNCE: Bounce(s);
		}
		
	}
	
	//@SuppressWarnings("unchecked")
	public void CollisionAction(Group g1,int i,int j){};
	
	public void CollisionAction(int hash1,int hash2){};
	
	/**
	 * Change the Sprite angle to simulate an elastic collision
	 * with the boundary
	 * @param s {@link Sprite}
	 */
	protected void Reflection(Sprite s)
	
	{

  	    Edges edge=Edges.NULL;
  	    if (s.getX() - s.getWest() < 0  ) edge=Edges.WEST; //wall=3;
	    if (s.getX() + s.getEast() > this.dim.width) edge = Edges.EAST;
	    if (s.getY() - s.getNorth() < 0 ) edge = Edges.NORTH;
	    if (s.getY() + s.getSouth() > this.dim.height) edge = Edges.SOUTH;
  	  
  	  switch (edge)
	    {   
	      case SOUTH:
	      
	      case NORTH: s.setAngleRadians( 2*Math.PI - s.getAngle()); break;
		 				
	      case EAST: 
				      { 
		  	        if(s.getAngle()>Math.PI*1.5 && s.getAngle()<Math.PI*2)
		  	        		s.setAngleRadians(3*Math.PI - s.getAngle());
		  	        else
		  	        		s.setAngleRadians( Math.PI - s.getAngle()); 
		  	        
		  	        break;
			    	  }
	    	  
	      case WEST: { 
	    	        if(s.getAngle()>Math.PI && s.getAngle()<Math.PI*1.5)
	    	        		s.setAngleRadians(3*Math.PI - s.getAngle());
	    	        else
	    	        		s.setAngleRadians( Math.PI - s.getAngle()); 
	    	        
	    	        break;
	      			  }
	    }
	}
	
	/**
	 * Change the Sprite position to simulate a pass through the boundary
	 * @param  s {@link Sprite}
	 */
	protected void Through(Sprite s)
	{  
		//TODO Fix This Method
	if ( s.getX() < 0  ) s.setX(this.dim.width - s.getWest());
	if ( s.getX()  > this.dim.width  ) s.setX( 0 + s.getEast());
	if ( s.getY() < 0  ) s.setY(this.dim.height - s.getSouth() );
	if ( s.getY()  > this.dim.height  ) s.setY( 0 + s.getNorth() );
	  
	}

	/**
	 * NOT YET IMPLEMENTED
	 */
	public void Bounce(Sprite s)
	{
		//TODO: Implements Bounce Collision
	}
	/**
	 * Set true the flag {@link #Reflection} in order to use the method {@link #Reflection(Sprite)}
	 */
	public void useReflection()
	{
		collisionType = CollisionsBoundType.REFLECTION;
	}
	
	/**
	 * Set true the flag {@link #Through} in order to use the method {@link #Through(Sprite)}
	 */
	public void usePassThrough()
	{
		collisionType = CollisionsBoundType.THROUGH;
	}
	
	/**
	 * Set true the flag {@link CollisionBoundDetector#Bounce} in order to use the method {@link #Bounce(Sprite) }
	 */
	public void useBounce()
	{
		collisionType = CollisionsBoundType.BOUNCE;
	}
	
}

