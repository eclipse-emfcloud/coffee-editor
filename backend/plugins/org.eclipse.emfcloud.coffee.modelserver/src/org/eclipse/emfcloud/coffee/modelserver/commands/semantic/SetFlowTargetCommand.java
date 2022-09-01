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
package org.eclipse.emfcloud.coffee.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;

public class SetFlowTargetCommand extends SemanticElementCommand {

   protected String semanticElementId;
   protected String newTargetElementId;

   public SetFlowTargetCommand(final EditingDomain domain, final URI modelUri, final String semanticElementId,
      final String newTargetElementId) {
      super(domain, modelUri);
      this.semanticElementId = semanticElementId;
      this.newTargetElementId = newTargetElementId;
   }

   @Override
   protected void doExecute() {
      Flow flow = SemanticCommandUtil.getElement(semanticModel, semanticElementId, Flow.class);
      Node newTarget = SemanticCommandUtil.getElement(semanticModel, newTargetElementId, Node.class);
      flow.setTarget(newTarget);
   }

}
