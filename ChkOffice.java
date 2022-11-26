package com.apo.employee;
/********************************************************************
* @(#)ChkOffice.java	1.0 10/06/03
* Copyright 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* ChkOffice: Check box selector for officess.
*
* @author Rick Salamone
* @version 1.00, 20110311 rts
*******************************************************/
import com.apo.employee.Office;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public final class ChkOffice extends JPanel
	{
	JCheckBox[] chkOffices;
	public ChkOffice()
		{
		this ( 1, 0 );
		}

	public ChkOffice(int rows, int cols)
		{
		super ( new GridLayout( rows, cols, 0, 0 ));
		int num = Office.countAll();
		chkOffices = new JCheckBox[num];
		int i = 0;
		for ( Office d : Office.getAll())
			{
			chkOffices[i] = new JCheckBox( d.toString());
	//		chkOffices[i].setToolTipText( d.name() + " (" + d.id() + ")");
			add( chkOffices[i++] );
			}
		}

	public ChkOffice(Office... offices)
		{
		this();
		select( offices );
		}

	public void select ( Office... offices )
		{
		for ( Office d : offices )
			for ( JCheckBox chk : chkOffices )
				if ( chk.getText().equals(d.toString()))
					chk.setSelected(true);
		}

	public boolean isSelected ( Office d )
		{
		for ( JCheckBox chk : chkOffices )
			if ( chk.getText().equals(d.toString()))
				return chk.isSelected();
		return false;
		}

	public Office[] getSelected()
		{
		int numSelected = 0;
		for ( JCheckBox chk : chkOffices )
			if ( chk.isSelected())
				++numSelected;
		Office[] selected = new Office[numSelected];
		int i = 0;
		for ( JCheckBox chk : chkOffices )
			if ( chk.isSelected())
				try { selected[i++] = Office.parse( chk.getText()); }
				catch(Exception e) {} // should not be possible
		return selected;
		}
	}
