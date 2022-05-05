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
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.SetFlowTargetCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class SetFlowTargetCommandContribution extends SemanticCommandContribution {

   public static final String TYPE = "setFlowTarget";
   public static final String NEW_TARGET_URI = "newTargetUri";

   public static CCommand create(final String semanticUri, final String newTargetUriFragment) {
      CCommand setTargetCommand = CCommandFactory.eINSTANCE.createCommand();
      setTargetCommand.setType(TYPE);
      setTargetCommand.getProperties().put(SEMANTIC_URI_FRAGMENT, semanticUri);
      setTargetCommand.getProperties().put(NEW_TARGET_URI, newTargetUriFragment);
      return setTargetCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String semanticUriFragment = command.getProperties().get(SEMANTIC_URI_FRAGMENT);
      String newTargetUriFragment = command.getProperties().get(NEW_TARGET_URI);

      return new SetFlowTargetCommand(domain, modelUri, semanticUriFragment, newTargetUriFragment);
   }

}
