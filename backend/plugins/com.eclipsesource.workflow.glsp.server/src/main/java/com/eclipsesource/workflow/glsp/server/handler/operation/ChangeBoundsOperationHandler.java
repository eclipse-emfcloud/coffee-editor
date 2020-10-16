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

import java.util.Optional;

import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.ChangeBoundsOperation;
import org.eclipse.glsp.api.types.ElementAndBounds;
import org.eclipse.glsp.server.operationhandler.BasicOperationHandler;

import com.eclipsesource.workflow.glsp.server.model.ShapeUtil;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;
import com.eclipsesource.workflow.glsp.server.wfnotation.DiagramElement;
import com.eclipsesource.workflow.glsp.server.wfnotation.Shape;

public class ChangeBoundsOperationHandler extends BasicOperationHandler<ChangeBoundsOperation> {

	@Override
	public void executeOperation(ChangeBoundsOperation operation, GraphicalModelState modelState) {
		for (ElementAndBounds element : operation.getNewBounds()) {
			changeElementBounds(element, modelState);
		}
	}

	private void changeElementBounds(ElementAndBounds element, GraphicalModelState modelState) {
		WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
		Node node = modelAccess.getNodeById(element.getElementId());
		Optional<DiagramElement> diagramElement = modelAccess.getWorkflowFacade().findDiagramElement(node);
		if (diagramElement.isEmpty() || !(diagramElement.get() instanceof Shape)) {
			throw new IllegalArgumentException("Could not find shape for node " + element.getElementId());
		}

		Shape shape = (Shape) diagramElement.get();
		shape.setPosition(ShapeUtil.point(element.getNewPosition().getX(), element.getNewPosition().getY()));
		shape.setSize(ShapeUtil.dimension(element.getNewSize().getWidth(), element.getNewSize().getHeight()));
	}

}
