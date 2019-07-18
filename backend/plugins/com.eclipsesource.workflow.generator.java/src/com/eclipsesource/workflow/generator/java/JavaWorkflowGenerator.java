package com.eclipsesource.workflow.generator.java;

import com.eclipsesource.workflow.generator.IWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorOutput;
import com.eclipsesource.workflow.generator.impl.WorkflowGeneratorOutput;

public class JavaWorkflowGenerator implements IWorkflowGenerator {
	private static final String SRC_FOLDER = "src";
	private static final String SRC_GEN_FOLDER = "src-gen";

	// workflow library
	private WorkflowTaskGenerator workflowTaskGen = new WorkflowTaskGenerator(SRC_GEN_FOLDER);
	private AutomaticWorkflowTaskGenerator automaticWorkflowTaskGen = new AutomaticWorkflowTaskGenerator(SRC_GEN_FOLDER);
	private ManualWorkflowTaskGenerator manualWorkflowTaskGen = new ManualWorkflowTaskGenerator(SRC_GEN_FOLDER);
	
	// testing library
	private JUnitUserTaskTestGenerator junitTestGen = new JUnitUserTaskTestGenerator(SRC_FOLDER);
	
	// task-specific files
	private TaskGenerator abstractTaskGen = new TaskGenerator(SRC_GEN_FOLDER);
	private UserTaskGenerator userTaskGen = new UserTaskGenerator(SRC_FOLDER);	
	

	@Override
	public IWorkflowGeneratorOutput generateClasses(IWorkflowGeneratorInput input) {
		WorkflowGeneratorOutput output = new WorkflowGeneratorOutput(input);
		if(input == null || input.getGraph() == null || input.getGraph().getTasks().isEmpty()) {
			return output;
		}
		
		String packageName = JavaUtil.normalize(input.getPackageName());
		String sourceFileName = input.getSourceFileName();
		
		// generate libraries
		generateWorkflowLibrary(output, packageName, SRC_GEN_FOLDER);

		// generate tasks
		input.getGraph().getTasks().stream().forEach(task -> {
			output.addGeneratedFile(
					abstractTaskGen.toFileName(packageName, task.getName()), 
					abstractTaskGen.toFileContent(packageName, sourceFileName, task));
			
			output.addGeneratedFile(
					userTaskGen.toFileName(packageName, task.getName()), 
					userTaskGen.toFileContent(packageName, sourceFileName, task));			
		});
		
		// generate JUnit test		
		output.addGeneratedFile(
				junitTestGen.toFileName(packageName),
				junitTestGen.toFileContent(packageName, sourceFileName, input.getGraph().getTasks()));

		return output;
	}

	private void generateWorkflowLibrary(WorkflowGeneratorOutput output, String packageName, String sourceDir) {
		output.addGeneratedFile(
				workflowTaskGen.toFileName(packageName), 
				workflowTaskGen.toFileContent(packageName),
				false);
		
		output.addGeneratedFile(
				automaticWorkflowTaskGen.toFileName(packageName), 
				automaticWorkflowTaskGen.toFileContent(packageName),
				false);
		
		output.addGeneratedFile(
				manualWorkflowTaskGen.toFileName(packageName), 
				manualWorkflowTaskGen.toFileContent(packageName),
				false);
	}
}
