/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.net.MalformedURLException;

import org.eclipse.emfcloud.coffee.modelserver.CoffeeModelServerClient;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPServer;

public class WorkflowGLSPServer extends EMSGLSPServer {

	@Override
	protected ModelServerClient createModelServerClient(final String modelServerURL) throws MalformedURLException {
		return new CoffeeModelServerClient(modelServerURL);
	}

}
