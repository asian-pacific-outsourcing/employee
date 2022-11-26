package com.apo.employee.edit;
/********************************************************************
* @(#)RoleEditor.java 1.00 20110316
* Copyright (c) 2011 by Richard T. Salamone, Jr. All rights reserved.
*
* RoleEditor: A job function immutable model object. Associates an access
* level with a job code, a short description, and a long description.
* Also maintains a static object for each role, as well as a list of
* all defined roles. The static method find retrieves the Role given
* it's access level.
*
* @author Rick Salamone
* @version 1.00, 20110316 rts created
*******************************************************/
import com.apo.contact.Dispo;
import com.apo.contact.edit.CheckDispo;
import com.apo.contact.touch.TouchCode;
import com.apo.employee.*;
import com.apo.net.*;
import com.shanebow.ui.LAF;
import com.shanebow.ui.layout.LabeledPairPanel;
import com.shanebow.ui.SBDialog;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

// for RawFieldChooser
import com.shanebow.ui.SublistChooser;
import com.apo.contact.Raw;
import com.shanebow.dao.FieldMeta;

public class RoleEditor
	extends JPanel
	{
	private final JComboBox        fAccess = new JComboBox(Access.ROLES);
	private final JTextField       fCode = new JTextField();
	private final JTextField       fName = new JTextField();
	private final JTextField       fDesc = new JTextField();
	private final JTextField       ffUnused = new JTextField();
	private final JTextField       fColumns = new JTextField("2");
	private final CheckDispo       fFetchDispos = new CheckDispo(0,4);
	private final CheckDispo       fSaveDispos = new CheckDispo(0,4);
	private final RawFieldChooser  fRawFields = new RawFieldChooser();
	private final JComboBox        fTouchCode = new JComboBox();
	private final PermissionEditor fPermissions = new PermissionEditor(5);

	public RoleEditor()
		{
		super(new BorderLayout());
		add( mainPanel(), BorderLayout.NORTH );
		JScrollPane perm = new JScrollPane(fPermissions);
		LAF.titled(perm, "Permissions");
		add( perm, BorderLayout.CENTER );
		fAccess.addActionListener( new ActionListener()
			{
			@Override public void actionPerformed(ActionEvent e)
				{
				AccessRole accessRole = (AccessRole)fAccess.getSelectedItem();
				long access = accessRole.access();
				try { set(SysDAO.DAO().getRole(access)); }
				catch (Exception x) { SBDialog.error("Role DAO Error", x.getMessage()); }
				}
			});
		}

	private JPanel mainPanel()
		{
		LabeledPairPanel general = new LabeledPairPanel();
		general.addRow( "Access: ",       fAccess );
		general.addRow( "Code: ",         fCode );
		general.addRow( "Name: ",         fName );
		general.addRow( "Description: ",  fDesc );
		general.addRow( "Touch Code: ",   fTouchCode );
		general.addRow( "Unused:",        ffUnused );
		for ( TouchCode code : TouchCode.getAll())
			if (code != TouchCode.XX)
				fTouchCode.addItem(code);
		general.addRow("Columns:",        fColumns );

		JPanel ur = new JPanel(new BorderLayout());
		ur.add( general, BorderLayout.CENTER);
		ur.add( fRawFields, BorderLayout.SOUTH);
		LAF.titled( fRawFields, "Select & Order Fields");

		JPanel dispos = new JPanel( new GridLayout(0,2));
		dispos.add( fFetchDispos );
		LAF.titled( fFetchDispos, "Fetch Dispos");
		dispos.add( fSaveDispos );
		LAF.titled( fSaveDispos, "Save Dispos");

		JPanel it = new JPanel(new BorderLayout());
		it.add( ur, BorderLayout.WEST );
		it.add( dispos, BorderLayout.CENTER);
		return it;
		}

	public Role getRole()
		{
		AccessRole accessRole = (AccessRole)fAccess.getSelectedItem();
		long access = accessRole.access();
		byte cols = Byte.parseByte(fColumns.getText());
		Dispo[] fetchDispos = fFetchDispos.getSelected();
		Dispo[] saveDispos = fSaveDispos.getSelected();
		TouchCode touchCode = (TouchCode)fTouchCode.getSelectedItem();
		return new Role( access, accessRole.toString(), fName.getText(),
		                 fDesc.getText(), fetchDispos, saveDispos,
		                 ffUnused.getText(),
		                 fRawFields.get(), cols, touchCode, fPermissions.get());
		}

	public void set(Role aRole)
		{
		for ( AccessRole ar : Access.ROLES )
			if ( ar.access() == aRole.access())
				fAccess.setSelectedItem(ar);
		fCode.setText(aRole.code());
		fName.setText(aRole.shortName());
		fDesc.setText(aRole.longName());
		fFetchDispos.select(aRole.fetchDispos());
		fSaveDispos.select(aRole.saveDispos());
//		ffUnused.setText(aRole.workfUnusedClass());
		fRawFields.set(aRole.rawFields());
		fColumns.setText("" + aRole.layoutColumns());
		fTouchCode.setSelectedItem(aRole.touchCode());
		fPermissions.set(aRole.permissions());
		}
	}

final class RawFieldChooser
	extends JPanel
	{
	private final SublistChooser fChooser;
	public RawFieldChooser()
		{
		super();
		fChooser = new SublistChooser( Raw.meta, Raw.meta.length, 8, 120 );
		add(fChooser);
		}

	public void set ( int[] fields )
		{
		fChooser.removeAll();
		FieldMeta[] chosen = new FieldMeta[fields.length];
		for ( int i = 0; i < fields.length; i++ )
if ( fields[i] != -1 )
			chosen[i] = Raw.meta[fields[i]];
		fChooser.chooseItems ( chosen );
		}

	public int[] get()
		{
		Object[] chosen = fChooser.getChosen();
		int[] fields = new int[chosen.length];
		for ( int i = 0; i < chosen.length; i++ )
			fields[i] = ((FieldMeta)chosen[i]).fieldNumber();
		return fields;
		}
	/*****
		for ( int i = 0; i < chosen.length; i++ )
			csv += ((i!=0)?",":"") + ((FieldMeta)chosen[i]).fieldNumber();
	*****/
	}
