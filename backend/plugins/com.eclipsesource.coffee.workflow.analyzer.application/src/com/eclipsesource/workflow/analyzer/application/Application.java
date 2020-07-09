package com.eclipsesource.workflow.analyzer.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class Application implements IApplication {

	private WorkflowAnalyzerServerLauncher serverLauncher;

	@Override
	public Object start(IApplicationContext context) throws Exception {

		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		Integer port = null; // DEFAULT_PORT;
		String host = null; // DEFAULT_HOST;
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
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		if (serverLauncher != null) {
			serverLauncher.shutdown();
		}
	}
}
