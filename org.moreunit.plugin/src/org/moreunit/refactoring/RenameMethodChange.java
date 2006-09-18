package org.moreunit.refactoring;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.moreunit.log.LogHandler;

public class RenameMethodChange extends Change {

	private final String	newName;
	private final IMethod	methodToRename;

	public RenameMethodChange(IMethod testMethod, String newName) {
		this.methodToRename = testMethod;
		this.newName = newName;
	}

	public Object getModifiedElement() {
		return methodToRename;
	}

	public String getName() {
		return "Rename method " + methodToRename.getElementName() + " in " + methodToRename.getDeclaringType().getElementName() + " to " + newName;
	}

	public void initializeValidationData(IProgressMonitor pm) {
	}

	public RefactoringStatus isValid(IProgressMonitor pm) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	public Change perform(IProgressMonitor pm) throws CoreException {
		String oldName = methodToRename.getElementName();
		methodToRename.rename(newName, false, pm);
		return getUndo(oldName);
	}

	private Change getUndo(String oldName) {
		IMethod newMethod = getNewMethod();
		if (newMethod == null) {
			return null;
		}
		return new RenameMethodChange(newMethod, oldName);
	}

	private IMethod getNewMethod() {
		try {
			IMethod[] methods = methodToRename.getDeclaringType().getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getElementName().equals(newName)) {
					return methods[i];
				}
			}
		} catch (JavaModelException e) {
			LogHandler.getInstance().handleExceptionLog(e);
		}
		return null;
	}
	
}

// $Log: not supported by cvs2svn $
// Revision 1.1  2006/06/22 20:22:28  gianasista
// package rename
//
// Revision 1.1  2006/06/19 20:08:48  gianasista
// CVS Refactoring
//
// Revision 1.1  2006/06/01 21:00:49  channingwalton
// made rename methods support undo, it would be nice to figure out how to show a preview too...
//
//