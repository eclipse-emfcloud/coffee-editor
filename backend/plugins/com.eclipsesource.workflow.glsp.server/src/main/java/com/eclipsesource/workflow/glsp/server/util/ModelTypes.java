/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.eclipsesource.workflow.glsp.server.util;

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
         default:
            break;
      }
      return "unknown";
   }
}
