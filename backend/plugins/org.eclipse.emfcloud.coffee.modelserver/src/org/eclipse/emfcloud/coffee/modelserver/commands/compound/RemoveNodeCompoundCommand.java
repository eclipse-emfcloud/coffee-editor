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
package org.eclipse.emfcloud.coffee.modelserver.commands.compound;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.modelserver.commands.notation.RemoveNodeShapeCommand;
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.RemoveNodeCommand;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;

public class RemoveNodeCompoundCommand extends CompoundCommand {

   public RemoveNodeCompoundCommand(final EditingDomain domain, final URI modelUri, final String semanticUriFragment) {
      this.append(new RemoveNodeCommand(domain, modelUri, semanticUriFragment));
      this.append(new RemoveNodeShapeCommand(domain, modelUri, semanticUriFragment));

      Workflow workflow = SemanticCommandUtil.getModel(modelUri, domain);
      Node nodeToRemove = SemanticCommandUtil.getElement(workflow, semanticUriFragment, Node.class);

      Collection<Setting> nodeUsages = UsageCrossReferencer.find(nodeToRemove, workflow.eResource());
      for (Setting setting : nodeUsages) {
         EObject eObject = setting.getEObject();
         if (eObject instanceof Flow && eObject.eContainer() == workflow) {
            String flowSemanticUriFragment = SemanticCommandUtil.getSemanticUriFragment(eObject);
            this.append(new RemoveFlowCompoundCommand(domain, modelUri, flowSemanticUriFragment));
         }
      }
   }
}
