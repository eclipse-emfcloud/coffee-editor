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
package com.eclipsesource.workflow.glsp.server.handler.operation;

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.CreateOperation;
import org.eclipse.glsp.api.utils.GenericsUtil;
import org.eclipse.glsp.server.operationhandler.BasicCreateOperationHandler;

import com.eclipsesource.workflow.glsp.server.model.WorkflowModelServerAccess;
import com.eclipsesource.workflow.glsp.server.model.WorkflowModelState;

public abstract class ModelServerAwareBasicCreateOperationHandler<T extends CreateOperation>
		extends BasicCreateOperationHandler<T> implements ModelserverAwareOperationHandler<T> {

	public ModelServerAwareBasicCreateOperationHandler(final String elementTypeId) {
		super(elementTypeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<T> deriveOperationType() {
		return (Class<T>) (GenericsUtil.getParametrizedType(getClass(),
				ModelServerAwareBasicCreateOperationHandler.class)).getActualTypeArguments()[0];
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
