/**
* @author Francesco di Dio
* Date: 29 Novembre 2010 18.14
* Titolo: J2DCanvasPanel.java
* Versione: 0.7.0 Rev.:
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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import com.tabuto.j2dgf.Game2D;


/**
 * Abstract class <code>J2DCanvasPanel</code> extends JPanel.
 * <p>
 * This Class implements a CavasPanel where the sprite "live". <br>
 * This Class should be a Control layer for the Game model
 * Use this class simply extends it and call the drawStuff(Game) method
 * 
 * @see com.tabuto.j2dgf.collision.CollisionDetector
 * 
 * @author tabuto83
 *
 * @version 0.7.0
 * 
 * @see Game2D
 * @see CollisionDetector
 * @see Sprite
 * @see Group
 */


public class J2DCanvasPanel extends JPanel implements  Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sleep value for the thread. Default value: 10ms
	 */
	protected int sleep=10;
	 /**
	  * Dimension DIM
	  * Real Dimension of the canvasPanel
	  */
	 protected Dimension DIM = new Dimension(1,1);
	 
	 protected boolean running = true;

	 protected transient Image BufferedImage;
	 
	 
	 protected boolean PLAY=true;
	
	 @SuppressWarnings("unused")
	protected boolean STOP=false;
	 
	//public Game2D Game;
	 
	 /**
	  * A BufferStrategy to implements ActiveRendering and DoubleBuffer
	  */
	 protected transient BufferStrategy bs;      
	 
	 /**
	  * The color panel's background
	  */
	 protected Color background;

	 /**
	  * Construct a new J2DCanvasPanel of d Dimension
	  * @param d Dimension of the new Panel
	  * @see Dimension
	  */
	 public J2DCanvasPanel(Dimension d)
	 {
		 DIM = d;
		 background = new Color(0,0,0); //Default value = BLACK
		 setDoubleBuffered(true);
	
	 }
	 
	 /**
	  * Refresh the panel to draw the new position of the sprite in a buffer
	  */
	 public void drawStuff(Game2D Game)
	    {
	            try
	            { 
	            	this.BufferedImage = createImage(DIM.width, DIM.height);
	            	Graphics buffer =  BufferedImage.getGraphics();
	            	buffer.setColor(background);
	            	buffer.fillRect(0, 0, DIM.width, DIM.height);      
	            		Game.drawStuff(buffer);
	            }//end try
	            catch (Exception e)
	            {
	            	System.out.println("Error at J2DCanvasPanel (213) ");
	            	e.getStackTrace();
	            	e.printStackTrace();
	                //System.exit(2);
	            }
	       
		} 
	  

	 /**
	  * Return the Background Color 
	  * @return {@link java.awt.Color} Background
	  */
	 public Color getBackgroundColor(){return this.background;}
	 
	 /**
	  * Return the panel's BufferStrategy
	  * @return bs
	  */
	 @SuppressWarnings("all")
	 public BufferStrategy getBufferStrategy(){return bs;}
	 
	 /**
	  * Return the Panel's <code>Dimension</code>
	  * @return Dimension {@link J2DCanvasPanel#DIM}
	  */
	 public Dimension getDimension(){return DIM;}
	 
	 /**
	  * Return the Minimum Size of the Panel as a {@link Dimension}
	  */
	 public Dimension getMinimumSize()
	  {  
	     return new Dimension(300,300);
	  }
	 
	 /**
	  * Return the PreferredSize of this Panel as a {@link Dimension}
	  */
	 public Dimension getPreferredSize()
	  {
	     return DIM;
	  }
	 
	 /**
	  * Return the panel's thread sleep time 
	  * @return sleep int
	  */
	 public int getSleep(){return sleep;}
	 
		/**
		 * Load a previously saved game stored in a file which PATH is path
		 * @param path The path of the Game2D saved file
		 */
	public Game2D loadGame(String path)
		{
			Game2D loaded = null;
			FileInputStream fis = null;
		    ObjectInputStream in = null;
			try
			 {
			   fis = new FileInputStream(path);
		       in = new ObjectInputStream(fis);
			   loaded = (Game2D)in.readObject();
		       in.close();
		      
		     }
		     catch(IOException ex)
		     {
			     ex.printStackTrace();
		     }
		     catch(ClassNotFoundException ex)
		     {
		     ex.printStackTrace();
		     }
		     
		     loaded.executeCollisionManager();
		     return loaded;
		}
	
	  /**
	   * Flush the content of the BufferImage on the Panel
	   */
	  protected void panelDraw()
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
	 
	  /**
	   * Restart the Drawing of the panel after a previously {@link J2DCanvasPanel#Stop()}
	   * @deprecated
	   * Use the activate() deactivate() Game2D methods
	   * @see Game2D
	   */
	  public void Play()
	  {
		  this.PLAY=true;
	  }
	 
	  /**
	   * Draw the Game2D Sprites
	   * @param Game Game2D
	   */
	  public void run(Game2D Game)
	    /* Repeatedly update, render, sleep */
	    {
		  
	      while(Game.isActive()) {
		   this.drawStuff(Game);   // render to a buffer
	       panelDraw();  // draw buffer to screen
	
	        try {
	          Thread.sleep(sleep);  // sleep a bit
	        }
	        catch(InterruptedException ex){}
	      }

	    } 
	 
	  /**
	   * Save a Game to a File
	   * @param game Game2D to save
	   * @param fileName File name of the Game2D saved
	   */
	public void saveGame(Game2D game, String fileName)
		{
		
			FileOutputStream fos = null;
		    ObjectOutputStream out = null;
			 try
			 {
				 fos = new FileOutputStream(fileName);
				 out = new ObjectOutputStream(fos);
				 out.writeObject(game);
				 out.close();
				 game.setSaved(true);
			 }
			  catch(IOException ex)
			  		{
				  		ex.printStackTrace();
					}
		}
	  
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
	  * Set the panel's BufferStrategy 
	  * @param b {@link BufferStrategy}
	  */
	 public void setBufferStrategy(BufferStrategy b){bs=b;}

	 /**
	  * Set the Dimension (width and heigth) of the panel
	  * @param dim {@link Dimension}
	  */
	 public void setDimension(Dimension dim) {DIM=dim;}
	 
	 
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

	  
	  //private void drawSprite(){Graphics2D g = (Graphics2D) this.getGraphics(); drawStuff(g);}
	  /**
	   * Stop the drawing of the Panel
	   * @deprecated
	   * Use the activate() deactivate() Game2D methods
	   * @see Game2D
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
	  * Not used
	  */
	 public void update(Graphics g) {
			    paint(g);
			  }
	  
	/**
	 * This Panel can be observer of other observable class,
	 * override this method to implements a change state when an observable
	 * object change state.
	 */
	public void update(Observable arg0, Object arg1) {
		
		
	}
}

