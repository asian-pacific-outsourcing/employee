package com.apo.employee;
/********************************************************************
* @(#)DlgUser.java	1.13 10/04/07
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* DlgUser: allows the admin to add, edit or delete a user.
*
* @version 1.00 04/07/10
* @author Rick Salamone
* 20100627 RTS 1.01 position at lower right corner of screen
* 20100627 RTS 1.02 responds to change in theme menu
*******************************************************/
import com.apo.employee.UserTableModel;
import com.apo.employee.User;
import com.shanebow.ui.SBDialog;
import com.shanebow.ui.layout.LabeledPairLayout;
import com.shanebow.ui.LAF;
import com.shanebow.util.SBLog;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.border.*;

public class DlgUser extends JDialog
	implements ActionListener
	{
	private static final String CMD_COMMIT="Save";
	private static final String CMD_DELETE="Delete";
	private static final String CMD_CANCEL="Close";

	private void log ( String fmt, Object... args )
		{
		SBLog.write( "DlgUser", String.format(fmt, args ));
		}

	public DlgUser(JFrame f)
		{
		super(f, "Users", false);

		JPanel top = new JPanel(new BorderLayout());
		top.setBorder(LAF.getStandardBorder());
		top.setPreferredSize(new Dimension(600,330));
		top.add(userTable(), BorderLayout.CENTER);
		top.add(btnPanel(), BorderLayout.SOUTH);
		setContentPane(top);
		pack();
		centerDialog();
		LAF.addUISwitchListener(this);
		}

	protected void centerDialog()
		{
		Dimension screenSize = this.getToolkit().getScreenSize();
		Dimension size = this.getSize();
		screenSize.height = screenSize.height/2;
		screenSize.width = screenSize.width/2;
		size.height = size.height/2;
		size.width = size.width/2;
		int y = screenSize.height - size.height;
		int x = screenSize.width - size.width;
		this.setLocation(x,y);
		}

	private JComponent userTable()
		{
		JTable table = new JTable(new UserTableModel());
		return new JScrollPane(table);
		}

	private JPanel btnPanel()
		{
		JPanel p = new JPanel();
		JButton btnCommit = makeButton(CMD_COMMIT);
		p.add( btnCommit );
//		p.add( makeButton(CMD_DELETE));
		p.add( makeButton(CMD_CANCEL));
		getRootPane().setDefaultButton(btnCommit);
		return p;
		}

	private JButton makeButton(String caption)
		{
		JButton b = new JButton(caption);
		b.addActionListener(this);
		return b;
		}

	public void actionPerformed(ActionEvent e)
		{
		String cmd = e.getActionCommand();
		if ( cmd.equals(CMD_COMMIT))
			onCommit();
		else if ( cmd.equals(CMD_DELETE))
			onDelete();
		else if ( cmd.equals(CMD_CANCEL))
			setVisible(false);
		}

	private void onCommit()
		{
log( "Commit not implemented" );
		User.freeze("apoUsers.csv");
//		long id = contactFields.getID();
//		if ( contactFields.updateContact())
//			firePropertyChange( "UPDATED USER", 0, id );
		}

	private final boolean onDelete()
		{
log( "Delete not implemented" );
	/***************
		long id = contactFields.getID();
		if ( SBDialog.confirm( "Are you sure you want to permanently delete this user?" )
		&&   Queries.deleteUser(id))
			{
			firePropertyChange( "DELETED CONTACT", 0, id );
			return true;
			}
	***************/
		return false;
		}
	}
