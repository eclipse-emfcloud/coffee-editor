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
