package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.emfcloud.modelserver.command.CCommandPackage;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.server.launch.DefaultGLSPServerLauncher;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;

public class WorkflowGLSPServerLauncher {

	private static final Logger LOGGER = Logger.getLogger(WorkflowGLSPServerLauncher.class.getSimpleName());

	private static final int WORKFLOW_DEFAULT_PORT = 5008;

	public static void main(final String[] args) {
		int port = getPort(args);
		configureLogger();
		ElkLayoutEngine.initialize(new LayeredMetaDataProvider());
		GLSPServerLauncher launcher = new DefaultGLSPServerLauncher(new WorkflowGLSPModule());
		CCommandPackage.eINSTANCE.eClass();
		launcher.start("localhost", port);
	}

	private static int getPort(final String[] args) {
		for (int i = 0; i < args.length; i++) {
			if ("--port".contentEquals(args[i])) {
				return Integer.parseInt(args[i + 1]);
			}
		}
		LOGGER.info("The server port was not specified; using default port 5008");
		return WORKFLOW_DEFAULT_PORT;
	}

	public static void configureLogger() {
		Logger root = Logger.getRootLogger();
		if (!root.getAllAppenders().hasMoreElements()) {
			root.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
		}
		root.setLevel(Level.INFO);
	}

}
