/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 18.14
* Titolo: J2DCanvasPanel.java
* Versione: 0.6.0 Rev.:
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


package com.tabuto.j2dgf.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;
import com.tabuto.j2dgf.collision.CollisionManager;

/**
 * Abstract class <code>J2DCanvasPanel</code> extends JPanel.
 * <p>
 * This Class implements a CavasPanel where the sprite "live". <br>
 * This Class should be had a CollisionDetector inner classes;
 * 
 * @see com.tabuto.j2dgf.collision.CollisionDetector
 * 
 * @author tabuto83
 *
 * @version 0.6.0
 */

public class J2DCanvasPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sleep value for the thread. Default value: 10
	 */
	protected int sleep=10;
	 /**
	  * Dimension DIM
	  * Real Dimension of the canvasPanel
	  */
	 protected Dimension DIM = new Dimension(1,1);
	 
	 protected boolean running = true;

	 protected Image BufferedImage;
	 
	 
	 private boolean PLAY=true;
	
	 private boolean STOP=false;
	 
	 
	 
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
	 protected Color background;
	 /**
	  * CONSTRUCTOR
	  * <p>
	  * public J2DCanvasPanel(int w, int h)
	  * <p>
	  * Constructor of <code>J2DCanvasPanel</code> that declares the new dimension of the JPanel;
	  * @param w <code>int</code> real width
	  * @param h <code>int</code> real height
	  */
	 public J2DCanvasPanel(int w, int h)
		{
			DIM = new Dimension(w,h);
			//VIS = new Dimension(vw,vh);
			background = new Color(0,0,0); //Default value = BLACK
			cm = new CollisionManager();
			//setAutoscrolls(true);
	        setDoubleBuffered(true);
	        //this.BufferedImage = createImage(DIM.width,DIM.height );
	        
		}
	 
	 /**
	  * This <code>abstract</code> method initialize the component of the game:
	  * <ul>
	  *  <li> {@link com.tabuto.j2dgf.Group}
	  *  <li> {@link com.tabuto.j2dgf.collision.CollisionDetector} 
	  *  <li> {@link com.tabuto.j2dgf.collision.CollisionBoundDetector} (if needs!)
	  *  </ul>
	  *  N.B. The {@link CollisionManager} has been initialize in the Costructor method.
	  */
	 public void initStuff(){};
	 
	 /**
	  * Set the Dimension (width and heigth) of the panel
	  * @param dim {@link Dimension}
	  */
	 public void setDimension(Dimension dim) {DIM=dim;}
	 
	 /**
	  * Return the Panel's <code>Dimension</code>
	  * @return Dimension {@link J2DCanvasPanel#DIM}
	  */
	 public Dimension getDimension(){return DIM;}
	 
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
	  * Set the panel's thread sleep time.<br>
	  * Default value: 10
	  * @param time <code>int</code>
	  */
	 public void setSleep(int time)
	 {
		 if (time>0)
		 sleep=time;
	 }
	 
	 /**
	  * Return the panel's thread sleep time 
	  * @return sleep int
	  */
	 public int getSleep(){return sleep;}
	 
	 /**
	  * Return the Background Color 
	  * @return {@link java.awt.Color} Background
	  */
	 public Color getBackgroundColor(){return this.background;}
	 
	 /**
	  * Set new background color as parameter b
	  * @param b {@link Color}
	  */
	 public void setBackgroundColor(Color b){this.background=b;}
	 
	 /**
	  * Set new background color as a new RGB component color
	  * @param r <code>int</code> red component value (0-255)
	  * @param g <code>int</code> green component value (0-255)
	  * @param b <code>int</code> blue component value (0-255)
	  */
	 public void setBackgroundColor(int r, int g, int b)
	 {
		 Color B = new Color(r,g,b);
		 this.background = B;
	 }
	 
	 /**
	  * Refresh the panel to draw the new position of the sprite in a buffer
	  */
	 public void drawStuff()
	    {
	            try
	            { 
	            	this.BufferedImage = createImage(DIM.width, DIM.height);
	            	Graphics buffer =  BufferedImage.getGraphics();
	            	buffer.setColor(background);
	            	buffer.fillRect(0, 0, DIM.width, DIM.height);      
	            	drawSprite(buffer);          	
	            }//end try
	            catch (Exception e)
	            {
	            	System.out.println("Error at J2DCanvasPanel (213) "+e.getMessage());
	                System.exit(2);
	            }
	       
		}   //end of DrawStuff
	 
	 
	 /**
	  * Should be contains the operations to do for moving the sprite;<br>
	  * <p>
	  * Example:
	  * <pre>
	  * for(i=0;MySpriteGroup.group.getSize()>i;i++)	   
      *    { 
      *		MySpriteGroup.get(i).move();
      *		 cm.RunCollisionManager();
      *		MySpriteGroup.get(i).drawMe(g2d);
      *	  }
      *</pre>
	  * @param g {@link Graphics}
	  */
	 protected void drawSprite(Graphics g){}
	 
	 
	 // get preferred ImagePanel size
	 
	 /**
	  * Return the PreferredSize of this Panel as a {@link Dimension}
	  */
	 public Dimension getPreferredSize()
	  {
	     return DIM;
	  }
	 
	 /**
	  * Return the Minimum Size of the Panel as a {@link Dimension}
	  */
	 public Dimension getMinimumSize()
	  {  
	     return new Dimension(300,300);
	  }

	 /**
	  * 
	  */
	  public void update(Graphics g) {
		    paint(g);
		  }
	  
	  /**
	   * Draw the content of this CanvasPanel
	   */
	  public void run()
	    /* Repeatedly update, render, sleep */
	    {
		  
	      while(PLAY) {
	    	this.drawStuff();   // render to a buffer
	       panelDraw();  // draw buffer to screen
	        try {
	          Thread.sleep(sleep);  // sleep a bit
	        }
	        catch(InterruptedException ex){}
	      }

	    } // end of run()
	  

	  /**
	   * Flush the content of the BufferImage on the Panel
	   */
	  private void panelDraw()
	  {
		  Graphics g;
	      try {
	        g = this.getGraphics();  // get the panel's graphic context

	          g.drawImage(BufferedImage, 0, 0, null);
	        Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
	        g.dispose();
	      }
	      catch (Exception e)
	      { System.out.println("Graphics context error: " + e);  }
		
		  
	  }
	  
	  //private void drawSprite(){Graphics2D g = (Graphics2D) this.getGraphics(); drawStuff(g);}
	  /**
	   * Stop the drawing of the Panel
	   */
	  public void Stop()
	  {
		  if (PLAY)
  			{
			  this.PLAY= false;
			  this.STOP= true;
			}
		  else
      		{
			  this.PLAY=true;
			  this.STOP=false;
      		}
	  }
	  
	  /**
	   * Restart the Drawing of the panel after a previously {@link J2DCanvasPanel#Stop()}
	   */
	  public void Play()
	  {
		  this.PLAY=true;
	  }
	  
	  /**
	   * Draw a single frame animation on the CanvasPAnel
	   */
	  public void Step()
	  {
		  this.drawStuff();   // render to a buffer
	       this.panelDraw();  // draw buffer to screen
	  }
}

