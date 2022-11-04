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
package org.eclipse.emfcloud.coffee.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.CoffeePackage;

public class AddWeightedFlowCommand extends AbstractAddFlowCommand {

   public AddWeightedFlowCommand(final EditingDomain domain, final URI modelUri, final String sourceElementId,
      final String targetElementId) {
      super(domain, modelUri, CoffeePackage.Literals.WEIGHTED_FLOW, sourceElementId, targetElementId);
   }
}
