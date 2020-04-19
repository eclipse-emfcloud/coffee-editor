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

import static org.eclipse.glsp.graph.util.GraphUtil.point;

import java.util.UUID;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.CoffeeFactory;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.CoffeePackage;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Node;
import org.eclipse.emfcloud.modelserver.coffee.model.coffee.Workflow;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.CreateNodeOperation;

import com.eclipsesource.workflow.glsp.server.model.ShapeUtil;
import com.eclipsesource.workflow.glsp.server.model.WorkflowFacade;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.wfnotation.Shape;
import com.eclipsesource.workflow.glsp.server.wfnotation.WfnotationFactory;

public abstract class AbstractCreateNodeHandler
		extends ModelServerAwareBasicCreateOperationHandler<CreateNodeOperation> {

	private EClass eClass;

	public AbstractCreateNodeHandler(String type, EClass eClass) {
		super(type);
		this.eClass = eClass;
	}

	@Override
	public void executeOperation(CreateNodeOperation operation, GraphicalModelState modelState,
			WorkflowModelServerAccess modelAccess) throws Exception {
		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		Workflow workflow = workflowFacade.getCurrentWorkflow();

		Node node = initializeNode((Node) CoffeeFactory.eINSTANCE.create(eClass), modelState);

		Command addCommand = AddCommand.create(modelAccess.getEditingDomain(), workflow,
				CoffeePackage.Literals.WORKFLOW__NODES, node);

		createDiagramElement(workflowFacade, workflow, node, operation);

		if (!modelAccess.edit(addCommand).thenApply(res -> res.body()).get()) {
			throw new IllegalAccessError("Could not execute command: " + addCommand);
		}

	}

	protected String generateId() {
		return UUID.randomUUID().toString();
	}

	protected void createDiagramElement(WorkflowFacade facace, Workflow workflow, Node node,
			CreateNodeOperation operation) {
		workflow.getNodes().add(node);

		facace.findDiagram(workflow).ifPresent(diagram -> {
			Shape shape = WfnotationFactory.eINSTANCE.createShape();
			shape.setGraphicId(generateId());
			shape.setSemanticElement(facace.createProxy(node));
			shape.setPosition(ShapeUtil.point(operation.getLocation().orElse(point(0, 0))));
			diagram.getElements().add(shape);
		});

		workflow.getNodes().remove(node);
	}

	protected Node initializeNode(Node node, GraphicalModelState modelState) {
		return node;
	}
}
