package com.apo.employee;
/********************************************************************
* @(#)User.java	1.00 10/05/30
* Copyright (c) 2010 by Richard T. Salamone, Jr. All rights reserved.
*
* User: A user of the APO system
*
* Other user info to track in next release:
Phone  contact number
Phone  account number (in house outbound line)
Job ID
Salary
User Code 

Job Table
	DM, TQ, VO, LO

Desk Table
  desk #
	IP
	Phone account
*
* @version 1.00 05/30/10
* @author Rick Salamone
* 20100623 rts Created based on Dispo
* 20100629 rts Split User & Source into separate classes
* 20100911 rts moved to admin package
* 20101007 rts added TQ new hires
* 20101019 rts added select to prompt for a user in a dialog
* 20101119 rts added user accounts H17 - H30
* 20110517 rts added user accounts D30 - D40
* 20110604 rts implements hashCode for binarySearch in SBArray
*******************************************************/
import com.shanebow.dao.EmpID;
import com.apo.net.Access;
import com.shanebow.ui.SBDialog;
import com.shanebow.util.SBArray;
import java.io.*;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

public final class User
	{
	protected static final SBArray<User> _all = new SBArray<User>(140);
	public static final User XX = new User(  0, "--", "", "", Office.XX, "", Access.NONE );
	static
		{
		new User(     1, "miners",  "", "",       Office.HOUSTON,  "", Access.NONE );
		new User(     2, "Pong",    "bkkpi", "Harry",  Office.OFFSITE,  "", Access.ALL );
		new User(     3, "Charlie", "", "",       Office.OFFSITE,  "", Access.NONE );
		new User(     4, "MailMan", "9i8", "Gregory", Office.KL,   "", Access.ALL );
		new User(     5, "Jingle",  "qwerty", "",       Office.OFFSITE,  "", Access.ALL );
		new User(     6, "Amy",     "xxx", "Amy", Office.HOUSTON,  "", Access.ALL );
		new User(     7, "Shane",   "enahs", "Caveman",  Office.OFFSITE,  "", Access.GOD );
		new User(     8, "Madman",  "", "Admin",  Office.OFFSITE, "", Access.ALL );
		new User(     9, "Dolus",   "composer", "Barry Manilow", Office.OFFSITE, "", Access.VM );
		new User(    10, "Buddy",   "", "Buddy",  Office.HOUSTON,  "", Access.NONE );
		new User(    11, "H6",      "", "Chula",  Office.HOUSTON,  "", Access.NONE );
		new User(    12, "D01",     "", "Dom",    Office.HOUSTON,  "", Access.NONE );
		new User(    13, "JW",      "", "Jeb",    Office.BROADWAY, "", Access.RM );
		new User(    14, "D02",     "", "Fon2",   Office.HOUSTON,  "", Access.NONE );
		new User(    15, "D03",     "", "Mae",    Office.HOUSTON,  "", Access.NONE );
		new User(    16, "D04",     "", "Martin", Office.HOUSTON,  "", Access.NONE );
		new User(    17, "H5",      "", "Nikki",  Office.HOUSTON,  "", Access.NONE );
		new User(    19, "D05",     "", "Pink",   Office.HOUSTON, "", Access.NONE );
		new User(    20, "D06",     "", "Pook",   Office.HOUSTON, "", Access.NONE );
		new User(    21, "D07",     "", "Sa",     Office.HOUSTON, "", Access.NONE );
		new User(    22, "H1",      "", "Shea",   Office.HOUSTON, "", Access.NONE );
		new User(    23, "H2",      "", "Fon",    Office.HOUSTON, "", Access.NONE );
		new User(    24, "D08",     "", "Looknam",Office.HOUSTON, "", Access.NONE );
		new User(    25, "D09",     "", "Deaw",   Office.HOUSTON, "", Access.NONE );
		new User(    50, "D10",     "", "Ting",   Office.HOUSTON, "", Access.NONE );
		new User(    51, "Beavis",  "fire", "Walker",  Office.OFFSITE,   "", Access.VM );

		new User(   52, "D12", "", "?",    Office.PI, "", Access.DM );
		new User(   53, "D13", "", "?",    Office.PI, "", Access.DM );
		new User(   54, "D14", "", "?",    Office.PI, "", Access.DM );
		new User(   55, "D15", "", "?",    Office.PI, "", Access.DM );
		new User(   56, "D16", "", "?",    Office.PI, "", Access.DM );
		new User(   57, "D17", "", "?",    Office.PI, "", Access.DM );
		new User(   58, "D18", "", "?",    Office.PI, "", Access.DM );
		new User(   59, "D19", "", "?",    Office.PI, "", Access.DM );
		new User(   60, "D20", "", "?",    Office.PI, "", Access.DM );
		new User(   61, "D21", "", "?",    Office.PI, "", Access.DM );
		new User(   62, "D22", "", "?",    Office.PI, "", Access.DM );
		new User(   63, "D23", "", "?",    Office.PI, "", Access.DM );
		new User(   64, "D24", "", "?",    Office.PI, "", Access.DM );
		new User(   65, "D25", "", "?",    Office.PI, "", Access.DM );
		new User(   66, "D26", "", "?",    Office.PI, "", Access.DM );
		new User(   67, "D27", "", "?",    Office.PI, "", Access.DM );
		new User(   68, "D28", "", "?",    Office.PI, "", Access.DM );
		new User(   69, "D29", "", "?",    Office.PI, "", Access.DM );
		new User(   70, "D30", "", "?",    Office.PI, "", Access.DM );
		new User(   71, "D31", "", "?",    Office.PI, "", Access.DM );
		new User(   72, "D32", "", "?",    Office.PI, "", Access.DM );
		new User(   73, "D33", "", "?",    Office.PI, "", Access.DM );
		new User(   74, "D34", "", "?",    Office.PI, "", Access.DM );
		new User(   75, "D35", "", "?",    Office.PI, "", Access.DM );
		new User(   76, "D36", "", "?",    Office.PI, "", Access.DM );
		new User(   77, "D37", "", "?",    Office.PI, "", Access.DM );
		new User(   78, "D38", "", "?",    Office.PI, "", Access.DM );
		new User(   79, "D39", "", "?",    Office.PI, "", Access.DM );
		new User(   90, "D40", "", "?",    Office.PI, "", Access.DM );

		new User(   101, "H3",      "", "Noi",    Office.HOUSTON, "", Access.NONE );
		new User(   102, "D11",     "", "Nong",   Office.HOUSTON, "", Access.NONE );
		new User(   103, "H7",      "", "Kim",    Office.HOUSTON, "", Access.NONE );
		new User(   104, "H9",      "", "Sherry", Office.HOUSTON, "", Access.NONE );
		new User(   105, "H10",     "", "Tony",   Office.HOUSTON, "", Access.NONE );
		new User(   106, "H11",     "", "May",    Office.HOUSTON, "", Access.NONE );
		new User(   108, "H8",      "", "Adam",   Office.HOUSTON, "", Access.NONE );
		new User(   112, "H12",     "", "Wee",    Office.HOUSTON, "", Access.NONE );
		new User(   113, "H13",     "", "Randy",  Office.HOUSTON, "", Access.NONE );
		new User(   114, "H14",     "", "?",      Office.HOUSTON, "", Access.NONE );
		new User(   115, "H15",     "", "Melissa",Office.HOUSTON, "", Access.NONE );
		new User(   116, "H16",     "", "Ann",    Office.HOUSTON, "", Access.NONE );
		new User(   117, "H17",     "", "Tiang",  Office.HOUSTON, "", Access.NONE );
		new User(   118, "H18",     "", "Fern",   Office.HOUSTON, "", Access.NONE );

		new User(   119, "T19",     "", "?",      Office.PI, "", Access.TQ );
		new User(   120, "T20",     "", "?",      Office.PI, "", Access.TQ );
		new User(   121, "T21",     "", "?",      Office.PI, "", Access.TQ );
		new User(   122, "T22",     "", "?",      Office.PI, "", Access.TQ );
		new User(   123, "T23",     "", "?",      Office.PI, "", Access.TQ );
		new User(   124, "T24",     "", "?",      Office.PI, "", Access.TQ );
		new User(   125, "T25",     "", "?",      Office.PI, "", Access.TQ );
		new User(   126, "T26",     "", "?",      Office.PI, "", Access.TQ );
		new User(   127, "T27",     "", "?",      Office.PI, "", Access.TQ );
		new User(   128, "T28",     "", "?",      Office.PI, "", Access.TQ );
		new User(   129, "T29",     "", "?",      Office.PI, "", Access.TQ );
		new User(   130, "T30",     "", "?",      Office.PI, "", Access.TQ );
		new User(   131, "T31",     "", "?",      Office.PI, "", Access.TQ );
		new User(   132, "T32",     "", "?",      Office.PI, "", Access.TQ );
		new User(   133, "T33",     "", "?",      Office.PI, "", Access.TQ );
		new User(   134, "T34",     "", "?",      Office.PI, "", Access.TQ );
		new User(   135, "T35",     "", "?",      Office.PI, "", Access.TQ );
		new User(   136, "T36",     "", "?",      Office.PI, "", Access.TQ );
		new User(   137, "T37",     "", "?",      Office.PI, "", Access.TQ );
		new User(   138, "T38",     "", "?",      Office.PI, "", Access.TQ );
		new User(   139, "T39",     "", "?",      Office.PI, "", Access.TQ );
		new User(   140, "T40",     "", "?",      Office.PI, "", Access.TQ );
		new User(   141, "T41",     "", "?",      Office.PI, "", Access.TQ );
		new User(   142, "T42",     "", "?",      Office.PI, "", Access.TQ );
		new User(   143, "T43",     "", "?",      Office.PI, "", Access.TQ );
		new User(   144, "T44",     "", "?",      Office.PI, "", Access.TQ );
		new User(   145, "T45",     "", "?",      Office.PI, "", Access.TQ );
		new User(   146, "T46",     "", "?",      Office.PI, "", Access.TQ );
		new User(   147, "T47",     "", "?",      Office.PI, "", Access.TQ );
		new User(   148, "T48",     "", "?",      Office.PI, "", Access.TQ );
		new User(   149, "T49",     "", "?",      Office.PI, "", Access.TQ );
		new User(   150, "T50",     "", "?",      Office.PI, "", Access.TQ );

		new User(   210, "V10", "", "?",    Office.PI, "", Access.VO );
		new User(   211, "V11", "", "?",    Office.PI, "", Access.VO );
		new User(   212, "V12", "", "?",    Office.PI, "", Access.VO );
		new User(   213, "V13", "", "?",    Office.PI, "", Access.VO );
		new User(   214, "V14", "", "?",    Office.PI, "", Access.VO );
		new User(   215, "V15", "", "?",    Office.PI, "", Access.VO );
		new User(   216, "V16", "", "?",    Office.PI, "", Access.VO );
		new User(   217, "V17", "", "?",    Office.PI, "", Access.VO );
		new User(   218, "V18", "", "?",    Office.PI, "", Access.VO );
		new User(   219, "V19", "", "?",    Office.PI, "", Access.VO );
		new User(   220, "V20", "", "?",    Office.PI, "", Access.VO );

		new User(   301, "A01", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   302, "A02", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   303, "A03", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   304, "A04", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   305, "A05", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   306, "A06", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   307, "A07", "", "David Clayton",    Office.BROADWAY, "", Access.AO );
		new User(   308, "A08", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   309, "A09", "", "James Green",    Office.BROADWAY, "", Access.AO );
		new User(   310, "A10", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   311, "A11", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   312, "A12", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   313, "A13", "", "Tyler Lynch",    Office.BROADWAY, "", Access.AO );
		new User(   314, "A14", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   315, "A15", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   316, "A16", "", "Matthew Bently",    Office.BROADWAY, "", Access.AO );
		new User(   317, "A17", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   318, "A18", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   319, "A19", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   320, "A20", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   321, "A21", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   322, "A22", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   323, "A23", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   324, "A24", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   325, "A25", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   326, "A26", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   327, "A27", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   328, "A28", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   329, "A29", "", "?",    Office.BROADWAY, "", Access.AO );
		new User(   330, "A30", "", "Jonathon Wright",   Office.BROADWAY, "", Access.AO );

		new User(   501, "L01", "", "David Clayton", Office.BROADWAY, "", Access.LO );
	/***********
		new User(   502, "L02", "", "",   Office.BROADWAY, "", Access.LO );
		new User(   503, "L03", "", "",   Office.BROADWAY, "", Access.LO );
	***********/
		}

	public static final int countAll() { return _all.capacity(); }
	public static final Iterable<User> getAll()  { return _all; }
	public static void freeze( String filespec )
		{
		try
			{
			PrintWriter file = new PrintWriter ( filespec );
			for ( User usr : _all )
				if ( usr != XX )
					file.println ( usr.m_id + ",\"" + usr.m_login + "\",\"" + usr.m_pwd
					 + "\",\"" + usr.m_name + "," + usr.m_office  + ",\"" + usr.m_phone
					 + "\"," + usr.fAccess );
			file.close();
			}
		catch (IOException e)
			{
			System.err.println(filespec + " Error: " + e.toString());
			}
		}

	public static final List<String> loginsAuthorizedTo(long access)
		{
		List<String> it = new Vector<String>();
		for ( User usr : _all )
			if ( usr.isAuthorizedTo(access))
				it.add(usr.login());
		return it;
		}

	public static final List<User> listBy(Role role)
		{
		List<User> it = new Vector<User>();
		for ( User usr : _all )
			if ( usr.isAuthorizedTo(role.access()))
				it.add(usr);
		return it;
		}

	// null if the user clicks Cancel
	public static User select(String title, String prompt, long access)
		{
		Object[] names = loginsAuthorizedTo(access).toArray();
		String name = (String)JOptionPane.showInputDialog(null, prompt, title,
 		               JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
		return parse(name);
		}

	public static User selectTQ(String prompt)
		{
		return select("Select Telephone Qualifier", prompt, Access.TQ );
		}

	public static User parse( String text )
		{
		if (text == null)
			return User.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty() || trimmed.equals("0"))
			return User.XX;
		for ( User usr : _all )
			if ( usr.m_login.equalsIgnoreCase(trimmed))
				return usr;
		try
			{
			short id = Short.parseShort(trimmed);
			return find(id);
			}
		catch(Exception e) {}
		return User.XX;
		}

	public static User findName( String text )
		{
		if (text == null)
			return User.XX;
		String trimmed = text.trim();
		if ( trimmed.isEmpty())
			return User.XX;
		for ( User usr : _all )
			if ( usr.m_name.equalsIgnoreCase(trimmed))
				return usr;
		return User.XX;
		}

	public static User findSlow(short id)
		{
		for ( User usr : _all )
			if ( usr.m_id == id )
				return usr;
		return User.XX;
		}

	public static User find(short id)
		{
		int index = _all.binarySearch(id);
		return (index < 0)? User.XX : _all.get(index);
		}

public static SBArray getList() { return _all; }

	private static User findPhone(String phone)
		{
		for ( User usr : _all )
			if ( usr.m_phone.equals(phone))
				return usr;
		return User.XX;
		}

	private final EmpID fID;
	private final short  m_id;
	private final String m_login;  // login name
	private final String m_pwd;    // the password
	private final String m_name;   // user's name
	private final Office m_office; // office location
	private final String m_phone;  // phone account
	private long   fAccess;
	private User( int id, String login, String password,
		            String name, Office office, String phone, long privledges )
		{
		if ( id > Access.MAX_UID )
			SBDialog.fatalError( "Max user id exceeded" );
		fID = new EmpID(id);
		m_id = (short)id;
		m_login = login;
		m_pwd = password;
		m_name = name;
		m_office = office;
		m_phone = phone;
		fAccess = privledges;
		_all.add( this );
		}

	@Override public int hashCode() { return m_id; }
	@Override public String  toString() { return m_login; }
	public EmpID   empID()    { return fID; }
	public short   id()       { return m_id; }
	public String  login()    { return m_login; }
	public String  name()     { return m_name; }
	public Office  office()   { return m_office; }
	public String  phone()    { return m_phone; }
	public String  password() { return m_pwd; }
	public long    getPermissions()  { return fAccess; }
	public boolean isAuthorizedTo(long flag) { return (fAccess & flag) != 0; }
	public void    addPermission(long flag) { fAccess |= flag; }
	public void    delPermission(long flag) { fAccess &= ~flag; }
	public void    setPermission(long flag, boolean on)
		{
		if ( on )
			fAccess |= flag;
		else
			fAccess &= ~flag;
		}
	public final boolean isManager() { return (fAccess & (Access.MGR|Access.ADM)) != 0; }
	}
