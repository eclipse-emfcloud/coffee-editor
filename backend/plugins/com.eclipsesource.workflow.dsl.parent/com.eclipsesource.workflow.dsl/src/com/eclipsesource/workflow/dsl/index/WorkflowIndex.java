package com.eclipsesource.workflow.dsl.index;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eclipsesource.workflow.IWorkflowGraph;
import com.eclipsesource.workflow.IWorkflowTask;
import com.google.inject.Singleton;

@Singleton
public class WorkflowIndex implements IWorkflowIndex {	
	private static Map<URI, IWorkflowGraph> graphs = new HashMap<>();
	
	@Override
	public void putGraph(String uri, IWorkflowGraph graph) {
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
	public Collection<IWorkflowGraph> getGraphs() {
		return Collections.unmodifiableCollection(graphs.values());
	}
	
	@Override
	public Optional<IWorkflowGraph> getGraph(String graphId) {
		return graphs.values().stream().filter(graph -> graph.getId().equals(graphId)).findAny();
	}
		
	@Override
	public List<IWorkflowTask> getTasks(String graphId) {
		return getGraph(graphId).map(IWorkflowGraph::getTasks).orElse(Collections.emptyList());
	}
	
	@Override
	public Optional<IWorkflowTask> getTask(String graphId, String taskId) {
		return getGraph(graphId).map(graph -> graph.getTasks().stream().filter(task -> task.getId().equals(taskId)).findFirst()).orElse(Optional.empty());
	}
	
	@Override
	public Optional<IWorkflowTask> getTaskByName(String graphId, String taskName) {
		return getGraph(graphId).map(graph -> graph.getTasks().stream().filter(task -> task.getName().equals(taskName)).findFirst()).orElse(Optional.empty());
	}
}
