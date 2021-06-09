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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelIndex;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;

public class DeleteOperationHandler extends BasicOperationHandler<DeleteOperation> {

	private Set<EObject> toDelete;

	@Override
	public void executeOperation(DeleteOperation operation, GModelState modelState) {
		toDelete = new HashSet<>();
		operation.getElementIds().forEach(id -> collectElementsToDelete(id, modelState));
		toDelete.forEach(e -> EcoreUtil.delete(e, true));
	}

	protected void collectElementsToDelete(String id, GModelState modelState) {
		Optional<GModelElement> maybeModelElement = modelState.getIndex().get(id);
		if (maybeModelElement.isEmpty()) {
			return;
		}

		GModelElement element = findTopLevelElement(maybeModelElement.get());
		if (!isNodeOrEdge(element)) {
			return;
		}

		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
		WorkflowModelIndex modelIndex = WorkflowModelState.getModelState(modelState).getIndex();
		if (element instanceof GNode) {
			modelIndex.getSemantic(element).ifPresent(toDelete::add);

			Optional<DiagramElement> diagramElement = modelIndex.getNotation(element);
			if (!diagramElement.isEmpty() && diagramElement.get() instanceof Shape) {
				toDelete.add(diagramElement.get());
			}

			modelState.getIndex().getIncomingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
			modelState.getIndex().getOutgoingEdges(element)
					.forEach(edge -> collectElementsToDelete(edge.getId(), modelState));
		} else if (element instanceof GEdge) {
			GEdge gEdge = (GEdge) element;
			Node sourceNode = modelIndex.getSemantic(gEdge.getSourceId(), Node.class).get();
			Node targetNode = modelIndex.getSemantic(gEdge.getTargetId(), Node.class).get();
			Optional<Flow> maybeFlow = modelAccess.getFlow(sourceNode, targetNode);
			if (maybeFlow.isEmpty()) {
				return;
			}
			toDelete.add(maybeFlow.get());

			Optional<DiagramElement> edge = maybeFlow
					.flatMap(flow -> modelIndex.getNotation(flow));
			if (edge.isEmpty()) {
				return;
			}
			toDelete.add(edge.get());
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
