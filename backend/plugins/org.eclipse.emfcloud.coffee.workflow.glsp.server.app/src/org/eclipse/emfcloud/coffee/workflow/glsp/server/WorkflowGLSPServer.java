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
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowGLSPServer.InitializeOptions;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;

import com.google.inject.Inject;

public class WorkflowGLSPServer extends DefaultGLSPServer<InitializeOptions> {
	static Logger Log = Logger.getLogger(WorkflowGLSPServer.class);
	@Inject
	private ModelServerClientProvider modelServerClientProvider;

	public WorkflowGLSPServer() {
		super(InitializeOptions.class);
	}

	@Override
	public CompletableFuture<Boolean> handleOptions(InitializeOptions options) {
		if (options != null) {
			Log.debug(String.format("[%s] Pinging modelserver at: '%s'", options.getTimestamp(),
					options.getModelserverURL()));
			try {
				ModelServerClient client = new ModelServerClient(options.getModelserverURL());
				boolean alive = client.ping().thenApply(Response<Boolean>::body).get();
				if (alive) {
					modelServerClientProvider.setModelServerClient(client);
					return CompletableFuture.completedFuture(true);
				}
			} catch (Exception e) {
				Log.error("Error during initialization of modelserver connection", e);
			}
		}
		return CompletableFuture.completedFuture(false);
	}

	public class InitializeOptions {
		private Date timestamp;
		private String modelserverURL;
		private String workspaceRoot;

		public Date getTimestamp() {
			return timestamp;
		}

		public String getModelserverURL() {
			return modelserverURL;
		}

		public String getWorkspaceRoot() {
			return workspaceRoot;
		}
	}
}
