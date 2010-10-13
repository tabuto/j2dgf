/**
* @author Francesco di Dio
* Date: 11 Ottobre 2010 18.14
* Titolo: DrawPanel.java
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
 * Classe che rappresenta un CanvasPanel all'interno del quale si muovono gli sprite
 * 
 * 
 */


package com.tabuto.j2dgf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import com.tabuto.j2dgf.collision.CollisionManager;

/**
 * Abstract class <code>DrawingPanel</code> extends Panel.
 * <p>
 * This Class implements a CavasPanel where the sprite "live". <br>
 * This Class should be had a CollisionDetector inner classes;
 * 
 * @see com.tabuto.j2dgame.collision.CollisionDetector
 * 
 * @author tabuto83
 *
 * @version 0.1.0
 */

public abstract class DrawPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sleep value for the thread. Default value: 10
	 */
	int sleep=10;
	 /**
	  * Dimension DIM
	  * Dimension of the canvasPanel
	  */
	 protected Dimension DIM = new Dimension(1,1);
	 
	 /**
	  * A BufferStrategy to implements ActiveRendering and DoubleBuffer
	  */
	 protected BufferStrategy bs;      
	 
	 /**
	  * A CollisionManager declaration to manage CollisionDetector
	  */
	 protected CollisionManager cm;
	 
	 /**
	  * The color panel's background
	  */
	 public Color background;
	 /**
	  * CONSTRUCTOR
	  * <p>
	  * public DrawingPanel(int w, int h)
	  * <p>
	  * Constructor of <code>DrawingPanel</code> that declares the new dimension of the Panel;
	  * @param w int
	  * @param h int
	  */
	 public DrawPanel(int w, int h)
		{
			DIM = new Dimension(w,h);
			background = new Color(0,0,0); //Default value = BLACK
			cm = new CollisionManager();
		}
	 
	 /**
	  * This <code>abstract</code> method initialize the component of the game:
	  * <ul>
	  *  <li> {@link SpriteGroup}
	  *  <li> {@link CollisionDetector} 
	  *  <li> {@link CollisionBoundDetector} (if needs!)
	  *  </ul>
	  *  N.B. The {@link CollisionManager} has been initialize in the Costructor method.
	  */
	 public abstract void initStuff();
	 
	 /**
	  * Set the Dimension (width and heigth) of the panel
	  * @param dim {@link Dimension}
	  */
	 public void setDimension(Dimension dim) {DIM=dim;}
	 
	 /**
	  * Return the panel's BufferStrategy
	  * @return bs
	  */
	 public BufferStrategy getBufferStrategy(){return bs;}
	 
	 /**
	  * Set the panel's BufferStrategy 
	  * @param b {@link BufferStrategy}
	  */
	 public void setBufferStrategy(BufferStrategy b){bs=b;}
	 
	 /**
	  * Set the panel's repainting sleep time 
	  * @param sl
	  */
	 public void setSleep(int sl){sleep=sl;}
	 
	 /**
	  * Return the panel's repainting sleep time 
	  * @return sleep int
	  */
	 public int getSleep(){return sleep;}
	 
	 /**
	  * Refresh the panel to draw the new position of the sprite
	  */
	 public void drawStuff()
	    {
	            try
	            {
	            Graphics2D g = (Graphics2D)bs.getDrawGraphics();
	            g.setColor(background);
	            g.fillRect(0,0,DIM.width,DIM.height);
	            
				drawSprite(g);
	            
	            bs.show();
	            Toolkit.getDefaultToolkit().sync();
	            g.dispose();
	            Thread.sleep(sleep);
	            }//end try
	            catch (Exception e)
	            {
	                System.exit(0);
	            }
	       
		}   //end of DrawStuff
	 
	 
	 /**
	  * Should be contains the operations to do for moving the sprite;<br>
	  * <p>
	  * Example:
	  * <pre>
	  * for(i=0;MySpriteGroup.group.getSize()>i;i++)	   
      *    { 
      *		MySpriteGroup.group.get(i).move();
      *		 cm.RunCollisionManager();
      *		MySpriteGroup.group.get(i).drawMe(g2d);
      *	  }
      *</pre>
	  * @param g2d {@link Graphics2D}
	  */
	 protected abstract void drawSprite(Graphics2D g2d);
	 
	 
	
}
