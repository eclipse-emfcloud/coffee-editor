import { injectable, inject } from "inversify";
import {
  BaseWidget,
  Navigatable,
  Message,
  Widget,
  MessageLoop
} from "@theia/core/lib/browser";
import { JsonFormsTreeWidget } from "../json-forms-tree/json-forms-tree-widget";
import { Disposable, Emitter, Event } from "@theia/core/lib/common";

import * as React from "react";
import * as ReactDOM from "react-dom";
import URI from "@theia/core/lib/common/uri";

import { JsonFormsDispatch, JsonFormsReduxContext } from "@jsonforms/react";
import {
  materialRenderers,
  materialCells
} from "@jsonforms/material-renderers";
import { JsonFormsState, jsonformsReducer, Actions } from "@jsonforms/core";

import {
  coffeeSchema,
  controlUnitView,
  machineView,
  brewingView
} from "../models/coffee-schemas";
import { JsonFormsTree } from "../json-forms-tree/json-forms-tree";
import { combineReducers, createStore } from "redux";
import { Provider } from "react-redux";

export const JsonFormsTreeEditorWidgetOptions = Symbol(
  "JsonFormsTreeEditorWidgetOptions"
);
export interface JsonFormsTreeEditorWidgetOptions {
  uri: URI;
}

@injectable()
export class JsonFormsTreeEditorWidget extends BaseWidget
  implements Navigatable {
  protected contentNode: HTMLElement;
  protected treeNode: HTMLElement;
  protected formsNode: HTMLElement;

  protected readonly onDidUpdateEmitter = new Emitter<void>();
  readonly onDidUpdate: Event<void> = this.onDidUpdateEmitter.event;

  protected selectedNode: JsonFormsTree.Node;

  protected store: any;

  constructor(
    @inject(JsonFormsTreeEditorWidgetOptions)
    protected readonly options: JsonFormsTreeEditorWidgetOptions,
    @inject(JsonFormsTreeWidget)
    protected readonly treeWidget: JsonFormsTreeWidget
  ) {
    super();
    this.id = JsonFormsTreeEditorWidget.WIDGET_ID;
    this.title.label = options.uri.path.base;
    this.title.caption = JsonFormsTreeEditorWidget.WIDGET_LABEL;
    this.addClass(JsonFormsTreeEditorWidget.Styles.JSONFORMS_TREE_EDITOR_CLASS);

    this.contentNode = document.createElement("div");
    this.contentNode.classList.add("json-forms-tree-editor-content");
    this.node.appendChild(this.contentNode);

    this.treeNode = document.createElement("div");
    this.treeNode.classList.add("json-forms-tree-editor-tree-container");
    this.contentNode.appendChild(this.treeNode);

    this.formsNode = document.createElement("div");
    this.formsNode.classList.add("json-forms-tree-editor-forms-container");
    this.contentNode.appendChild(this.formsNode);

    this.toDispose.push(
      this.treeWidget.onSelectionChange(ev => this.treeSelectionChanged(ev))
    );
    this.toDispose.push(this.treeWidget);

    this.store = this.initStore();
    this.store.dispatch(Actions.init({}, { type: "string" }));
  }

  getResourceUri(): URI | undefined {
    return this.options.uri;
  }

  createMoveToUri(resourceUri: URI): URI | undefined {
    return this.options.uri && this.options.uri.withPath(resourceUri.path);
  }

  initStore() {
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
        this.formsNode
      );
    } else {
      this.renderEmptyForms();
    }
  }

  protected renderEmptyForms(): void {
    ReactDOM.render(
      <React.Fragment>Please select an element</React.Fragment>,
      this.formsNode
    );
  }

  protected getUiSchema(type: string) {
    switch (type) {
      case "machine":
        return machineView;
      case "control-unit":
        return controlUnitView;
      case "activity":
        return undefined;
      case "brewing-unit":
        return brewingView;
      default:
        console.log("Can't find ui schema for type " + type);
        return undefined;
    }
  }

  protected getSchema(type: string) {
    switch (type) {
      case "machine":
        return coffeeSchema;
      case "control-unit":
        return coffeeSchema.definitions.controlunit;
      case "activity":
        return coffeeSchema.definitions.activity;
      case "brewing-unit":
        return coffeeSchema.definitions.brewingunit;
      default:
        console.log("Can't find schema for type " + type);
        return undefined;
    }
  }

  protected onAfterAttach(msg: Message): void {
    super.onAfterAttach(msg);
    Widget.attach(this.treeWidget, this.treeNode);
    this.toDisposeOnDetach.push(
      Disposable.create(() => {
        Widget.detach(this.treeWidget);
      })
    );
    this.renderForms();
  }

  protected onAfterShow(msg: Message): void {
    super.onAfterShow(msg);
    this.treeWidget.activate();
  }

  protected onUpdateRequest(msg: Message): void {
    super.onUpdateRequest(msg);
    this.renderForms();
    this.onDidUpdateEmitter.fire(undefined);
  }

  protected onResize(msg: Widget.ResizeMessage): void {
    super.onResize(msg);
    MessageLoop.sendMessage(this.treeWidget, Widget.ResizeMessage.UnknownSize);
  }

  protected onActivateRequest(msg: Message): void {
    super.onActivateRequest(msg);
    this.treeWidget.activate();
  }

  protected treeSelectionChanged(
    selectedNodes: readonly Readonly<JsonFormsTree.Node>[]
  ) {
    if (selectedNodes.length === 0) {
      this.selectedNode = undefined;
    } else {
      this.selectedNode = selectedNodes[0];
      this.store.dispatch(
        Actions.init(
          this.selectedNode.jsonforms.data,
          {
            definitions: coffeeSchema.definitions,
            ...this.getSchema(this.selectedNode.jsonforms.type)
          },
          this.getUiSchema(this.selectedNode.jsonforms.type),
          {
            refParserOptions: {
              dereference: { circular: "ignore" }
            }
          }
        )
      );
    }
    this.update();
  }
}

export namespace JsonFormsTreeEditorWidget {
  export const WIDGET_ID = "json-forms-tree-editor";
  export const WIDGET_LABEL = "JSONForms Tree Editor";

  export namespace Styles {
    export const JSONFORMS_TREE_EDITOR_CLASS = "json-forms-tree-editor";
  }
}
