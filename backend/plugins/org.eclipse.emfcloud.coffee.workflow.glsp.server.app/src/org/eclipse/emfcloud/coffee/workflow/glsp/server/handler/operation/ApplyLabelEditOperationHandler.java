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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.edit.command.AddCommandContribution;
import org.eclipse.emfcloud.modelserver.edit.command.CompoundCommandContribution;
import org.eclipse.emfcloud.modelserver.edit.command.RemoveCommandContribution;
import org.eclipse.emfcloud.modelserver.edit.command.SetCommandContribution;

public class ApplyLabelEditOperationHandler extends ModelServerAwareBasicOperationHandler<ApplyLabelEditOperation> {

	@Override
	public void executeOperation(ApplyLabelEditOperation operation, GModelState modelState,
			WorkflowModelServerAccess modelAccess) throws Exception {
		Optional<GModelElement> element = modelState.getIndex().get(operation.getLabelId());
		if (!element.isPresent() && !(element.get() instanceof GLabel)) {
			throw new IllegalArgumentException("Element with provided ID cannot be found or is not a GLabel");
		}

		// FIXME: switch to custom commands
//		GNode gNode = getParentGNode((GLabel) element.get());
//		Node node = modelAccess.getNodeById(gNode.getId());
//		if (!(node instanceof Task)) {
//			throw new IllegalAccessError("Edited label isn't label representing a task");
//		}
//
//		SetCommand setCommand = (SetCommand) SetCommand.create(modelAccess.getEditingDomain(), node, CoffeePackage.Literals.TASK__NAME,
//				operation.getText());
//		CCommand setCCommand = SetCommandContribution.clientCommand(setCommand);
//		if (!modelAccess.edit(setCCommand).thenApply(res -> res.body()).get()) {
//			throw new IllegalAccessError("Could not execute command: " + setCommand);
//		}
	}

	public GNode getParentGNode(GLabel sLabel) {
		EObject parent = sLabel;
		while (!(parent instanceof GNode) || parent == null) {
			parent = parent.eContainer();
		}
		if (!(parent instanceof GNode)) {
			throw new IllegalArgumentException("Cannot find node parent of label");
		}
		return (GNode) parent;
	}

}
