/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.generator.GeneratedFile;

public class JavaWorkflowGenerator {
	private static final String SRC_FOLDER = "src";
	private static final String SRC_GEN_FOLDER = "src-gen";

	// workflow library
	private WorkflowTaskGenerator workflowTaskGen = new WorkflowTaskGenerator(SRC_GEN_FOLDER);
	private AutomaticWorkflowTaskGenerator automaticWorkflowTaskGen = new AutomaticWorkflowTaskGenerator(SRC_GEN_FOLDER);
	private ManualWorkflowTaskGenerator manualWorkflowTaskGen = new ManualWorkflowTaskGenerator(SRC_GEN_FOLDER);
	
	// testing library
//	private JUnitUserTaskTestGenerator junitTestGen = new JUnitUserTaskTestGenerator(SRC_FOLDER);
	// main runner
	private MainMethodUserTaskGenerator mainTestGen = new MainMethodUserTaskGenerator(SRC_FOLDER);
	
	// task-specific files
	private TaskGenerator abstractTaskGen = new TaskGenerator(SRC_GEN_FOLDER);
	private UserTaskGenerator userTaskGen = new UserTaskGenerator(SRC_FOLDER);	
	

	public Collection<GeneratedFile> generateClasses(Machine machine, String packageName, String sourceFileName) {
		List<GeneratedFile> result = new ArrayList<GeneratedFile>();
		
		// generate libraries
		generateWorkflowLibrary(result, packageName, SRC_GEN_FOLDER);

		List<Task> tasks = machine.getWorkflows().stream().flatMap(w -> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast)).collect(Collectors.toList());
		
		// generate tasks
		tasks.forEach(task -> {
			result.add(new GeneratedFile(
					abstractTaskGen.toFileName(packageName, task.getName()), 
					abstractTaskGen.toFileContent(packageName, sourceFileName, task)));
			
			result.add(new GeneratedFile(
					userTaskGen.toFileName(packageName, task.getName()), 
					userTaskGen.toFileContent(packageName, sourceFileName, task)));			
		});
		
		// generate JUnit test	
//		result.add(new GeneratedFile(junitTestGen.toFileName(packageName), junitTestGen.toFileContent(packageName, sourceFileName, tasks), true));

		result.add(new GeneratedFile(mainTestGen.toFileName(packageName), mainTestGen.toFileContent(packageName, sourceFileName, tasks), true));

		return result;
	}

	private void generateWorkflowLibrary(Collection<GeneratedFile> result, String packageName, String sourceDir) {
		result.add(new GeneratedFile(
				workflowTaskGen.toFileName(packageName), 
				workflowTaskGen.toFileContent(packageName),
				false));
		
		result.add(new GeneratedFile(
				automaticWorkflowTaskGen.toFileName(packageName), 
				automaticWorkflowTaskGen.toFileContent(packageName),
				false));
		
		result.add(new GeneratedFile(
				manualWorkflowTaskGen.toFileName(packageName), 
				manualWorkflowTaskGen.toFileContent(packageName),
				false));
	}
}
