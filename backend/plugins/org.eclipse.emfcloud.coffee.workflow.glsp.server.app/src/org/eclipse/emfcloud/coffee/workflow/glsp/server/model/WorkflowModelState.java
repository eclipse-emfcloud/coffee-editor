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

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptions;

public class WorkflowModelState extends EMSModelState {
	public static final String OPTION_WORKFLOW_INDEX = "workflowIndex";
	public static final int WORKFLOW_INDEX_DEFAULT = 0;
	private static Logger LOGGER = Logger.getLogger(WorkflowModelState.class);
	
	protected WorkflowModelServerAccess modelAccess;

	protected Diagram notationModel;
	// Our semantic model is not the whole machine but only the selected workflow
	protected Workflow semanticModel;
	
	private int workflowIndex;

	public static WorkflowModelServerAccess getModelAccess(GModelState state) {
		return getModelState(state).getModelServerAccess();
	}

	public static WorkflowModelState getModelState(final GModelState state) {
		if (!(state instanceof WorkflowModelState)) {
			throw new IllegalArgumentException("Argument must be a WorkflowModelState");
		}
		return ((WorkflowModelState) state);
	}

	@Override
	public void initialize(Map<String, String> clientOptions, EMSModelServerAccess modelServerAccess) {
		super.initialize(clientOptions, modelServerAccess);
		Optional<Integer> givenWorkflowIndex = ClientOptions.getIntValue(clientOptions, OPTION_WORKFLOW_INDEX);
		workflowIndex = givenWorkflowIndex.orElse(WORKFLOW_INDEX_DEFAULT);
		if (givenWorkflowIndex.isEmpty()) {
			LOGGER.warn("No workflow index given to create model, use workflow with index: " + workflowIndex);
		}
	}

	@Override
	public WorkflowModelServerAccess getModelServerAccess() {
		return modelAccess;
	}

	@Override
	protected void setModelServerAccess(final EMSModelServerAccess modelServerAccess) {
		this.modelAccess = (WorkflowModelServerAccess) modelServerAccess;
	}

	@Override
	public Diagram getNotationModel() {
		return notationModel;
	}

	@Override
	public Workflow getSemanticModel() {
		return semanticModel;
	}

	@Override
	public WorkflowModelIndex getIndex() {
		return WorkflowModelIndex.get(getRoot());
	}

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
