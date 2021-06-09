/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.WeightedFlow;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.ActivityNodeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.TaskNodeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.WorkflowBuilder.WeightedEdgeBuilder;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.ActivityNode;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.TaskNode;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WeightedEdge;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GGraphBuilder;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.model.GModelState;

public class WorkflowModelFactory implements GModelFactory {
	private static final String ROOT_ID = "sprotty";

	@Override
	public void createGModel(GModelState gModelState) {
		WorkflowModelState modelState = WorkflowModelState.getModelState(gModelState);
		GModelRoot gModelRoot = createEmptyRoot();
		modelState.setRoot(gModelRoot);

		WorkflowModelIndex modelIndex = modelState.getIndex();
		modelIndex.clear();

		Workflow semanticModel = modelState.getSemanticModel();
		EcoreUtil.resolveAll(semanticModel);
		Diagram diagram = getOrCreateDiagram(modelState.getNotationModel(), semanticModel, modelIndex);
		initializeMissing(diagram, modelIndex);

		populate(gModelRoot, modelIndex, semanticModel, diagram);

	}

	/**
	 * Populates the GModelRoot by mapping the diagram's notation elements to GModel elements.
	 */
	public static void populate(GModelRoot gModelRoot, WorkflowModelIndex modelIndex, Workflow workflow,
			Diagram diagram) {
		gModelRoot.setId(diagram.getGraphicId());
		Map<Node, GNode> nodeMapping = new HashMap<>();
		for (Node node : workflow.getNodes()) {
			modelIndex.getNotation(node, Shape.class) //
					.flatMap(shape -> toGNode(node, shape)) //
					.ifPresent(gnode -> {
						nodeMapping.put(node, gnode);
						modelIndex.indexSemantic(gnode.getId(), node);
						gModelRoot.getChildren().add(gnode);
					});
		}

		for (Flow flow : workflow.getFlows()) {
			modelIndex.getNotation(flow, Edge.class)//
					.flatMap(edge -> toGEdge(flow, edge, nodeMapping)) //
					.ifPresent(gedge -> {
						modelIndex.indexSemantic(gedge.getId(), flow);
						gModelRoot.getChildren().add(gedge);
					});
		}
	}

	private Diagram getOrCreateDiagram(Diagram diagram, final Workflow workflow, final WorkflowModelIndex modelIndex) {
		if (diagram == null) {
			diagram = createDiagram(workflow);
		}
		findUnresolvedElements(diagram, workflow)
				.forEach(e -> e.setSemanticElement(resolved(e.getSemanticElement(), workflow)));
		modelIndex.indexNotation(diagram);
		return diagram;
	}

	private Diagram createDiagram(Workflow workflow) {
		Diagram diagram = WfnotationFactory.eINSTANCE.createDiagram();
		diagram.setSemanticElement(createProxy(workflow));
		diagram.setGraphicId(generateId());
		return diagram;
	}

	private static GModelRoot createEmptyRoot() {
		return new GGraphBuilder(DefaultTypes.GRAPH)//
				.id(ROOT_ID) //
				.build();
	}

	private static Optional<GEdge> toGEdge(Flow flow, Edge edge, Map<Node, GNode> nodeMapping) {
		GEdge gedge = flow instanceof WeightedFlow ? createWeightedEdge((WeightedFlow) flow, edge, nodeMapping)
				: createEdge(flow, edge, nodeMapping);
		return Optional.ofNullable(gedge);
	}

	private static Optional<GNode> toGNode(Node node, DiagramElement shape) {
		GNode gnode = node instanceof Task //
				? createTaskNode((Task) node, (Shape) shape)
				: createActivityNode(node, (Shape) shape);
		return Optional.ofNullable(gnode);
	}

	private static WeightedEdge createWeightedEdge(WeightedFlow flow, Edge edge, Map<Node, GNode> nodeMapping) {
		WeightedEdgeBuilder builder = new WeightedEdgeBuilder() //
				.probability(flow.getProbability().getName()) //
				.source(nodeMapping.get(flow.getSource())) //
				.target(nodeMapping.get(flow.getTarget()));
		edge.getBendPoints().forEach(bendPoint -> builder.addRoutingPoint(bendPoint.getX(), bendPoint.getY()));
		builder.id(edge.getGraphicId());
		return builder.build();
	}

	private static GEdge createEdge(Flow flow, Edge edge, Map<Node, GNode> nodeMapping) {
		GEdgeBuilder builder = new GEdgeBuilder();
		builder.source(nodeMapping.get(flow.getSource()));
		builder.target(nodeMapping.get(flow.getTarget()));
		edge.getBendPoints().forEach(bendPoint -> builder.addRoutingPoint(bendPoint.getX(), bendPoint.getY()));
		builder.id(edge.getGraphicId());
		return builder.build();
	}

	private static TaskNode createTaskNode(Task task, Shape shape) {
		String type = CoffeeTypeUtil.toType(task);
		String nodeType = CoffeeTypeUtil.toNodeType(task);
		TaskNodeBuilder builder = new TaskNodeBuilder(type, task.getName(), nodeType, task.getDuration());
		if (shape.getPosition() != null) {
			builder.position(shape.getPosition().getX(), shape.getPosition().getY());
		}
		if (shape.getSize() != null) {
			builder.size(shape.getSize().getWidth(), shape.getSize().getHeight());
		}
		builder.id(shape.getGraphicId());

		return builder.build();
	}

	private static ActivityNode createActivityNode(Node node, Shape shape) {
		String type = CoffeeTypeUtil.toType(node);
		String nodeType = CoffeeTypeUtil.toNodeType(node);
		ActivityNodeBuilder builder = new ActivityNodeBuilder(type, nodeType);
		if (shape.getPosition() != null) {
			builder.position(shape.getPosition().getX(), shape.getPosition().getY());
		}
		if (shape.getSize() != null) {
			builder.size(shape.getSize().getWidth(), shape.getSize().getHeight());
		}
		builder.id(shape.getGraphicId());
		return builder.build();
	}

	private List<DiagramElement> findUnresolvedElements(final Diagram diagram, final Workflow workflow) {
		List<DiagramElement> unresolved = new ArrayList<>();

		if (diagram.getSemanticElement() == null
				|| resolved(diagram.getSemanticElement(), workflow).getResolvedElement() == null) {
			unresolved.add(diagram);
		}

		unresolved.addAll(diagram.getElements().stream()
				.filter(element -> element.getSemanticElement() == null ? false
						: resolved(element.getSemanticElement(), workflow).getResolvedElement() == null)
				.collect(Collectors.toList()));

		return unresolved;
	}

	private SemanticProxy resolved(SemanticProxy proxy, Workflow workflow) {
		if (proxy.getResolvedElement() != null) {
			return proxy;
		}
		return reResolved(proxy, workflow);
	}

	private SemanticProxy reResolved(SemanticProxy proxy, Workflow workflow) {
		// The xmi:id is used as URI to identify elements, we use the underlying
		// resource to fetch elements by id
		Resource semanticResource = workflow.eResource();
		proxy.setResolvedElement(semanticResource.getEObject(proxy.getUri()));
		return proxy;
	}

	/** Create missing Notation elements for semantic elements. */
	private Diagram initializeMissing(Diagram diagram, WorkflowModelIndex modelIndex) {
		Workflow workflow = (Workflow) diagram.getSemanticElement().getResolvedElement();
		workflow.getNodes().forEach(node -> {
			if (modelIndex.getNotation(node).isEmpty()) {
				diagram.getElements().add(this.initializeShape(node, modelIndex));
			}
		});
		workflow.getFlows().forEach(flow -> {
			if (modelIndex.getNotation(flow).isEmpty()) {
				diagram.getElements().add(this.initializeEdge(flow, modelIndex));
			}
		});
		return diagram;
	}

	private Shape initializeShape(Node node, WorkflowModelIndex modelIndex) {
		Shape shape = WfnotationFactory.eINSTANCE.createShape();
		shape.setSemanticElement(createProxy(node));
		shape.setGraphicId(generateId());
		modelIndex.indexNotation(shape);
		return shape;
	}

	private Edge initializeEdge(Flow flow, WorkflowModelIndex modelIndex) {
		Edge edge = WfnotationFactory.eINSTANCE.createEdge();
		edge.setSemanticElement(createProxy(flow));
		edge.setGraphicId(generateId());
		modelIndex.indexNotation(edge);
		return edge;
	}

	private SemanticProxy createProxy(EObject eObject) {
		SemanticProxy proxy = WfnotationFactory.eINSTANCE.createSemanticProxy();
		proxy.setResolvedElement(eObject);
		proxy.setUri(EcoreUtil.getURI(eObject).fragment().toString());
		return proxy;
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}
}
