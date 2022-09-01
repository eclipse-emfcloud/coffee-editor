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
package org.eclipse.emfcloud.coffee.modelserver.commands.compound;

import java.util.function.Supplier;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.AddFlowCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.AddEdgeCommand;

public class AddFlowCompoundCommand extends CompoundCommand {

   public AddFlowCompoundCommand(final EditingDomain domain, final URI modelUri, final String sourceElementId,
      final String targetElementId) {
      // Chain semantic and notation command
      AddFlowCommand command = new AddFlowCommand(domain, modelUri, sourceElementId, targetElementId);
      this.append(command);
      Supplier<Flow> semanticResultSupplier = () -> command.getFlow();
      this.append(new AddEdgeCommand(domain, modelUri, semanticResultSupplier));
   }
}
