package com.tabuto.test.j2dgf;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;

import com.tabuto.j2dgf.gui.J2DControlPanel;

public class MyControlRightPanel extends J2DControlPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4975971744365752259L;

	public MyControlRightPanel(Dimension d)
	{
		super(d);
		addContent();
		this.setLayout(new FlowLayout());
	}
	
	protected void addContent()
	{
	
		 
		 JButton Right = new JButton("RIGHT");
		 this.add(Right);
	}
	

}
