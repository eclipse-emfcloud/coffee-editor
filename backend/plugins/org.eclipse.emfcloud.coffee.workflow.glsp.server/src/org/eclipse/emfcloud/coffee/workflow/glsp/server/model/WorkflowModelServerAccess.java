/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.CoffeeResource;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddWeightedFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowSourceCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetFlowTargetCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.v1.ModelServerClientV1;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.emf.common.EMFFacetConstraints;
import org.eclipse.emfcloud.modelserver.glsp.notation.epackage.NotationUtil;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.validation.framework.ValidationFilter;
import org.eclipse.emfcloud.validation.framework.ValidationFramework;
import org.eclipse.emfcloud.validation.framework.ValidationResult;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.common.base.Preconditions;

public class WorkflowModelServerAccess extends EMSNotationModelServerAccess {

   private static Logger LOGGER = Logger.getLogger(WorkflowModelServerAccess.class);

   private final ActionDispatcher actionDispatcher;

   private ValidationFramework validationFramework;

   public WorkflowModelServerAccess(final String sourceURI, final ModelServerClientV1 modelServerClient,
      final ActionDispatcher actionDispatcher) {
      super(sourceURI, modelServerClient, CoffeeResource.FILE_EXTENSION, NotationUtil.NOTATION_EXTENSION);
      Preconditions.checkNotNull(modelServerClient);
      this.actionDispatcher = actionDispatcher;
   }

   public void createValidationFramework(final GModelState modelState) {
      WorkflowValidationResultChangeListener changeListener = new WorkflowValidationResultChangeListener(
         modelState.getClientId(), actionDispatcher);
      this.validationFramework = new ValidationFramework(this.getSemanticURI(), modelServerClient, changeListener);
   }

   @Override
   public EObject getSemanticModel() {
      try {
         // fetch model in dedicated coffee format
         return modelServerClient.get(getSemanticURI(), CoffeeResource.FILE_EXTENSION).thenApply(res -> res.body())
            .get();
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error(e);
         throw new GLSPServerException("Error during model loading", e);
      }
   }

   protected String getOwnerRefUri(final EObject element) {
      return "file:" + baseSourceUri.appendFileExtension(this.semanticFileExtension)
         .appendFragment(getSemanticUriFragment(element)).toString();
   }

   protected String getSemanticUriFragment(final EObject element) {
      return EcoreUtil.getURI(element).fragment();
   }

   public CompletableFuture<Response<Boolean>> addManualTask(final WorkflowModelState modelState,
      final Optional<GPoint> position) {
      CCompoundCommand command = AddManualTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> addAutomatedTask(final WorkflowModelState modelState,
      final Optional<GPoint> position) {
      CCompoundCommand command = AddAutomatedTaskCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> addDecisionNode(final WorkflowModelState modelState,
      final Optional<GPoint> position) {
      CCompoundCommand command = AddDecisionNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> addMergeNode(final WorkflowModelState modelState,
      final Optional<GPoint> position) {
      CCompoundCommand command = AddMergeNodeCommandContribution.create(position.orElse(GraphUtil.point(0, 0)));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> addFlow(final WorkflowModelState modelState, final Node source,
      final Node target) {
      String sourceUriFragment = getSemanticUriFragment(source);
      String targetUriFragment = getSemanticUriFragment(target);
      CCompoundCommand command = AddFlowCommandContribution.create(sourceUriFragment, targetUriFragment);
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> addWeightedFlow(final WorkflowModelState modelState, final Node source,
      final Node target) {
      String sourceUriFragment = getSemanticUriFragment(source);
      String targetUriFragment = getSemanticUriFragment(target);
      CCompoundCommand command = AddWeightedFlowCommandContribution.create(sourceUriFragment, targetUriFragment);
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> removeFlow(final WorkflowModelState modelState, final Flow flow) {
      CCompoundCommand command = RemoveFlowCommandContribution.create(getSemanticUriFragment(flow));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> removeNode(final WorkflowModelState modelState, final Node node) {
      CCompoundCommand command = RemoveNodeCommandContribution.create(getSemanticUriFragment(node));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> setTaskName(final WorkflowModelState modelState,
      final Node nodeToRename, final String newName) {
      CCommand setCommand = SemanticCommandUtil.createSetTaskNameCommand(nodeToRename, getOwnerRefUri(nodeToRename),
         newName);
      return this.edit(setCommand);
   }

   public CompletableFuture<Response<Boolean>> reconnectFlowSource(final Flow flow, final Node newSource) {
      CCommand command = SetFlowSourceCommandContribution.create(getSemanticUriFragment(flow),
         getSemanticUriFragment(newSource));
      return this.edit(command);
   }

   public CompletableFuture<Response<Boolean>> reconnectFlowTarget(final Flow flow, final Node newTarget) {
      CCommand command = SetFlowTargetCommandContribution.create(getSemanticUriFragment(flow),
         getSemanticUriFragment(newTarget));
      return this.edit(command);
   }

   public CompletableFuture<Void> validate() throws IOException, InterruptedException, ExecutionException {
      return this.validationFramework.validate();
   }

   public List<ValidationResult> getValidationResult() throws IOException, InterruptedException, ExecutionException {
      return this.validationFramework.getRecentValidationResult();
   }

   public void initConstraintList() {
      this.validationFramework.getConstraintList();
   }

   public EMFFacetConstraints getConstraintList(final String elementID, final String featureID) {
      Map<String, EMFFacetConstraints> featureMap = this.validationFramework.getInputValidationMap().get(elementID);
      if (featureMap != null) {
         return featureMap.get(featureID);
      }
      return null;
   }

   public void subscribeToValidation() {
      this.validationFramework.subscribeToValidation();
   }

   public void addValidationFilter(final List<ValidationFilter> contraintValues)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.addValidationFilter(contraintValues);
   }

   public void removeValidationFilter(final List<ValidationFilter> contraintValues)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.removeValidationFilter(contraintValues);
   }

   public void toggleValidationFilter(final ValidationFilter contraintValue)
      throws IOException, InterruptedException, ExecutionException {
      this.validationFramework.toggleValidationFilter(contraintValue);
   }
}
