package com.eclipsesource.workflow.architecture.internal.commands;

import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getActivePage;
import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getCurrentProject;

import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getCurrentProject;
import org.apache.commons.lang.WordUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Action;

import com.eclipsesource.workflow.architecture.internal.Activator;

/**
 * 
 * @author Tobias Ortmayr <tortmayr.ext@eclipsesource.com>
 *
 */
public class ActionToCodeNavigationCommand extends AbstractCommand {
	private Action action;

	public ActionToCodeNavigationCommand(Action action) {
		super(action.getName());
		this.action = action;

	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {
		IWorkbenchPage page = getActivePage();
		CommandResult result = CommandResult.newOKCommandResult();
		if (page != null) {
			IProject project = getCurrentProject();

			if (project != null) {
				String path =toClassFileName(action);
				
				IFile file = project.getFile("src/" + path);
				if (path!=null &&!file.exists()) {
					new ErrorDialog(page.getActivePart().getSite().getShell(), "Source file does not exist",
							"The source file does not exist. We recommend to save this model and try again.",
							new Status(IStatus.WARNING, Activator.PLUGIN_ID,
									"Either the model has not been saved yet and, thus, "
											+ "the corresponding sources have not been generated yet, "
											+ "or an error occured during the generation."),
							IStatus.WARNING).open();
					return CommandResult.newErrorCommandResult("File does not exist");
				}
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				try {
					page.openEditor(new FileEditorInput(file), desc.getId());
				} catch (PartInitException e) {
					result = CommandResult.newErrorCommandResult(e);
				}
			}
		}

		return result;
	}

	private String toClassFileName(Action action) {
		IProject project = getCurrentProject();
		if (project != null) {
			return project.getName().replaceAll("\\.", "/") + "/" + normalize(action.getName()) + ".java";
		}
		return null;
	}

	private String normalize(String string) {
		// camelCase+ only alphanumeric
		return WordUtils.capitalize(string).replaceAll("[^A-Za-z0-9]", "");
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {
		return null;
	}

}
