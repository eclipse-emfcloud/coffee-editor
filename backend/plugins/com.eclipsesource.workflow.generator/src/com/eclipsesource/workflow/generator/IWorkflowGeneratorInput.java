package com.eclipsesource.workflow.generator;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.Model;

public interface IWorkflowGeneratorInput {

	Model getModel();

	IProject getProject();

	void dispose();

}
