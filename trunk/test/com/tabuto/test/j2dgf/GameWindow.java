/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 18.14
* Titolo: GameWindow.java
* Versione: 0.5.1 Rev.:
*/

package com.tabuto.test.j2dgf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;


import com.tabuto.test.j2dgf.MyCanvas;

public class GameWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	BufferStrategy bs;      //BufferStrategy
    int W=1024,H=668;       //Window Frame Size
    Dimension d;            //Dimension of window size
    private static final String version =" v.0.5.1";
    boolean PLAY = true;
    boolean STOP = false;
    
    //GUI PANELS AND ELEMENTS
    MyCanvas panel;
    JMenuBar j2dmenubar;
    MyControlLeftPanel cp_west;
    MyControlRightPanel cp_east;
    MyBottomPanel bottom;
    JScrollPane scroller;
  
    
    public GameWindow()
    {
    	d = new Dimension(W,H);
    	cp_west = new MyControlLeftPanel(d);
    	cp_east = new MyControlRightPanel(d);
    	panel = new MyCanvas(1024,1024); //Declare the DrawingPanel
    	scroller = new JScrollPane(panel);
    	bottom = new MyBottomPanel(d);
    	j2dmenubar = new JMenuBar();
    	
        setTitle("J2DFrame" + version);
        setSize(d.width,d.height);
        setResizable(true);
    	BufferStrategy bs;
		createBufferStrategy(1);
		bs = getBufferStrategy();
        setLayout(new BorderLayout());
        cp_west.setVisible(true);
        cp_east.setVisible(true);
        bottom.setVisible(true);
        addMenu();
        addPanels();
		this.setJMenuBar(j2dmenubar);
        pack();        
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);       
    }
   
    
    //Start the game
    public void startNow()
    {
    	if(!STOP)
        panel.initStuff();
		
    	while(PLAY){ panel.run(); };   
    }
    
    
    private void addMenu()
    {
    	 //MENU
        //CREATE THE MENU BAR
        
        j2dmenubar.setIgnoreRepaint(true);
     
        
        //FILE MENU
        JMenu filemenu = new JMenu("File");
        filemenu.setMnemonic('F');
        		//ITEMS
        				//EXIT
        		JMenuItem exit = new JMenuItem("Exit");
        		exit.setMnemonic('Q');
        		exit.addActionListener(new ActionListener()
				{
        			public void actionPerformed( ActionEvent action )
						{
        					System.exit(0);
						}
				});
        		filemenu.add( exit );
        		
         filemenu.getPopupMenu().setLightWeightPopupEnabled(false);

        	
        // ACTION MENU
        JMenu actionmenu = new JMenu("Action");
        actionmenu.setMnemonic('A');
        		//ITEMS
        				//START
        	JMenuItem start = new JMenuItem("Start");
        	start.setMnemonic('S');
        	start.setAccelerator(KeyStroke.getKeyStroke(
      		         KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        	start.addActionListener(new ActionListener()
			{
    			public void actionPerformed( ActionEvent action )
					{panel.Play();}
			});
        	actionmenu.add(start);
        		
        				//STEP
        	JMenuItem step = new JMenuItem("Step");
        	step.setMnemonic('E');
        	step.setAccelerator(KeyStroke.getKeyStroke(
      		         KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        	step.addActionListener(new ActionListener()
			{
    			public void actionPerformed( ActionEvent action )
					{panel.Step();}
			});
        	actionmenu.add(step);
        	
        				//STOP
        	JMenuItem stop = new JMenuItem("Stop");
        	stop.setMnemonic('T');
        	stop.setAccelerator(KeyStroke.getKeyStroke(
   		         KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        	stop.addActionListener(new ActionListener()
			{
    			public void actionPerformed( ActionEvent action )
					{   panel.Stop(); }
			});
        	actionmenu.add(stop);
        	
        				//RESET
        	JMenuItem reset = new JMenuItem("Reset");
        	reset.setMnemonic(KeyEvent.VK_R);
        	reset.setAccelerator(KeyStroke.getKeyStroke(
        		         KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        	reset.addActionListener(new ActionListener()
        									{
        	      public void actionPerformed( ActionEvent action )
        	      								{
        										panel.deleteStuff();
        										panel.initStuff();
        	      								}
        									});
        	actionmenu.add(reset);
        	
        actionmenu.getPopupMenu().setLightWeightPopupEnabled(false);
        
        // ABOUT MENU
        JMenu aboutmenu = new JMenu("About");
        	//ITEMS
        				//INFO
    		JMenuItem about = new JMenuItem("Info");
    		about.addActionListener(new ActionListener()
			{
    			
      	      public void actionPerformed( ActionEvent action )
      	      								{
      	    	  		JOptionPane.showMessageDialog(null, 
      	    	  				"JUniverse "+ version+" is written by tabuto83" 
      	    	  				+"\n Using J2DGF v.0.6.2"
      	    	  				+"\nGet more information at: http://code.google.com/p/j2dgf/", 
      	    	  				"About", 
      	    	  				JOptionPane.PLAIN_MESSAGE);
      	      								}
      									});
    	aboutmenu.add(about);
    	
    	aboutmenu.getPopupMenu().setLightWeightPopupEnabled(false);
    	
    	//ADD THE MENU AT MENUBAR
        j2dmenubar.add( filemenu);
        j2dmenubar.add( actionmenu) ;
        j2dmenubar.add( aboutmenu);
        j2dmenubar.setVisible(true);
        j2dmenubar.setIgnoreRepaint(true);
        j2dmenubar.setFocusable(true);

    }
    
    private void addPanels()
    {
        panel.setFocusable(true);
        this.getContentPane().add(scroller,BorderLayout.CENTER);
        this.getContentPane().add( cp_west, BorderLayout.LINE_START);
        this.getContentPane().add( cp_east, BorderLayout.LINE_END);
        this.getContentPane().add( bottom, BorderLayout.PAGE_END);
    }
    
}
