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
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.SetFlowSourceCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class SetFlowSourceCommandContribution extends SemanticCommandContribution {

   public static final String TYPE = "setFlowSource";
   public static final String NEW_SOURCE_URI = "newSourceUri";

   public static CCommand create(final String semanticUri, final String newTargetUriFragment) {
      CCommand setSourceCommand = CCommandFactory.eINSTANCE.createCommand();
      setSourceCommand.setType(TYPE);
      setSourceCommand.getProperties().put(SEMANTIC_URI_FRAGMENT, semanticUri);
      setSourceCommand.getProperties().put(NEW_SOURCE_URI, newTargetUriFragment);
      return setSourceCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String semanticUriFragment = command.getProperties().get(SEMANTIC_URI_FRAGMENT);
      String newSourceUriFragment = command.getProperties().get(NEW_SOURCE_URI);

      return new SetFlowSourceCommand(domain, modelUri, semanticUriFragment, newSourceUriFragment);
   }

}
