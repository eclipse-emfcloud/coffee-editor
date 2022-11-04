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
import org.eclipse.emfcloud.coffee.modelserver.commands.compound.AddWeightedFlowCompoundCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class AddWeightedFlowCommandContribution extends CompoundCommandContribution {

   public static final String TYPE = "addWeightedFlowContribution";

   public static CCompoundCommand create(final String sourceElementId, final String targetElementId) {
      CCompoundCommand command = CCommandFactory.eINSTANCE.createCompoundCommand();
      command.setType(TYPE);
      command.getProperties().put(SOURCE_ELEMENT_ID, sourceElementId);
      command.getProperties().put(TARGET_ELEMENT_ID, targetElementId);
      return command;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {
      String sourceElementId = command.getProperties().get(SOURCE_ELEMENT_ID);
      String targetElementId = command.getProperties().get(TARGET_ELEMENT_ID);
      return new AddWeightedFlowCompoundCommand(domain, modelUri, sourceElementId, targetElementId);
   }

}
