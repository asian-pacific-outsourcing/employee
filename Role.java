package com.apo.employee;
/********************************************************************
* @(#)Role.java 1.00 20110203
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* Role: A job function immutable model object. Associates an access
* level with a job code, a short description, and a long description.
* Also maintains a static object for each role, as well as a list of
* all defined roles. The static method find retrieves the Role given
* it's access level.
*
* @author Rick Salamone
* @version 1.00, 20110203 rts created
* @version 1.02, 20110301 rts added mail manager
* @version 1.03, 20110315 rts added touch code & isManager()
* @version 1.04, 20110317 rts added marshall() & parse
*******************************************************/
import com.apo.contact.Raw;
import com.apo.contact.Dispo;
import com.apo.contact.touch.TouchCode;
import com.shanebow.dao.DataField;
import com.shanebow.dao.DataFieldException;
import com.apo.net.Access;
import com.shanebow.ui.SBDialog;
import com.shanebow.util.CSV;
import com.shanebow.util.SBMisc;
import java.util.ArrayList;

public final class Role
	implements DataField
	{
	private static final byte ONE_COL = (byte)1;
	private static final byte TWO_COL = (byte)2;
//	private static final ArrayList<Role> _all = new ArrayList<Role>();
	public static final Role _DM = new Role(Access.DM, "DM", "Miner", "Data Miner",
		Dispo.NO_DISPOS, // fetch from dispos
		Dispo.NO_DISPOS, // save as dispos
		null,
		new int[] {Raw.NAME, Raw.POSITION, Raw.COMPANY, Raw.PHONE, Raw.MOBILE,
		           Raw.ALTPHONE, Raw.EMAIL, Raw.ADDRESS, Raw.COUNTRYID, Raw.TYPE },
		ONE_COL, TouchCode.MINED, "000000000000000000000000000" );

	public static final Role TQ = new Role(Access.TQ, "TQ", "Qualifier", "Telephone Qualifier",
		new Dispo[] {Dispo.XX, Dispo.XC, Dispo.CB },
		new Dispo[] {Dispo.BD, Dispo.UD, Dispo.NI, Dispo.XC, Dispo.CB, Dispo.L},
		"unused",
		new int[] {Raw.NAME, Raw.POSITION, Raw.COMPANY, Raw.PHONE, Raw.MOBILE, Raw.ALTPHONE,
		           Raw.EMAIL, -1, Raw.ADDRESS, Raw.COUNTRYID, Raw.HOMELAND },
		TWO_COL, TouchCode.TQCALLED, "000000000000000000000000000" );

/******************
	public static final Role QV = new Role(Access.VO, "QV", "Tingle", "Vero Prescreen",
		new Dispo[] {Dispo.L, Dispo.VCB1},
		new Dispo[] {Dispo.VCB1, Dispo.VUD, Dispo.VBD, Dispo.VNI, Dispo.VCB},
		new int[] {Raw.NAME, Raw.COUNTRYID, Raw.HOMELAND, Raw.POSITION, Raw.COMPANY,
		           Raw.PHONE, Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL, Raw.CALLBACK, Raw.DISPO},
		"unused",
		TWO_COL, TouchCode.VOCALLED, "000000000000000000000000000" );
******************/

	public static final Role VO = new Role(Access.VO, "VO", "Vero", "Verifier",
//		new Dispo[] {Dispo.L, Dispo.VCB1, Dispo.VCB, Dispo.BR},
		new Dispo[] {Dispo.L, Dispo.VCB, Dispo.BR},
		new Dispo[] {Dispo.UD, Dispo.BD, Dispo.NI, Dispo.VCB, Dispo.BR, Dispo.VOL},
		"unused",
		new int[] {Raw.NAME, Raw.POSITION, Raw.COMPANY, Raw.PHONE,
		           Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL, -1,
		           Raw.DISPO, Raw.ADDRESS, Raw.COUNTRYID, Raw.HOMELAND },
		TWO_COL, TouchCode.VOCALLED, "000000000000000000000000000" );

	public static final Role AO = new Role(Access.AO, "AO", "Opener", "Account Opener",
		new Dispo[] {Dispo.VOL, Dispo.ACB, Dispo.AOP, Dispo.AOF},  // fetch from dispos
		new Dispo[] {Dispo.UNC, Dispo.TOL, Dispo.KOL, Dispo.CO, Dispo.ACB,
		             Dispo.AOP, Dispo.ADP, Dispo.RMP, Dispo.AOF, Dispo.LI}, // save as dispos
		"unused",
//		new int[] {Raw.NAME, Raw.COUNTRYID, Raw.HOMELAND, Raw.POSITION, Raw.COMPANY, -1,
//		           Raw.PHONE, Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL},
		new int[] {Raw.NAME, Raw.POSITION, Raw.COMPANY, Raw.PHONE,
		           Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL, -1,
		           Raw.DISPO, Raw.ADDRESS, Raw.COUNTRYID, Raw.HOMELAND },
		TWO_COL, TouchCode.AOCALLED, "000000000000000000000000000" );

	public static final Role LO = new Role(Access.LO, "LO", "Loader", "Account Loader",
		new Dispo[] {Dispo.LI, Dispo.LCB},  // fetch from dispos
		new Dispo[] {Dispo.LCB, Dispo.TOL}, // save as dispos
		"unused",
		new int[] {Raw.NAME, Raw.COUNTRYID, Raw.HOMELAND, Raw.POSITION, Raw.COMPANY, -1,
		           Raw.DISPO, Raw.PHONE, Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL, Raw.CALLBACK},
		TWO_COL, TouchCode.LOCALLED, "000000000000000000000000000" );

	public static final Role MM = new Role(Access.MM, "MM", "Mail Man", "Mail Manager",
		Dispo.NO_DISPOS,  // fetch from dispos
		Dispo.NO_DISPOS, // save as dispos
		null,
		new int[] {Raw.NAME, Raw.COMPANY,
		           Raw.EMAIL, Raw.CALLER, Raw.CALLBACK, -1, Raw.DISPO, Raw.ADDRESS, Raw.COUNTRYID, Raw.TYPE},
		TWO_COL, TouchCode.MODIFIED, "000000000000000000000000000" );

	public static final Role MGRAD = new Role(Access.MGRAD, "MGR", "Manager", "Room Manager",
		Dispo.NO_DISPOS,  // fetch from dispos
		Dispo.NO_DISPOS, // save as dispos
		null,
		new int[] {Raw.NAME, Raw.POSITION, Raw.COMPANY,
		           Raw.PHONE, Raw.MOBILE, Raw.ALTPHONE, Raw.EMAIL, -1, Raw.TYPE, Raw.CALLER,
		           Raw.HTRCOUNT, Raw.CALLBACK, Raw.DISPO, Raw.COUNTRYID, Raw.HOMELAND},
		TWO_COL, TouchCode.MODIFIED, "000000000000000000000000000" );

//	private static final Role[] _all = { _DM, TQ, QV, VO, AO, LO, MM, VM, RM };
	private static final Role[] _all = { _DM, TQ, VO, AO, LO, MM, MGRAD };
	public static final int countAll() {return _all.length;}
	public static final Role[] getAll()  { return _all; }

	public static Role find( final long aAccess )
		{
		for ( Role role : _all )
			if ( role.access() == aAccess )
				return role;
		return null;
		}

	public static Role parse( final String text )
		throws DataFieldException
		{
		String code = text.split(" ")[0].toUpperCase();
		for ( Role role : _all )
			if ( code.equals(role.fCode))
				return role;
		try { return find(Long.parseLong(text)); }
		catch(Exception e) {}
		throw new DataFieldException("Role " + BAD_LOOKUP + text);
		}

	public final long access() { return fAccess; }
	public final String code() { return fCode; }
	public final String shortName() { return fShortName; }
	public final String longName() { return fLongName; }
	public final Dispo[] fetchDispos() { return fFetchDispos; }
	public final Dispo[] saveDispos() { return fSaveDispos; }
	public final int[] rawFields() { return fRawFields; }
	public final byte  layoutColumns() { return fLayoutColumns; }
	public final TouchCode touchCode() { return fTouchCode; }
	public final String permissions() { return fPermissions; }
	public final boolean isCaller()
		{
		return (fFetchDispos != null) && (fFetchDispos.length > 0);
		}
	public final boolean isManager() { return (fAccess & (Access.MGR|Access.ADM)) != 0; }
	public final boolean callsDispo( Dispo aDispo )
		{
		for ( Dispo calledDispo : fFetchDispos )
			if ( calledDispo.equals(aDispo))
				return true;
		return false;
		}

	@Override public final String toString() { return fCode + " " + fLongName; }
	@Override public String csvRepresentation() { return (isEmpty()? "" : "" + fCode); }
	@Override public String dbRepresentation()  { return "" + fAccess; }
	@Override public boolean isEmpty() { return (fAccess == 0); }

	/**
	* marshall() - encodes the Role object as a String suitable
	* for network transmission. Inverse of unmarshall().
	*/
	private static final String SEP = "~"; // separates parts of marshalled object
	public String marshall()
		{
		StringBuffer csv = new StringBuffer();
		String unused = ((fUnused == null)? "" : fUnused);
		csv.append(fAccess);
		csv.append(SEP).append(fCode);
		csv.append(SEP).append(fShortName);
		csv.append(SEP).append(fLongName);
		csv.append(SEP).append(Dispo.arrayToCSV(fFetchDispos));
		csv.append(SEP).append(Dispo.arrayToCSV(fSaveDispos));
		csv.append(SEP).append(unused);
		csv.append(SEP).append(CSV.intArrayToCSV(fRawFields));
		csv.append(SEP).append(fLayoutColumns);
		csv.append(SEP).append(fTouchCode.csvRepresentation());
		csv.append(SEP).append(fPermissions);
		return csv.toString();
		}

	/**
	* unmarshall() - decodes a marshalled Role into a new Role object
	* Inverse of marshall().
	*/
	public static final Role unmarshall(String aMarshalled)
		throws DataFieldException
		{
		String[] pieces = aMarshalled.split(SEP);
//System.out.println("Role unmarshall: " + aMarshalled );
//for (int i=0; i < pieces.length; i++)
//System.out.format("  pieces[%d]: '%s'\n", i, pieces[i]);
		try
			{
			String unused = pieces[6];
			if ( unused.isEmpty())
				unused = null;
			return new Role( Long.parseLong(pieces[0]),
			                 pieces[1], // code 
			                 pieces[2], // shortName
			                 pieces[3], // longName
			                 Dispo.csvToArray(pieces[4]), // fetch dispos
			                 Dispo.csvToArray(pieces[5]), // save dispos
			                 unused,
			                 CSV.csvToIntArray(pieces[7]), // rawFields
			                 Byte.parseByte(pieces[8]),   // Layout Columns
			                 TouchCode.parse(pieces[9]),
			                 pieces[10] ); // permissions
			}
		catch (Throwable t) { throw new DataFieldException("Role init failed: " + t);}
		}

	// PRIVATE //
	private final long fAccess;
	private final String fCode;
	private final String fShortName;
	private final String fLongName;
	private final Dispo[] fFetchDispos; // fetch contacts having these dispos
	private final Dispo[] fSaveDispos;  // contacts may be saved as one of these dispos
	private final int[] fRawFields; // raw contact fields displayed on main screen
	private final byte  fLayoutColumns;
	private final String fUnused;
	private final TouchCode fTouchCode; // basic touch code for contact interaction
	private final String fPermissions;

	public Role( long aAccess, String aCode, String aShortName, String aLongName,
		Dispo[] aFetchDispos, Dispo[] aSaveDispos, String aUnused,
		int[] aRawFields, byte aLayoutColumns,
		TouchCode aTouchCode, String aPermissions )
		{
		fAccess = aAccess;
		fCode = aCode;
		fShortName = aShortName;
		fLongName = aLongName;
		fFetchDispos = aFetchDispos;
		fSaveDispos = aSaveDispos;
		fUnused = aUnused;
		fRawFields = aRawFields;
		fLayoutColumns = aLayoutColumns;
		fTouchCode = aTouchCode;
		fPermissions = aPermissions;
//		_all.add( this );
		}
	} // 210
