package com.eclipsesource.workflow.generator;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public interface IWorkflowGeneratorInput {

	IProject getProject();
	
	IResource getResource();
	
	List<IWorkflowTask> getTasks();

	void dispose();

}
