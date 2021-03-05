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
package org.eclipse.emfcloud.coffee.workflow.analyzer.application;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.common.ModelServerClientUtil;
import org.eclipse.emfcloud.coffee.workflow.analyzer.coffee.AnalyzeWorkflow;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

public class WorkflowAnalyzerServerImpl implements WorkflowAnalyzerServer {

	public void connect(WorkflowAnalysisClient client) {
		// nothing to do -- no need to communicate to client for now
	}

	public void dispose() {
		// nothing to do
	}

	@Override
	public CompletableFuture<String> runAnalysis(String uri, String configUri) {
		return CompletableFutures.computeAsync(cancelChecker -> doRunAnalysis(uri, configUri));
	}

	private String doRunAnalysis(String uri, String configUri) {
		try {
			Machine machine = ModelServerClientUtil.loadResource(URI.create(uri), Machine.class)
					.orElseThrow(IllegalArgumentException::new);
			String config = new String(Files.readAllBytes(Paths.get(URI.create(configUri))));
			return new AnalyzeWorkflow(machine, config).generate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
