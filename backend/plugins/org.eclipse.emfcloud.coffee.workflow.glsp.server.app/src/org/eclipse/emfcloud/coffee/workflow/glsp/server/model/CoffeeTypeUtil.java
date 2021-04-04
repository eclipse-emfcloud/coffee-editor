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

import org.eclipse.emf.ecore.EObject;


import org.eclipse.emfcloud.coffee.AutomaticTask;
import org.eclipse.emfcloud.coffee.Decision;
import org.eclipse.emfcloud.coffee.Fork;
import org.eclipse.emfcloud.coffee.Join;
import org.eclipse.emfcloud.coffee.ManualTask;
import org.eclipse.emfcloud.coffee.MenuSelectionTask;
import org.eclipse.emfcloud.coffee.Merge;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.util.CoffeeSwitch;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;

public final class CoffeeTypeUtil {
	public static final String FORK_NODE = "activityNode:fork";
	public static final String JOIN_NODE = "activityNode:join";

	private static final CoffeeSwitch<String> TO_TYPE = new CoffeeSwitch<String>() {
		public String caseAutomaticTask(AutomaticTask object) {
			return ModelTypes.AUTOMATED_TASK;
		}
		
		public String caseManualTask(ManualTask object) {
			return ModelTypes.MANUAL_TASK;
		}
		
		public String caseMenuSelectionTask(MenuSelectionTask object) {
			return ModelTypes.MENU_SELECTION_TASK;
		}
		
		public String caseDecision(Decision object) {
			return ModelTypes.DECISION_NODE;
		}
		
		public String caseMerge(Merge object) {
			return ModelTypes.MERGE_NODE;
		}
		
		public String caseFork(Fork object) {
			return FORK_NODE;
		}
		
		public String caseJoin(Join object) {
			return JOIN_NODE;
		}
		
		public String defaultCase(EObject object) {
		return "unknown";
	}
	};

	private static final CoffeeSwitch<String> TO_NODE_TYPE = new CoffeeSwitch<String>() {
		public String caseAutomaticTask(AutomaticTask object) {
			return "automated";
		}
		
		public String caseManualTask(ManualTask object) {
			return "manual";
		}
		
		public String caseMenuSelectionTask(MenuSelectionTask object) {
			return "menuselection";
		}
		
		public String caseDecision(Decision object) {
			return "decisionNode";
		}
		
		public String caseMerge(Merge object) {
			return "mergeNode";
		}
		
		public String caseFork(Fork object) {
			return "forkNode";
		}
		
		public String caseJoin(Join object) {
			return "joinNode";
		}
		
		public String defaultCase(EObject object) {
		return "unknown";
		}
	};
	
	public static String toType(Node node) {
		return TO_TYPE.doSwitch(node);
	}

	public static String toNodeType(Node node) {
		return TO_NODE_TYPE.doSwitch(node);
	}

	private CoffeeTypeUtil() {
	}
}
