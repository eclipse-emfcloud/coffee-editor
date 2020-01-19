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

import com.eclipsesource.glsp.example.workflow.utils.ModelTypes;
import com.eclipsesource.modelserver.coffee.model.coffee.AutomaticTask;
import com.eclipsesource.modelserver.coffee.model.coffee.Decision;
import com.eclipsesource.modelserver.coffee.model.coffee.Fork;
import com.eclipsesource.modelserver.coffee.model.coffee.Join;
import com.eclipsesource.modelserver.coffee.model.coffee.ManualTask;
import com.eclipsesource.modelserver.coffee.model.coffee.Merge;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;

public final class CoffeeTypeUtil {
	public static final String FORK_NODE = "activityNode:fork";
	public static final String JOIN_NODE = "activityNode:join";

	public static String toType(Node node) {
		if (node instanceof AutomaticTask) {
			return ModelTypes.AUTOMATED_TASK;
		}
		if (node instanceof ManualTask) {
			return ModelTypes.MANUAL_TASK;
		}
		if (node instanceof Decision) {
			return ModelTypes.DECISION_NODE;
		}
		if (node instanceof Merge) {
			return ModelTypes.MERGE_NODE;
		}
		if (node instanceof Fork) {
			return FORK_NODE;
		}
		if (node instanceof Join) {
			return JOIN_NODE;
		}
		return "unknown";
	}

	public static String toNodeType(Node node) {
		if (node instanceof AutomaticTask) {
			return "automated";
		}
		if (node instanceof ManualTask) {
			return "manual";
		}
		if (node instanceof Decision) {
			return "decisionNode";
		}
		if (node instanceof Merge) {
			return "mergeNode";
		}
		if (node instanceof Fork) {
			return "forkNode";
		}
		if (node instanceof Join) {
			return "joinNode";
		}
		return "unknown";
	}

	private CoffeeTypeUtil() {
	}
}
