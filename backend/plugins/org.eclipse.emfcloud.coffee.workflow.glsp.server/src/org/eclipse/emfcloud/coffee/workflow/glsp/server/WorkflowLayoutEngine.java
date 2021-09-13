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
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.layout.GLSPLayoutConfigurator;
import org.eclipse.glsp.server.model.GModelState;

public class WorkflowLayoutEngine extends ElkLayoutEngine {
   @Override
   public void layout(final GModelState modelState) {
      if (modelState.getRoot() instanceof GGraph) {
         GLSPLayoutConfigurator configurator = new GLSPLayoutConfigurator();
         configurator.configureByType("graph");
         this.layout((GGraph) modelState.getRoot(), configurator);
      }
   }

}
