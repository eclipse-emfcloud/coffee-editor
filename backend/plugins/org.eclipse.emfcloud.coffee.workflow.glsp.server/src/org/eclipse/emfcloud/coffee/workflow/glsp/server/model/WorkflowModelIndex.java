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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationElement;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelIndex;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.impl.GModelIndexImpl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class WorkflowModelIndex extends EMSNotationModelIndex {
   private final BiMap<String, EObject> semanticIndex;
   private final BiMap<EObject, NotationElement> notationIndex;

   protected WorkflowModelIndex(final EObject target) {
      super(target);
      semanticIndex = HashBiMap.create();
      notationIndex = HashBiMap.create();
   }

   public static WorkflowModelIndex get(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      WorkflowModelIndex existingIndex = (WorkflowModelIndex) EcoreUtil.getExistingAdapter(root,
         WorkflowModelIndex.class);
      return Optional.ofNullable(existingIndex).orElseGet(() -> (create(element)));
   }

   public static WorkflowModelIndex create(final GModelElement element) {
      return new WorkflowModelIndex(EcoreUtil.getRootContainer(element));
   }

   @Override
   public boolean isAdapterForType(final Object type) {
      return WorkflowModelIndex.class.equals(type) || GModelIndexImpl.class.equals(type);
   }

   @Override
   public void clear() {
      this.semanticIndex.clear();
      this.notationIndex.clear();
   }

   @Override
   public void indexSemantic(final String id, final EObject semanticElement) {
      semanticIndex.putIfAbsent(id, semanticElement);
   }

   @Override
   public void indexNotation(final NotationElement notationElement) {
      if (notationElement.getSemanticElement() != null) {
         EObject semanticElement = notationElement.getSemanticElement().getResolvedElement();
         notationIndex.put(semanticElement, notationElement);
         semanticIndex.inverse().putIfAbsent(semanticElement, EcoreUtil.getURI(semanticElement).fragment());
      }

      if (notationElement instanceof Diagram) {
         ((Diagram) notationElement).getElements().forEach(this::indexNotation);
      }
   }

   public Optional<EObject> getSemantic(final String id) {
      return Optional.ofNullable(semanticIndex.get(id));
   }

   public Optional<String> getSemanticId(final EObject semanticElement) {
      return Optional.ofNullable(semanticIndex.inverse().get(semanticElement));
   }

   @Override
   public <T extends EObject> Optional<T> getSemantic(final String id, final Class<T> clazz) {
      return safeCast(Optional.ofNullable(semanticIndex.get(id)), clazz);
   }

   public Optional<EObject> getSemantic(final GModelElement gModelElement) {
      return getSemantic(gModelElement.getId());
   }

   public <T extends EObject> Optional<T> getSemantic(final GModelElement gModelElement, final Class<T> clazz) {
      return getSemantic(gModelElement.getId(), clazz);
   }

   public Optional<NotationElement> getNotation(final EObject semanticElement) {
      return Optional.ofNullable(notationIndex.get(semanticElement));
   }

   public <T extends NotationElement> Optional<T> getNotation(final EObject semanticElement, final Class<T> clazz) {
      return safeCast(getNotation(semanticElement), clazz);
   }

   public Optional<NotationElement> getNotation(final String id) {
      return getSemantic(id).flatMap(this::getNotation);
   }

   @Override
   public <T extends NotationElement> Optional<T> getNotation(final String id, final Class<T> clazz) {
      return safeCast(getNotation(id), clazz);
   }

   public Optional<NotationElement> getNotation(final GModelElement gModelElement) {
      return getNotation(gModelElement.getId());
   }

   public <T extends NotationElement> Optional<T> getNotation(final GModelElement element, final Class<T> clazz) {
      return safeCast(getNotation(element), clazz);
   }

   private <T> Optional<T> safeCast(final Optional<?> toCast, final Class<T> clazz) {
      return toCast.filter(clazz::isInstance).map(clazz::cast);
   }

   public String add(final EObject eObject) {
      if (eObject instanceof GModelElement) {
         return ((GModelElement) eObject).getId();
      }
      String id = null;
      if (eObject instanceof NotationElement) {
         EObject semanticElement = ((NotationElement) eObject).getSemanticElement().getResolvedElement();
         id = add(semanticElement);
         notationIndex.putIfAbsent(semanticElement, (NotationElement) eObject);
      } else {
         id = getSemanticId(eObject).orElse(null);
         if (id == null) {
            id = EcoreUtil.getURI(eObject).fragment();
            indexSemantic(id, eObject);
         }
      }
      return id;

   }

   public void remove(final EObject eObject) {
      if (eObject instanceof NotationElement) {
         EObject semanticElement = ((NotationElement) eObject).getSemanticElement().getResolvedElement();
         notationIndex.remove(semanticElement);
         remove(semanticElement);
         return;
      } else if (eObject instanceof GModelElement) {
         // do nothing;
         return;
      }
      semanticIndex.inverse().remove(eObject);
   }
}
