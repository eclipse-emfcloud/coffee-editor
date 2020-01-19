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

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;

public interface  ModelStateAwareOperationHandler extends OperationHandler {
	@Override
	default public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		try {
			doExecute(action, modelState, modelAccess);
		} catch(Exception ex) {
			if(ex instanceof RuntimeException) {
				// simply re-throw
				throw (RuntimeException)ex;				
			} else {
				// wrap
				throw new RuntimeException(ex);
			}
		}
	}
	
	public void doExecute(AbstractOperationAction action, GraphicalModelState modelState, WorkflowModelServerAccess modelAccess) throws Exception;
}
