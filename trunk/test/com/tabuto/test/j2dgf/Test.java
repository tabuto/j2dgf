package com.tabuto.test.j2dgf;

import javax.swing.JOptionPane;

import com.tabuto.util.MyUtils;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
			if ( MyUtils.isVersionHigherThan(1.6) )
				 {
					GameWindow main = new GameWindow();
					while(true){main.startNow();}
		         }
		       
			else
				JOptionPane.showMessageDialog(null, 
	    	  				"Your Java version lower than "+ 1.6 + ". To run this software you need JRE v.1.6 or higher. Update your Java virtual machine", 
	    	  				"Version Control", 
	    	  				JOptionPane.WARNING_MESSAGE);
	
	}

}
