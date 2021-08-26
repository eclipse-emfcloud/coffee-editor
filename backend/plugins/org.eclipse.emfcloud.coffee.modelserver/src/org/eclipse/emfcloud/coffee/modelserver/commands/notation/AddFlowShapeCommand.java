/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver.commands.notation;

import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.NotationCommandUtil;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.Edge;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.SemanticProxy;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.WfnotationFactory;

public class AddFlowShapeCommand extends NotationElementCommand {

	protected Supplier<Flow> flowSupplier;
	protected String semanticProxyUri;

	private AddFlowShapeCommand(EditingDomain domain, URI modelUri) {
		super(domain, modelUri);
	}

	public AddFlowShapeCommand(EditingDomain domain, URI modelUri, String semanticProxyUri) {
		super(domain, modelUri);
		this.semanticProxyUri = semanticProxyUri;
	}

	public AddFlowShapeCommand(EditingDomain domain, URI modelUri, Supplier<Flow> flowSupplier) {
		super(domain, modelUri);
		this.flowSupplier = flowSupplier;
	}

	@Override
	protected void doExecute() {
		Edge edge = WfnotationFactory.eINSTANCE.createEdge();
		edge.setGraphicId(NotationCommandUtil.generateId());
		SemanticProxy proxy = WfnotationFactory.eINSTANCE.createSemanticProxy();
		
		if (this.semanticProxyUri != null) {
			proxy.setUri(this.semanticProxyUri);
		} else {
			proxy.setUri(NotationCommandUtil.getSemanticProxyUri(flowSupplier.get()));
		}
		edge.setSemanticElement(proxy);

		notationModel.getElements().add(edge);
	}

}
