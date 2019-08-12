package com.eclipsesource.workflow.dsl.index;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.Task;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;


public interface IWorkflowIndex {
	void putGraph(String uri, Machine graph);

	void removeGraph(String uri);

	Collection<Machine> getMachines();
	
	Optional<Machine> getMachine(String machine);
	
	Collection<Workflow> getWorkflows(String machine);
	
	Optional<Workflow> getWorkflow(String machine, String workflow);
	
	List<Task> getTasks(String machine, String workflow);
	
	Optional<Task> getTask(String machine, String workflow, String task);
}
