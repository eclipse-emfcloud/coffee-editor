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
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.NotationCommandUtil;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.SemanticProxy;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.Shape;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.WfnotationFactory;
import org.eclipse.glsp.graph.GPoint;

public class AddNodeShapeCommand extends NotationElementCommand {

	protected final GPoint shapePosition;
	protected String semanticProxyUri;
	protected Supplier<Node> nodeSupplier;

	private AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position) {
		super(domain, modelUri);
		this.shapePosition = position;
		this.nodeSupplier = null;
		this.semanticProxyUri = null;
	}

	public AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
			final String semanticProxyUri) {
		this(domain, modelUri, position);
		this.semanticProxyUri = semanticProxyUri;
	}

	public AddNodeShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
			final Supplier<Node> nodeSupplier) {
		this(domain, modelUri, position);
		this.nodeSupplier = nodeSupplier;
	}

	@Override
	protected void doExecute() {
		Shape shape = WfnotationFactory.eINSTANCE.createShape();
		shape.setGraphicId(NotationCommandUtil.generateId());

		SemanticProxy proxy = WfnotationFactory.eINSTANCE.createSemanticProxy();
		if (this.semanticProxyUri != null) {
			proxy.setUri(this.semanticProxyUri);
		} else {
			proxy.setUri(NotationCommandUtil.getSemanticProxyUri(nodeSupplier.get()));
		}
		shape.setSemanticElement(proxy);

		notationModel.getElements().add(shape);
	}

}
