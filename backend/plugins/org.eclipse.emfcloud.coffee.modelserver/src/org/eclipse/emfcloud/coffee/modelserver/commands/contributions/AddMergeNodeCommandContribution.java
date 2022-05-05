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

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.modelserver.commands.compound.AddMergeNodeCompoundCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.NotationCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GPoint;

public class AddMergeNodeCommandContribution extends CompoundCommandContribution {

   public static final String TYPE = "addMergeNodeContribution";

   public static CCompoundCommand create(final GPoint position) {
      CCompoundCommand addCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addCommand.setType(TYPE);
      addCommand.getProperties().put(NotationCommandContribution.POSITION_X, String.valueOf(position.getX()));
      addCommand.getProperties().put(NotationCommandContribution.POSITION_Y, String.valueOf(position.getY()));
      return addCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      GPoint position = NotationCommandUtil.getGPoint(
         command.getProperties().get(NotationCommandContribution.POSITION_X),
         command.getProperties().get(NotationCommandContribution.POSITION_Y));

      return new AddMergeNodeCompoundCommand(domain, modelUri, position);
   }

}
