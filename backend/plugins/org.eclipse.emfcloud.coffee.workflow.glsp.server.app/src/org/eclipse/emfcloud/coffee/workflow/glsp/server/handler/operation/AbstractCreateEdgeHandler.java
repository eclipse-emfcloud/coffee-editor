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

import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emfcloud.coffee.CoffeeFactory;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowFacade;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationFactory;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.edit.command.AddCommandContribution;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;

public abstract class AbstractCreateEdgeHandler
		extends ModelServerAwareBasicCreateOperationHandler<CreateEdgeOperation> {

	private EClass eClass;

	public AbstractCreateEdgeHandler(String type, EClass eClass) {
		super(type);
		this.eClass = eClass;
	}

	@Override
	public void executeOperation(CreateEdgeOperation operation, GModelState modelState,
			WorkflowModelServerAccess modelAccess) throws Exception {
		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		Workflow workflow = workflowFacade.getCurrentWorkflow();

		Flow flow = (Flow) CoffeeFactory.eINSTANCE.create(eClass);
		flow.setSource(modelAccess.getNodeById(operation.getSourceElementId()));
		flow.setTarget(modelAccess.getNodeById(operation.getTargetElementId()));

		AddCommand addCommand = (AddCommand) AddCommand.create(modelAccess.getEditingDomain(), workflow,
				CoffeePackage.Literals.WORKFLOW__FLOWS, flow);
		CCommand addCCommand = AddCommandContribution.clientCommand(addCommand);

		createDiagramElement(workflowFacade, workflow, flow, operation);

		if (!modelAccess.edit(addCCommand).thenApply(res -> res.body()).get()) {
			throw new IllegalAccessError("Could not execute command: " + addCommand);
		}
	}

	protected void createDiagramElement(WorkflowFacade facace, Workflow workflow, Flow flow,
			CreateEdgeOperation operation) {
		workflow.getFlows().add(flow);

		facace.findDiagram(workflow).ifPresent(diagram -> {
			Edge edge = WfnotationFactory.eINSTANCE.createEdge();
			edge.setGraphicId(generateId());
			edge.setSemanticElement(facace.createProxy(flow));
			diagram.getElements().add(edge);
		});

		workflow.getFlows().remove(flow);
	}

	protected String generateId() {
		return UUID.randomUUID().toString();
	}

}
