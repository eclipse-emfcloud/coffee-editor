/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;

public class WorkflowModelState extends EMSNotationModelState {
   public static final String OPTION_WORKFLOW_INDEX = "workflowIndex";
   public static final int WORKFLOW_INDEX_DEFAULT = 0;
   private static Logger LOGGER = Logger.getLogger(WorkflowModelState.class);

   protected WorkflowModelServerAccess modelAccess;

   // Our semantic model is not the whole machine but only the selected workflow
   protected Workflow semanticModel;
   protected Diagram notationModel;

   private int workflowIndex;

   public static WorkflowModelServerAccess getModelAccess(final GModelState state) {
      return getModelState(state).getModelServerAccess();
   }

   public static WorkflowModelState getModelState(final GModelState state) {
      if (!(state instanceof WorkflowModelState)) {
         throw new IllegalArgumentException("Argument must be a WorkflowModelState");
      }
      return ((WorkflowModelState) state);
   }

   @Override
   public WorkflowModelServerAccess getModelServerAccess() { return modelAccess; }

   @Override
   protected void setModelServerAccess(final EMSModelServerAccess modelServerAccess) {
      this.modelAccess = (WorkflowModelServerAccess) modelServerAccess;
   }

   @Override
   public Diagram getNotationModel() { return notationModel; }

   @Override
   public Workflow getSemanticModel() { return semanticModel; }

   @Override
   public WorkflowModelIndex getIndex() { return WorkflowModelIndex.get(getRoot()); }

   @Override
   public void loadSourceModels() throws GLSPServerException {
      EObject semanticRoot = modelAccess.getSemanticModel();
      if (!(semanticRoot instanceof Machine)) {
         throw new GLSPServerException("Error during semantic model loading");
      }
      Machine machine = (Machine) semanticRoot;
      if (workflowIndex < 0 || workflowIndex >= machine.getWorkflows().size()) {
         LOGGER.error("No workflow with index " + workflowIndex + " in " + machine + ".");
         throw new GLSPServerException("Error during semantic model loading");
      }
      this.semanticModel = machine.getWorkflows().get(workflowIndex);

      // initialize semantic model
      EcoreUtil.resolveAll(semanticModel);

      EObject notationRoot = modelAccess.getNotationModel();
      if (notationRoot != null && !(notationRoot instanceof Diagram)) {
         throw new GLSPServerException("Error during notation diagram loading");
      }
      this.notationModel = (Diagram) notationRoot;
   }
}
