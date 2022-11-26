package com.apo.employee;
/********************************************************************
* @(#)Access.java 1.00 20100927
* Copyright (c) 2010-2011 by Richard T. Salamone, Jr. All rights reserved.
*
* Offices: Physical locations of users and associated information.
*
* @version 1.00 20100927
* @author Rick Salamone
* 20100927 RTS initial version
* 20110519 rts added checking against valid IP addresses
*******************************************************/
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class Office
	implements DataField, Comparable<Office>
	{
	private static final ArrayList<Office> _all = new ArrayList<Office>();

	// Office Codes
	public static final Office XX  = new Office(0, "--");
	public static final Office OFFSITE  = new Office(1, "Offsite");
	public static final Office HOUSTON  = new Office(2, "Houston");
	public static final Office BROADWAY = new Office(3, "Broadway", "58.181.");
	public static final Office KL       = new Office(4, "KL");
	public static final Office PI       = new Office(5, "PI", "121.127.7.180");

	public static final Iterable<Office> getAll()  { return _all; }
	public static final int countAll() {return _all.size(); }

	public static Office parse( String text )
		throws DataFieldException
		{
		if (text == null)
			return Office.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty() || trimmed.equals("0"))
			return Office.XX;
		try { return find(Integer.parseInt(trimmed)); }
		catch(Exception e) {}
		for ( Office office : _all )
			if (office.equals(trimmed))
				return office;
		throw new DataFieldException("Office " + BAD_LOOKUP + text);
		}

	public static Office read(ResultSet rs, int rsCol)
		throws DataFieldException
		{
		try { return find(rs.getInt(rsCol)); }
		catch (SQLException e) { throw new DataFieldException(e); }
		}

	public static Office find(int id)
		{
		for ( Office office : _all )
			if ( office.fID == id )
				return office;
		return Office.XX;
		}

	public int id() { return fID; }
	public String name() { return fName; }

	@Override public boolean equals(Object other)
		{
		if ( other instanceof Office )
			return this == other;
		else return this.fName.equals(other.toString());
		}

	@Override public int compareTo(Office other)
		{
		return this.fID - other.fID;
		}

	@Override public boolean isEmpty() { return (fID == 0); }
	@Override public String toString() { return fName; }
	@Override public String csvRepresentation() { return (isEmpty()? "" : "" + fID); }
	@Override public String dbRepresentation()  { return "" + fID; }

	// PRIVATE
	private final int fID;
	private final String fName; // the full name
	private final String[] fValidIPs;
	private Office( int aID, String aName, String... aValidIPs )
		{
		fID = aID;
		fName = aName;
		fValidIPs = aValidIPs;
		if (aID > 0) _all.add( this );
		}

	public final boolean isValidIP(java.net.InetAddress aInetAddress)
		{
System.out.println("addr: " + aInetAddress
 + " is local: " + aInetAddress.isAnyLocalAddress());
		if (aInetAddress.isLoopbackAddress()
//		|| aInetAddress.isAnyLocalAddress()
		||  fValidIPs == null || fValidIPs.length == 0)
			return true;
		String clientIP = aInetAddress.getHostAddress();
//		if ( clientIP.startsWith("192.168.") return true;
		for ( String validIP : fValidIPs )
			if ( clientIP.startsWith(validIP)) return true;
		return false;
		}
	}
