/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.glsp.graph.GModelElement;

public abstract class AbstractGModelFactory<T extends EObject, E extends GModelElement> {

   protected WorkflowModelState modelState;

   public AbstractGModelFactory(final WorkflowModelState modelState) {
      this.modelState = modelState;
   }

   public abstract E create(T semanticElement);

   public <U extends E> Optional<U> create(final T semanticElement, final Class<U> clazz) {
      return Optional.ofNullable(create(semanticElement)).filter(clazz::isInstance).map(clazz::cast);
   }

   protected String toId(final EObject semanticElement) {
      String id = modelState.getIndex().getSemanticId(semanticElement).orElse(null);
      if (id == null) {
         id = EcoreUtil.getURI(semanticElement).fragment();
         modelState.getIndex().indexSemantic(id, semanticElement);
      }
      return id;

   }
}
