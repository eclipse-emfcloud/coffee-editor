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

import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.modelserver.glsp.notation.Edge;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationFactory;
import org.eclipse.emfcloud.modelserver.glsp.notation.SemanticProxy;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.NotationElementCommand;

public class AddFlowShapeCommand extends NotationElementCommand {

   protected Supplier<Flow> flowSupplier;
   protected String semanticProxyUri;

   private AddFlowShapeCommand(final EditingDomain domain, final URI modelUri) {
      super(domain, modelUri);
   }

   public AddFlowShapeCommand(final EditingDomain domain, final URI modelUri, final String semanticProxyUri) {
      super(domain, modelUri);
      this.semanticProxyUri = semanticProxyUri;
   }

   public AddFlowShapeCommand(final EditingDomain domain, final URI modelUri, final Supplier<Flow> flowSupplier) {
      super(domain, modelUri);
      this.flowSupplier = flowSupplier;
   }

   @Override
   protected void doExecute() {
      Edge edge = NotationFactory.eINSTANCE.createEdge();

      SemanticProxy proxy = NotationFactory.eINSTANCE.createSemanticProxy();
      if (this.semanticProxyUri != null) {
         proxy.setUri(this.semanticProxyUri);
      } else {
         proxy.setUri(EcoreUtil.getURI(flowSupplier.get()).fragment());
      }
      edge.setSemanticElement(proxy);

      notationDiagram.getElements().add(edge);
   }

}
