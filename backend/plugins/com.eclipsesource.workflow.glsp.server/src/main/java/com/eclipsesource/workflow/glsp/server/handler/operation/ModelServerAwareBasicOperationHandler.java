/*******************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.utils.GenericsUtil;
import org.eclipse.glsp.server.operationhandler.BasicOperationHandler;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;

public abstract class ModelServerAwareBasicOperationHandler<T extends Operation> extends BasicOperationHandler<T>
		implements ModelserverAwareOperationHandler<T> {

	@SuppressWarnings("unchecked")
	@Override
	protected Class<T> deriveOperationType() {
		return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), ModelServerAwareBasicOperationHandler.class))
				.getActualTypeArguments()[0];
	}

	@Override
	public void executeOperation(final T operation, final GraphicalModelState modelState) {
		if (handles(operation)) {
			try {
				WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
				executeOperation(operationType.cast(operation), modelState, modelAccess);
			} catch (Exception ex) {
				if (ex instanceof RuntimeException) {
					// simply re-throw
					throw (RuntimeException) ex;
				} else {
					// wrap
					throw new RuntimeException(ex);
				}
			}
		}
	}
}
