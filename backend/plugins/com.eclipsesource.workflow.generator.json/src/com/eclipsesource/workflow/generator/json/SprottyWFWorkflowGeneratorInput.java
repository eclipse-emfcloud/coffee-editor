package com.eclipsesource.workflow.generator.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eclipsesource.workflow.generator.AbstractWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowTask;
import com.eclipsesource.workflow.generator.impl.AutomaticWorkflowTask;
import com.eclipsesource.workflow.generator.impl.ManualWorkflowTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SprottyWFWorkflowGeneratorInput extends AbstractWorkflowGeneratorInput implements IWorkflowGeneratorInput {

	private static final String GRAPH_TYPE = "type";
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
	
	private String content;
	private URI source;

	public SprottyWFWorkflowGeneratorInput(String packageName, String sourceFileName, String content) {
		super(packageName, sourceFileName);
		this.content = content;
	}
	public SprottyWFWorkflowGeneratorInput(String packageName, URI source) {
		super(packageName, source.getPath());
		this.source = source;
	}

	@Override
	public List<IWorkflowTask> getTasks() {
		List<IWorkflowTask> tasks = new ArrayList<>();
		JsonParser parser = new JsonParser();
		try {
			JsonElement graph;
			if(content!=null)
				graph = parser.parse(content);
			else
				graph = parser.parse(new FileReader(new File(source)));
			JsonElement graphType = graph.getAsJsonObject().get(GRAPH_TYPE);
			if(graphType == null || !GRAPH_TYPE_EXPECTED.equalsIgnoreCase(graphType.getAsString())) {
				return tasks;
			}
			JsonArray children = graph.getAsJsonObject().getAsJsonArray(GRAPH_CHILDREN);
			for(JsonElement task : children) {
				JsonElement taskType = task.getAsJsonObject().get(TASK_TYPE);
				if(taskType != null) {
					String childTaskType = taskType.getAsString();
					if(TASK_TYPE_AUTOMATIC.equals(childTaskType)) {
						tasks.add(createAutomaticWorkflowTask(task.getAsJsonObject()));
					}
					if(TASK_TYPE_MANUAL.equals(childTaskType)) {
						tasks.add(createManualWorkflowTask(task.getAsJsonObject()));
					}
				}
			}
		} catch (JsonIOException | JsonSyntaxException | IllegalStateException | FileNotFoundException e) {
		}
		return tasks;
	}
	
	private IWorkflowTask createAutomaticWorkflowTask(JsonObject task) {
		String id = getAsString(task, TASK_ID).orElse(TASK_ID_UNDEFINED);
		String name = getAsString(task, TASK_NAME).orElse(TASK_NAME_UNDEFINED);
		Integer duration = getAsInteger(task, TASK_DURATION).orElse(TASK_DURATION_UNDEFINED);
		String component = getAsString(task, TASK_REFERENCE).orElse(TASK_REFERENCE_UNDEFINED);
		return new AutomaticWorkflowTask(id, name, duration, component);
	}
	
	private IWorkflowTask createManualWorkflowTask(JsonObject task) {
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
