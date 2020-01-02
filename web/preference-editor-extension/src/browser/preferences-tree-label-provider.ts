/*!
 * Copyright (C) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
import { TreeNode } from '@theia/core/lib/browser/tree/tree';
import { injectable } from 'inversify';
import { TreeEditor } from 'theia-tree-editor';

// const DEFAULT_COLOR = 'black';

/* Icon for unknown types */
// const UNKNOWN_ICON = 'fa-question-circle ' + DEFAULT_COLOR;

@injectable()
export class PreferencesTreeLabelProvider implements TreeEditor.LabelProvider {

  public getIconClass(node: TreeNode | string): string {
    // TODO return proper icon

    // return 'far ' + UNKNOWN_ICON;
    return 'fa fa-cog black';
  }

  public getName(data: any): string {
    // TODO return proper names
    if (data.title) {
      return data.title;
    }
    if (data.name) {
      return data.name;
    }

    return 'PLACEHOLDER';
  }
}
