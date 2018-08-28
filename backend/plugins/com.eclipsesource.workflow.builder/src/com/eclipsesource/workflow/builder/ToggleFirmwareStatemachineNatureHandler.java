package com.eclipsesource.workflow.builder;

import static com.eclipsesource.workflow.builder.ProjectNatureUtil.addProjectNature;
import static com.eclipsesource.workflow.builder.ProjectNatureUtil.removeProjectNature;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleFirmwareStatemachineNatureHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				IProject project = getProject(it.next());
				if (project != null) {
					try {
						toggleNature(project);
					} catch (CoreException e) {
						throw new ExecutionException("Failed to toggle firmware workflow nature", e);
					}
				}
			}
		}

		return null;
	}

	private IProject getProject(Object element) {
		final IProject project;
		if (element instanceof IResource) {
			project = ((IResource) element).getProject();
		} else if (element instanceof IAdaptable) {
			project = ((IAdaptable) element).getAdapter(IProject.class);
		} else {
			project = null;
		}
		return project;
	}

	private void toggleNature(IProject project) throws CoreException {
		if (!removeProjectNature(project, WorkflowNature.NATURE_ID, null)) {
			addProjectNature(project, WorkflowNature.NATURE_ID, null);
		}
	}

}