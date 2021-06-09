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

import static org.eclipse.glsp.server.utils.ServerMessageUtil.error;

import java.util.Optional;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.modelserver.client.ModelServerNotification;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSSubscriptionListener;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ServerStatusAction;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.types.ServerStatus;
import org.eclipse.glsp.server.types.Severity;

public class WorkflowModelServerSubscriptionListener extends EMSSubscriptionListener {
	private static Logger LOG = Logger.getLogger(WorkflowModelServerSubscriptionListener.class);

	public WorkflowModelServerSubscriptionListener(final EMSModelState modelState,
			final ActionDispatcher actionDispatcher, final ModelSubmissionHandler submissionHandler) {
		super(modelState, actionDispatcher, submissionHandler);
	}

	// TODO is this necessary?
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
		actionDispatcher.dispatch(modelState.getClientId(),
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
		actionDispatcher.dispatch(modelState.getClientId(), error(errorMsg, t));
		LOG.error(errorMsg, t);
	}

	@Override
	public void onClosed(int code, String reason) {
		String errorMsg = "Subscribtion connection to modelserver has been closed!";
		actionDispatcher.dispatch(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, reason)));
		LOG.error(errorMsg + "\n" + reason);
	}

	@Override
	public void onFailure(Throwable t, Response<String> response) {
		String errorMsg = "Subscribtion connection to modelserver failed:" + "\n" + response;
		actionDispatcher.dispatch(modelState.getClientId(), error(errorMsg, t));
		LOG.error(errorMsg, t);
	}
}
