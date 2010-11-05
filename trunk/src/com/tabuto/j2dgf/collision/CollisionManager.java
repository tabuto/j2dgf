/**
* @author Francesco di Dio
* Date: 05 Novembre 2010
* Titolo: CollisionManager.java
* Versione: 0.5 Rev.:
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
 * Questa classe non fa altro che gestire una serie di CollisionDetector, 
 * Ha un metodo Check che passa in rassegna tutti i collisionDetector registrati 
 */

package com.tabuto.j2dgf.collision;

import java.util.Vector;

/**
 * 
 * class <code>CollisionManager</code>
 * <p>
 * This class manage a vector of CollisionDetector. <p>
 * Add Every CollisionDetector to this class, and the method {@link #RunCollisionManager()} checks every  {@linkplain CollisionDetector}
 * using the method {@linkplain CollisionDetector#checkCollision()}
 *
 * @author tabuto83
 * 
 * @version 0.1.0
 * 
 * @see Vector
 */

public class CollisionManager {
	
	/**
	 * The collisionDetector Vector
	 */
	private Vector<CollisionDetector> CollisionsList; 
	
	
	/**
	 * CONSTRUCTOR 
	 * <p>
	 * public CollisionManager()
	 * <p>
	 * The constructor instances the CollisionDetector Vector
	 */
	public CollisionManager()
	{
		CollisionsList = new Vector<CollisionDetector>();
	}
	
	/**
	 * Add a collision detector on the CollisionManager
	 * @param  cd {@link CollisionDetector}
	 */
	public void addCollision(CollisionDetector cd)
	{
		CollisionsList.add(cd);
	}
	
	/**
	 * Delete a collisionDetector from CollisionManager
	 * @param cd {@link CollisionDetector}
	 */
	public void delCollision(CollisionDetector cd)
	{
		CollisionsList.remove(cd);
	}
	
	/**
	 * Delete all CollisionDetector from CollisionManager
	 */
	public void clearCollision()
	{
		CollisionsList.clear();
	}
	
	/**
	 * Calls every {@linkplain Sprite } in all CollisionDetector.
	 * registered in this <code>CollisionManager</code>.
	 */
	public void RunCollisionManager()
	{
		int max= CollisionsList.size();
		for (int i=0; i<max;i++)
			CollisionsList.get(i).checkCollision();
	}
	

}
