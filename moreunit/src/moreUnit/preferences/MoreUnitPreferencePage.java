package moreUnit.preferences;

import moreUnit.MoreUnitPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author vera
 * 08.01.2006 19:24:23
 */
public class MoreUnitPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public MoreUnitPreferencePage() {
		super(FieldEditorPreferencePage.FLAT);
	}
	
	protected void createFieldEditors() {
		StringFieldEditor junitDirPreferenceField = new StringFieldEditor(PreferenceConstants.PREF_JUNIT_PATH, "Directory for testcases", 10, getFieldEditorParent());
		BooleanFieldEditor showDialogField = new BooleanFieldEditor(PreferenceConstants.SHOW_REFACTORING_DIALOG, "Should ask before perform refactorings to tests either", getFieldEditorParent());
		addField(junitDirPreferenceField);
		addField(showDialogField);
		
		addField(new NameListEditor(PreferenceConstants.PREFIXES, "Unit Test &Prefixes:", getFieldEditorParent()));
		addField(new NameListEditor(PreferenceConstants.SUFFIXES, "Unit Test &Suffixes:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.TEST_PACKAGE_PREFIX, "Test package prefix", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.USE_WIZARDS, "Use class creation &Wizards", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.SWITCH_TO_MATCHING_METHOD, "Switch to matching &Methods", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(MoreUnitPlugin.getDefault().getPreferenceStore());
	}
}

// $Log: not supported by cvs2svn $
// Revision 1.6  2006/05/20 16:09:40  gianasista
// Integration of switchunit preferences
//
// Revision 1.5  2006/05/12 17:53:41  gianasista
// started extended preferences (Lists of testcase prefixes, suffixes)
//
// Revision 1.4  2006/04/14 17:11:56  gianasista
// Suffix for testcasename ist configurable (+Tests)
//
// Revision 1.3  2006/02/19 21:46:45  gianasista
// Dialog to ask user of refactoring should be performed on corresponding tests (configurable via properties)
//
// Revision 1.2  2006/01/19 21:39:44  gianasista
// Added CVS-commit-logging to all java-files
//