/**
* @author Francesco di Dio
* Project Name: J2DGame
* Date: 11/ott/2010
* Titolo: SpriteGroup.java
* Versione: 0.1 Rev.:
**/

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
 * Questa classe rappresenta e gestisce un gruppo di Sprite
*/

package com.tabuto.j2dgf;



import java.util.Vector;

/**
 * The <code>SpirteGroup</code> class provide to manage a SpriteGroup like a Vector of Objects.
 * <p>
 * Like any other vector is possible to add or remove an element.
 * 
 * @author tabuto83
 * 
 * @version 0.1.0
 */
public class SpriteGroup {

/**
 * The Sprite vector group
 * @see #java.util.Vector
 */
public Vector<Sprite> group = new Vector<Sprite>();
private String GroupName;
private boolean ACTIVE; 

/**
 * This Method return the Group's Name 
 * @return  GroupName String
 */
public String getGroupName() {return GroupName;}

/**
 * This Method set the Group's Name
 * @param s String
 */
public void setGroupName(String s){GroupName=s;}

/**
 * CONSTRUCTOR
 * 
 * SpriteGroup (String name)
 * <p>
 * The Constructor set the name of the group
 * @param name String
 */
 public SpriteGroup(String name)
 {
	 GroupName = name;
	 ACTIVE = true;
 }

 /**
  * This method add an element in the group Vector
  * @param  s {@link Sprite}
  */
 
 public void AddSprite(Sprite s)
 {
	 group.add(s);
 }
 /**
  * Remove a Sprite from the Group Vector
  * @param p {@link Sprite}
  */
 public void RemoveSprite(Sprite p)
 
 {
	 int index;
	 index = FindSprite(p);
	 group.get(index).ACTIVE=false;
	 group.removeElementAt(index);
	 group.trimToSize();
	 
 }

 /**
  * Find the <code>Sprite</code> p in the group Vector and returns the element's vector index 
  * @param  p {@link Sprite}
  * @return indexVector int
  */
 private int FindSprite(Sprite p)
 {
	 int size = group.size();
	 int i;
	 for (i=0; i<size; i++)
	 {
		 if (group.get(i).getId() == p.getId())
			 break;
		 
		 
	 }
	  
	 return i;
	 
 }
 
 /**
  * Returns the element's number of the group
  * @return Size int
  */
 public int getSize()
 {
	return group.size(); 
 }
 
 /**
  * Find a sprite with a specific ID
  * @param id
  * @return s {@link Sprite}
  */
 public Sprite getSpriteById(int id)
 {

	 int size = group.size();
	 int i;
	 for (i=0; i<size; i++)
	 {
		 if (group.get(i).getId() == id)
			 break;
		  	
	 }
	 return group.get(i);	
 }
 
 /**
  * Return a Sprite by the index
  * @param index int
  * @return {@link Sprite}
  */
 public Sprite getSprite(int index)
 {
	 return group.get(index);
	 
 }
 
 /**
  * Set true the group ACTIVE
  */
 public void Activate() {this.ACTIVE = true;}
 
 /**
  * Set false the group ACTIVE
  */
 public void Deactivate() {this.ACTIVE = false;}
 
 /**
  * Set false the ACTIVE flag of every sprite owened by this group.
  * Set false the ACTIVE flag of this group
  */
 public void DeactiveAll()
 {
	 int size = group.size();
	 int i;
	 for (i=0; i<size; i++)
		 group.get(i).Deactivate();
	 
	 this.ACTIVE = false;
	 
 }
 
 /**
  * Set true the ACTIVE flag of every sprite owened by this group.
  * Set true the ACTIVE flag of this group
  */
 public void ActiveAll()
 {
	 int size = group.size();
	 int i;
	 for (i=0; i<size; i++)
		 group.get(i).Activate();
	 
	 this.ACTIVE = true;
 }
 
 /**
  * Return the state of this group
  * @return boolean ACTIVE
  */
 public boolean isActive(){return this.ACTIVE;}
 
}

