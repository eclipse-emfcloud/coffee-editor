/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.notation.AddNodeShapeCommand;
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.AddDecisionNodeCommand;
import org.eclipse.glsp.graph.GPoint;

public class AddDecisionNodeCompoundCommand extends CompoundCommand {

   public AddDecisionNodeCompoundCommand(final EditingDomain domain, final URI modelUri, final GPoint classPosition) {
      // Chain semantic and notation command
      AddDecisionNodeCommand command = new AddDecisionNodeCommand(domain, modelUri);
      this.append(command);
      Supplier<Node> semanticResultSupplier = () -> command.getNode();
      this.append(new AddNodeShapeCommand(domain, modelUri, classPosition, semanticResultSupplier));
   }
}
