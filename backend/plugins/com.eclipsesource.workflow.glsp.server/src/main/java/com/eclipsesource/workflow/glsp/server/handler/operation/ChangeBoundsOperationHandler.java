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
package com.eclipsesource.workflow.glsp.server.handler.operation;

import java.util.Optional;

import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.ChangeBoundsOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.types.ElementAndBounds;

import com.eclipsesource.workflow.glsp.server.model.ShapeUtil;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;
import com.eclipsesource.workflow.glsp.server.wfnotation.DiagramElement;
import com.eclipsesource.workflow.glsp.server.wfnotation.Shape;

public class ChangeBoundsOperationHandler implements OperationHandler {

	@Override
	public Class<? extends Action> handlesActionType() {
		return ChangeBoundsOperationAction.class;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Move or resize element";
	}

	@Override
	public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		ChangeBoundsOperationAction changeBoundsAction = (ChangeBoundsOperationAction) action;
		for (ElementAndBounds element : changeBoundsAction.getNewBounds()) {
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
