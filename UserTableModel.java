package com.apo.employee;
/********************************************************************
* @(#)UserTableModel.java	1.00 20100911
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* UserTableModel: Manages the table data for adding/modifying the
* users of the system.
*
* @version 1.00 20100911
* @author Rick Salamone
*******************************************************/
import com.apo.contact.*;
import com.apo.net.Access;
import com.apo.employee.User;
import com.shanebow.dao.*;
import com.shanebow.util.SBLog;
import java.util.List;
import java.sql.*;
import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel
	{
	private static final String MODULE="UTM";
	List<User>    m_rows = new java.util.Vector<User>();

	// constructor code
		{
		for ( User usr : User.getAll())
			m_rows.add(usr);
		fireTableDataChanged();
		}

	private void log( String fmt, Object... args )
		{
		SBLog.write( MODULE, String.format(fmt,args));
		}

	private static final String[] columnNames
		= { "id", "login", "password", "name", "office", "phone",
		    "DBA", "MGR", "DM", "RD", "TQ", "VO", "AO", "LO" }; // , "access" };
	private static final int COL_ID = 0;
	private static final int COL_LOGIN = 1;
	private static final int COL_PASSWORD = 2;
	private static final int COL_NAME = 3;
	private static final int COL_OFFICE = 4;
	private static final int COL_PHONE = 5;
	private static final int COL_ACCESS_DBA = 6;
	private static final int COL_ACCESS_MGR = 7;
	private static final int COL_ACCESS_DM = 8;
	private static final int COL_ACCESS_RD = 9;
	private static final int COL_ACCESS_TQ = 10;
	private static final int COL_ACCESS_VO = 11;
	private static final int COL_ACCESS_AO = 12;
	private static final int COL_ACCESS_LO = 13;
	private static final int COL_ACCESS = 14;

	public String getColumnName(int col)
		{
		return columnNames[col];
		}

	public Class getColumnClass(int col)
		{
		switch (col)
			{
			case COL_ID:         return Integer.class;
			case COL_OFFICE:     return Byte.class;

			case COL_ACCESS:     return Long.class;

			case COL_ACCESS_DBA:
			case COL_ACCESS_MGR:
			case COL_ACCESS_DM:
			case COL_ACCESS_RD:
			case COL_ACCESS_TQ:
			case COL_ACCESS_VO:
			case COL_ACCESS_AO:
			case COL_ACCESS_LO:  return Boolean.class;

			case COL_LOGIN:
			case COL_PASSWORD:
			case COL_NAME:
			case COL_PHONE:
			default:             return String.class;
			}
		}

	public int getColumnCount()
		{
		return columnNames.length;
		}

	public int getRowCount()
		{
		return (m_rows == null) ? 1 : (m_rows.size() + 1);
		}

	public Object getValueAt(int row, int col)
		{
		if ((m_rows == null) || (row == m_rows.size())) return null; // "";
		User usr = m_rows.get(row);
		switch (col)
			{
			case COL_ID:          return usr.id();
			case COL_LOGIN:       return usr.login();
			case COL_PASSWORD:    return usr.password();
			case COL_NAME:        return usr.name();
			case COL_OFFICE:      return usr.office();
			case COL_PHONE:       return usr.phone();
			case COL_ACCESS:      return usr.getPermissions();
			case COL_ACCESS_DBA:  return usr.isAuthorizedTo(Access.DBA);
			case COL_ACCESS_MGR:  return usr.isAuthorizedTo(Access.MGR);
			case COL_ACCESS_DM:   return usr.isAuthorizedTo(Access.DM);
			case COL_ACCESS_RD:   return usr.isAuthorizedTo(Access.RD);
			case COL_ACCESS_TQ:   return usr.isAuthorizedTo(Access.TQ);
			case COL_ACCESS_VO:   return usr.isAuthorizedTo(Access.VO);
			case COL_ACCESS_AO:   return usr.isAuthorizedTo(Access.AO);
			case COL_ACCESS_LO:   return usr.isAuthorizedTo(Access.LO);
			default:              return null;
			}
		}

	public boolean isCellEditable(int row, int col)
		{
		return (col != 0); // || (row < m_rows.size());
		}

	public void setValueAt(Object value, int row, int col)
		{
		User usr = null;
		if ( row == m_rows.size())
			{
			log( "Adding a new user" );
			return;
			}

		usr = m_rows.get(row);
		log( "Modifying user" );
		switch (col)
			{
			case COL_ACCESS_DBA:
				usr.setPermission( Access.DBA, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_MGR:
				usr.setPermission( Access.MGR, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_DM:
				usr.setPermission( Access.DM, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_RD:
				usr.setPermission( Access.RD, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_TQ:
				usr.setPermission( Access.TQ, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_VO:
				usr.setPermission( Access.VO, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_AO:
				usr.setPermission( Access.AO, ((Boolean)value).booleanValue());
				break;
			case COL_ACCESS_LO:
				usr.setPermission( Access.LO, ((Boolean)value).booleanValue());
				break;

			case COL_LOGIN:
			case COL_PASSWORD:
			case COL_NAME:
			case COL_ACCESS:
			default:
			}
		fireTableCellUpdated(row,col);
	fireTableCellUpdated(row,COL_ACCESS);
		}
	}
