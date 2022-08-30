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
package org.eclipse.emfcloud.coffee.modelserver.commands.notation;

import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.NotationElementCommand;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;
import org.eclipse.glsp.server.emf.model.notation.Shape;

public class AddNodeShapeCommand extends NotationElementCommand {

   protected final GPoint shapePosition;
   protected String semanticProxyUri;
   protected Supplier<Node> nodeSupplier;

   private AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position) {
      super(domain, modelUri);
      this.shapePosition = position;
      this.nodeSupplier = null;
      this.semanticProxyUri = null;
   }

   public AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final String semanticProxyUri) {
      this(domain, modelUri, position);
      this.semanticProxyUri = semanticProxyUri;
   }

   public AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final Supplier<Node> nodeSupplier) {
      this(domain, modelUri, position);
      this.nodeSupplier = nodeSupplier;
   }

   @Override
   protected void doExecute() {
      Shape shape = NotationFactory.eINSTANCE.createShape();
      shape.setPosition(shapePosition);

      SemanticElementReference semanticReference = NotationFactory.eINSTANCE.createSemanticElementReference();
      if (this.semanticProxyUri != null) {
         semanticReference.setElementId(this.semanticProxyUri);
      } else {
         semanticReference.setElementId(EcoreUtil.getURI(nodeSupplier.get()).fragment());
      }
      shape.setSemanticElement(semanticReference);

      notationDiagram.getElements().add(shape);
   }

}
