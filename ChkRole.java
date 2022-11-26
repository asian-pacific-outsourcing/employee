package com.apo.employee;
/********************************************************************
* @(#)ChkRole.java	1.0 10/06/03
* Copyright 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* ChkRole: Check box selector for roless.
*
* @author Rick Salamone
* @version 1.00, 20110311 rts
*******************************************************/
import com.apo.employee.Role;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public final class ChkRole
	extends JPanel
	{
	JCheckBox[] chkRoles;
	public ChkRole()
		{
		this ( 1, 0 );
		}

	public ChkRole(int rows, int cols)
		{
		super ( new GridLayout( rows, cols, 0, 0 ));
		int num = Role.countAll();
		chkRoles = new JCheckBox[num];
		int i = 0;
		for ( Role d : Role.getAll())
			{
			chkRoles[i] = new JCheckBox( d.code());
			chkRoles[i].setToolTipText( d.longName());
			add( chkRoles[i++] );
			}
		}

	public ChkRole(Role... roles)
		{
		this();
		select( roles );
		}

	public void select ( Role... roles )
		{
		for ( Role d : roles )
			for ( JCheckBox chk : chkRoles )
				if ( chk.getText().equals(d.code()))
					chk.setSelected(true);
		}

	public boolean isSelected ( Role d )
		{
		for ( JCheckBox chk : chkRoles )
			if ( chk.getText().equals(d.code()))
				return chk.isSelected();
		return false;
		}

	public Role[] getSelected()
		{
		int numSelected = 0;
		for ( JCheckBox chk : chkRoles )
			if ( chk.isSelected())
				++numSelected;
		Role[] selected = new Role[numSelected];
		int i = 0;
		for ( JCheckBox chk : chkRoles )
			if ( chk.isSelected())
				try { selected[i++] = Role.parse( chk.getText()); }
				catch(Exception e) {} // should not be possible
		return selected;
		}
	}
