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

import static org.eclipse.glsp.server.protocol.GLSPServerException.getOrThrow;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSBasicOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;

public class WorkflowDeleteOperationHandler
		extends EMSBasicOperationHandler<DeleteOperation, WorkflowModelState, WorkflowModelServerAccess> {

	@Override
	public void executeOperation(final DeleteOperation operation, final WorkflowModelState modelState,
			final WorkflowModelServerAccess modelServerAccess) {

		operation.getElementIds().forEach(elementId -> {

			EObject semanticElement = getOrThrow(modelState.getIndex().getSemantic(elementId), EObject.class,
					"Could not find element for id '" + elementId + "', no delete operation executed.");

			if (semanticElement instanceof Task) {
				// TODO
//				modelServerAccess.removeTask(modelState, (Task) semanticElement).thenAccept(response -> {
//					if (!response.body()) {
//						throw new GLSPServerException(
//								"Could not execute delete operation on Layer: " + semanticElement.toString());
//					}
//				});
			} else if (semanticElement instanceof Node) {
				// TODO
//				modelServerAccess.removeNode(modelState, (Node) semanticElement).thenAccept(response -> {
//					if (!response.body()) {
//						throw new GLSPServerException(
//								"Could not execute delete operation on Connectable: " + semanticElement.toString());
//					}
//				});
			} else if (semanticElement instanceof Flow) {
				// TODO
//				modelServerAccess.removeFlow(modelState, (Flow) semanticElement).thenAccept(response -> {
//					if (!response.body()) {
//						throw new GLSPServerException(
//								"Could not execute delete operation on Connection: " + semanticElement.toString());
//					}
//				});
			}
		});

	}

}
