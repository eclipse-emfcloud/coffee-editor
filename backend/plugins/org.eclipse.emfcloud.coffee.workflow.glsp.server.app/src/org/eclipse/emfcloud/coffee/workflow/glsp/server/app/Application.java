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

import org.eclipse.emfcloud.coffee.workflow.glsp.server.WorkflowGLSPServerLauncher;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class controls all aspects of the application's execution.
 */
public class Application implements IApplication {

   @Override
   public Object start(final IApplicationContext context) throws Exception {
      String[] args = getArgs(context);
      WorkflowGLSPServerLauncher.main(args);
      return null;
   }

   private String[] getArgs(final IApplicationContext context) {
      Object object = context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
      if (object instanceof String[]) {
         return (String[]) object;
      }
      return new String[0];
   }

   @Override
   public void stop() {
      // Nothing
   }

}
