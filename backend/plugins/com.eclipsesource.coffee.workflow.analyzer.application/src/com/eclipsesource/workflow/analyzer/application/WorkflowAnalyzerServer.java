package com.eclipsesource.workflow.analyzer.application;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public interface WorkflowAnalyzerServer {

	@JsonRequest
	CompletableFuture<String> runAnalysis(String uri, String configUri);

}
