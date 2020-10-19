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
package com.eclipsesource.workflow.glsp.server.model;

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.server.model.ModelStateImpl;

public class WorkflowModelState extends ModelStateImpl {

	private WorkflowModelServerAccess modelAccess;

	public static WorkflowModelServerAccess getModelAccess(GraphicalModelState state) {
		if (!(state instanceof WorkflowModelState)) {
			throw new IllegalArgumentException("Argument must be a ModelServerAwareModelState");
		}
		return ((WorkflowModelState) state).getModelAccess();
	}

	public void setModelAccess(WorkflowModelServerAccess modelAccess) {
		this.modelAccess = modelAccess;
	}

	public WorkflowModelServerAccess getModelAccess() {
		return modelAccess;
	}
}
