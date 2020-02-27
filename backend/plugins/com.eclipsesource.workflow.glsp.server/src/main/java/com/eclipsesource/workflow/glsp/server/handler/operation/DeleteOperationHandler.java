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
package com.eclipsesource.workflow.glsp.server.handler.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Flow;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.DeleteOperation;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;
import com.eclipsesource.workflow.glsp.server.wfnotation.DiagramElement;
import com.eclipsesource.workflow.glsp.server.wfnotation.Shape;

public class DeleteOperationHandler extends ModelServerAwareBasicOperationHandler<DeleteOperation> {

	private Set<EObject> toDeleteNodes;
	private Set<EObject> toDeleteEdges;
	private Set<DiagramElement> toDeleteLocal;

	@Override
	public void executeOperation(DeleteOperation operation, GraphicalModelState modelState,
			WorkflowModelServerAccess modelAccess) throws Exception {
		toDeleteNodes = new HashSet<>();
		toDeleteEdges = new HashSet<>();
		toDeleteLocal = new HashSet<>();
		operation.getElementIds().forEach(id -> collectElementsToDelete(id, modelState));
		toDeleteLocal.forEach(e -> EcoreUtil.delete(e, true));

		List<Command> deleteEdges = delete(toDeleteEdges, modelAccess);
		List<Command> deleteNodes = delete(toDeleteNodes, modelAccess);
		List<Command> unifiedToDelete = new ArrayList<>();
		Comparator<Command> sortByIndex = new Comparator<Command>() {

			@Override
			public int compare(Command o1, Command o2) {
				if (!(o1 instanceof RemoveCommand))
					return 1;
				if (!(o2 instanceof RemoveCommand))
					return -1;
				RemoveCommand rc1 = (RemoveCommand) o1;
				RemoveCommand rc2 = (RemoveCommand) o2;
				CommandParameter.Indices cp2 = (CommandParameter.Indices) rc2.getCollection().iterator().next();
				CommandParameter.Indices cp1 = (CommandParameter.Indices) rc1.getCollection().iterator().next();
				return cp2.getIndices()[0] - cp1.getIndices()[0];

			}
		};
		// need to sort as otherwise the index is not correct when commands are applied.
		Collections.sort(deleteEdges, sortByIndex);
		Collections.sort(deleteNodes, sortByIndex);
		unifiedToDelete.addAll(deleteEdges);
		unifiedToDelete.addAll(deleteNodes);

		CompoundCommand cc = new CompoundCommand(unifiedToDelete);
		if (!modelAccess.edit(cc).thenApply(res -> res.body()).get()) {
			throw new IllegalAccessError("Could not execute command: " + cc);
		}
	}

	@SuppressWarnings("unchecked")
	private List<Command> delete(Set<EObject> eObjects, final WorkflowModelServerAccess modelAccess) {
		List<Command> result = new ArrayList<>();
		for (EObject e : eObjects) {
			EObject container = e.eContainer();
			EStructuralFeature containingFeature = e.eContainingFeature();
			// use index as object id cannot be used due to glsp -> modelserver -> glsp
			// communication
			int index = ((EList<EObject>) container.eGet(containingFeature)).indexOf(e);
			Command removeEdgesCommand = RemoveCommand.create(modelAccess.getEditingDomain(), container,
					containingFeature, index);
			result.add(removeEdgesCommand);

		}
		return result;
	}

	protected void collectElementsToDelete(String id, GraphicalModelState modelState) {
		Optional<GModelElement> maybeModelElement = modelState.getIndex().get(id);
		if (maybeModelElement.isEmpty()) {
			return;
		}

		GModelElement element = findTopLevelElement(maybeModelElement.get());
		if (!isNodeOrEdge(element)) {
			return;
		}

		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
		if (element instanceof GNode) {
			Node node = modelAccess.getNodeById(element.getId());
			toDeleteNodes.add(node);

			Optional<DiagramElement> diagramElement = modelAccess.getWorkflowFacade().findDiagramElement(node);
			if (!diagramElement.isEmpty() && diagramElement.get() instanceof Shape) {
				toDeleteLocal.add(diagramElement.get());
			}

			modelState.getIndex().getIncomingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
			modelState.getIndex().getOutgoingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
		} else if (element instanceof GEdge) {
			GEdge gEdge = (GEdge) element;
			Node sourceNode = modelAccess.getNodeById(gEdge.getSourceId());
			Node targetNode = modelAccess.getNodeById(gEdge.getTargetId());
			Optional<Flow> maybeFlow = modelAccess.getFlow(sourceNode, targetNode);
			if (maybeFlow.isEmpty()) {
				return;
			}
			toDeleteEdges.add(maybeFlow.get());

			Optional<DiagramElement> edge = maybeFlow
					.flatMap(flow -> modelAccess.getWorkflowFacade().findDiagramElement(flow));
			if (edge.isEmpty()) {
				return;
			}
			toDeleteLocal.add(edge.get());
		}
	}

	protected GModelElement findTopLevelElement(GModelElement element) {
		if (isNodeOrEdge(element)) {
			return element;
		}

		GModelElement parent = element.getParent();
		if (parent == null) {
			return element;
		}

		return findTopLevelElement(parent);
	}

	public boolean isNodeOrEdge(GModelElement element) {
		return element instanceof GNode || element instanceof GEdge;
	}

}
