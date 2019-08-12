package com.eclipsesource.workflow.analyzer.coffee;

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
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.eclipsesource.modelserver.coffee.model.coffee.AutomaticTask;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeePackage;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.ManualTask;
import com.eclipsesource.modelserver.coffee.model.coffee.Probability;
import com.eclipsesource.modelserver.coffee.model.coffee.WeightedFlow;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;
import com.eclipsesource.workflow.dsl.WorkflowStandaloneSetup;
import com.eclipsesource.workflow.dsl.workflow.ProbabilityConfiguration;
import com.eclipsesource.workflow.dsl.workflow.WorkflowConfiguration;
import com.eclipsesource.workflow.dsl.workflow.WorkflowFactory;
import com.google.inject.Injector;

import workflowanalyzer.Decision;
import workflowanalyzer.ForkOrJoin;
import workflowanalyzer.Merge;
import workflowanalyzer.Node;
import workflowanalyzer.Performer;
import workflowanalyzer.Task;

public class AnalyzeWorkflow {
	private ProbabilityConfiguration probabilities;
	private Map<com.eclipsesource.modelserver.coffee.model.coffee.Node, Node> nodeMap = new LinkedHashMap<>();
	private Map<com.eclipsesource.modelserver.coffee.model.coffee.Node, Set<WeightedFlow>> weightedEdgeMap = new LinkedHashMap<>();
	private WorkflowAnalysisGeneric analysis;

	public AnalyzeWorkflow(String machine, String config) throws IOException {
		this(loadResource(machine), config);
	}

	public AnalyzeWorkflow(Machine machine, String config) throws IOException {
		this(machine, loadWorkflowConfiguration(config));
	}

	public AnalyzeWorkflow(Machine machine, WorkflowConfiguration config) {
		analysis = new WorkflowAnalysisGeneric();
		probabilities = getProbabilityConfiguration(config);
		Workflow workflow = machine.getWorkflows().stream()
					.filter(w-> w.getName().equals(config.getModel())).findFirst()
					.orElseThrow(()->new IllegalArgumentException(String.format("No workflow with name %s in %s",config.getModel(),config.getMachine())));
		workflow.getNodes().forEach(this::addToNodeMap);
		workflow.getFlows().forEach(this::connectSourceAndTarget);
		workflow.getNodes().forEach(this::setProbabilities);
	}

	private ProbabilityConfiguration getProbabilityConfiguration(WorkflowConfiguration config) {
		ProbabilityConfiguration probabilities = config.getProbConf();
		if (probabilities == null) {
			probabilities = WorkflowFactory.eINSTANCE.createProbabilityConfiguration();
			probabilities.setLow(0.25f);
			probabilities.setMedium(0.5f);
			probabilities.setHigh(0.75f);
		}
		return probabilities;
	}

	private void addToNodeMap(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		if (node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Decision) {
			nodeMap.put(node, createDecision(node));
		} else if (node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Merge) {
			nodeMap.put(node, createMerge(node));
		} else if (node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Fork
				|| node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Join) {
			nodeMap.put(node, createForkOrJoin(node));
		} else if (node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Task) {
			nodeMap.put(node, createTask((com.eclipsesource.modelserver.coffee.model.coffee.Task) node));
		}
	}

	private Task createTask(com.eclipsesource.modelserver.coffee.model.coffee.Task node) {
		Task task = new Task(node.getName(), new Performer("unkown"), node.getDuration());
		if (node instanceof AutomaticTask) {
			analysis.addTask(task, "automatic");
		} else if (node instanceof ManualTask) {
			analysis.addTask(task, "manual");
		}
		return task;
	}

	private Node createDecision(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		return new Decision(getId(node), new HashMap<>());
	}

	private Node createMerge(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		return new Merge(getId(node));
	}

	private Node createForkOrJoin(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		return new ForkOrJoin(getId(node));
	}

	private String getId(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		return node.eResource().getURIFragment(node);
	}

	private void connectSourceAndTarget(Flow flow) {
		com.eclipsesource.modelserver.coffee.model.coffee.Node source = flow.getSource();
		com.eclipsesource.modelserver.coffee.model.coffee.Node target = flow.getTarget();
		Node sourceNode = nodeMap.get(source);
		Node targetNode = nodeMap.get(target);
		sourceNode.connectTo(targetNode);

		if (flow instanceof WeightedFlow) {
			weightedEdgeMap.computeIfAbsent(source, id -> new LinkedHashSet<>()).add((WeightedFlow) flow);
		}
	}

	private void setProbabilities(com.eclipsesource.modelserver.coffee.model.coffee.Node node) {
		if(node instanceof com.eclipsesource.modelserver.coffee.model.coffee.Decision) {
			Decision decision = (Decision) nodeMap.get(node);
			for (WeightedFlow flow : weightedEdgeMap.get(node)) {
				Probability probability = flow.getProbability();
				Node targetNode = nodeMap.get(flow.getTarget());
				decision.getProbabilities().put(targetNode, toFloat(probability));
			}			
		}
	}

	private Float toFloat(Probability probability) {
		switch (probability) {
		case LOW:
			return probabilities.getLow();
		case MEDIUM:
			return probabilities.getMedium();
		case HIGH:
			return probabilities.getHigh();
		}
		return 1f;
	}

	public String generate() {
		return analysis.generateAnalysisDataAsJson(nodeMap.values().stream().collect(Collectors.toList()));
	}

	private static WorkflowConfiguration loadWorkflowConfiguration(String content) throws IOException {
		Injector injector = new WorkflowStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		Resource resource = rs.createResource(URI.createURI("dummy:/dummy.wfconfig"));
		try (ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes())) {
			resource.load(bis, null);
		} catch (IOException e) {
			System.err.println(resource.getErrors());
			throw e;
		}
		return ((WorkflowConfiguration) resource.getContents().get(0));
	}

	private static Machine loadResource(String content) throws IOException {
		// init coffee package to allow standalone usage
		CoffeePackage.eINSTANCE.eClass();
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource resource = rs.createResource(org.eclipse.emf.common.util.URI.createURI("dummy:/dummy.coffee"));
		resource.load(new ByteArrayInputStream(content.getBytes()), null);
		return (Machine) resource.getContents().get(0);
	}
}
