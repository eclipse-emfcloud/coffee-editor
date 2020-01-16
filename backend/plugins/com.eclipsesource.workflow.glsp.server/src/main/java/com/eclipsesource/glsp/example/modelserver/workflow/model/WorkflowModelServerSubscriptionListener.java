/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import static com.eclipsesource.glsp.api.utils.ServerStatusUtil.getDetails;
import static com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory.OPTION_WORKFLOW_INDEX;
import static com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory.WORKFLOW_INDEX_DEFAULT;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.jetbrains.annotations.NotNull;

import com.eclipsesource.glsp.api.action.ActionProcessor;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.types.ServerStatus;
import com.eclipsesource.glsp.api.types.ServerStatus.Severity;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.modelserver.client.ModelServerNotification;
import com.eclipsesource.modelserver.client.Response;
import com.eclipsesource.modelserver.client.XmiToEObjectSubscriptionListener;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.command.CCommand;
import com.eclipsesource.modelserver.common.codecs.DecodingException;
import com.google.common.collect.Lists;

public class WorkflowModelServerSubscriptionListener extends XmiToEObjectSubscriptionListener {
	private static final String TEMP_COMMAND_RESOURCE_URI = "command$1.command";
	private static Logger LOG = Logger.getLogger(WorkflowModelServerSubscriptionListener.class);
	private ActionProcessor actionProcessor;
	private WorkflowModelServerAccess modelServerAccess;
	private GraphicalModelState modelState;

	public WorkflowModelServerSubscriptionListener(GraphicalModelState modelState, WorkflowModelServerAccess modelServerAccess,
			ActionProcessor actionProcessor) {
		this.actionProcessor = actionProcessor;
		this.modelServerAccess = modelServerAccess;
		this.modelState = modelState;
	}
	
	@Override
	public void onIncrementalUpdate(CCommand command) {
		LOG.debug("Incremental update from model server received: " + command);
		Resource commandResource = null;
		try {
			// execute command on semantic resource
			EditingDomain domain = modelServerAccess.getEditingDomain();
			commandResource = createCommandResource(domain, command);
			Command cmd = modelServerAccess.getCommandCodec().decode(domain, command);
			domain.getCommandStack().execute(cmd);
			
			// update notation resource
			WorkflowFacade facade = modelServerAccess.getWorkflowFacade();
			Resource notationResource = facade.getNotationResource();
			updateNotationResource(facade, notationResource);
		} catch (DecodingException ex) {
			LOG.error("Could not decode command: " + command, ex);
			throw new RuntimeException(ex);
		} finally {
			if(commandResource != null) {
				commandResource.getResourceSet().getResources().remove(commandResource);
			}
		}
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
		MappedGModelRoot mappedGModelRoot = WorkflowModelServerModelFactory
				.populate(modelServerAccess.getWorkflowFacade(), modelState);
		modelServerAccess.setNodeMapping(mappedGModelRoot.getMapping());
		actionProcessor.send(modelState.getClientId(), new RequestBoundsAction(modelState.getRoot()));
	}

	private Resource createCommandResource(EditingDomain domain, CCommand command) {
		Resource resource = domain.createResource(TEMP_COMMAND_RESOURCE_URI);
		resource.getContents().add(command);
		return resource;
	}
	
	@Override
	public void onDirtyChange(boolean isDirty) {
		LOG.debug("Dirty State Changed: " + isDirty);
	}
	
	@Override
	public void onUnknown(ModelServerNotification notification) {
		// Try to see if we have an update if the notification type is not set properly
		EObject data = notification.getData().flatMap(WorkflowModelServerSubscriptionListener::decode).orElse(null);
		if(data instanceof CCommand) {
			onIncrementalUpdate((CCommand)data);
		} else if(data instanceof Machine) {
			onFullUpdate((Machine)data);
		} else {
			LOG.warn("Unknown notification received: " + notification);			
		}
	}
	
	@Override
	public void onError(Optional<String> message) {
		String errorMsg = message.orElse("Error occurred on model server!");
		actionProcessor.send(modelState.getClientId(), new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg)));
		LOG.error(errorMsg);
	}
	
	@Override
	public void onSuccess(Optional<String> messasge) {
		messasge.ifPresent(LOG::debug);
	}

	@Override
	public void onFailure(Throwable t) {
		String errorMsg = "Subscribtion connection to modelserver failed!";
		actionProcessor.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, getDetails(t))));
		LOG.error(errorMsg, t);
	}
	
	@Override
	public void onClosing(int code, @NotNull String reason) {
	}

	@Override
	public void onClosed(int code, @NotNull String reason) {
		String errorMsg = "Subscribtion connection to modelserver has been closed!";
		actionProcessor.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, reason)));
		LOG.error(errorMsg + "\n" + reason);
	}

	@Override
	public void onFailure(Throwable t, Response<String> response) {
		String errorMsg = "Subscribtion connection to modelserver failed:" + "\n" + response;
		actionProcessor.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, getDetails(t))));
		LOG.error(errorMsg, t);
	}

	public static Stream<?> toStream(Iterable<?> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
