package com.eclipsesource.workflow.generator;

import java.util.List;

public interface IWorkflowGeneratorInput {

	String getPackageName();
	
	String getSourceFileName();
	
	List<IWorkflowTask> getTasks();

	void dispose();

}
