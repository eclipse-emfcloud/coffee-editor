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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.server;

import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.coffee.workflow.dsl.index.IWorkflowIndex;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.xtext.ide.server.LanguageServerImpl;

import com.google.inject.Inject;

public class WorkflowLanguageServer extends LanguageServerImpl {
	@Inject
	IWorkflowIndex index;
	WorkflowIndexService indexService;
	
	public WorkflowIndexService getIndexService() {
		if(indexService == null) {
			indexService = new WorkflowIndexService(index);
		}
		return indexService;
	}
	
	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		getIndexService().initialize(params);
		return super.initialize(params);
	}
	
	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		getIndexService().didChangeWatchedFiles(params);
		super.didChangeWatchedFiles(params);
	}
	
	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		getIndexService().didChangeConfiguration(params);
		super.didChangeConfiguration(params);
	}
	
	@Override
	public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
		getIndexService().didChangeWorkspaceFolders(params);
		super.didChangeWorkspaceFolders(params);
	}

}
