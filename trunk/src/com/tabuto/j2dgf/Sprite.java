/**
* @author Francesco di Dio
* Date: 12 Ottobre 2010 18.14
* Titolo: Sprite.java
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
 * Classe astratta che rappresenta un generico sprite
 * 
 */


package com.tabuto.j2dgf;

import java.awt.Dimension;
import java.awt.Graphics2D;



/**
 * abstract class <code>Sprite</code> 
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
 * @version 0.1.0
 * 
 * @see com.tabuto.j2dgame.SpriteGroup
 * 
 */

public abstract class Sprite {

	/**
	 * 
	 * Sprite radius
	 */
	public int r; 
	
	/**
	 * Sprite's center coordinates
	 */
	public double xc, yc;
	
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
	 * Sprite's Angle Direction in radians
	 */
	protected double a;     
	
	/**
	 * The Dimension of the playfield where sprites live. 
	 * Example:<br>
	 * <pre>
	 * Dimension playfield = new Dimension(1024,768);
	 * playfiled.width return width (1024);
	 * playfield.height return heigth (768);
	 * </pre>
	 */
	
	protected Dimension d;  
	
	/**
	 *  The speed of the sprite 
	 *  @see java.awt.Dimension
	 */
    protected int s; 

    /**
     * The default SPeed of the sprite
     */
    public int DefaultSpeed = 50; 

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
		  xc=posX;
		  yc=posY;
		  ACTIVE=true;
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
		  if (angle>=0 && angle < 361)
		  a = Math.toRadians(90 + angle);
	  }
	  
	  /**
	   * Accepts a radians values and set the sprite angle
	   * @param angle double
	   */
	  public void setAngleRadians(double angle)
	  {
		  a = angle;
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
			  s=speed;
		  else
			  s=DefaultSpeed;
	  }
	  
	  /**
	   * Set the x Sprite's center coordinate 
	   * @param x double
	   */
	  public void setX(double x){xc=x;}
	  
	  /**
	   * Set the y Sprite's center coordinate 
	   * @param y double
	   */
	  public void setY(double y) {yc=y;}  
	  
	  /**
	   * Return the sprite's number id
	   * @return {@link Sprite#id} int
	   */
	  public int getId()  {  return id;  }
	  
	  /**
	   * Return the sprite's Angle direction in radians
	   * @return {@link Sprite#a} double 
	   */
	  public double getAngle(){return a;}
	 
	  /**
	   * Return the sprite's speed
	   * @return {@link Sprite#s} double 
	   */
	  public int getSpeed(){return s;}
	  
	  /**
	   * Return the sprite's center x coordinate
	   * @return {@link Sprite#xc} double 
	   */
	  public double getX(){return xc;}
	  
	  /**
	   * Return the sprite's center y coordinate
	   * @return Sprite#yc double
	   */
	  public double getY(){return yc;}
	  
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
	   * Set sprite's angle in order to reach the nx,ny coordinate's point
	   * @param  nx double 
	   * @param  ny double
	   * @see Sprite#move()
	   */
	  public void moveTo(double nx, double ny)
	  {
              if ((ACTIVE) && (xc != nx && yc != ny) )
              {       
              double angle =  Math.toDegrees( Math.PI/2 + Math.atan2(ny-this.yc, nx-this.xc));
           
              //Set the new angle
              this.a = angle;
              }
	  }
       
	  /**
	   * Calculate the new coordinate of the sprite in terms of speed and angle.
	   * @see Sprite#moveTo(double,double)
	   */
      public void move()
      {
    	  
    	  if (ACTIVE && s>0)
    	  {  
    	      
    		  double nx = (this.xc + this.getMySpeed() * Math.cos(this.a) );
    		  double ny = (this.yc + this.getMySpeed() * Math.sin(this.a));
    		  this.xc = nx;
    		  this.yc = ny;
    	  }
    	  
      }
      
      /**
       * Returns the speed as a double value between  0 and 2.100
       */
      protected double getMySpeed()
      {
    	  double speedy = this.s * 0.021;
    	  return speedy;
    	  
      }
      
      
              
}


	  
	  

	  
	  


