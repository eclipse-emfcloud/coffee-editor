package com.eclipsesource.workflow.architecture.internal.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.uml.diagram.common.editparts.IUMLEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class UIUtil {

	private UIUtil() {
	};

	public static ISelection getCurrentSelection() {
		IWorkbenchPage page = getActivePage();
		if (page != null) {
			return page.getSelection();
		}
		return null;
	}

	public static IProject getCurrentProject() {
		IWorkbenchPage page = getActivePage();

		if (page != null) {
			ISelection selection = page.getSelection();
			IResource resource = extractSelection(selection);
			if (resource != null && resource.getProject() != null) {
				return resource.getProject();
			}

			IEditorPart editor = page.getActiveEditor();
			if (editor != null) {
				IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
				IFile file = input.getFile();
				return file.getProject();
			}
		}
		return null;
	}

	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (wb != null) {
			return wb.getActiveWorkbenchWindow();
		}
		return null;
	}

	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow wbw = getActiveWorkbenchWindow();
		if (wbw != null) {
			return wbw.getActivePage();
		}
		return null;
	}

	public static IEditorPart getActiveEditor() {
		IWorkbenchPage page = getActivePage();
		if (page != null) {
			return page.getActiveEditor();
		}
		return null;
	}

	public static IResource extractSelection(ISelection sel) {
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IResource) {
			return (IResource) element;
		}
		if (!(element instanceof IAdaptable)) {
			return null;
		}
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}

	public static EObject getSelectedObject() {
		ISelection selection = getCurrentSelection();
		return getSelectedObject(selection);
	}

	public static EObject getSelectedObject(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof IUMLEditPart) {
				return ((IUMLEditPart) object).getUMLElement();
			}
		}
		return null;
	}

	public static DiagramEditor getDiagramEditor() {
		IEditorPart editor = getActiveEditor();
		return getDiagramEditor(editor);
	}

	public static DiagramEditor getDiagramEditor(IEditorPart editor) {
		if (editor instanceof DiagramEditor) {
			return (DiagramEditor) editor;
		} else if (editor instanceof PapyrusMultiDiagramEditor) {
			return (DiagramEditor) ((PapyrusMultiDiagramEditor) editor).getActiveEditor();
		}
		return null;
	}

}
