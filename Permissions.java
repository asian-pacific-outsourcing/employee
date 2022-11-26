package com.apo.employee;
/********************************************************************
* @(#)Permissions.java 1.00 20110316
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* Permissions: A job function immutable model object. Associates an access
* level with a job code, a short description, and a long description.
* Also maintains a static object for each role, as well as a list of
* all defined roles. The static method find retrieves the Role given
* it's access level.
*
* @author Rick Salamone
* @version 1.00, 20110316 rts created
*******************************************************/

public class Permissions
	{
	public static final short DBA           = 0;
	public static final short USER_ADD      = 1;
	public static final short USER_EDIT     = 2;
	public static final short ROLE_EDIT     = 3;
	public static final short RAW_ADD       = 4;
	public static final short RAW_ADDLEAD   = 5;
	public static final short RAW_ASSIGN    = 6;
	public static final short RAW_OWN       = 7;
	public static final short RAW_VIEWALL   = 8;
	public static final short RAW_VIEWOWN   = 9;
	public static final short RAW_VIEWDUE   = 10;
	public static final short CALL_AUTONEXT = 11;
	public static final short CALL_POSTPONE = 12;
	public static final short EMAIL_WELCOME = 13;
	public static final short HIST_VIEW     = 14;
	public static final short HIST_COMMENT  = 15;
	public static final short MAIL_REQWELCOME = 16;
	public static final short MAIL_REQALL     = 17;
	public static final short MAIL_SEND       = 18;
	public static final short MAIL_SENDALL    = 19;
	public static final short ORD_ADD         = 20;
	public static final short ORD_CAN         = 21;
	public static final short ORD_EDIT        = 22;
	public static final short ORD_VIEW        = 23;
	public static final short ORD_VIEWALL     = 24;
	
	public static final short NUM_PERMISSIONS = ORD_VIEWALL + 1;
	}