/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server.model;

import static org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelFactory.OPTION_WORKFLOW_INDEX;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelFactory.WORKFLOW_INDEX_DEFAULT;
import static org.eclipse.glsp.server.utils.ServerMessageUtil.error;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.DiagramElement;
import org.eclipse.emfcloud.modelserver.client.ModelServerNotification;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.XmiToEObjectSubscriptionListener;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandExecutionResult;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ServerStatusAction;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.features.core.model.RequestBoundsAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.ServerStatus;
import org.eclipse.glsp.server.types.Severity;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.common.collect.Lists;

public class WorkflowModelServerSubscriptionListener extends XmiToEObjectSubscriptionListener {
	private static final String TEMP_COMMAND_RESOURCE_URI = "command$1.command";
	private static Logger LOG = Logger.getLogger(WorkflowModelServerSubscriptionListener.class);
	private ActionDispatcher actionProcessor;
	private WorkflowModelServerAccess modelServerAccess;
	private GModelState modelState;

	public WorkflowModelServerSubscriptionListener(GModelState modelState, WorkflowModelServerAccess modelServerAccess,
			ActionDispatcher actionProcessor) {
		this.actionProcessor = actionProcessor;
		this.modelServerAccess = modelServerAccess;
		this.modelState = modelState;
	}

	@Override
	public void onIncrementalUpdate(CCommandExecutionResult commandResult) {
		LOG.debug("Incremental update from model server received: " + commandResult);

		// update notation resource
		EObject root = modelServerAccess.getModel();
		onFullUpdate(root);
	}

	@Override
	public void onFullUpdate(EObject root) {
		LOG.debug("Full update from model server received");

		WorkflowFacade facade = modelServerAccess.getWorkflowFacade();
		Resource semanticResource = facade.getSemanticResource();
		semanticResource.getContents().clear();
		semanticResource.getContents().add(root);

		Resource notationResource = facade.getNotationResource();
		updateNotationResource(facade, notationResource);
	}

	private void updateNotationResource(WorkflowFacade facade, Resource notationResource) {
		// Clear outdated resolved proxies of notation resource
		Lists.newArrayList(notationResource.getAllContents()).stream().filter(DiagramElement.class::isInstance)
				.map(DiagramElement.class::cast).forEach(e -> e.getSemanticElement().setResolvedElement(null));

		// Set the corresponding workflow
		Optional<Integer> givenWorkflowIndex = ClientOptions.getIntValue(modelState.getClientOptions(),
				OPTION_WORKFLOW_INDEX);
		int workflowIndex = givenWorkflowIndex.orElse(WORKFLOW_INDEX_DEFAULT);
		facade.setCurrentWorkflowIndex(workflowIndex);

		// Re-populate GModel and initiate a client model update
		MappedGModelRoot mappedGModelRoot = WorkflowModelFactory.populate(modelServerAccess.getWorkflowFacade(),
				modelState);
		modelServerAccess.setNodeMapping(mappedGModelRoot.getMapping());
		modelState.setRoot(mappedGModelRoot.getRoot());
		actionProcessor.dispatch(modelState.getClientId(), new RequestBoundsAction(modelState.getRoot()));
	}

	private Resource createCommandResource(EditingDomain domain, CCommand command) {
		Resource resource = domain.createResource(TEMP_COMMAND_RESOURCE_URI);
		resource.getContents().add(command);
		return resource;
	}

	@Override
	public void onDirtyChange(boolean isDirty) {
		LOG.debug("Dirty State Changed: " + isDirty);
		actionProcessor.dispatch(modelState.getClientId(), new SetDirtyStateAction(isDirty));
	}

	@Override
	public void onUnknown(ModelServerNotification notification) {
		// Try to see if we have an update if the notification type is not set properly
		EObject data = notification.getData().flatMap(WorkflowModelServerSubscriptionListener::decode).orElse(null);
		if (data instanceof CCommand) {
			onIncrementalUpdate((CCommand) data);
		} else if (data instanceof Machine) {
			onFullUpdate(data);
		} else {
			LOG.warn("Unknown notification received: " + notification);
		}
	}

	@Override
	public void onError(Optional<String> message) {
		String errorMsg = message.orElse("Error occurred on model server!");
		actionProcessor.dispatch(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg)));
		LOG.error(errorMsg);
	}

	@Override
	public void onSuccess(Optional<String> messasge) {
		messasge.ifPresent(LOG::debug);
	}

	@Override
	public void onFailure(Throwable t) {
		String errorMsg = "Subscribtion connection to modelserver failed!";
		actionProcessor.dispatch(modelState.getClientId(), error(errorMsg, t));
		LOG.error(errorMsg, t);
	}

	@Override
	public void onClosing(int code, String reason) {
	}

	@Override
	public void onClosed(int code, String reason) {
		String errorMsg = "Subscribtion connection to modelserver has been closed!";
		actionProcessor.dispatch(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, reason)));
		LOG.error(errorMsg + "\n" + reason);
	}

	@Override
	public void onFailure(Throwable t, Response<String> response) {
		String errorMsg = "Subscribtion connection to modelserver failed:" + "\n" + response;
		actionProcessor.dispatch(modelState.getClientId(), error(errorMsg, t));
		LOG.error(errorMsg, t);
	}

	public static Stream<?> toStream(Iterable<?> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
