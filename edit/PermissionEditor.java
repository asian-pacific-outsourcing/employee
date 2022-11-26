package com.apo.employee.edit;
/********************************************************************
* @(#)PermissionEditor.java 1.00 20110316
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* PermissionEditor: A job function immutable model object. Associates an access
* level with a job code, a short description, and a long description.
* Also maintains a static object for each role, as well as a list of
* all defined roles. The static method find retrieves the Role given
* it's access level.
*
* @author Rick Salamone
* @version 1.00, 20110316 rts created
*******************************************************/
import com.apo.employee.*;
import java.awt.*;
import javax.swing.*;

public class PermissionEditor
	extends JPanel
	{
	public PermissionEditor(int aColumns)
		{
		super(new GridLayout( 0, aColumns ));
		for ( ChkPermission chkBox : fChkPermissions )
			add( chkBox );
		}

	public void set(String aPermString)
		{
		for ( ChkPermission chkBox : fChkPermissions )
			if ( chkBox.index() >= aPermString.length())
				chkBox.set( '0' );
			else
				chkBox.set( aPermString.charAt(chkBox.index()));
		}

	public String get()
		{
		String it = "";
		for ( short i = 0; i < Permissions.NUM_PERMISSIONS; i++ )
			it += get(i);
		return it;
		}

	private char get( short aIndex )
		{
		for ( ChkPermission chkBox : fChkPermissions )
			if ( chkBox.index() == aIndex) return chkBox.value();
		return ' ';
		}

	private final ChkPermission[] fChkPermissions =
		{
		new ChkPermission( Permissions.DBA           , "DBA" ),
		new ChkPermission( Permissions.USER_ADD      , "User Add" ),
		new ChkPermission( Permissions.USER_EDIT     , "User Edit" ),
		new ChkPermission( Permissions.ROLE_EDIT     , "Role Edit" ),
		new ChkPermission( Permissions.RAW_ADD       , "Raw Add" ),
		new ChkPermission( Permissions.RAW_ADDLEAD   , "Raw Add Lead" ),
		new ChkPermission( Permissions.RAW_ASSIGN    , "Raw Assign" ),
		new ChkPermission( Permissions.RAW_OWN       , "Raw Own" ),
		new ChkPermission( Permissions.RAW_VIEWALL   , "Raw View All" ),
		new ChkPermission( Permissions.RAW_VIEWOWN   , "Raw View Owned" ),
		new ChkPermission( Permissions.RAW_VIEWDUE   , "Raw View Due" ),
		new ChkPermission( Permissions.CALL_AUTONEXT , "Call AutoNext" ),
		new ChkPermission( Permissions.CALL_POSTPONE , "Call Postpone" ),
		new ChkPermission( Permissions.EMAIL_WELCOME , "Email Welcome" ),
		new ChkPermission( Permissions.HIST_VIEW     , "History View" ),
		new ChkPermission( Permissions.HIST_COMMENT  , "History Comment" ),
		new ChkPermission( Permissions.MAIL_REQWELCOME , "Request Brochure" ),
		new ChkPermission( Permissions.MAIL_REQALL     , "Mail Request All" ),
		new ChkPermission( Permissions.MAIL_SEND       , "Mail Send" ),
		new ChkPermission( Permissions.MAIL_SENDALL    , "Mail Send All" ),
		new ChkPermission( Permissions.ORD_ADD         , "Order Add" ),
		new ChkPermission( Permissions.ORD_CAN         , "Order Cancel" ),
		new ChkPermission( Permissions.ORD_EDIT        , "Order Edit" ),
		new ChkPermission( Permissions.ORD_VIEW        , "Order View" ),
		new ChkPermission( Permissions.ORD_VIEWALL     , "Order View All" ),
		};
	}

final class ChkPermission
	extends JCheckBox
	{
	private final short fIndex;

	public ChkPermission( short aIndex, String aDesc )
		{
		super( aDesc + ": " + aIndex );
		fIndex = aIndex;
		}

	public short index() { return fIndex; }
	public char value() { return isSelected()? '1' : '0'; }
	public void set(char x) { setSelected( x == '1' ); }
	}
