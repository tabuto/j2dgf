/**
* @author Francesco di Dio
* Date: 09 Novembre 2010 18.14
* Titolo: Sprite.java
* Versione: 0.6.1 Rev.9:
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
 * Classe astratta che rappresenta un generico sprite. Nella nuova versione,
 * posizione,velocità=modulo e direzione sono espressi usando l'oggetto vettore.
 */


package com.tabuto.j2dgf;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;

import com.tabuto.util.Point;
import com.tabuto.util.Vettore;



/**
 * Sprite is an Abstract Class that represents an object able draws himself and move on
 * a playfield of fixed dimension.
 * <p>
 * It has got an ACTIVE flag and a {@linkplain #ThisIsMe(Graphics)} method called by {@linkplain #drawMe(Graphics)} 
 * method when ACTIVE is true; <br>
 * Every Sprite has a position defined by two variables (xc and yc) that identifies the sprite's center;
 * Every sprite also has speed and angle variable.<br>
 * To instance a new Sprite you have to call the constructor method {@linkplain #Sprite(Dimension,double, double)}
 * to set the dimension plane where sprite live and his start position.<br>
 * Every sprite can be part of {@link Group} <br>
 * 
 * @author tabuto83
 * 
 * @version 0.6.1
 * 
 * @see Group
 * 
 */

public abstract class Sprite implements Drawable, Serializable {

	/**
	 * {@link Vettore} vector represent position, direction and speed of each sprite
	 */
	protected Vettore vector;
	
	
	/**
	 * Sprite height
	 */
	public int h; 
	
	/**
	 * Sprite width
	 */
	public int w; 
	
	/**
	 * Actual Sprite state: If {@code true} sprite draws himself, move and live;
	 */
	protected boolean ACTIVE;
	
	
	/**
	 * The integer variable ID identifies every sprite
	 */
    private int id; 
    
    
	protected boolean collided; 
	
	/**
	 * Indicate the pixel above, under, on left and right of Sprite's center.
	 * Use it for precision collision.
	 */
	protected int NORTH, SOUTH, EAST, WEST;
	
	/**
	 * The Dimension of the playfield where sprites live is. 
	 * Example:<br>
	 * <pre>
	 * Dimension playfield = new Dimension(1024,768);
	 * playfiled.width return width (1024);
	 * playfield.height return heigth (768);
	 * </pre>
	 * @see Dimension
	 */
	protected Dimension d;  
	
	/**
	 *  The speed of the sprite 
	 *  @see Dimension
	 */
    protected int speed; 

    /**
     * The default Speed of the sprite
     */
    public int DefaultSpeed = 50; 

    /**
     * Max radius of Sprite for increase the speed of CheckCollision method;
     * If the distance of two sprite is greater of this value, CheckCollision dosn't
     * check other value.
     */
    protected int CollidedRadius;
    
    /**
     * 
     * 	Creates new <code>Sprite</code> in the playfiled of Dimension dim, with specific coordinates.
     *  @param dim {@linkplain Dimension} the Playfields dimension where Sprite lives in
     *  @param posX double the x Sprite coordinate
     *  @param posY double the y Sprite coordinate
     *  
     */
	  public Sprite(Dimension dim, double posX, double posY)
	  {
		  d=dim;
		  ACTIVE=true;
		  if (posX < 0 || posY<0) {
			  throw new IllegalArgumentException();
			    }

		  vector = new Vettore(posX, posY);
		  vector.setNewModule(this.speed);
		  id= this.hashCode();
		  NORTH=0;SOUTH=0;EAST=0; WEST=0;
	  }
	  
	  
	  /**
	   * Set true the flag ACTIVE
	   */
	  public void Activate(){ACTIVE=true;}
	  
	  /**
	   * Set The Sprite inactive
	   */
	  public void Deactivate(){ACTIVE=false;}
	  
	  /**
	   * This method draw the sprite if it is ACTIVE
	   * @param g the Sprite Graphics context
	   */
	  public void drawMe(Graphics g)
	  {
		if(ACTIVE)
			ThisIsMe(g);	
	  }
	  
	  /**
	   * Return the sprite's Angle direction in radians
	   * @return Angle an angle express in Radians
	   */
	  public double getAngle(){return vector.getDirectionRadians();}
	  
	  /**
	   * Return the pixel's number on the right of the Sprite's center; 
	   * @return {@link Sprite#EAST}
	   */
	public int getEast(){return EAST;}  
	  
	  /**
	   * Return the sprite's number id
	   * @return {@link Sprite#id} 
	   */
	 public int getId()  {  return id;  }
	 
     /**
      * Returns the speed as a double value between  0 and 2.100
      */
     protected double getMySpeed()
     {
   	  double speedy = this.speed * 0.021;
   	  return speedy;
     }

	 /**
	  * Return the pixel's number above the Sprite center; 
	  * @return  {@link Sprite#NORTH}
	  */
	public int getNorth(){return NORTH;}

	/**
	 * Return the Sprite Poisition <code>Point</code>
	 * @return the actual center position <code>Point</code>
	 */
	public Point getPosition(){return vector.origin;}
	 
	 /**
	  * Return the pixel's number under the Sprite's center; 
	  * @return  {@link Sprite#SOUTH}
      */
	public int getSouth(){return SOUTH;}

	  /**
	   * Return the sprite's speed
	   * @return {@link Sprite#speed}
	   */
	public int getSpeed(){return this.speed;}

	  /**
	   * Return the pixel's number on the right of the Sprite's center; 
	   * @return  {@link Sprite#WEST}
	   */
    public int getWest(){return WEST;}  
	
	  /**
	   * Return the sprite's center x coordinate
	   * @return the actual Sprite Center x coordinates
	   */
	public double getX(){return vector.origin.x;}
	  
	  /**
	   * Return the sprite's center y coordinate
	   * @return @return the actual Sprite Center y coordinates
	   */
	public double getY(){return vector.origin.y;}

	  /**
	   * Return the sprite's Active state
	   * @return boolean {@link Sprite#ACTIVE}
	   */
	public boolean isActive(){return ACTIVE;}
	
    /**
     * Return true if this <code>Sprite</code> collides with other one.
     */
    public boolean isCollide(Sprite other)
    {
  	  double distance = this.getPosition().getDistance(other.getPosition());
  	  boolean result=false;
  	  
        search:
  	  while(true)  
  	  {
  		  if (distance > ( this.CollidedRadius + other.CollidedRadius) )
  		  {break search;}
  		  
  		  if(distance<this.NORTH+other.SOUTH )
  		  {result=true;break search;}
  		  
  		  if(distance<this.SOUTH + other.NORTH )
  		  {result=true;break search;}
  		  
  		  if(distance<this.EAST+other.WEST )
  		  {result=true;break;}
  		
  		  if(distance<this.WEST+other.EAST )
  		  {result=true;break search;}
  		  
  	  double NE1 = Math.sqrt( (Math.pow(this.NORTH,2) + Math.pow(this.EAST, 2) ) );
  	  double SW2 = Math.sqrt( (Math.pow(other.SOUTH,2) + Math.pow(other.WEST, 2) ) );
  	  
  	  if (distance < NE1+SW2)
  	  	{result=true;break search;}
  	  
  	  double NE2 = Math.sqrt( (Math.pow(other.NORTH,2) + Math.pow(other.EAST, 2) ) );
  	  double SW1 = Math.sqrt( (Math.pow(this.SOUTH,2) + Math.pow(this.WEST, 2) ) );
  	  
  	  if (distance < NE2+SW1)
	  			{result=true;break search;}
  	  
  	  double NW1 = Math.sqrt( (Math.pow(this.NORTH,2) + Math.pow(this.WEST, 2) ) );
  	  double SE2 = Math.sqrt( (Math.pow(other.SOUTH,2) + Math.pow(other.EAST, 2) ) );
  	  
  	  if (distance < NW1+SE2)
			{result=true;break search;}
  	  
  	  double SE1 = Math.sqrt( (Math.pow(this.SOUTH,2) + Math.pow(this.EAST, 2) ) ); 	  
  	  double NW2 = Math.sqrt( (Math.pow(other.NORTH,2) + Math.pow(other.WEST, 2) ) );
  	  
  	  if (distance < SE1+NW2)
			{result=true;break search;}
  	  
  	  break search;
    }
    
  	  return result;
 }
    
    
    /**
     * Return true if this <code>Sprite</code> collides with other or if
     * the distance beetween sprite is minus than DIST
     * @param other <code>Sprite</code>
     * @param DIST <code>int</code> distance which behind the sprites collide;
     * @return {@code boolean}
     */
      public boolean isCollide(Sprite other, double DIST)
    {
  	  double distance = this.getPosition().getDistance(other.getPosition());
  
  	  boolean result=false;
  	  
        search:
  	  while(true)  
  	  {
  		  if (distance > ( this.CollidedRadius + other.CollidedRadius + DIST) )
  		  {break search;}
  		  
  		  //if (DIST> this.CollidedRadius && distance < DIST  )
  		  //{result=true;break search;}
  		  
  		  if(distance<this.NORTH+other.SOUTH + DIST)
  		  {result=true;break search;}
  		  
  		  if(distance<this.SOUTH + other.NORTH + DIST )
  		  {result=true;break search;}
  		  
  		  if(distance<this.EAST+other.WEST + DIST )
  		  {result=true;break;}
  		
  		  if(distance<this.WEST+other.EAST + DIST )
  		  {result=true;break search;}
  		  
  	  double NE1 = Math.sqrt( (Math.pow(this.NORTH,2) + Math.pow(this.EAST, 2) ) );
  	  double SW2 = Math.sqrt( (Math.pow(other.SOUTH,2) + Math.pow(other.WEST, 2) ) );
  	  
  	  if (distance < NE1+SW2+DIST)
  	  	{result=true;break search;}
  	  
  	  double NE2 = Math.sqrt( (Math.pow(other.NORTH,2) + Math.pow(other.EAST, 2) ) );
  	  double SW1 = Math.sqrt( (Math.pow(this.SOUTH,2) + Math.pow(this.WEST, 2) ) );
  	  
  	  if (distance < NE2+SW1+DIST)
	  			{result=true;break search;}
  	  
  	  double NW1 = Math.sqrt( (Math.pow(this.NORTH,2) + Math.pow(this.WEST, 2) ) );
  	  double SE2 = Math.sqrt( (Math.pow(other.SOUTH,2) + Math.pow(other.EAST, 2) ) );
  	  
  	  if (distance < NW1+SE2+DIST)
			{result=true;break search;}
  	  
  	  double SE1 = Math.sqrt( (Math.pow(this.SOUTH,2) + Math.pow(this.EAST, 2) ) ); 	  
  	  double NW2 = Math.sqrt( (Math.pow(other.NORTH,2) + Math.pow(other.WEST, 2) ) );
  	  
  	  if (distance < SE1+NW2+DIST)
			{result=true;break search;}
  	  
  	  break search;
    }
    
  	  return result;
 }

	  /**
	   * Calculate the new Sprite position coordinate in terms of speed and angle.
	   * @see Sprite#moveTo(double,double)
	   */
      public void move()
      {
    	  if ( ACTIVE && this.getSpeed()> 0 )
    	  {  
    	      
    		  double nx = (this.vector.origin.x + this.getMySpeed() * Math.cos(this.vector.getDirectionRadians() ) );
    		  double ny = (this.vector.origin.y + this.getMySpeed() * Math.sin(this.vector.getDirectionRadians()));
    		  this.vector.setNewOrigin(nx,ny);
    	  }
      }
	 
	  /** 
	   * Set sprite's direction in order to reach the nx,ny coordinate's point
	   * @param  nx <code>double</code> X direction which Sprite move to
	   * @param  ny <code>double</code> Y direction which Sprite move to
	   * @see Sprite#move()
	   */
	  public void moveTo(double nx, double ny)
	  {
              if ((ACTIVE) && (this.vector.origin.x != nx && this.vector.origin.y != ny) )
              {       
              double angle =  Math.toDegrees( Math.PI/2 + Math.atan2(ny-this.vector.origin.y, nx-this.vector.origin.x));
           
              //Set the new angle
              vector.setNewDirectionRadians(angle);
              }
	  }
	  
	  /** 
	   * Set sprite's direction in order to reach the Point p
	   * @param  p <code>Point</code> p Point which Sprite move to
	   * @see Sprite#move()
	   */
	  public void moveTo(Point p)
	  {
              this.moveTo(p.getX(), p.getY());
	  }
	  
	  /** 
	   * Set sprite's direction in order to reach the Point p
	   * @param  s <code>Sprite</code> which this Sprite move to
	   * @see Sprite#move()
	   */
	  public void moveTo(Sprite s)
	  {
              this.moveTo(s.getX(), s.getY());
	  }
       

      
      /**
	   * Accepts a degree values and set the sprite angle in radians
	   * @param angle an angle express in degrees
	   */
	  public void setAngleDegree(double angle)
	  {
		  angle = Math.toRadians(angle);
		 vector.setNewDirectionRadians( angle);
	  }
	  
	  /**
	   * Accepts a radians values and set the sprite angle
	   * @param angle an angle express in radians
	   */
	  public void setAngleRadians(double angle)
	  {
		  vector.setNewDirectionRadians( angle);
	  }

      /**
       * Set the collision radius as the greater value between NORT,SOUTH,EAST WEST variables;
       * This method is necessary to simplify and improve speed in the CollisionCheck.
       */
      private void setCollisionRadius()
      {
    	   this.CollidedRadius = (int) Math.max( Math.max(this.NORTH, this.SOUTH), Math.max(this.EAST, this.WEST) );
      }
	  
	  /**
	   * Set the new dimension of the playfield
	   */
	  public void setDim (Dimension dim){d=dim;}
	  
	  /**
	   * Set the Sprite identify
	   * @param ID an ID number for the Sprite
	   */
	  public void setId(int ID){id=ID;}

      /**
       * Set the
       * @param n <code>int</code>max pixel above the Sprite center
       * @param s <code>int</code>max pixel under the Sprite center
       * @param w <code>int</code>max pixel on the left of the Sprite center
       * @param e <code>int</code>max pixel on the right of the Sprite center
       */
      protected void setMaxOffset(int n,int s, int w, int e)
      {
    	  NORTH=n;SOUTH=s;EAST=e; WEST=w;
    	  this.setCollisionRadius();
      }
	  
	  /**
	   * Accept a int between 0 and 100 and set the sprite's speed
	   * @param speed int Sprite's speed
	   */
	  public void setSpeed(int speed)
	  {
		  if (speed>=0 && speed<=100)
			  this.speed=speed;
		  else
			  this.speed=DefaultSpeed;
		  
		  vector.setNewModule(this.speed);
	  }
	  
	  /**
	   * Set the sprite center Position
	   * @param pos the new position {@link Point}
	   */
	  public void setPosition(Point pos)
	  {
		  vector.setNewOrigin(pos);
	  }
	  
	  /**
	   * Set the sprite center Position
	   * @param x the new position x coordinate
	   * @param y the new position y coordinate
	   */
	  public void setPosition(double x, double y)
	  {
		  vector.setNewOrigin(x,y);
	  }
	    
	  /**
	   * Set the x Sprite's center coordinate 
	   * @param x the new position x coordinate
	   */
	  public void setX(double x){vector.setNewOriginX(x);}
	  
	  /**
	   * Set the y Sprite's center coordinate 
	   * @param y the new position y coordinate
	   */
	  public void setY(double y) {vector.setNewOriginY(y);}  
	  
	  
	  public void swichCollided() { collided = !collided;}
	  
	  //public abstract void Play();
	  
	  /**
	   * This method must be override to implements how the Sprite must be draw
	   */
	  protected abstract void ThisIsMe(Graphics g);
	  
      
}//End Class


	 
	  

	  
	  


