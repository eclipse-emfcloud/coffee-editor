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

   public static CCompoundCommand create(final String sourceUriFragment, final String targetUriFragment) {
      CCompoundCommand command = CCommandFactory.eINSTANCE.createCompoundCommand();
      command.setType(TYPE);
      command.getProperties().put(SOURCE_URI_FRAGMENT, sourceUriFragment);
      command.getProperties().put(TARGET_URI_FRAGMENT, targetUriFragment);
      return command;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {
      String sourceUriFragment = command.getProperties().get(SOURCE_URI_FRAGMENT);
      String targetUriFragment = command.getProperties().get(TARGET_URI_FRAGMENT);
      return new AddWeightedFlowCompoundCommand(domain, modelUri, sourceUriFragment, targetUriFragment);
   }

}
