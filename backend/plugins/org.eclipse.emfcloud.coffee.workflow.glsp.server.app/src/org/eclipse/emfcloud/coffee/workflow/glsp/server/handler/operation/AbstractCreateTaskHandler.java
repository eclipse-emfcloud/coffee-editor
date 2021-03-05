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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.glsp.server.model.GModelState;

import com.google.common.base.Preconditions;

public abstract class AbstractCreateTaskHandler extends AbstractCreateNodeHandler {

	public AbstractCreateTaskHandler(String type, EClass eClass) {
		super(type, eClass);
	}

	@Override
	protected Node initializeNode(Node node, GModelState modelState) {
		Preconditions.checkArgument(node instanceof Task);
		((Task) node).setName("NewTask");
		return node;
	}

}
