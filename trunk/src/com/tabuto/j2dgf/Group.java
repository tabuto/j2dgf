/**
* @author Francesco di Dio
* Date: 19/nov/2010 21.41.16
* Titolo: Group.java
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

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Vector;

import com.tabuto.util.Point;

/**
* Group is a variable size contiguous indexable array of {@code Sprite}. The size of
* the vector is the number of {@code Sprite} it contains. The capacity of the vector
* is the number of objects it can hold.
* <p>
* Group can be contains every object extends {@code Sprite}. Every Group has a Name,
* and it is possible to Active/Deactive Group, or Active/Deactive single {@code Sprite}
*  
* @see Sprite
* 
* @author tabuto83
* 
* @version 0.6.5
*/


public class Group<E extends Sprite> extends Vector<E> implements Serializable {


	/**
	 * Not use
	 */
	//private static final long serialVersionUID = 2075695098828857052L;

	/**
	 * Name of the group
	 */
	private String GroupName;
	
	/**
	 * Current Group state: Default=true;
	 */
	private boolean ACTIVE=true; 
	
	/**
	* Constructs a new instance of {@code Group} containing the elements as Sprite or Sprite subclass in
	* {@code collection}.
	*/
	public Group(){super();}
	
	/**
	* Constructs a new instance of {@code Group} containing the elements as Sprite or Sprite subclass in
	* {@code collection}.
	* @param name the Group Name
	*/
	public Group(String name){super();this.setGroupName(name);}
	
	 /**
      * Constructs a new Group using the specified capacity and capacity
	  * increment.
	  * 
	  * @param capacity
	  *            the initial capacity of the new vector.
	  * @param capacityIncrement
	  *            the amount to increase the capacity when this vector is full.
	  * @throws IllegalArgumentException
	  *             if {@code capacity} is negative.
	  */

    public Group(int capacity, int capacityIncrement) {
		   if (capacity < 0) 
		   {
			   throw new IllegalArgumentException();
		   }
		   	elementData = newElementArray(capacity);
		   	elementCount = 0;
		   	this.capacityIncrement = capacityIncrement;
		        }

	
/**
* Constructs a new instance of {@code Group} containing the elements as Sprite or Sprite subclass in
* {@code collection}. The order of the elements in the new {@code Vector}
* is dependent on the iteration order of the seed collection.
* 
* @param collection
*            the collection of elements to add.
*/
public Group(Vector<? extends E> collection) 
	{
			this(collection.size(), 0);
			java.util.Iterator<? extends E> it = collection.iterator();
		while (it.hasNext()) {
				elementData[elementCount++] = it.next();
							}

	}

/**
* Constructs a new instance of {@code Group} containing the elements as Sprite or Sprite subclass in
* {@code collection} and set the Group name as 'name'. The order of the elements in the new {@code Vector}
* is dependent on the iteration order of the seed collection.
* 
* @param collection
*            the collection of elements to add.
* @param name The {@code String} contains the name of this Group
*/
public Group(Vector<? extends E> collection, String name) 
{
		this();
		this.setGroupName(name);
}

/**
 * Set true the group ACTIVE
 */
public void activate() {this.ACTIVE = true;}

/**
 * Set as Active all Group's Sprite 
 */
public void activeAll()
{
	for(int i=0;i<this.size();i++)
	  this.get(i).Activate();
}

/**
* Adds the specified object at the end of this vector.
* 
* @param object
*            the object to add to the vector.
* @return {@code true}
*/
public synchronized boolean addSprite(E object) 
	{
		if (elementCount == elementData.length) {
        this.growByOne();
     }
      elementData[elementCount++] = object;
      modCount++;
      return true;
      }

/**
 * Set false the group ACTIVE
 */
public void deactivate() {this.ACTIVE = false;}

/**
 * Set as Not Active all Group's Sprite
 */
public void deactiveAll()
{
	for(int i=0;i<this.size();i++)
	  this.get(i).Deactivate();
}

/**
 * Draw all the Group Active Sprite
 */
public void draw(Graphics g)
{
	for(int i=0;i<this.size();i++)
	{ 	if(this.get(i).isActive())
			this.get(i).drawMe(g);
		else
			{ remove(i); trimToSize();}
		
	}
}

/**
 * This Method return the Group's Name 
 * @return  GroupName String
 */
public String getGroupName() {return GroupName;}

/**
 * Find a sprite with a specific ID
 * @param hash int Sprite's Object Hash
 * @return s {@link Sprite}
 * @see Object#hashCode()
 */

@SuppressWarnings("unchecked")
public synchronized E getSpriteByHash(int hash) 
{
	            for(int i=0;i<this.size();i++)
	            {	if (elementData[i].hashCode()==hash)
	            		return (E) elementData[i];
	            }
	        throw new IndexOutOfBoundsException();
}

/**
 * Return a sprite if its distance from Point(x,y) is minus than tolerance
 * @param x The x coordinate point in which search the sprite
 * @param y The y coordinate point in which search the sprite
 * @param tolerance
 * @return Object
 */
public synchronized E getSpriteByPos(int x, int y, int tolerance)
{
	 int find=-1;
	 Point click = new Point(x,y);
	 for(int i=0;i<this.size();i++)
     {	double dist = this.get(i).getPosition().getDistance(click);
		 if( this.get(i).getPosition().getDistance(click) < tolerance )
			 {find=i;break;}
     }
	 
	 if(find>=0)
		 return this.get(find);
	 else
		 return null;
}

/**
* JIT optimization
*/
private void growByOne() {
           int adding = 0;
           if (capacityIncrement <= 0) {
               if ((adding = elementData.length) == 0) {
                  adding = 1;
              }
          } else {
              adding = capacityIncrement;
          }
   
          E[] newData = newElementArray(elementData.length + adding);
           System.arraycopy(elementData, 0, newData, 0, elementCount);
          elementData = newData;
      }
  
@SuppressWarnings("unused")
private void growBy(int required) {
           int adding = 0;
       if (capacityIncrement <= 0) {
              if ((adding = elementData.length) == 0) {
                   adding = required;
              }
             while (adding < required) {
                   adding += adding;
             }
          } else {
              adding = (required / capacityIncrement) * capacityIncrement;
              if (adding < required) {
                  adding += capacityIncrement;
              }
       }
         E[] newData = newElementArray(elementData.length + adding);
           System.arraycopy(elementData, 0, newData, 0, elementCount);
          elementData = newData;
     }

/**
 * Return the state of this group
 * @return boolean ACTIVE
 */
public boolean isActive(){return this.ACTIVE;}

@SuppressWarnings("unchecked")
private E[] newElementArray(int size) 
  {
      return (E[]) new Object[size];
  }

/**
 * Moves all the Sprite
 */
public void move()
{
	for(int i=0;i<this.size();i++)
	if(this.get(i).isActive()==true)
		this.get(i).move();
	else
	{this.remove(i);trimToSize();}
}

/**
 * Set as Active the Sprite at index i
 * @param i int
 */
public void setActive(int i)
{
	this.get(i).Activate();
}

/**
 * Deactive Sprite at Index i
 * @param i int
 */
public void setDeactive(int i)
{
	this.get(i).Deactivate();
}

/**
 * This Method set the Group's Name
 * @param s String
 */
public void setGroupName(String s){GroupName=s;}


public synchronized E selectSprite(Point p, int tolerance)
	{
	 int find=-1;
	for(int i=0;i<this.size();i++)
		if ( this.get(i).getPosition().getDistance(p) < tolerance)
			{find=i;break;}
	
	 if(find>0)
		 return this.get(find);
	 else
		 return null;
	
	}


}