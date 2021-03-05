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

import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;

public class MappedGModelRoot {

	private GModelRoot root;
	private Map<Node, GNode> mapping;

	public MappedGModelRoot(GModelRoot root, Map<Node, GNode> mapping) {
		super();
		this.root = root;
		this.mapping = mapping;
	}

	public Map<Node, GNode> getMapping() {
		return mapping;
	}

	public GModelRoot getRoot() {
		return root;
	}

}
