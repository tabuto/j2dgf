
/**
* @author Francesco di Dio
* Date: 22 Ottobre 2010 18.14
* Titolo: Particle.java
* Versione: 0.4.0 Rev:34:
*/

package com.tabuto.test.j2dgf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import com.tabuto.j2dgf.Sprite;
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

//This class in an example how to extends Sprite class

public class Particle extends Sprite{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361345090908913365L;
	Color c = new Color(35,35,255);
	private int radius;
	public int mass;
	
	public Particle (Dimension d, double x, double y)
	{
		super(d,x,y);
		radius = 1;
		this.setSpeed(DefaultSpeed);
	}
	
	public Color getColor(){return c;}
	
	public void ThisIsMe(Graphics g)
	{
		 g.setColor( this.getColor() );
	     g.fillOval((int)this.getX() - this.radius,(int) this.getY() - this.radius, 2*this.radius,2*this.radius);
	
	}
	
	public void setRadius(int r)
	{
		if (r>0)
			this.radius = r;
		
		//this.vector.setNewModule(this.mass * this.speed);
	
	this.setMaxOffset(r, r, r, r);
	
	}
	
	public int getRadius(){return this.radius;}

	public int getMass(){return this.mass;}

}
