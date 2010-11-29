package com.tabuto.test.j2dgf;


import java.awt.Dimension;


import javax.swing.JLabel;

import com.tabuto.j2dgf.gui.J2DBottomPanel;

public class MyBottomPanel extends J2DBottomPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4975971744365752259L;

	public MyBottomPanel(Dimension d)
	{
		super(d);
		addContent();
	}
	
	protected void addContent()
	{
			JLabel StateBar = new JLabel();
			StateBar.setText("  JUniverse v.0.7.0 by Tabuto83");
			this.add(StateBar);
	}
	

}