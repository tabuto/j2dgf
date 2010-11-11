/**
* @author Francesco di Dio
* Date: 05 Novembre 2010 18.14
* Titolo: DPanel.java
* Versione: 0.5.0 Rev:34:
*/

package com.tabuto.test.j2dgf;

import java.awt.Graphics;

import com.tabuto.j2dgf.Group;
import com.tabuto.j2dgf.Sprite;
import com.tabuto.j2dgf.SpriteGroup;
import com.tabuto.j2dgf.collision.CollisionAction;
import com.tabuto.j2dgf.collision.CollisionBoundDetector;
import com.tabuto.j2dgf.collision.CollisionDetector;
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
	
	// Max Number of Particles
	int N_Particles=15;
	// Array of Particle
	Particle[] pArray = new Particle[N_Particles + 1]; 
	//ParticleGroup
	Group<Particle> alfaGroup = new Group<Particle>();
	//Group<Particle> betaGroup= new Group<Particle>("Beta");
	//Particle collision check collision between particles
	ParticleCollision pCollision;
	//CollisionBoundDetector check collision woth the boundary
	CollisionBoundDetector cbd;

	//Constructor Method
	public MyCanvas(int w, int h){super(w,h);}
	
	//Override init stuff
	public void initStuff()
	{
		alfaGroup.setGroupName("alfa");
		//Init Particle Collision
		pCollision = new ParticleCollision(alfaGroup);
		pCollision.setDistance(0);
		//Init CollisionBoundDetector
		cbd = new CollisionBoundDetector(alfaGroup,DIM);
	  
		
		//CollisionBoundDetector must use Reflection Collision
		cbd.useReflection();
		
		//CollisionBoundDetector must use PassThrough
		//cbd.usePassThrough();
		
		//CollisionBoundDetector must use PassThrough
		//cbd.useBounce();
		
		//Registering the collisionDetectors in a CollisionManager
		cm.addCollision(cbd);
		cm.addCollision(pCollision);
		
		//Init the sprites
		for (int i = 0; i< ( N_Particles );i++)
		{
			//Random coordinates
			int RandomX = (int) (1+Math.random() * (DIM.width -5) );
			int RandomY = (int) (1+Math.random() * (DIM.height-5) );
			//Create a new Particle SPrite
			pArray[i] = new Particle(DIM,RandomX , RandomY);
			//Random Angle
			pArray[i].setAngleRadians(Math.random()*2 * Math.PI );
			//Random radius
		    pArray[i].setRadius(  (int)( 1+Math.random()*11 )); // Assegno un raggio casuale
			pArray[i].setSpeed(80);
		    //Add the new Particle in the ParticleGroup
		    alfaGroup.add(pArray[i]);
		}		
		
    }

	//Override the method for Sprite movement
	 protected void drawSprite(Graphics g)
	    {
		    //Disegna gli oggetti sul panel e ne controlla la posizione
	    	for(int i=0;i<N_Particles;i++)
	          {
	    		//Moving the sprite
	    		alfaGroup.get(i).move();
	    		//CheckCollision
	    		 cm.RunCollisionManager();
	    		 //Draw the Sprites
	    		alfaGroup.get(i).drawMe(g);
	    	   
	    	  }
	    }
	 
	 public void deleteStuff()
	 {
		 alfaGroup.clear();
		 
	 }
	 
	 
	//CollisionDetector: What does it do when a collision checked?
	public class ParticleCollision extends CollisionDetector{

		Particle p1,p2;
		//Constructor
		 public ParticleCollision(Group<Particle> sp1)
		 {
			 super(sp1);
		 }
		 
		 //Override CollisionAction
		public void CollisionAction(int s1, int s2)
		  {
			//Cast to class extends Sprite 
			
			p1 = (Particle) this.group1.get(s1); 
			p2 = (Particle) this.group1.get(s2); 
			
			double temp= this.group1.get(s1).getAngle();
			
			this.group1.get(s1).setAngleRadians(group1.get(s2).getAngle());
			this.group1.get(s2).setAngleRadians(temp);
			
			if (p1.getMass()>p2.getMass())
				p2.setRadius( p1.getRadius());
			else
				p1.setRadius( p2.getRadius());
			
			
		  }

			
		 
	}
}


