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
import URI from '@theia/core/lib/common/uri';

import { injectable, inject } from 'inversify';
import { ComparisonBackendService } from '../../common/protocol';
import { GLSPDiagramWidget } from "@eclipse-glsp/theia-integration/lib/browser";

@injectable()
export class GraphicalComparisonOpener {

  constructor(
    @inject(ComparisonBackendService) readonly comparisonBackendService: ComparisonBackendService) {
  }

  async getLeftDiagram(uri: URI, highlights: any): Promise<GLSPDiagramWidget> {
    throw Error("Needs to be implemented in the specific editors");
  }

  async getRightDiagram(uri: URI, highlights: any): Promise<GLSPDiagramWidget> {
    throw Error("Needs to be implemented in the specific editors");
  }

  getHighlights(left: string, right: string, origin: string = "undefined"): any {
    return this.comparisonBackendService.getHighlight(left, right, origin);
  }
}
