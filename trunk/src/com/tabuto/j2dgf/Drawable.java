/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 18.14
* Titolo: Drawable.java
* Versione: 0.6.2 Rev.a:
*/


package com.tabuto.j2dgf;

import java.awt.Graphics;


/**
 * Interface <code>Drawable</code> is an abstraction representing something can be draw on a J2DGF package.
 * 
 * @author tabuto83
 *
 * @version 0.6.2
 * 
 * @see Sprite
 */
public interface Drawable {
	
	 /**
	  * Represent the graphics of the object implements the Drawable interface.
	  * @param g Graphics 
	  */
	 public void drawMe(Graphics g);
	 
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
	 

}
