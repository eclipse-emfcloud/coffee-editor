package com.eclipsesource.workflow.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.google.common.base.Preconditions;

public abstract class AbstractWorkflowGeneratorInput implements IWorkflowGeneratorInput {

	protected IResource resource;
	protected IProject project;

	public AbstractWorkflowGeneratorInput(IResource resource) {
		this.resource = Preconditions.checkNotNull(resource);
		this.project = Preconditions.checkNotNull(resource.getProject());
	}
	
	@Override
	public IResource getResource() {
		return resource;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void dispose() {
		// do nothing		
	}

}
