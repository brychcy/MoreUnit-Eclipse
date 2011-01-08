/**
 * 
 */
package org.moreunit.elements;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.moreunit.log.LogHandler;
import org.moreunit.util.MoreUnitContants;

/**
 * @author vera 25.01.2006 21:50:37 EditorPartFacade offers easy access to
 *         {@link IEditorPart}
 */
public class EditorPartFacade
{

    private IEditorPart editorPart;

    public EditorPartFacade(IEditorPart editorPart)
    {
        this.editorPart = editorPart;
    }

    public IFile getFile()
    {
        return (IFile) editorPart.getEditorInput().getAdapter(IFile.class);
    }

    public boolean isJavaFile()
    {
        IFile file = getFile();
        return file != null && file.getName().endsWith(MoreUnitContants.JAVA_FILE_EXTENSION);
    }

    public ICompilationUnit getCompilationUnit()
    {
        return JavaCore.createCompilationUnitFrom(getFile());
    }

    public ITextSelection getTextSelection()
    {
        IWorkbenchPartSite site = editorPart.getSite();
        ISelectionProvider selectionProvider = site.getSelectionProvider();
        return (ITextSelection) selectionProvider.getSelection();
    }

    public IMethod getMethodUnderCursorPosition()
    {
        IMethod method = null;
        try
        {
            IJavaElement javaElement = getCompilationUnit().getElementAt(getTextSelection().getOffset());
            if(javaElement instanceof IMethod)
            {
                method = (IMethod) javaElement;
            }
            else
                LogHandler.getInstance().handleInfoLog("No method found.");
        }
        catch (JavaModelException e)
        {
            LogHandler.getInstance().handleExceptionLog(e);
        }

        return method;
    }

    public IJavaProject getJavaProject()
    {
        return getCompilationUnit().getJavaProject();
    }

    public IMethod getFirstTestMethodForMethodUnderCursorPosition(IType testcaseType)
    {
        if(TypeFacade.isTestCase(getCompilationUnit().findPrimaryType()))
            return null;

        ClassTypeFacade classTypeFacade = new ClassTypeFacade(getCompilationUnit());

        IMethod methodUnderCursorPosition = getMethodUnderCursorPosition();
        return classTypeFacade.getCorrespondingTestMethod(methodUnderCursorPosition, testcaseType);
    }

    public List<IMethod> getTestmethodsForMethodUnderCursorPosition()
    {
        if(TypeFacade.isTestCase(getCompilationUnit().findPrimaryType()))
            return null;

        ClassTypeFacade classTypeFacade = new ClassTypeFacade(getCompilationUnit());

        IMethod methodUnderCursorPosition = getMethodUnderCursorPosition();
        classTypeFacade.getOneCorrespondingTestCase(false);
        return classTypeFacade.getCorrespondingTestMethods(methodUnderCursorPosition);
    }

    public IEditorPart getEditorPart()
    {
        return editorPart;
    }

    /**
     * Returns the first method that surrounds the cursor position and that is
     * not part of an anonymous type.
     */
    // TODO Nicolas: determine whether this behavior would be preferable to the current
    // getMethodUnderCursorPosition() in any case
    public IMethod getFirstNonAnonymousMethodSurroundingCursorPosition()
    {
        IMethod method = getFirstMethodSurroundingCursorPosition();
        return method == null ? null : new MethodFacade(method).getFirstNonAnonymousMethodCallingThisMethod();
    }

    private IMethod getFirstMethodSurroundingCursorPosition()
    {
        IMethod method = null;
        try
        {
            IJavaElement javaElement = getCompilationUnit().getElementAt(getTextSelection().getOffset());
            if(javaElement instanceof IMethod)
            {
                method = (IMethod) javaElement;
            }
            else if(javaElement instanceof IType && ((IType) javaElement).isAnonymous() && javaElement.getParent() instanceof IMethod)
            {
                method = (IMethod) javaElement.getParent();
            }
            else
                LogHandler.getInstance().handleInfoLog("No method found.");
        }
        catch (JavaModelException e)
        {
            LogHandler.getInstance().handleExceptionLog(e);
        }
        return method;
    }
}

// $Log: not supported by cvs2svn $
// Revision 1.8  2010/08/15 17:05:00  ndemengel
// Feature Requests 3036484: part 1, prevents running a non-test method
//
// Revision 1.7  2010/06/30 22:55:56  makkimesser
// CodeWarnings resolved
// Deprecated API removed
// Missing AnnotationType Extension added
//
// Revision 1.6  2009/04/05 19:14:27  gianasista
// code formatter
//
// Revision 1.5 2009/03/25 20:27:25 gianasista
// Bugfix: NPE when opening class-files
//
// Revision 1.4 2009/03/23 19:17:48 gianasista
// Bugfix: Exception when opening non-java-files
//
// Revision 1.3 2006/11/25 14:57:46 gianasista
// getter for EditorPart
//
// Revision 1.2 2006/11/04 08:50:17 channingwalton
// Fix for [ 1579660 ] Testcase selection dialog opens twice
//
// Revision 1.1.1.1 2006/08/13 14:31:15 gianasista
// initial
//
// Revision 1.1 2006/06/22 20:22:29 gianasista
// package rename
//
// Revision 1.1 2006/06/19 20:08:48 gianasista
// CVS Refactoring
//
// Revision 1.8 2006/05/23 19:38:01 gianasista
// Splitted JavaFileFacade into two classes
//
// Revision 1.7 2006/05/20 16:05:56 gianasista
// Integration of switchunit preferences
//
// Revision 1.6 2006/05/12 17:52:37 gianasista
// added comments
//
// Revision 1.5 2006/04/14 17:14:22 gianasista
// Refactoring Support with dialog
//
// Revision 1.4 2006/02/19 21:47:44 gianasista
// Deleted unnecessary code
//
// Revision 1.3 2006/01/30 21:12:31 gianasista
// Further Refactorings (moved methods from singleton classes like PluginTools
// to facade classes)
//
// Revision 1.2 2006/01/28 15:48:25 gianasista
// Moved several methods from PluginTools to EditorPartFacade
//
// Revision 1.1 2006/01/25 21:26:30 gianasista
// Started implementing a smarter code
//
