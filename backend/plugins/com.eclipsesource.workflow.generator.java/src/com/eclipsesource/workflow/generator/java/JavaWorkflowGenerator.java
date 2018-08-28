package com.eclipsesource.workflow.generator.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.eclipsesource.workflow.generator.AbstractWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorOutput;
import com.eclipsesource.workflow.generator.impl.WorkflowGeneratorOutput;

public class JavaWorkflowGenerator extends AbstractWorkflowGenerator {
	@Override
	protected void validateInputModel(IWorkflowGeneratorInput input, IProgressMonitor monitor) throws CoreException {
		// do nothing
	}

	@Override
	public IWorkflowGeneratorOutput generateClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor) {
		WorkflowGeneratorOutput output = new WorkflowGeneratorOutput(input);
		
		DynamicCodeGenerator dynamicGen = new DynamicCodeGenerator();
		StaticCodeGenerator staticGen = new StaticCodeGenerator();
		
		String packageName = input.getPackageName();
		
		output.addGeneratedFile(
				staticGen.toWorkflowTaskName(packageName), 
				staticGen.toWorkflowTaskContent(packageName),
				false);
		
		output.addGeneratedFile(
				staticGen.toAutomaticWorkflowTaskName(packageName), 
				staticGen.toAutomaticWorkflowTaskContent(packageName),
				false);
		
		output.addGeneratedFile(
				staticGen.toManualWorkflowTaskName(packageName), 
				staticGen.toManualWorkflowTaskContent(packageName),
				false);		

		input.getTasks().stream().forEach(task -> {
			String dynamicFileName = dynamicGen.toFileName(input.getPackageName(), task.getName()).toString();
			String dynamicFileContent = dynamicGen.toFileContent(input.getPackageName(), input.getSourceFileName(), task).toString();
			output.addGeneratedFile(dynamicFileName, dynamicFileContent);
			
			String staticFileName = staticGen.toFileName(input.getPackageName(), task.getName()).toString();
			String staticFileContent = staticGen.toFileContent(input.getPackageName(), input.getSourceFileName(), task).toString();
			output.addGeneratedFile(staticFileName, staticFileContent);
		});
		return output;
	}
}
