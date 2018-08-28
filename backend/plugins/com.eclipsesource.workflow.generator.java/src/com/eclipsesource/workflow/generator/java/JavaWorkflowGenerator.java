package com.eclipsesource.workflow.generator.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IPackageFragment;

import com.eclipsesource.workflow.generator.AbstractWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;

public class JavaWorkflowGenerator extends AbstractWorkflowGenerator {

	IPackageFragment pack;

	@Override
	protected void validateInputModel(IWorkflowGeneratorInput input, IProgressMonitor monitor) throws CoreException {
		// do nothing
	}

	@Override
	protected void generateTasksClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 4);
		DynamicCodeGenerator dynamicGen = new DynamicCodeGenerator();
		StaticCodeGenerator staticGen = new StaticCodeGenerator();
		dynamicGen.init(input.getProject(), subMonitor.split(1));
		staticGen.init(input.getProject(), subMonitor.split(1));

		input.getTasks().stream().forEach(task -> {
			dynamicGen.generate(input.getProject(), input.getResource(), task, subMonitor.split(1));
			staticGen.generate(input.getProject(), input.getResource(), task, subMonitor.split(1));
		});
	}

}
