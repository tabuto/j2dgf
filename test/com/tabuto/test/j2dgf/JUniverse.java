package com.tabuto.test.j2dgf;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;


/**
* @author Francesco di Dio
* Date: 3 Ottobre 2010 18.14
* Titolo: Sprite.java
* Versione: 0.4.0 Rev:34:
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
 * JUniverse: An example for use a j2dgame package. A simple ball game;
 * 
 */


public class JUniverse extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferStrategy bs;      //BufferStrategy
    int W=1024,H=768;       //Window Frame Size
    Dimension d;            //Dimension of window size
    FlowLayout Layout;      //FlowLayout
   
    
    DPanel panel = new DPanel(W,H); //Declare the DrawingPanel

	//Game Flags
    private boolean PLAY=true; 
    private boolean STOP= false;
	
    
    public JUniverse()
    {
    	//Frame config
    	d = new Dimension(W,H);
    	panel.setDimension(d);
        setIgnoreRepaint(true);
        setTitle("JUniverse v.0.3.0.34");
        setSize(d.width,d.height);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        bs = getBufferStrategy();
        panel.setBufferStrategy(bs);
        Layout = new FlowLayout ();
        setLayout (Layout);        
        //Add Menu
		addMenu();
		//Add the DrawingPanel
        this.add(panel);
        //DrawingPanel setting
        panel.setIgnoreRepaint(true);
        panel.setVisible(true);
        panel.setFocusable(true);
    }
    
    
    
    //Start the game
    public void startNow()
    {
    	if(!STOP)
        panel.initStuff();
		
    	while(PLAY){ panel.drawStuff(); };   
    }
    
    
    //Create and Add the menubar
    private void addMenu(){
    	
   	 MenuBar menuBar = new MenuBar();
   	    Menu file = new Menu("File");
   	    file.addActionListener(new WindowHandler());
   	    
   	    Menu action = new Menu("Action");
   	    action.addActionListener(new WindowHandler());
   	    
   	    Menu about = new Menu("About");
   	    about.addActionListener(new WindowHandler());
   	    
   	    //now add menu items to these Menu objects
   	    file.add(new MenuItem("Exit"));
   	    
   	    action.add(new MenuItem("Start", new MenuShortcut(KeyEvent.VK_S ))).addActionListener(new WindowHandler());
	    action.add(new MenuItem("Step", new MenuShortcut( KeyEvent.VK_F ))).addActionListener(new WindowHandler());
	    action.add(new MenuItem("Stop", new MenuShortcut( KeyEvent.VK_SPACE ))).addActionListener(new WindowHandler());
   	    action.add(new MenuItem("Reset",new MenuShortcut( KeyEvent.VK_R ))).addActionListener(new WindowHandler());
   
   	    about.add(new MenuItem("About"));
   	    
   	    //add menus to menubar
   	    menuBar.add(file);
   	    menuBar.add(action);
   	    menuBar.add(about);
   	    
   	    {this.setMenuBar(menuBar);}

   }
    
    //Implements the listener for the menù bar
    private class WindowHandler extends WindowAdapter implements ActionListener
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }

      public void actionPerformed(ActionEvent e)
      {
        System.out.println(e.getActionCommand());
        //check to see if the action command is equal to exit
        if(e.getActionCommand().equalsIgnoreCase("exit"))
        {
          System.exit(0);
        }
        else if(e.getActionCommand().equalsIgnoreCase("About"))
        {
          JOptionPane.showMessageDialog(null, "JUniverse v0.4 is written by tabuto83", "About", JOptionPane.PLAIN_MESSAGE);
        }
        
        else if(e.getActionCommand().equalsIgnoreCase("Start"))
        {   
        	PLAY=true;
        }
        
        else if(e.getActionCommand().equalsIgnoreCase("Stop"))
        {
        	//Commuta i flag di gioco
        	if (PLAY)
        	{PLAY= false;
             STOP= true;}
            else
            {PLAY=true;
             STOP=false;
            	
            }
        }
        
        else if(e.getActionCommand().equalsIgnoreCase("Step"))
        {
        	panel.drawStuff();
        }
        
        else if(e.getActionCommand().equalsIgnoreCase("Reset"))
        {
        	panel.deleteStuff();
        	panel.initStuff();
        }
        
        else
        {
          JOptionPane.showMessageDialog(null, "You asked for a "+e.getActionCommand(), "A Simple Drawing Tool", JOptionPane.PLAIN_MESSAGE);
        }
      }//actionPerformed()
    }//windowHandler - Inner Class ends here
    
    
    //Entry Point
    public static void main(String[] args)
   {
       JUniverse main = new JUniverse();
       while(true){main.startNow();}
   }
	
}//end class

