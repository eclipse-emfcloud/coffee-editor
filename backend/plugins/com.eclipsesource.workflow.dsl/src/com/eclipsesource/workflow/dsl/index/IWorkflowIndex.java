package com.eclipsesource.workflow.dsl.index;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.Task;


public interface IWorkflowIndex {
	void putGraph(String uri, Machine graph);

	void removeGraph(String uri);

	Collection<Machine> getGraphs();
	
	Optional<Machine> getGraph(String graphId);
	
	List<Task> getTasks(String graphId);
	
	Optional<Task> getTaskByName(String graphId, String taskName);
}
