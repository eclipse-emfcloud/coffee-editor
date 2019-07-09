package com.eclipsesource.workflow.dsl.index;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eclipsesource.emfforms.coffee.model.coffee.Machine;
import com.eclipsesource.emfforms.coffee.model.coffee.Task;
import com.google.inject.Singleton;

@Singleton
public class WorkflowIndex implements IWorkflowIndex {	
	private static Map<URI, Machine> graphs = new HashMap<>();
	
	@Override
	public void putGraph(String uri, Machine graph) {
		if(graph != null && uri != null) {
			try {
				graphs.put(new URI(uri), graph);
			} catch (URISyntaxException e) {
			}
		}
	}
	
	@Override
	public void removeGraph(String uri) {
		try {
			graphs.remove(new URI(uri));
		} catch (URISyntaxException e) {
		}
	}
	
	@Override
	public Collection<Machine> getGraphs() {
		return Collections.unmodifiableCollection(graphs.values());
	}
	
	@Override
	public Optional<Machine> getGraph(String graphId) {
		return graphs.values().stream().filter(graph -> graph.getName().equals(graphId)).findAny();
	}
		
	@Override
	public List<Task> getTasks(String graphId) {
		return getGraph(graphId).map(m -> m.getWorkflows().stream().flatMap(w-> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast)).collect(Collectors.toList())).orElse(Collections.emptyList());
	}
	
//	@Override
//	public Optional<Task> getTask(String graphId, String taskId) {
//		  return getGraph(graphId).map(m -> m.getWorkflows().stream().flatMap(w-> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast).filter(task -> task.getId().equals(taskId))).findFirst()).orElse(Optional.empty());
//	}
	
	@Override
	public Optional<Task> getTaskByName(String graphId, String taskName) {
		return getGraph(graphId).map(m -> m.getWorkflows().stream().flatMap(w-> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast).filter(task -> task.getName().equals(taskName))).findFirst()).orElse(Optional.empty());
	}
}
