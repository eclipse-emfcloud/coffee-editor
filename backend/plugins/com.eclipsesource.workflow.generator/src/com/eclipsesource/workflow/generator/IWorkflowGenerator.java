package com.eclipsesource.workflow.generator;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IWorkflowGenerator {
	
	IWorkflowGeneratorOutput generateClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor);

}
