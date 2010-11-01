/**
* @author Francesco di Dio
* Date: 28 Ottobre 2010 18.14
* Titolo: Drawable.java
* Versione: 0.4 Rev.a:
*/


package com.tabuto.j2dgf;

import java.awt.Graphics2D;


/**
 * Interface <code>Drawable</code> is an abstraction representing something can be draw on a J2DGF package.
 * 
 * @author tabuto83
 *
 * @version 0.4.0
 * 
 * @see com.tabuto.j2dgame.Sprite
 */
public interface Drawable {
	
	 /**
	  * Represent the graphics of the object implements the Drawable interface.
	  * @param Graphics2D g
	  */
	 public void drawMe(Graphics2D g);
	 
	 /**
	  * Override this method to implements the routines to move your object
	  */
	 public void move();
	 
	 /**
	  * Override this method to implements the actions in order to activate the drawable object
	  */
	 public void Activate();
	 
	 /**
	  * Override this method to implements the actions in order to deactivate the drawable object
	  */
	 public void Deactivate();
	 
	 /**
	  * Override this method in order to return a unique ID number to identify your drawable object
	  * @return int ID number
	  */
	 public int getId();

}
