/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.	
 * 	
 * This program and the accompanying materials are made available under the	
 * terms of the Eclipse Public License v. 2.0 which is available at	
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is	
 * available at https://opensource.org/licenses/MIT.	
 * 	
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.workflow.glsp.server.app;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowGLSPModule;
import org.eclipse.emfcloud.modelserver.command.CCommandPackage;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.server.launch.DefaultGLSPServerLauncher;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		configureLogger();
		ElkLayoutEngine.initialize(new LayeredMetaDataProvider());
		GLSPServerLauncher launcher = new DefaultGLSPServerLauncher(new WorkflowGLSPModule());
		CCommandPackage.eINSTANCE.eClass();
		launcher.start("localhost", 5008);
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
	public static void configureLogger() {
		Logger root = Logger.getRootLogger();
		if (!root.getAllAppenders().hasMoreElements()) {
			root.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
		}
		root.setLevel(Level.DEBUG);
	}
}
