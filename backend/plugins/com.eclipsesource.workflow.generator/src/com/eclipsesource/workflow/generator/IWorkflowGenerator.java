package com.eclipsesource.workflow.generator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface IWorkflowGenerator {

	IStatus generate(IWorkflowGeneratorInput input, IProgressMonitor monitor);
	
	IWorkflowGeneratorOutput generateClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor);

	void dispose();

}
