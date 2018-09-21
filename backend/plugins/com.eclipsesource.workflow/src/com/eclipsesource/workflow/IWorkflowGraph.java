package com.eclipsesource.workflow;

import java.util.List;

import com.eclipsesource.workflow.impl.WorkflowGraph;

public interface IWorkflowGraph {
	IWorkflowGraph NULL_GRAPH = new WorkflowGraph("null");
	
	String getId();
	
	List<IWorkflowTask> getTasks();
	
	boolean addTask(IWorkflowTask task);
	
	boolean removeTask(IWorkflowTask task);
}
