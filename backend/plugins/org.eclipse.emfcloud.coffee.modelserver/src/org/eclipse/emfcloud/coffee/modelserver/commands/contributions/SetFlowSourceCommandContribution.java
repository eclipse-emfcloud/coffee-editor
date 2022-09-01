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
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.SetFlowSourceCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class SetFlowSourceCommandContribution extends SemanticCommandContribution {

   public static final String TYPE = "setFlowSource";
   public static final String NEW_SOURCE_ELEMENT_ID = "newSourceElementId";

   public static CCommand create(final String semanticElementId, final String newSourceElementId) {
      CCommand setSourceCommand = CCommandFactory.eINSTANCE.createCommand();
      setSourceCommand.setType(TYPE);
      setSourceCommand.getProperties().put(SEMANTIC_ELEMENT_ID, semanticElementId);
      setSourceCommand.getProperties().put(NEW_SOURCE_ELEMENT_ID, newSourceElementId);
      return setSourceCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String semanticElementId = command.getProperties().get(SEMANTIC_ELEMENT_ID);
      String newSourceElementId = command.getProperties().get(NEW_SOURCE_ELEMENT_ID);

      return new SetFlowSourceCommand(domain, modelUri, semanticElementId, newSourceElementId);
   }

}
