/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WorkflowHighlightStore {

   private final Map<String, String> highlights = new HashMap<>();

   public Map<String, String> getHighlights() { return highlights; }

   public void addHighlight(final Entry<String, String> entry) {
      String current = this.highlights.get(entry.getKey());
      if (current == null || isHigher(entry.getKey(), current)) {
         this.highlights.put(entry.getKey(), entry.getValue());
      }
   }

   private boolean isHigher(final String key, final String current) {
      if (current == "deleted") {
         return false;
      }
      if (current == "changed") {
         if (key == "deleted") {
            return true;
         }
      }
      if (current == "added") {
         if (key == "deleted" || key == "changed") {
            return true;
         }
      }
      return false;
   }
}
