package com.eclipsesource.workflow.generator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

public abstract class AbstractWorkflowGenerator implements IWorkflowGenerator {

	@Override
	public IStatus generate(IWorkflowGeneratorInput input, IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 3); // three steps
		try {
			subMonitor.setTaskName("Validating model");
			validateInputModel(input, subMonitor.split(1));
			subMonitor.setTaskName("Creating manual classes for all tasks");
			generateTasksClasses(input, subMonitor.split(1));
			return Status.OK_STATUS;
		} catch (CoreException e) {
			return e.getStatus();
		} finally {
			subMonitor.setWorkRemaining(0);
		}
	}

	protected abstract void validateInputModel(IWorkflowGeneratorInput input, IProgressMonitor monitor)
			throws CoreException;

	protected abstract void generateTasksClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor)
			throws CoreException;

	@Override
	public void dispose() {
		// nothing to dispose
	}

}
