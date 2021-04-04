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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.util;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public final class ModelTypes {
   private ModelTypes() {}

   public static final String LABEL_HEADING = "label:heading";
   public static final String LABEL_TEXT = "label:text";
   public static final String COMP_HEADER = "comp:header";
   public static final String LABEL_ICON = "label:icon";
   public static final String WEIGHTED_EDGE = "edge:weighted";
   public static final String ICON = "icon";
   public static final String DECISION_NODE = "activityNode:decision";
   public static final String MERGE_NODE = "activityNode:merge";
   public static final String MANUAL_TASK = "task:manual";
   public static final String AUTOMATED_TASK = "task:automated";
   public static final String MENU_SELECTION_TASK = "task:menuselection";

   public static final Set<String> TASKS = Set.of(MANUAL_TASK, AUTOMATED_TASK, MENU_SELECTION_TASK);
   public static final Set<String> CONTROL_NODES = Set.of(DECISION_NODE, MERGE_NODE);
   public static final Set<String> ALL_NODES = ImmutableSet.copyOf(Iterables.concat(
		   TASKS, CONTROL_NODES));
	
   public static String toNodeType(final String type) {
      switch (type) {
         case DECISION_NODE:
            return "decisionNode";
         case MERGE_NODE:
            return "mergeNode";
         case MANUAL_TASK:
            return "manual";
         case AUTOMATED_TASK:
            return "automated";
         case MENU_SELECTION_TASK:
        	 return "menuselection";
         default:
            break;
      }
      return "unknown";
   }
}
