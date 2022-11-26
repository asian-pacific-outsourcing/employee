package com.apo.employee.edit;
/********************************************************************
* @(#)EditRoleAction.java 1.01 20110316
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* EditRoleAction: Displays a modal dialog for editing Role objects which
* essentially configure various client applications.
*
* @author Rick Salamone
* @version 1.00 20110316 rts created
*******************************************************/
import com.apo.employee.*;
import com.shanebow.dao.*;
import com.apo.net.Access;
import com.apo.net.SysDAO;
import com.shanebow.ui.LAF;
import com.shanebow.ui.SBAction;
import com.shanebow.ui.SBDialog;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public final class EditRoleAction
	extends SBAction
	{
	/**
	* Constructor. 
	*/
	public EditRoleAction()
		{
		super( "Role Editor", 'R', "Configure applications", null );
		}

	@Override public void actionPerformed(ActionEvent evt)
		{
		Role role = editRole(Access.getRole());
		if ( role != null )
//			System.out.println( "" + role.marshall() );
			try { SysDAO.DAO().setRole(role); }
			catch (Exception x) { SBDialog.error("Role DAO Error", x.getMessage()); }
		}

	private Role editRole(Role role)
		{
		RoleEditor editor = new RoleEditor();
		editor.set(role);
		String[] options = { "Save", "Cancel" };
		while (true)
			{
			int selected = JOptionPane.showOptionDialog(null, editor,
				LAF.getDialogTitle(this.toString()), JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0] );
			if ( selected != 0 ) return null;
			return editor.getRole();
			}
		}
	}
