package com.eclipsesource.workflow.generator;

import com.eclipsesource.workflow.IWorkflowGraph;

public interface IWorkflowGeneratorInput {

	String getPackageName();
	
	String getSourceFileName();
	
	IWorkflowGraph getGraph();

	void dispose();

}
