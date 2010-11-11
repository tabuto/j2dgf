/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 18.14
* Titolo: J2DBottomPanel.java
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
 * Classe che rappresenta unJPanel all'interno del quale è possibile aggiungere
 * componenti swing per il controllo del gioco. In particolare è ottimizzato per rapresentare
 * il pannello in basso al frame (COme barre di stato...)
 */

package com.tabuto.j2dgf.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Abstract class <code>J2DBottomPanel</code> extends JPanel.
 * <p>
 * This Class implements a Bottom ControlPanel in which Swing components can adds. <br>
 * This Panel has the same WIDTH of the Parent's JFRame in which it can be add, and a specified
 * HEIGHT (default value 50px);
 * This Class declares an abstract addContent() method.
 * To use this class, simple extends it and override the {@link J2DControlPanel#addContent()}
 * to add a swing Components.
 * The SubClass Constructor should must be implements as the follow:
 * <pre>
 * public class MyControlPanel extends J2DControlPanel {
 * 
 * public MyControlPanel(Dimension FrameDimension){
 * 	super(FrameDimension);
 * 	this.addContent();
 * }
 * 
 * public addContents()
 * {
 *      //AddButton and controls Here
 * }
 * </pre>
 * 
 * @author tabuto83
 *
 * @version 0.6.0
 */
public abstract class J2DBottomPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5292309279791010152L;
	
	/**
	 * Default Panel HEIGHT
	 */
	private int HEIGHT=50;
	/**
	 * Default Panel WIDTH
	 */
	private int WIDTH=50;
	/**
	 * Minimum Panel Size
	 */
	private Dimension MinimumSize = new Dimension(WIDTH,HEIGHT);
	/**
	 * Preferred Panel Size
	 */
	private Dimension PreferredSize;
	/**
	 * Parent's Frame Dimension
	 */
	private Dimension FrameSize = new Dimension();

	/**
	 * CONSTRUCTOR
	  * <p>
	  * public J2DBottomPanel(Dimension d)
	  * <p>
	 * @param d Parent's Frame <code>Dimension</code>
	 */
	public J2DBottomPanel(Dimension d)
	{
	super();
	FrameSize = d;
	PreferredSize = new Dimension(FrameSize.width, HEIGHT);
	setSize(PreferredSize);
	setLayout (new BorderLayout());
	}
	
	 /**
	  * Abstract method using to add Swing component at this panel
	  */
	protected abstract void addContent();
	
	 /**
	  * Return the PreferredSize of this Panel as a {@link Dimension}
	  */
	 public Dimension getPreferredSize()
	  {
	     return PreferredSize;
	  }
	 
	 /**
	  * Return the Minimum Size of this Panel as a {@link Dimension}
	  */
	 public Dimension getMinimumSize()
	  {  
	     return MinimumSize;
	  }
	 /**
	  * Set the MinimumSize of this Panel as a {@link Dimension}
	  * @param min <code>Dimension</code>
	  */
	 public void setMinimumSize(Dimension min)
	 {
		this.MinimumSize = min;
	 }
}
