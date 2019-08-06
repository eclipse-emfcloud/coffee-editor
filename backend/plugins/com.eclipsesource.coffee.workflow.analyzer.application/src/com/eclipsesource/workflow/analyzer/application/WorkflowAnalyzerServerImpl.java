package com.eclipsesource.workflow.analyzer.application;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

import com.eclipsesource.workflow.analyzer.json.AnalyzeWorkflow;

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
		// TODO fetch coffee model from coffee server
		try {
			String graph = new String(Files.readAllBytes(Paths.get(URI.create(uri))));
			String config = new String(Files.readAllBytes(Paths.get(URI.create(configUri))));
			return new AnalyzeWorkflow(graph, config).generate();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
