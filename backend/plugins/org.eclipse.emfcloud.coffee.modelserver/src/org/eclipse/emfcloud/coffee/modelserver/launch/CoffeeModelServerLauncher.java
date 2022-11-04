/*******************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver.launch;

import java.io.IOException;

import org.eclipse.emfcloud.coffee.modelserver.CoffeeModelServerModule;
import org.eclipse.emfcloud.modelserver.emf.launch.CLIBasedModelServerLauncher;
import org.eclipse.emfcloud.modelserver.emf.launch.CLIParser;
import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

@SuppressWarnings("UncommentedMain")
public final class CoffeeModelServerLauncher {
   private CoffeeModelServerLauncher() {}

   private static String EXECUTABLE_NAME = "org.eclipse.emfcloud.coffee.modelserver-0.1.0-standalone.jar";

   /**
    * Launch Coffee Model Server.
    *
    * @param args arguments
    */
   public static void main(final String[] args) throws IOException {
      final ModelServerLauncher launcher = new CLIBasedModelServerLauncher(createCLIParser(args),
         new CoffeeModelServerModule());
      launcher.run();
   }

   protected static CLIParser createCLIParser(final String[] args) {
      CLIParser parser = new CLIParser(args, CLIParser.getDefaultCLIOptions(), EXECUTABLE_NAME, 8081);
      return parser;
   }

}
