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
package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.util.Optional;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.types.ElementAndBounds;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ShapeUtil;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.graph.GBounds;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

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
			changeElementBounds(element.getElementId(), element.getNewBounds(), modelState);
		}
	}

	private void changeElementBounds(String elementId, GBounds newBounds, GraphicalModelState modelState) {
		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		Node node = modelAccess.getNodeById(elementId);
		Optional<DiagramElement> element = modelAccess.getWorkflowFacade().findDiagramElement(node);
		if (element.isEmpty() || !(element.get() instanceof Shape)) {
			throw new IllegalArgumentException("Could not find shape for node " + elementId);
		}

		Shape shape = (Shape) element.get();
		shape.setPosition(ShapeUtil.point(newBounds.getX(), newBounds.getY()));
		shape.setSize(ShapeUtil.dimension(newBounds.getWidth(), newBounds.getHeight()));
	}

}
