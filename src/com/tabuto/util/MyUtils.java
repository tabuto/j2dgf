/**
* @author Francesco di Dio
* Date: 07/nov/2010 15.27.44
* Titolo: MyUtils.java
* Versione: 0.1 Rev.a:
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
  * Classe che esporta metodi statici di diversa utilità
  */

package com.tabuto.util;

import java.util.Properties;

/**
 * Class <code>MyUtils</code> provides Static facilities multi-purphose methods.
 * 
 * @author tabuto83
 *
 * @version 0.1.0
 */
public class MyUtils {

	/**
	 * Return true is the current JVM version executes this code is equals or higher than version parameter
	 * @param version <code>double</code> 
	 * @return <code>boolean</code>
	 */
	public static boolean isVersionHigherThan(double version)
	{
		float v =(float) version;
		boolean result = false;
		String sVersion ="";
		Properties sProp = java.lang.System.getProperties();
		sVersion = sProp.getProperty("java.version");
		sVersion = sVersion.substring(0,3);
		Float f = Float.valueOf(sVersion);
			if (f.floatValue() >= (float) v)
				return result = true;
		
			return result;
	}

	/**
	 * Return true is the current JVM version executes this code is equals or lower than version parameter
	 * @param version <code>double</code> 
	 * @return <code>boolean</code>
	 */
	public static boolean isVersionLowerThan(double version)
	{
		float v =(float) version;
		boolean result = false;
		String sVersion ="";
		Properties sProp = java.lang.System.getProperties();
		sVersion = sProp.getProperty("java.version");
		sVersion = sVersion.substring(0,3);
		Float f = Float.valueOf(sVersion);
			if (f.floatValue() <= (float) v)
				return result = true;
		
			return result;
	}
}
