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
package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

@SuppressWarnings("UncommentedMain")
public final class CoffeeModelServerLauncher {
   private CoffeeModelServerLauncher() {}

   /**
    * Launch Coffee Model Server.
    *
    * @param args arguments
    */
   public static void main(final String[] args) {
      final ModelServerLauncher launcher = new ModelServerLauncher(new CoffeeModelServerModule());
      launcher.run();
   }

}
