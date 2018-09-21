package com.eclipsesource.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.workflow.IWorkflowGraph;
import com.eclipsesource.workflow.IWorkflowTask;

public class WorkflowGraph implements IWorkflowGraph {
	public static WorkflowGraph NULL_GRAPH = new WorkflowGraph("null");	
	
	private String id;
	private List<IWorkflowTask> tasks = new ArrayList<>();

	public WorkflowGraph(String id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<IWorkflowTask> getTasks() {
		return tasks;
	}

	@Override
	public boolean addTask(IWorkflowTask task) {
		return tasks.add(task);
	}

	@Override
	public boolean removeTask(IWorkflowTask task) {
		return tasks.remove(task);
	}

}
