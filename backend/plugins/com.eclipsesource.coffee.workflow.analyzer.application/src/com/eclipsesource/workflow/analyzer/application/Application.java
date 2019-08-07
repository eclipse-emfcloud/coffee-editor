package com.eclipsesource.workflow.analyzer.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class Application implements IApplication {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8023;

	private WorkflowAnalyzerServerLauncher serverLauncher;

	@Override
	public Object start(IApplicationContext context) throws Exception {

		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		Integer port = DEFAULT_PORT;
		String host = DEFAULT_HOST;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "-port":
				i++;
				port = Integer.valueOf(args[i]);
				break;
			case "-host":
				i++;
				host = args[i];
				break;
			}
		}

		serverLauncher = new WorkflowAnalyzerServerLauncher();
		serverLauncher.start(host, port);

		System.out.println("[WorkflowAnalysisServer] Press any key to stop server...");
		System.in.read();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		if (serverLauncher != null) {
			serverLauncher.shutdown();
		}
	}
}
