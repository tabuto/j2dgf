package com.tabuto.test.j2dgf;

import java.awt.Graphics2D;
import com.tabuto.j2dgf.DrawPanel;
import com.tabuto.j2dgf.Sprite;
import com.tabuto.j2dgf.SpriteGroup;
import com.tabuto.j2dgf.collision.CollisionBoundDetector;
import com.tabuto.j2dgf.collision.CollisionDetector;
import com.tabuto.util.Vettore;

/**
* @author Francesco di Dio
* Date: 22 Ottobre 2010 18.14
* Titolo: DPanel.java
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
 * DPanel: An example for use a j2dgame package. A simple extension of a DrawingPanel
 * 
 */

public class DPanel extends DrawPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Max Number of Particles
	int N_Particles=15;
	// Array of Particle
	Particle[] pArray = new Particle[N_Particles + 1]; 
	//ParticleGroup
	SpriteGroup alfaGroup = new SpriteGroup("Alfa");
	//Particle collision check collision between particles
	ParticleCollision pCollision;
	//CollisionBoundDetector check collision woth the boundary
	CollisionBoundDetector cbd;

	//Constructor Method
	public DPanel(int w, int h){super(w,h);}
	
	//Override init stuff
	public void initStuff()
	{
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
			//Add the new Particle in the ParticleGroup
		    alfaGroup.AddSprite(pArray[i]);
		    
		    //Add the ID
		    //pArray[i].setId(i);
		}		
		
		/*
		Particle Sole = new Particle(DIM,512,512);
		Sole.r= 10;
		Sole.setSpeed(0);
		Sole.setId(N_Particles);
		pArray[N_Particles] = Sole;
		alfaGroup.AddSprite(Sole);
		*/
    }

	//Override the method for Sprite movement
	 protected void drawSprite(Graphics2D g)
	    {
		    //Disegna gli oggetti sul panel e ne controlla la posizione
	    	for(int i=0;i<N_Particles;i++)
	          {
	    		//Moving the sprite
	    		alfaGroup.getSprite(i).move();
	    		//CheckCollision
	    		 cm.RunCollisionManager();
	    		 //Draw the Sprites
	    		alfaGroup.getSprite(i).drawMe(g);
	    	   
	    	  }
	    }
	 
	 public void deleteStuff()
	 {
		 alfaGroup.group.clear();
		 
	 }
	 
	 
	//CollisionDetector: What does it do when a collision checked?
	public class ParticleCollision extends CollisionDetector{

		//Constructor
		 public ParticleCollision(SpriteGroup sp1)
		 {
			 super(sp1);
		 }
		 //Override CollisionAction
		 public void CollisionAction(int s1, int s2)
		  {
			//Cast to class extends Sprite 
			Particle p1,p2;
			p1 = (Particle) group1.getSpriteByHash(s1);
			p2 = (Particle) group1.getSpriteByHash(s2);
			
			//TO-DO
			
			double temp= p2.getAngle();
			
			p2.setAngleRadians(p1.getAngle());
			p1.setAngleRadians(temp);
			
			if (p1.getMass()>p2.getMass())
				p2.setRadius( p1.getRadius());
			else
				p1.setRadius( p2.getRadius());
			
			
		  }

			
		 
	}
	
}

