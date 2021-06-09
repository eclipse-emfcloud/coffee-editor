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

import java.util.Optional;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import com.google.common.base.Preconditions;

public class WorkflowModelServerAccess extends EMSNotationModelServerAccess {
	public WorkflowModelServerAccess(String sourceURI, ModelServerClient modelServerClient) {
		super(sourceURI, modelServerClient, "coffee", "coffeenotation");
		Preconditions.checkNotNull(modelServerClient);
	}

	// TODO move functionality to more suitable position?
	public Optional<Flow> getFlow(Node source, Node target) {
		return Workflow.class.cast(getSemanticModel()).getFlows().stream()
				.filter(flow -> source.equals(flow.getSource()) && target.equals(flow.getTarget())).findFirst();
	}
}
