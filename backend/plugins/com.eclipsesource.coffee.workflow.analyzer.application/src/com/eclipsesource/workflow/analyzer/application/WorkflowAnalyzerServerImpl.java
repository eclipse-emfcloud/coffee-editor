package com.eclipsesource.workflow.analyzer.application;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

import com.eclipsesource.coffee.common.ModelServerClientUtil;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.workflow.analyzer.coffee.AnalyzeWorkflow;

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
