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

import java.util.Map;

import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelSourceLoader;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptions;

public class WorkflowModelSourceLoader extends EMSModelSourceLoader {

	@Override
	public EMSModelServerAccess createModelServerAccess(String sourceURI, ModelServerClient modelServerClient) {
		return new WorkflowModelServerAccess(sourceURI, modelServerClient);
	}

	@Override
	public EMSModelState createModelState(GModelState modelState) {
		return WorkflowModelState.getModelState(modelState);
	}

	@Override
	protected String getSourceURI(final Map<String, String> clientOptions) {
		// We want to use the absolute sourceUri instead of the relative one from the
		// super class
		String sourceURI = ClientOptions.getSourceUri(clientOptions)
				.orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
		return sourceURI;
	}

}
