/**
* @author Francesco di Dio
* Date: 26/nov/2010 15.14.21
* Titolo: Game2D.java
* Versione: 0.6.5 Rev.a:
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
  * Inserisci qui breve descrizione in italiano della classe
  */

package com.tabuto.j2dgf;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Observable;

import com.tabuto.j2dgf.collision.CollisionManager;


public abstract class Game2D extends Observable implements Serializable{

	/**
	 * Game Playfield Dimension
	 */
	protected Dimension DIM;
	
	/**
	 * Game name, use it for save the Game
	 */
	protected String Name=""; 
	
	/**
	 * Actual game state, if saved (save action allows) else only saveAs permitted;
	 */
	protected boolean saved=false;
	/**
	 * The Directory path where Game file is saved/loaded.
	 * Not yet used
	 */
	protected String PATH="";
	
	protected boolean ACTIVE=true;
	
	//COLLISIONS
	protected CollisionManager cm;
	
	/**
	 * Game2D Constructor 
	 * @param dim Dimension of the playfield canvas
	 */
	public Game2D(Dimension dim)
	{
		DIM=dim;
		cm = new CollisionManager();
	}
	

	/**
	 * Set this Game2D Active
	 */
	public void activate()
	{
		ACTIVE=true;
	}
	
	public void deactivate()
	{
		ACTIVE=false;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return ACTIVE Game2D state
	 */
	public boolean isActive(){
		return ACTIVE;
	}
	
	/**
	 * @return the saved
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * @param saved the saved to set
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * @return the pATH
	 */
	public String getPATH() {
		return PATH;
	}

	
	/**
	 * @param pATH the pATH to set
	 */
	public void setPATH(String pATH) {
		PATH = pATH;
	}

	/**
	 * @return the dIM
	 */
	public Dimension getDimension() {
		return DIM;
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
	public abstract void initGame();
	
	
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
	public abstract void drawStuff(Graphics g);
}
