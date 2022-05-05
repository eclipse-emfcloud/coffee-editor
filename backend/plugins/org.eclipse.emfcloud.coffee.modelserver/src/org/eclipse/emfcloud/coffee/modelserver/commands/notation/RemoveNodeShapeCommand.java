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
package org.eclipse.emfcloud.coffee.modelserver.commands.notation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.NotationElementCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;

public class RemoveNodeShapeCommand extends NotationElementCommand {

   protected final Shape shapeToRemove;

   public RemoveNodeShapeCommand(final EditingDomain domain, final URI modelUri, final String semanticProxyUri) {
      super(domain, modelUri);
      shapeToRemove = NotationCommandUtil.getNotationElement(modelUri, domain, semanticProxyUri, Shape.class);
   }

   @Override
   protected void doExecute() {
      notationDiagram.getElements().remove(shapeToRemove);
   }

}
