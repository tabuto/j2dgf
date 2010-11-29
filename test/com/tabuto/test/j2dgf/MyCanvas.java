/**
* @author Francesco di Dio
* Date: 29 Novembre 2010 18.14
* Titolo: DPanel.java
* Versione: 0.5.3 Rev:34:
*/

package com.tabuto.test.j2dgf;

import java.awt.Dimension;
import com.tabuto.j2dgf.Game2D;
import com.tabuto.j2dgf.gui.J2DCanvasPanel;

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
 * DPanel: An example for use a j2dgame package. A simple extension of a DrawingPanel
 * 
 */

public class MyCanvas extends J2DCanvasPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	//Constructor Method
	public MyCanvas(Dimension d)
	{
		super(d);
	}
	

	 /**
	   * Draw a single frame animation on the CanvasPAnel
	   */
	  public void Step(Game2D Game)
	  {
		  //TODO: fix this code
		  drawStuff(Game);   // render to a buffer
	       panelDraw();  // draw buffer to screen
	  }
	 
	
	 

}


