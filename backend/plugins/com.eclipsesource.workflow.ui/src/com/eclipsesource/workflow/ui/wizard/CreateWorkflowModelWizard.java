package com.eclipsesource.workflow.ui.wizard;

import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getCurrentProject;
import static com.eclipsesource.workflow.builder.WorkflowNature.NATURE_ID;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRepresentationKindPage;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.CreateModelWizard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;

import com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants;
import com.eclipsesource.workflow.builder.ProjectNatureUtil;
import com.eclipsesource.workflow.ui.Activator;
import com.eclipsesource.workflow.ui.pages.MockSelectRepresentationKindPage;
import com.eclipsesource.workflow.ui.pages.WorkflowMainPage;

public class CreateWorkflowModelWizard extends CreateModelWizard {

	private WorkflowMainPage mainPage;
	private IProject project;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.project = getCurrentProject();
		setWindowTitle("Create new Workflow Model");
		super.init(workbench, selection);
		setDefaultPageImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/papyrus/PapyrusProjectWizban_75x66.gif")); //$NON-NLS-1$
		setMainPage(new WorkflowMainPage("WFMainPage"));
		getMainPage().setDescription("Choose your model name and select a set of actors and components");
		if (project == null) {
			mainPage.setNoProject(true);
		}

	}

	@Override
	public boolean performFinish() {
		if (super.performFinish()) {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);

			Resource modelResource = resourceSet.getResource(createUMLModelURI(), true);
			Model model = (Model) modelResource.getContents().get(0);
			Activity activity = (Activity) model.getPackagedElements().stream().filter(e -> e instanceof Activity)
					.findFirst().orElse(null);
			if (activity != null) {
				activity.setName(getMainPage().getModelName());
			}
			getMainPage().getSelectedActors()
					.forEach(s -> model.createPackagedElement(s, UMLPackage.eINSTANCE.getActor()));
			getMainPage().getSelectedComponents()
					.forEach(s -> model.createPackagedElement(s, UMLPackage.eINSTANCE.getComponent()));

			try {
				modelResource.save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				ProjectNatureUtil.addProjectNature(project, NATURE_ID, new NullProgressMonitor());
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return true;
		}

		return false;
	}

	@Override
	public void addPages() {
		addPage(mainPage);
		super.addPages();
	}

	@Override
	public boolean canFinish() {
		return project != null && getMainPage().isPageComplete();
	}

	@Override
	protected SelectRepresentationKindPage doCreateSelectRepresentationKindPage() {
		return new MockSelectRepresentationKindPage(createContextProvider());
	};

	@Override
	protected String[] getSelectedContexts() {
		return new String[] { ArchitectureConstants.CONTEXT_ID };
	}

	@Override
	protected String[] getSelectedViewpoints() {
		return new String[] { ArchitectureConstants.VIEWPOINT_ID };
	}

	public void setMainPage(WorkflowMainPage mainPage) {
		this.mainPage = mainPage;
	}

	public WorkflowMainPage getMainPage() {
		return mainPage;
	}

	@Override
	public IWizardPage getStartingPage() {
		return mainPage;
	}

	@Override
	protected URI createNewModelURI(String categoryId) {
		return createModelURI(getDiagramFileExtension(categoryId));

	}

	protected URI createUMLModelURI() {
		return createModelURI("uml");
	}

	private URI createModelURI(String extension) {
		IPath newFilePath = getProject().getFullPath()
				.append(((WorkflowMainPage) getPage("WFMainPage")).getModelName() + "." + extension); //$NON-NLS-1$
		return URI.createPlatformResourceURI(newFilePath.toString(), true);
	}

	private IProject getProject() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelection selection = page.getSelection();
		IResource resource = extractSelection(selection);
		IEditorPart editor = page.getActiveEditor();
		IProject project = null;
		try {
			if (resource.getProject() == null) {
				IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
				IFile file = input.getFile();
				project = file.getProject();
			} else {
				project = resource.getProject();

			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			try {
				IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
				IFile file = input.getFile();
				project = file.getProject();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return project;
	}

	IResource extractSelection(ISelection sel) {
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

}
