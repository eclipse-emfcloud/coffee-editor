package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServerException;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.server.actionhandler.AbstractActionHandler;

public class ModelServerAwareSaveActionHandler extends AbstractActionHandler {


	@Override
	public boolean handles(Action action) {
		return action instanceof SaveModelAction;
	}

	@Override
	protected Optional<Action> execute(Action action, GraphicalModelState modelState) {
		try {
			if (action instanceof SaveModelAction) {
				SaveModelAction saveAction = (SaveModelAction) action;
				if (saveAction != null) {
					WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
					String modelURI = modelAccess.getWorkflowFacade().getSemanticResource().getURI().toString();
					if (!modelAccess.getModelServerClient().save(modelURI).thenApply(res -> res.body()).get()) {
						throw new GLSPServerException("Could not execute save action: " + action.toString());
					}

					modelAccess.save();
				}
			}
		} catch (ExecutionException | InterruptedException e) {
			throw new GLSPServerException("Could not execute save action: " + action.toString(), e);
		}
		return Optional.empty();
	}

}
