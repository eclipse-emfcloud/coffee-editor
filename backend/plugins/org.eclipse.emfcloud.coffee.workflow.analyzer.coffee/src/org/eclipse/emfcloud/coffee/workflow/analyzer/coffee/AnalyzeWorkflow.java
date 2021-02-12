/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.analyzer.coffee;

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
import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.ManualTask;
import org.eclipse.emfcloud.coffee.Probability;
import org.eclipse.emfcloud.coffee.WeightedFlow;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.xtext.resource.XtextResourceSet;

import org.eclipse.emfcloud.coffee.workflow.dsl.WorkflowStandaloneSetup;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.ProbabilityConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowConfiguration;
import org.eclipse.emfcloud.coffee.workflow.dsl.workflow.WorkflowFactory;
import com.google.inject.Injector;

import workflowanalyzer.Decision;
import workflowanalyzer.ForkOrJoin;
import workflowanalyzer.Merge;
import workflowanalyzer.Node;
import workflowanalyzer.Performer;
import workflowanalyzer.Task;

public class AnalyzeWorkflow {
	private ProbabilityConfiguration probabilities;
	private Map<org.eclipse.emfcloud.coffee.Node, Node> nodeMap = new LinkedHashMap<>();
	private Map<org.eclipse.emfcloud.coffee.Node, Set<WeightedFlow>> weightedEdgeMap = new LinkedHashMap<>();
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

	private void addToNodeMap(org.eclipse.emfcloud.coffee.Node node) {
		if (node instanceof org.eclipse.emfcloud.coffee.Decision) {
			nodeMap.put(node, createDecision(node));
		} else if (node instanceof org.eclipse.emfcloud.coffee.Merge) {
			nodeMap.put(node, createMerge(node));
		} else if (node instanceof org.eclipse.emfcloud.coffee.Fork
				|| node instanceof org.eclipse.emfcloud.coffee.Join) {
			nodeMap.put(node, createForkOrJoin(node));
		} else if (node instanceof org.eclipse.emfcloud.coffee.Task) {
			nodeMap.put(node, createTask((org.eclipse.emfcloud.coffee.Task) node));
		}
	}

	private Task createTask(org.eclipse.emfcloud.coffee.Task node) {
		Task task = new Task(node.getName(), new Performer("unkown"), node.getDuration());
		if (node instanceof AutomaticTask) {
			analysis.addTask(task, "automatic");
		} else if (node instanceof ManualTask) {
			analysis.addTask(task, "manual");
		}
		return task;
	}

	private Node createDecision(org.eclipse.emfcloud.coffee.Node node) {
		return new Decision(getId(node), new HashMap<>());
	}

	private Node createMerge(org.eclipse.emfcloud.coffee.Node node) {
		return new Merge(getId(node));
	}

	private Node createForkOrJoin(org.eclipse.emfcloud.coffee.Node node) {
		return new ForkOrJoin(getId(node));
	}

	private String getId(org.eclipse.emfcloud.coffee.Node node) {
		return node.eResource().getURIFragment(node);
	}

	private void connectSourceAndTarget(Flow flow) {
		org.eclipse.emfcloud.coffee.Node source = flow.getSource();
		org.eclipse.emfcloud.coffee.Node target = flow.getTarget();
		Node sourceNode = nodeMap.get(source);
		Node targetNode = nodeMap.get(target);
		sourceNode.connectTo(targetNode);

		if (flow instanceof WeightedFlow) {
			weightedEdgeMap.computeIfAbsent(source, id -> new LinkedHashSet<>()).add((WeightedFlow) flow);
		}
	}

	private void setProbabilities(org.eclipse.emfcloud.coffee.Node node) {
		if(node instanceof org.eclipse.emfcloud.coffee.Decision) {
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
