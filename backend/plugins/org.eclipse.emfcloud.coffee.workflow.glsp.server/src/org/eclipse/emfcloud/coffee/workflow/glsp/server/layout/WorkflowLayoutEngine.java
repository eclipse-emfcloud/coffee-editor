/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.layout;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.EdgeRouting;
import org.eclipse.emfcloud.modelserver.glsp.layout.EMSLayoutEngine;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.layout.GLSPLayoutConfigurator;

public class WorkflowLayoutEngine extends EMSLayoutEngine {

   @Override
   protected void configureLayoutOptions(final GLSPLayoutConfigurator configurator) {
      // ELK Layered Algorithm Reference:
      // https://www.eclipse.org/elk/reference/algorithms/org-eclipse-elk-layered.html
      configurator.configureByType(DefaultTypes.GRAPH)//
         .setProperty(LayeredOptions.DIRECTION, Direction.DOWN)
         .setProperty(LayeredOptions.SPACING_BASE_VALUE, 35d)
         .setProperty(LayeredOptions.EDGE_ROUTING, EdgeRouting.UNDEFINED);
   }

}
