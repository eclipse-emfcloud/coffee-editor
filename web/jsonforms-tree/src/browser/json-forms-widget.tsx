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
import { Actions, jsonformsReducer, JsonFormsState } from '@jsonforms/core';
import { materialCells, materialRenderers } from '@jsonforms/material-renderers';
import { JsonFormsDispatch, JsonFormsReduxContext } from '@jsonforms/react';
import { Emitter, Event } from '@theia/core';
import { BaseWidget, Message } from '@theia/core/lib/browser';
import { inject, injectable } from 'inversify';
import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { TreeEditor } from './interfaces';

@injectable()
export class JSONFormsWidget extends BaseWidget {
  private selectedNode: TreeEditor.Node;
  private store: any;

  protected changeEmitter = new Emitter<Readonly<any>>();

  constructor(@inject(TreeEditor.ModelService) private readonly modelService: TreeEditor.ModelService) {
    super();

    this.store = this.initStore();
    this.store.dispatch(Actions.init({}, { type: 'string' }));
    this.toDispose.push(this.changeEmitter);
    this.store.subscribe(() => {
      this.changeEmitter.fire(this.store.getState().jsonforms.core.data);
    });
    this.renderEmptyForms();
  }
  get onChange(): Event<Readonly<any>> {
    return this.changeEmitter.event;
  }

  private initStore() {
    const initState: JsonFormsState = {
      jsonforms: {
        cells: materialCells,
        renderers: materialRenderers
      }
    };
    return createStore(
      combineReducers({ jsonforms: jsonformsReducer() }),
      initState
    );
  }

  setSelection(selectedNode: TreeEditor.Node) {
    this.selectedNode = selectedNode;

    this.store.dispatch(
      Actions.init(
        this.modelService.getDataForNode(this.selectedNode),
        this.modelService.getSchemaForNode(this.selectedNode),
        this.modelService.getUiSchemaForNode(this.selectedNode),
        {
          refParserOptions: {
            dereference: { circular: 'ignore' }
          }
        }
      )
    );
    this.renderForms();
  }

  protected renderForms(): void {
    if (this.selectedNode) {
      ReactDOM.render(
        <React.Fragment>
          <Provider store={this.store}>
            <JsonFormsReduxContext>
              <JsonFormsDispatch />
            </JsonFormsReduxContext>
          </Provider>
        </React.Fragment>,
        this.node
      );
    } else {
      this.renderEmptyForms();
    }
  }
  protected renderEmptyForms(): void {
    ReactDOM.render(
      <React.Fragment>Please select an element</React.Fragment>,
      this.node
    );
  }
  protected onUpdateRequest(msg: Message): void {
    super.onUpdateRequest(msg);
    this.renderForms();
  }
}
