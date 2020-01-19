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

import java.util.Map;

import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

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
