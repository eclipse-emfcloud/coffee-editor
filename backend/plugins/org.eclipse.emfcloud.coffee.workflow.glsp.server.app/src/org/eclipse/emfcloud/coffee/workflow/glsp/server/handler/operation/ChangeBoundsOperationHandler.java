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

import java.util.Optional;

import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.ShapeUtil;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelIndex;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Shape;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ChangeBoundsOperation;
import org.eclipse.glsp.server.types.ElementAndBounds;

public class ChangeBoundsOperationHandler extends BasicOperationHandler<ChangeBoundsOperation> {

	@Override
	public void executeOperation(ChangeBoundsOperation operation, GModelState modelState) {
		for (ElementAndBounds element : operation.getNewBounds()) {
			changeElementBounds(element, modelState);
		}
	}

	private void changeElementBounds(ElementAndBounds element, GModelState modelState) {
		WorkflowModelIndex modelIndex = WorkflowModelState.getModelState(modelState).getIndex();
		Optional<DiagramElement> diagramElement = modelIndex.getNotation(element.getElementId());
		if (diagramElement.isEmpty() || !(diagramElement.get() instanceof Shape)) {
			throw new IllegalArgumentException("Could not find shape for node " + element.getElementId());
		}

		Shape shape = (Shape) diagramElement.get();
		shape.setPosition(ShapeUtil.point(element.getNewPosition().getX(), element.getNewPosition().getY()));
		shape.setSize(ShapeUtil.dimension(element.getNewSize().getWidth(), element.getNewSize().getHeight()));
	}

}
