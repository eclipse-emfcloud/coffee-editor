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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.SemanticProxy;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory;

public class WorkflowFacade {

	private Resource semanticResource;
	private Resource notationResource;

	public WorkflowFacade(Resource coffeeResource, Resource notationResource) {
		this.semanticResource = coffeeResource;
		this.notationResource = notationResource;
	}
	
	public Diagram initializeNotation(Workflow workflow) {
		Optional<Diagram> existingDiagram = findDiagram(workflow);
		Diagram diagram = existingDiagram.isPresent() ? existingDiagram.get() : createDiagram(workflow);
		return initializeMissing(diagram);
	}

	private Diagram createDiagram(Workflow workflow) {
		Diagram diagram = WfnotationFactory.eINSTANCE.createDiagram();
		diagram.setSemanticElement(createProxy(workflow));
		diagram.setGraphicId(generateId());
		notationResource.getContents().add(diagram);
		return diagram;
	}
	
	private String generateId() {
		return UUID.randomUUID().toString();
	}

	public Diagram initializeMissing(Diagram diagram) {
		Workflow workflow = (Workflow) diagram.getSemanticElement().getResolvedElement();
		workflow.getNodes().forEach(node -> {
			if (findDiagramElement(node).isEmpty()) {
				diagram.getElements().add(this.initializeShape(node));
			}
		});
		workflow.getFlows().forEach(flow -> {
			if (findDiagramElement(flow).isEmpty()) {
				diagram.getElements().add(this.initializeEdge(flow));
			}
		});
		return diagram;
	}

	public Shape initializeShape(Node node) {
		Shape shape = WfnotationFactory.eINSTANCE.createShape();
		shape.setSemanticElement(createProxy(node));
		shape.setGraphicId(generateId());
		return shape;
	}

	public Edge initializeEdge(Flow flow) {
		Edge edge = WfnotationFactory.eINSTANCE.createEdge();
		edge.setSemanticElement(createProxy(flow));
		edge.setGraphicId(generateId());
		return edge;
	}

	public SemanticProxy createProxy(EObject eObject) {
		SemanticProxy proxy = WfnotationFactory.eINSTANCE.createSemanticProxy();
		proxy.setResolvedElement(eObject);
		proxy.setUri(semanticResource.getURIFragment(eObject));
		return proxy;
	}

	public List<DiagramElement> findUnresolvableElements(Diagram diagram) {
		return diagram.getElements().stream()
				.filter(element -> resolved(element.getSemanticElement()).getResolvedElement() == null)
				.collect(Collectors.toList());
	}

	public SemanticProxy resolved(SemanticProxy proxy) {
		if (proxy.getResolvedElement() != null) {
			return proxy;
		}
		return reResolved(proxy);
	}

	public SemanticProxy reResolved(SemanticProxy proxy) {
		proxy.setResolvedElement(semanticResource.getEObject(proxy.getUri()));
		return proxy;
	}

	public <T extends DiagramElement> Optional<T> findDiagramElement(EObject semanticElement, Class<T> clazz) {
		return findDiagramElement(semanticElement).map(elem -> safeCast(elem, clazz));
	}

	public Optional<DiagramElement> findDiagramElement(EObject semanticElement) {
		if (semanticElement.eContainer() instanceof Workflow) {
			return findDiagramElement(semanticElement, (Workflow) semanticElement.eContainer());
		}
		Optional<Diagram> diagram = notationResource.getContents().stream()
				.filter(eObject -> containsDiagramElementForSemanticElement(eObject, semanticElement))
				.map(Diagram.class::cast).findFirst();
		return findDiagramElement(semanticElement, diagram);
	}

	public boolean containsDiagramElementForSemanticElement(EObject diagram, EObject semanticElement) {
		return diagram instanceof Diagram
				&& findDiagramElement(semanticElement, Optional.of(((Diagram) diagram))).isPresent();
	}

	public Optional<DiagramElement> findDiagramElement(EObject semanticElement, Workflow workflow) {
		Optional<Diagram> diagram = findDiagram(workflow);
		if (!diagram.isPresent()) {
			return Optional.empty();
		}
		return findDiagramElement(semanticElement, diagram);
	}

	public Optional<DiagramElement> findDiagramElement(EObject semanticElement, Optional<Diagram> diagram) {
		if (diagram.isEmpty()) {
			return Optional.empty();
		}
		return diagram.get().getElements().stream()
				.filter(element -> isDiagramElementForSemanticElement(element, semanticElement)).findFirst();
	}

	public boolean isDiagramElementForSemanticElement(DiagramElement diagramElement, EObject semanticElement) {
		return resolved(diagramElement.getSemanticElement()).getResolvedElement() == semanticElement;
	}

	public Optional<Diagram> findDiagram(Workflow workflow) {
		return notationResource.getContents().stream().filter(eObject -> isDiagramForWorkflow(eObject, workflow))
				.map(Diagram.class::cast).findFirst();
	}

	public boolean isDiagramForWorkflow(EObject diagram, Workflow workflow) {
		return diagram instanceof Diagram && isDiagramElementForSemanticElement((Diagram) diagram, workflow);
	}

	private static <T> T safeCast(Object obj, Class<T> clazz) {
		return clazz.isInstance(obj) ? clazz.cast(obj) : null;
	}
}
