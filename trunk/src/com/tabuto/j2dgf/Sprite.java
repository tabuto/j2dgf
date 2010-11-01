/**
* @author Francesco di Dio
* Date: 28 Ottobre 2010 18.14
* Titolo: Sprite.java
* Versione: 0.4 Rev.4:
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
import java.awt.Graphics2D;

import com.tabuto.util.Point;
import com.tabuto.util.Vettore;



/**
 * Abstract class <code>Sprite</code> 
 * <p>
 * Sprite is an Abstract Class that represents an object able draws himself and move on
 * a playfield of fixed dimension.
 * <p>
 * It has got an ACTIVE flag and a {@linkplain #ThisIsMe(Graphics2D)} method called by {@linkplain #drawMe(Graphics2D)} 
 * method when ACTIVE is true; <br>
 * Every Sprite has a position defined by two variables (xc and yc) that identifies the sprite's center;
 * Every sprite also has speed and angle variable.<br>
 * To instance a new Sprite you have to call the constructor method {@linkplain #Sprite(Dimension,double, double)}
 * to set the dimension plane where sprite live and his start position.<br>
 * Every sprite can be part of SpriteGroup. <br>
 * 
 * @author tabuto83
 * 
 * @version 0.4.0
 * 
 * @see com.tabuto.j2dgame.SpriteGroup
 * 
 */

public abstract class Sprite implements Drawable {

	/**
	 * {@link Vettore} vector represent position, direction and speed of each sprite
	 */
	public Vettore vector;
	
	
	/**
	 * Sprite height
	 */
	public int h; 
	
	/**
	 * Sprite width
	 */
	public int w; 
	
	/**
	 * If ACTIVE is true sprite draws himself
	 */
	public boolean ACTIVE;
	
	
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
	 * The Dimension of the playfield where sprites live. 
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
	 *  @see java.awt.Dimension
	 */
    protected int speed; 

    /**
     * The default SPeed of the sprite
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
     * 	CONSTRUCTOR
     *  <p>
     *  Sprite(Dimension, double, double)
     *  <p>
     * 	Creates new <code>Sprite</code> in the playfiled of Dimension dim, with specific coordinates
     *  <p>
     *  @param dim {@linkplain java.awt.Dimension#Dimension()}
     *  @param posX double 
     *  @param posY double
     *  
     * 
     */

    
	  public Sprite(Dimension dim, double posX, double posY)
	  {
		  d=dim;
		  ACTIVE=true;
		  vector = new Vettore(posX, posY);
		  vector.setNewModule(this.speed);
		  id= this.hashCode();
		  
		  NORTH=0;SOUTH=0;EAST=0; WEST=0;
		  
	  };
	  
	  /**
	   * Set the Sprite identify
	   * @param ID
	   * 
	   */
	  public void setId(int ID){id=ID;}
	  
	  /**
	   * Accepts a degree values and set the sprite angle in radians
	   * @param angle
	   */
	  public void setAngleDegree(double angle)
	  {
		  angle = Math.toRadians(angle);
		 vector.setNewDirectionRadians( angle);
	  }
	  
	  /**
	   * Accepts a radians values and set the sprite angle
	   * @param angle double
	   */
	  public void setAngleRadians(double angle)
	  {
		  vector.setNewDirectionRadians( angle);
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
	   * Set the dimension of the playfield
	   */
	  public void setDim (Dimension dim){d=dim;}

	  /**
	   * Accept a int between 0 and 100 and set the sprite's speed
	   * @param speed int
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
	   * @param pos {@link Point}
	   */
	  public void setPosition(Point pos)
	  {
		  vector.setNewOrigin(pos);
	  }
	  
	  /**
	   * Set the sprite center Position
	   * 
	   */
	  public void setPosition(double x, double y)
	  {
		  vector.setNewOrigin(x,y);
	  }
	  
	  public Point getPosition(){return vector.origin;}
	  
	  /**
	   * Set the x Sprite's center coordinate 
	   * @param x <code>double</code>
	   */
	  public void setX(double x){vector.setNewOriginX(x);}
	  
	  /**
	   * Set the y Sprite's center coordinate 
	   * @param y <code>double</code>
	   */
	  public void setY(double y) {vector.setNewOriginY(y);}  
	  
	  /**
	   * Return the sprite's number id
	   * @return {@link Sprite#id} int
	   */
	  public int getId()  {  return id;  }
	  
	  /**
	   * Return the sprite's Angle direction in radians
	   * @return {@link Sprite#a} double 
	   */
	  public double getAngle(){return vector.getDirectionRadians();}
	 
	  /**
	   * Return the sprite's speed
	   * @return {@link Sprite#s} double 
	   */
	  public int getSpeed(){return this.speed;}
	  
	  /**
	   * Return the sprite's center x coordinate
	   * @return double 
	   */
	  public double getX(){return vector.origin.x;}
	  
	  /**
	   * Return the sprite's center y coordinate
	   * @return double
	   */
	  public double getY(){return vector.origin.y;}
	  
	  /**
	   * Return the sprite's Active state
	   * @return boolean ACTIVE
	   */
	  public boolean isActive(){return ACTIVE;}

	  
	  public void swichCollided() { collided = !collided;}
	  
	  //public abstract void Play();
	  
	  /**
	   * This method must be override to implements the graphics elements of the sprite
	   */
	  protected abstract void ThisIsMe(Graphics2D g);
	  
	  /**
	   * This method draw the sprite if it is ACTIVE
	   * @param g Graphics2D
	   */
	  public void drawMe(Graphics2D g)
	  {
		if(ACTIVE)
			ThisIsMe(g);	
	  }
	 
	 /**
	  * Return the pixel above the Sprite center; 
	  * @return int
	  */
	  public int getNorth(){return NORTH;}
	  
	/**
     * Return the pixel under the Sprite's center; 
     * @return int
	 */
	  public int getSouth(){return SOUTH;}
		  
		 /**
		  * Return the pixel on the right of the Sprite's center; 
		  * @return int
		  */
		  public int getEast(){return EAST;}  

		  /**
		   * Return the pixel on the right of the Sprite's center; 
		   * @return int
		   */
	      public int getWest(){return WEST;}  
		  
	  /** 
	   * Set sprite's direction in order to reach the nx,ny coordinate's point
	   * @param  nx <code>double</code> X direction which Sprite move on
	   * @param  ny <code>double</code> Y direction which Sprite move on
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
	   * Calculate the new coordinate of the sprite in terms of speed and angle.
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
       * Returns the speed as a double value between  0 and 2.100
       */
      protected double getMySpeed()
      {
    	  double speedy = this.speed * 0.021;
    	  return speedy;
    	  
      }
     
      /*
      public void finalize()
      {
      }
      */
      
      /**
       * Return true if this <code>Sprite</code> collides with other.
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
       * @return boolean
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
       * Set the collision radius as the Greater value of NORT,SOUTH,EAST WEST variables;
       * This method is necessary to simplify and improve speed in the CollisionCheck.
       */
      private void setCollisionRadius()
      {
    	   this.CollidedRadius = (int)    Math.max( Math.max(this.NORTH, this.SOUTH), Math.max(this.EAST, this.WEST) );
      }
      
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
      
}


	 
	  

	  
	  


