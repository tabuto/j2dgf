/**
* @author Francesco di Dio
* Date: 29/nov/2010 15.14.21
* Titolo: Game2D.java
* Versione: 0.7.0 Rev.a:
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
import java.io.Serializable;
import java.util.Observable;

import com.tabuto.j2dgf.collision.CollisionManager;

/**
 * Game2D is an Abstract Class that represents a single Game.
 * <p>
 * Extends this class to add the Sprites Group and your own CollisionDetector
 * Override the {@linkplain Game2D#initGame()} methods to initialize Groups and register Collsions 
 * to the CollisionManager.
 * Override the {@link Game2D#drawStuff(Graphics)} to execute the Sprite Drawing. 
 * Use the methods provides from class Group to move and/or draw your entire sprite collection.
 * <p>
 * See the GameTest class on the test package.
 * 
 * @author tabuto83
 * 
 * @version 0.7.0
 * 
 * @see Group
 * @see Sprite
 * @see CollisionManager
 * @see CollisionDetector
 * 
 */
public abstract class Game2D extends Observable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Actual Game State
	 */
	protected boolean ACTIVE=true;

	/**
	 * CollisionManager object
	 * @see CollisionManager
	 */
	protected CollisionManager cm;
	
	/**
	 * Game Playfield Dimension
	 */
	protected Dimension DIM;
	
	/**
	 * Game name, use it for save the Game
	 */
	protected String Name=""; 
	
	/**
	 * The Directory path where Game file is saved/loaded.
	 * Not yet used
	 */
	protected String PATH="";
	
	/**
	 * Actual game state, if saved (save action allows) else only saveAs permitted;
	 */
	protected boolean saved=false;

	
	/**
	 * Game2D Constructor 
	 * @param dim  Dimension of the Game's canvas playfield
	 * @see Dimension
	 */
	public Game2D(Dimension dim)
	{
		DIM=dim;
		cm = new CollisionManager();
	}
	

	/**
	 * Set this Game Active
	 */
	public void activate()
	{
		ACTIVE=true;
		//cm.resume();
	}
	
	/**
	 * Set this Game not Active, set the CollisionManager thread pool to pause
	 */
	public void deactivate()
	{
		ACTIVE=false;
		//cm.pause();
	}
	
	 /**
	  * Should be contains the operations to do for moving the sprite;<br>
	  * <p>
	  * Example:
	  * <pre>
	  *
      *		MySpriteGroup.move();
      *		MySpriteGroup.draw(g);
      *	
      *</pre>
	  * @param g {@link Graphics}
	  * @see Group
	  */
	public abstract void drawStuff(Graphics g);
	
	/**
	 * Execute the CollisionManager thread pool
	 * @see CollisionManager
	 */
	public void executeCollisionManager()
	{
		cm.execute();
	}
	
	/**
	 * @return the Playfield's Game Dimension 
	 */
	public Dimension getDimension() {
		return this.DIM;
	}
	
	/**
	 * @return the Game's name
	 */
	public String getName() {
		return Name;
	}

	 /**
	  * This <code>abstract</code> method initialize the component of the game:
	  *  <li>  Group
	  *  <li> CollisionDetector
	  *  <li> CollisionBoundDetector (if needs!)
	  *  </ul>
	  *  N.B. The  CollisionManager has been initialize in the Costructor method.
	  *  @see CollisionManager
	  *  @see Group
	  *  @see CollisionBoundDetector
	  *  @see CollisionDetector
	  */
	public abstract void initGame();
	
	/**
	 * @return ACTIVE Game2D state
	 */
	public boolean isActive(){
		return ACTIVE;
	}
	
	/**
	 * @return true if Game's saved
	 */
	public boolean isSaved() {
		return saved;
	}


	/**
	 * @param name the Game's name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	
	/**
	 * @param saved the Game's saved state to set
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * @return the Game's Saved files Path 
	 */
	public String getPATH() {
		return PATH;
	}

	
	/**
	 * @param pATH the Game's files pATH to set
	 */
	public void setPATH(String pATH) {
		PATH = pATH;
	}

	
}
