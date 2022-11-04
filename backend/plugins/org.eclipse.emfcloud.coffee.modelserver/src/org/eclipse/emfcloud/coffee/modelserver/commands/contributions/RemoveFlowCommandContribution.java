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
package org.eclipse.emfcloud.coffee.modelserver.commands.contributions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.modelserver.commands.compound.RemoveFlowCompoundCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class RemoveFlowCommandContribution extends CompoundCommandContribution {

   public static final String TYPE = "removeFlow";

   public static CCompoundCommand create(final String semanticElementId) {
      CCompoundCommand removeNodeCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      removeNodeCommand.setType(TYPE);
      removeNodeCommand.getProperties().put(SEMANTIC_ELEMENT_ID, semanticElementId);
      return removeNodeCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {
      String semanticElementId = command.getProperties().get(SEMANTIC_ELEMENT_ID);
      return new RemoveFlowCompoundCommand(domain, modelUri, semanticElementId);
   }

}
