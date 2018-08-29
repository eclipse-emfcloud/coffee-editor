package com.eclipsesource.workflow.analyzer.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eclipse.core.runtime.IProgressMonitor;
import com.google.gson.Gson;

import workflowanalyzer.Decision;
import workflowanalyzer.Node;
import workflowanalyzer.NodeExecutionIterator;
import workflowanalyzer.Task;
import workflowanalyzer.Workflow;

public class WorkflowAnalysisGeneric {

	private class TaskElement {
		@SuppressWarnings("unused")
		String name, type;

		@SuppressWarnings("unused")
		int size;

		List<TaskElement> children = new ArrayList<>();

		public TaskElement(String name, String type, int size) {
			super();
			this.name = name;
			this.type = type;
			this.size = size;
		}
	}
	
	private Map<Task, String> taskTypeMap = new HashMap<>();
	
	public void addTask(Task task, String type) {
		taskTypeMap.put(task, type);
	}
	private Task getFirstTask(Workflow workflow) {
		return (Task) workflow.getNodes().stream()
				.filter((node) -> node instanceof Task && node.getIncoming().isEmpty()).findFirst().get();
	}
	public String generateAnalysisDataAsJson(List<Node> nodes, IProgressMonitor monitor) {
		final TaskElement rootTask = new TaskElement("Root", "", 1000);
		buildTaskElementHierarchy(getFirstTask(new Workflow(nodes)), rootTask, 1f);
		return new Gson().toJson(rootTask);
	}
	
	public InputStream generateAnalysisData(List<Node> nodes, IProgressMonitor monitor) {
		String json = generateAnalysisDataAsJson(nodes, monitor);
		return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
	}
	
	private void buildTaskElementHierarchy(Node node, TaskElement rootTaskElement, float probability) {
		TaskElement currentTaskElement = rootTaskElement;
		int size = Math.round(currentTaskElement.size * probability);
		NodeExecutionIterator iterator = node.getNodeExecutionIterator();
		while (iterator.hasNext()) {
			final Node next = iterator.next();
			if (next instanceof Task) {
				TaskElement taskElement = createTaskElement((Task) next, size);
				currentTaskElement.children.add(taskElement);
				currentTaskElement = taskElement;
			} else if (next instanceof Decision) {
				for (Node nodeAfterDecision : next.getOutgoing()) {
					buildTaskElementHierarchy(nodeAfterDecision, currentTaskElement,
							nodeAfterDecision.getProbabilityInBranch());
				}
				return;
			}
		}
	}
	
	private TaskElement createTaskElement(Task task, int probability) {
		String type = Optional.ofNullable(taskTypeMap.get(task)).orElse("");
		TaskElement taskElement = new TaskElement(task.getName(), type, probability);
		return taskElement;
	}
}
