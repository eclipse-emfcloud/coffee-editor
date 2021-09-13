/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelSourceLoader;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSSubscriptionListener;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.model.GModelState;

public class WorkflowModelSourceLoader extends EMSModelSourceLoader {

	@Override
	public EMSModelServerAccess createModelServerAccess(String sourceURI, ModelServerClient modelServerClient) {
		return new WorkflowModelServerAccess(sourceURI, modelServerClient);
	}

	@Override
	public EMSModelState createModelState(GModelState modelState) {
		return (WorkflowModelState) modelState;
	}

	@Override
	public EMSSubscriptionListener createSubscriptionListener(EMSModelState modelState,
			ActionDispatcher actionDispatcher, ModelSubmissionHandler submissionHandler) {
		return new WorkflowModelServerSubscriptionListener(modelState, actionDispatcher, submissionHandler);
	}
}
