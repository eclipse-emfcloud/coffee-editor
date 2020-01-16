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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.AddCommand;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ShapeUtil;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowFacade;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationFactory;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeeFactory;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeePackage;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;

public abstract class AbstractCreateNodeHandler implements ModelStateAwareOperationHandler {

	protected String type;
	private EClass eClass;

	public AbstractCreateNodeHandler(String type, EClass eClass) {
		super();
		this.type = type;
		this.eClass = eClass;
	}

	@Override
	public Class<? extends Action> handlesActionType() {
		return CreateNodeOperationAction.class;
	}

	@Override
	public boolean handles(AbstractOperationAction action) {
		return ModelStateAwareOperationHandler.super.handles(action)
				? ((CreateNodeOperationAction) action).getElementTypeId().equals(type)
				: false;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Create node";
	}

	@Override
	public void doExecute(AbstractOperationAction action, GraphicalModelState modelState,
			WorkflowModelServerAccess modelAccess) throws Exception {
		CreateNodeOperationAction createNodeOperationAction = (CreateNodeOperationAction) action;
		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		Workflow workflow = workflowFacade.getCurrentWorkflow();

		Node node = initializeNode((Node) CoffeeFactory.eINSTANCE.create(eClass), modelState);

		Command addCommand = AddCommand.create(modelAccess.getEditingDomain(), workflow,
				CoffeePackage.Literals.WORKFLOW__NODES, node);

		createDiagramElement(workflowFacade, workflow, node, createNodeOperationAction);

		if (!modelAccess.edit(addCommand).thenApply(res -> res.body()).get()) {
			throw new IllegalAccessError("Could not execute command: " + addCommand);
		}

	}

	protected void createDiagramElement(WorkflowFacade facace, Workflow workflow, Node node,
			CreateNodeOperationAction createNodeOperationAction) {
		workflow.getNodes().add(node);

		facace.findDiagram(workflow).ifPresent(diagram -> {
			Shape shape = WfnotationFactory.eINSTANCE.createShape();
			shape.setSemanticElement(facace.createProxy(node));
			shape.setPosition(ShapeUtil.point(createNodeOperationAction.getLocation()));
			diagram.getElements().add(shape);
		});

		workflow.getNodes().remove(node);
	}

	protected Node initializeNode(Node node, GraphicalModelState modelState) {
		return node;
	}
}
