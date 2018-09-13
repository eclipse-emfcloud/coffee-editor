package com.eclipsesource.workflow.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.util.Optional;

import com.eclipsesource.workflow.IWorkflowGraph;
import com.eclipsesource.workflow.IWorkflowTask;
import com.eclipsesource.workflow.impl.AutomaticWorkflowTask;
import com.eclipsesource.workflow.impl.ManualWorkflowTask;
import com.eclipsesource.workflow.impl.WorkflowGraph;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SprottyWFParser {
	private static final String GRAPH_TYPE = "type";
	private static final String GRAPH_ID = "id";
	private static final String GRAPH_ID_UNDEFINED = "undefined";
	private static final String GRAPH_TYPE_EXPECTED = "graph";	
	private static final String GRAPH_CHILDREN = "children";

	private static final String TASK_ID = "id";
	private static final String TASK_ID_UNDEFINED = "undefined";
	private static final String TASK_TYPE = "taskType";
	private static final String TASK_TYPE_MANUAL = "manual";
	private static final String TASK_TYPE_AUTOMATIC = "automated";
	private static final String TASK_NAME = "name";
	private static final String TASK_NAME_UNDEFINED = "undefined";
	private static final String TASK_DURATION = "duration";
	private static final Integer TASK_DURATION_UNDEFINED = 0;
	private static final String TASK_REFERENCE = "reference";
	private static final String TASK_REFERENCE_UNDEFINED = "undefined";
	
	public static IWorkflowGraph parseGraph(String content) {
		try {
			return parseGraph(new JsonParser().parse(content));
		} catch (JsonIOException | JsonSyntaxException | IllegalStateException e) {
		}
		return IWorkflowGraph.NULL_GRAPH;
	}
	
	public static IWorkflowGraph parseGraph(URI fileURI) throws FileNotFoundException {
		try {
			return parseGraph(new JsonParser().parse(new FileReader(new File(fileURI))));
		} catch (JsonIOException | JsonSyntaxException | IllegalStateException e) {
		}
		return IWorkflowGraph.NULL_GRAPH;
	}
	
	public static IWorkflowGraph parseGraph(JsonElement graph) {
		try {
			JsonElement graphType = graph.getAsJsonObject().get(GRAPH_TYPE);
			if(graphType == null || !GRAPH_TYPE_EXPECTED.equalsIgnoreCase(graphType.getAsString())) {
				return IWorkflowGraph.NULL_GRAPH;
			}
			
			WorkflowGraph workflowGraph = new WorkflowGraph(GRAPH_ID_UNDEFINED);
			String graphId = getAsString(graph.getAsJsonObject(), GRAPH_ID).orElse(GRAPH_ID_UNDEFINED);
			workflowGraph.setId(graphId);

			JsonArray children = graph.getAsJsonObject().getAsJsonArray(GRAPH_CHILDREN);
			for(JsonElement task : children) {
				JsonElement taskType = task.getAsJsonObject().get(TASK_TYPE);
				if(taskType != null) {
					String childTaskType = taskType.getAsString();
					if(TASK_TYPE_AUTOMATIC.equals(childTaskType)) {
						workflowGraph.addTask(createAutomaticWorkflowTask(task.getAsJsonObject()));
					}
					if(TASK_TYPE_MANUAL.equals(childTaskType)) {
						workflowGraph.addTask(createManualWorkflowTask(task.getAsJsonObject()));
					}
				}
			}
			return workflowGraph;
		} catch (JsonIOException | JsonSyntaxException | IllegalStateException e) {
		}
		return IWorkflowGraph.NULL_GRAPH;
	}
	
	private static IWorkflowTask createAutomaticWorkflowTask(JsonObject task) {
		String id = getAsString(task, TASK_ID).orElse(TASK_ID_UNDEFINED);
		String name = getAsString(task, TASK_NAME).orElse(TASK_NAME_UNDEFINED);
		Integer duration = getAsInteger(task, TASK_DURATION).orElse(TASK_DURATION_UNDEFINED);
		String component = getAsString(task, TASK_REFERENCE).orElse(TASK_REFERENCE_UNDEFINED);
		return new AutomaticWorkflowTask(id, name, duration, component);
	}
	
	private static IWorkflowTask createManualWorkflowTask(JsonObject task) {
		String id = getAsString(task, TASK_ID).orElse(TASK_ID_UNDEFINED);
		String name = getAsString(task, TASK_NAME).orElse(TASK_NAME_UNDEFINED);
		Integer duration = getAsInteger(task, TASK_DURATION).orElse(TASK_DURATION_UNDEFINED);
		String actor = getAsString(task, TASK_REFERENCE).orElse(TASK_REFERENCE_UNDEFINED);
		return new ManualWorkflowTask(id, name, duration, actor);
	}
	
	private static Optional<String> getAsString(JsonObject object, String member) {
		JsonElement jsonElement = object.get(member);
		return jsonElement == null ? Optional.empty() : Optional.ofNullable(jsonElement.getAsString());
	}
	
	private static Optional<Integer> getAsInteger(JsonObject object, String member) {
		JsonElement jsonElement = object.get(member);
		return jsonElement == null ? Optional.empty() : Optional.ofNullable(jsonElement.getAsInt());
	}
}
