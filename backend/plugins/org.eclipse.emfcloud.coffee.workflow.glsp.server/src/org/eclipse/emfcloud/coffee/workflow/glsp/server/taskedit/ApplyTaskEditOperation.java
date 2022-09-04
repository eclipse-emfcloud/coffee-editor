/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.taskedit;

import org.eclipse.glsp.server.operations.Operation;

public class ApplyTaskEditOperation extends Operation {

   private String taskId;
   private String expression;

   public ApplyTaskEditOperation() {
      super("applyTaskEdit");
   }

   public ApplyTaskEditOperation(final String taskId, final String expression) {
      this();
      this.taskId = taskId;
      this.expression = expression;
   }

   public String getTaskId() { return taskId; }

   public void setTaskId(final String taskId) { this.taskId = taskId; }

   public String getExpression() { return expression; }

   public void setExpression(final String expression) { this.expression = expression; }

}
