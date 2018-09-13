package com.eclipsesource.workflow.analyzer.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.eclipsesource.workflow.dsl.WorkflowStandaloneSetup;
import com.eclipsesource.workflow.dsl.workflow.ProbabilityConfiguration;
import com.eclipsesource.workflow.dsl.workflow.WorkflowConfiguration;
import com.eclipsesource.workflow.dsl.workflow.WorkflowFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Injector;

import workflowanalyzer.Decision;
import workflowanalyzer.ForkOrJoin;
import workflowanalyzer.Merge;
import workflowanalyzer.Node;
import workflowanalyzer.Performer;
import workflowanalyzer.Task;

public class AnalyzeWorkflow {

	private ProbabilityConfiguration probconf;
	private Map<String, Node> nodeMap = new LinkedHashMap<String, Node>();
	private Map<String, Set<JsonObject>> weightedEdgeMap = new LinkedHashMap<String, Set<JsonObject>>();
	private WorkflowAnalysisGeneric analysis;

	public AnalyzeWorkflow(String graphJson, String configJson) throws IOException {
		analysis = new WorkflowAnalysisGeneric();
		JsonElement graph = new JsonParser().parse(graphJson);

		initializeProbabilityConfiguration(configJson);

		initializeNodeMap(graph);
		initializeConnections(graph);
		initializeProbabilities(graph);

	}

	private void initializeProbabilityConfiguration(String configJson) throws IOException {
		probconf = loadConfiguration(configJson);
		if (probconf == null) {
			probconf = WorkflowFactory.eINSTANCE.createProbabilityConfiguration();
			probconf.setLow(0.25f);
			probconf.setMedium(0.5f);
			probconf.setHigh(0.75f);
		}
	}

	private ProbabilityConfiguration loadConfiguration(String configJson) throws IOException {
		Injector injector = new WorkflowStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		Resource resource = rs.createResource(URI.createURI("dummy:/dummy.wfconfig"));
		try (ByteArrayInputStream bis = new ByteArrayInputStream(configJson.getBytes())) {
			resource.load(bis, null);
		} catch(IOException e) {
			System.err.println(resource.getErrors());
			throw e;
		}
		return ((WorkflowConfiguration) resource.getContents().get(0)).getProbConf();
	}

	private void initializeNodeMap(JsonElement graph) {
		JsonArray children = graph.getAsJsonObject().getAsJsonArray("children");
		children.forEach(element -> addToNodeMap(element));

	}

	private void addToNodeMap(JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		if (jsonObject.has("nodeType"))
			switch (jsonObject.get("nodeType").getAsString()) {
			case "decisionNode":
				nodeMap.put(getId(jsonObject), createDecision(jsonObject));
				break;
			case "mergeNode":
				nodeMap.put(getId(jsonObject), createMerge(jsonObject));
				break;
			case "forkNode":
				nodeMap.put(getId(jsonObject), createForkOrJoin(jsonObject));
				break;
			case "joinNode":
				nodeMap.put(getId(jsonObject), createForkOrJoin(jsonObject));
				break;
			}
		if (jsonObject.has("type"))
			switch (jsonObject.get("type").getAsString()) {
			case "node:task":
				nodeMap.put(getId(jsonObject), createTask(jsonObject));
				break;
			}
	}

	private Task createTask(JsonObject object) {
		Task task = new Task(getName(object), new Performer("unkown"), getDuration(object));
		if (isAutomaticTask(object)) {
			analysis.addTask(task, "automatic");
		} else if (isManualTask(object)) {
			analysis.addTask(task, "manual");
		}
		return task;
	}

	private boolean isAutomaticTask(JsonObject object) {
		return getTaskType(object).equals("automated");
	}

	private boolean isManualTask(JsonObject object) {
		return getTaskType(object).equals("manual");
	}

	private Node createDecision(JsonObject object) {
		return new Decision(getId(object), new HashMap<>());
	}

	private Node createMerge(JsonObject object) {
		return new Merge(getId(object));
	}

	private Node createForkOrJoin(JsonObject object) {
		return new ForkOrJoin(getId(object));
	}

	private String getId(JsonObject object) {
		return object.get("id").getAsString();
	}

	private String getName(JsonObject object) {
		return object.get("name").getAsString();
	}

	private String getTaskType(JsonObject object) {
		return object.get("taskType").getAsString();
	}

	private int getDuration(JsonObject object) {
		return object.get("duration").getAsInt();
	}

	private void initializeConnections(JsonElement graph) {
		JsonArray children = graph.getAsJsonObject().getAsJsonArray("children");
		children.forEach(this::connectSourceAndTarget);
	}

	private void connectSourceAndTarget(JsonElement edge) {
		JsonObject jsonObject = edge.getAsJsonObject();
		if (jsonObject.get("type").getAsString().equals("edge") || jsonObject.get("type").getAsString().equals("edge:weighted")) {
			Node sourceNode = nodeMap.get(jsonObject.get("sourceId").getAsString());
			sourceNode.connectTo(nodeMap.get(jsonObject.get("targetId").getAsString()));
		}
		if (jsonObject.get("type").getAsString().equals("edge:weighted")) {
			String sourceId = jsonObject.get("sourceId").getAsString();
			if (!weightedEdgeMap.containsKey(sourceId)) {
				weightedEdgeMap.put(sourceId, new LinkedHashSet<JsonObject>());
			}
			weightedEdgeMap.get(sourceId).add(jsonObject);
		}
	}

	private void initializeProbabilities(JsonElement graph) {
		JsonArray children = graph.getAsJsonObject().getAsJsonArray("children");
		children.forEach(this::setProbabilities);
	}

	private void setProbabilities(JsonElement decisionNode) {
		JsonObject jsonObject = decisionNode.getAsJsonObject();

		if (jsonObject.has("nodeType") && jsonObject.get("nodeType").getAsString().equals("decisionNode")) {
			Decision decision = (Decision) nodeMap.get(getId(jsonObject));

			for (JsonObject edge : weightedEdgeMap.get(getId(jsonObject))) {
				String probability = edge.get("probability").getAsString();
				Node targetNode = nodeMap.get(edge.get("targetId").getAsString());
				decision.getProbabilities().put(targetNode, toFloat(probability));
			}
		}

	}

	private Float toFloat(String probability) {
		switch (probability) {
		case "low":
			return probconf.getLow();
		case "medium":
			return probconf.getMedium();
		case "high":
			return probconf.getHigh();
		}
		return 1f;
	}
	
	public String generate() {
		return analysis.generateAnalysisDataAsJson(nodeMap.values().stream().collect(Collectors.toList()));
	}
}
