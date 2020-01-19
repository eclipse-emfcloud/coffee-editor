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
package com.eclipsesource.glsp.example.modelserver.workflow;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.example.modelserver.workflow.WorkflowModelServerGLSPServer.InitializeOptions;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.server.jsonrpc.DefaultGLSPServer;
import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.client.Response;
import com.google.inject.Inject;

public class WorkflowModelServerGLSPServer extends DefaultGLSPServer<InitializeOptions> {
	static Logger Log = Logger.getLogger(WorkflowModelServerGLSPServer.class);
	@Inject
	private ModelServerClientProvider modelServerClientProvider;

	public WorkflowModelServerGLSPServer() {
		super(InitializeOptions.class);
	}

	@Override
	public CompletableFuture<Boolean> handleOptions(InitializeOptions options) {
		if (options != null) {
			Log.debug(String.format("[%s] Pinging modelserver at: '%s'", options.getTimestamp(),
					options.getModelserverURL()));
			try {
				@SuppressWarnings("resource")
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

	@Override
	public void exit(String clientId) {
		modelStateProvider.getModelState(clientId).ifPresent(ms -> {
			if (ms instanceof ModelServerAwareModelState) {
				((ModelServerAwareModelState) ms).getModelAccess().unsubscribe();
			}
		});
		super.exit(clientId);
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
