package com.eclipsesource.workflow.analyzer.ui;

import static com.eclipsesource.workflow.analyzer.ui.WorkflowAnalysis.loadResource;

import java.util.Arrays;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Model;

public class WorkflowAnalysisHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		Object element = selection.getFirstElement();
		if (element instanceof ActivityEditPart) {
			ActivityEditPart editPart = (ActivityEditPart) element;
			Object modelElement = editPart.getAdapter(Activity.class);
			if (modelElement instanceof Activity) {
				Activity activity = (Activity) modelElement;
				runAnalysis(activity, null);
			}
		} else {
			IEditorInput editorInput = HandlerUtil.getActiveEditorInput(event);
			if (editorInput != null) {
				IContainer rootDir = ((FileEditorInput) editorInput).getFile().getParent();
				try {
					Optional<IFile> file = Arrays.stream(rootDir.members())
							.filter(f -> f.getLocation().getFileExtension().equals("uml")).map(IFile.class::cast)
							.findFirst();
					if (file.isPresent()) {
						Activity activity = loadActivity(file.get());
						runAnalysis(activity, file.get());
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}

			}

		}
		return null;
	}

	private Activity loadActivity(IResource iResource) {
		URI uri = URI.createFileURI(iResource.getLocation().toString());
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = loadResource(uri, rs);
		return ((Model) resource.getContents().get(0)).getOwnedElements().stream().filter(Activity.class::isInstance)
				.map(Activity.class::cast).findFirst().orElse(null);

	}

	private void runAnalysis(Activity activity, final IFile modelFile) {
		Job job = new Job("Running workflow analysis of " + activity.getName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {

					URI uri = activity.eResource().getURI();
					IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
					IFile umlFile = modelFile != null ? modelFile
							: workspaceRoot.getFile(new Path(uri.toPlatformString(true)));

					IContainer container = umlFile.getParent();
					IFile file = new WorkflowAnalysis(activity).persistIn(container, monitor);
					openEditor(file);
				} catch (CoreException e) {
					e.printStackTrace();
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();
	}

	private void openEditor(IFile file) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
					IWorkbenchPage page = window.getActivePage();
					IDE.openEditor(page, file);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
