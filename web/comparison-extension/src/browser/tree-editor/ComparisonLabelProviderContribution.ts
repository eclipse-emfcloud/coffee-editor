/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
 ********************************************************************************/
import { injectable } from 'inversify';
import { LabelProviderContribution } from '@theia/core/lib/browser';
import { TreeEditor } from '../tree-widget/interfaces';
import { ComparisonTreeEditorWidget } from './ComparisonTreeEditorWidget';
import { ComparisonModel } from './comparison-model';
import URI from '@theia/core/lib/common/uri';

@injectable()
export class ComparisonTreeLabelProvider implements LabelProviderContribution {

  public canHandle(element: object): number {
    if ((TreeEditor.Node.is(element) || TreeEditor.CommandIconInfo.is(element))
      && element.editorId === ComparisonTreeEditorWidget.EDITOR_ID) {
      return 100;
    }
    return 0;
  }

  getTextColor(element: object): string | undefined {
    const data = TreeEditor.Node.is(element) ? element.jsonforms.data : element;
    if (data.color && data.color.trim() !== "") {
      return data.color;
    }
    return undefined;
  }

  getIcon(element: object): string | undefined {
    const data = TreeEditor.Node.is(element) ? element.jsonforms.data : element;
    if (data.icon && data.icon.trim() !== "") {
      return data.icon;
    }
    return undefined;
  }

  public getName(element: object): string | undefined {
    const data = TreeEditor.Node.is(element) ? element.jsonforms.data : element;
    if (data.eClass) {
      switch (data.eClass) {
        case ComparisonModel.Type.DiagramLandscape:
        case ComparisonModel.Type.DiagramNodeAttribute:
        case ComparisonModel.Type.DiagramEdge:
        case ComparisonModel.Type.DiagramEdgeAttribute:
        case ComparisonModel.Type.DiagramEdgeMember:
        case ComparisonModel.Type.DiagramMatch:
        case ComparisonModel.Type.DiagramDiff:
        case ComparisonModel.Type.DiagramNode:
        case ComparisonModel.Type.DiagramAttribute:
        case ComparisonModel.Type.DiagramReference:
        case ComparisonModel.Type.DiagramConflicts:
        case ComparisonModel.Type.DiagramInformation:
          return data.name || this.getTypeName(data.eClass);
        default:
          return this.getTypeName(data.eClass);
      }
    }
    return undefined;
  }

  highlight

  private getTypeName(eClass: string): string {
    const fragment = new URI(eClass).fragment;
    if (fragment.startsWith('//')) {
      return fragment.substring(2);
    }
    return fragment;
  }
}
