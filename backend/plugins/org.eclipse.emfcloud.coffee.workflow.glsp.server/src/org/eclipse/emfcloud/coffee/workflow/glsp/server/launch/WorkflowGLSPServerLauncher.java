/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.launch;

import java.util.function.Predicate;

import org.apache.commons.cli.ParseException;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowDiagramModule;
import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPServerModule;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.DefaultCLIParser;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.glsp.server.launch.SocketGLSPServerLauncher;
import org.eclipse.glsp.server.utils.LaunchUtil;

@SuppressWarnings("UncommentedMain")
public final class WorkflowGLSPServerLauncher {

   private WorkflowGLSPServerLauncher() {}

   private static final int WORKFLOW_DEFAULT_PORT = 5008;

   public static void main(final String[] args) {
      String processName = "WorkflowGLSPServer";
      try {
         ElkLayoutEngine.initialize(new LayeredMetaDataProvider());
         DefaultCLIParser parser = new DefaultCLIParser(args, processName);
         LaunchUtil.configure(parser);

         Predicate<Integer> validator = (port) -> LaunchUtil.isValidPort(port);
         int serverPort = parser.parseIntOption(DefaultCLIParser.OPTION_PORT, WORKFLOW_DEFAULT_PORT, validator);

         ServerModule coffeeServerModule = new EMSGLSPServerModule()
            .configureDiagramModule(new WorkflowDiagramModule());

         GLSPServerLauncher launcher = new SocketGLSPServerLauncher(coffeeServerModule);
         launcher.start("localhost", serverPort);
      } catch (ParseException ex) {
         ex.printStackTrace();
         LaunchUtil.printHelp(processName, DefaultCLIParser.getDefaultOptions());
      }
   }

}
