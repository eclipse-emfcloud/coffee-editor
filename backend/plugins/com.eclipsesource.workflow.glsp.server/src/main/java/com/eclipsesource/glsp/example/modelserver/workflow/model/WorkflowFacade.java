/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationFactory;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;

public class WorkflowFacade {

	private Resource semanticResource;
	private Resource notationResource;
	private Workflow currentWorkflow;

	public WorkflowFacade(Resource coffeeResource, Resource notationResource) {
		this.semanticResource = coffeeResource;
		this.notationResource = notationResource;
	}

	public Resource getSemanticResource() {
		return semanticResource;
	}

	public Resource getNotationResource() {
		return notationResource;
	}

	public Optional<Machine> getMachine() {
		EObject content = this.semanticResource.getContents().get(0);
		return content instanceof Machine ? Optional.of((Machine) content) : Optional.empty();
	}

	public Diagram initializeNotation(Workflow workflow) {
		Optional<Diagram> existingDiagram = findDiagram(workflow);
		Diagram diagram = existingDiagram.isPresent() ? existingDiagram.get() : createDiagram(workflow);
		return initializeMissing(diagram);
	}

	private Diagram createDiagram(Workflow workflow) {
		Diagram diagram = WfnotationFactory.eINSTANCE.createDiagram();
		diagram.setSemanticElement(createProxy(workflow));
		notationResource.getContents().add(diagram);
		return diagram;
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
		return shape;
	}

	public Edge initializeEdge(Flow flow) {
		Edge edge = WfnotationFactory.eINSTANCE.createEdge();
		edge.setSemanticElement(createProxy(flow));
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

	public void setCurrentWorkflowIndex(int workflowIndex) {
		if (workflowIndex < 0 || getMachine().isEmpty() || workflowIndex >= getMachine().get().getWorkflows().size()) {
			return;
		}
		currentWorkflow = getMachine().get().getWorkflows().get(workflowIndex);
	}

	public Workflow getCurrentWorkflow() {
		return currentWorkflow;
	}

	private static <T> T safeCast(Object obj, Class<T> clazz) {
		return clazz.isInstance(obj) ? clazz.cast(obj) : null;
	}
}
