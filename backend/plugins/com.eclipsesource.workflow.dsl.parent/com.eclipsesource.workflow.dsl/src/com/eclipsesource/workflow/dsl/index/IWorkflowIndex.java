package com.eclipsesource.workflow.dsl.index;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.workflow.IWorkflowGraph;
import com.eclipsesource.workflow.IWorkflowTask;

public interface IWorkflowIndex {
	void putGraph(String uri, IWorkflowGraph graph);

	void removeGraph(String uri);

	Collection<IWorkflowGraph> getGraphs();
	
	Optional<IWorkflowGraph> getGraph(String graphId);
	
	List<IWorkflowTask> getTasks(String graphId);
	
	Optional<IWorkflowTask> getTask(String graphId, String taskId);

	Optional<IWorkflowTask> getTaskByName(String graphId, String taskName);
}
