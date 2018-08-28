package com.eclipsesource.workflow.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;

import com.google.common.base.Preconditions;

public class WorkflowGeneratorInput implements IWorkflowGeneratorInput {

	private IResource umlResource;
	private ResourceSet resourceSet;
	private Resource modelResource;
	private IProject project;

	public WorkflowGeneratorInput(IResource umlResource) {
		this.umlResource = Preconditions.checkNotNull(umlResource);
		this.project = Preconditions.checkNotNull(umlResource.getProject());
	}

	@Override
	public Model getModel() {
		for (EObject eObject : getModelResource().getContents()) {
			if (eObject instanceof Model) {
				return (Model) eObject;
			}
		}
		return null;
	}

	private Resource getModelResource() {
		if (modelResource == null) {
			resourceSet = new ResourceSetImpl();
			URI uri = URI.createPlatformResourceURI(umlResource.getFullPath().toString(), true);
			modelResource = resourceSet.getResource(uri, true);
		}
		return modelResource;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void dispose() {
		if (modelResource != null) {
			modelResource.unload();
		}
	}

}
