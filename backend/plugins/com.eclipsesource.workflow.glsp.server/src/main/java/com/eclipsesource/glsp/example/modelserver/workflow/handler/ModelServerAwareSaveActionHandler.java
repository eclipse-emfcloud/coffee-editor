package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SaveModelAction;
import org.eclipse.glsp.api.jsonrpc.GLSPServerException;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.server.actionhandler.AbstractActionHandler;

import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;

public class ModelServerAwareSaveActionHandler extends AbstractActionHandler {


	@Override
	public boolean handles(Action action) {
		return action instanceof SaveModelAction;
	}

	@Override
	protected List<Action> execute(Action action, GraphicalModelState modelState) {
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
		return Collections.emptyList();
	}

}
