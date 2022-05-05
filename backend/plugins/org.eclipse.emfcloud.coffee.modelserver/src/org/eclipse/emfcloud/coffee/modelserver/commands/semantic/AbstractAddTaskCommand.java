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
package org.eclipse.emfcloud.coffee.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Task;

import com.google.common.base.Preconditions;

public abstract class AbstractAddTaskCommand extends AbstractAddNodeCommand {

   public AbstractAddTaskCommand(final EditingDomain domain, final URI modelUri, final EClass eClass) {
      super(domain, modelUri, eClass);
   }

   @Override
   protected void initializeNode(final Node node) {
      super.initializeNode(node);
      Preconditions.checkArgument(node instanceof Task);
      Task.class.cast(node).setName("New Task");
   }
}
