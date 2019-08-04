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
import { WorkspaceService } from "@theia/workspace/lib/browser/workspace-service";

import * as React from "react";
import * as ReactDOM from "react-dom";
import URI from "@theia/core/lib/common/uri";

import { JsonFormsDispatch, JsonFormsReduxContext } from "@jsonforms/react";
import {
  materialRenderers,
  materialCells
} from "@jsonforms/material-renderers";
import { JsonFormsState, jsonformsReducer, Actions } from "@jsonforms/core";

import { ModelServerApi } from "@modelserver/theia/lib/browser";

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

  protected instanceData: any;

  constructor(
    @inject(JsonFormsTreeEditorWidgetOptions)
    protected readonly options: JsonFormsTreeEditorWidgetOptions,
    @inject(JsonFormsTreeWidget)
    protected readonly treeWidget: JsonFormsTreeWidget,
    @inject(ModelServerApi)
    protected readonly modelServerApi: ModelServerApi,
    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService
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
    this.store.subscribe(() => {
      this.treeWidget.updateDataForNode(this.selectedNode, this.store.getState().jsonforms.core.data);
    })

    this.modelServerApi.get(this.getModelIDToRequest()).then(response => {
      if (response.statusCode === 200) {
        // response is wrongly typed
        this.instanceData = (response.element as any).data;
        this.treeWidget
          .setData({ error: false, data: this.instanceData })
          .then(() => this.treeWidget.selectFirst());
        return;
      }
      this.treeWidget.setData({ error: response.statusMessage });
      this.renderError(
        "An error occurred when requesting '" +
          this.getModelIDToRequest() +
          "' - Status " +
          response.statusCode +
          " " +
          response.statusMessage
      );
      this.instanceData = undefined;
      return;
    });
  }

  getModelIDToRequest(): string {
    const rootUriLength = this.workspaceService
      .getWorkspaceRootUri(this.options.uri)
      .toString().length;
    const thisUriLength = this.options.uri.toString().length;
    const thisUriExtLength = this.options.uri.path.ext.length;
    return (
      this.options.uri
        .toString()
        .substring(rootUriLength + 1, thisUriLength - thisUriExtLength) +
      ".json"
    );
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

  protected renderError(errorMessage: string): void {
    ReactDOM.render(
      <React.Fragment>{errorMessage}</React.Fragment>,
      this.formsNode
    );
  }

  protected getUiSchemaForNode(node: JsonFormsTree.Node) {
    let schema = this.getUiSchemaForType(node.jsonforms.type);
    if (schema) {
      return schema;
    }
    // there is no type, try to guess
    if (node.jsonforms.data.nodes) {
      return {
        type: "Label",
        text: "Workflow"
      };
    }
    return undefined;
  }

  protected getUiSchemaForType(type: string) {
    if (!type) {
      return undefined;
    }
    switch (type) {
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//Machine":
        return machineView;
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//ControlUnit":
        return controlUnitView;
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//BrewingUnit":
        return brewingView;
      default:
        console.log("Can't find registered ui schema for type " + type);
        return undefined;
    }
  }

  protected getSchemaForNode(node: JsonFormsTree.Node) {
    let schema = this.getSchemaForType(node.jsonforms.type);
    if (schema) {
      return schema;
    }
    // there is no type, try to guess
    if (node.jsonforms.data.nodes) {
      return coffeeSchema.definitions.workflow;
    }
    return undefined;
  }

  protected getSchemaForType(type: string) {
    if (!type) {
      return undefined;
    }
    const schema = Object.entries(coffeeSchema.definitions)
      .map(entry => entry[1])
      .find(
        definition =>
          definition.properties && definition.properties.eClass.const === type
      );
    if (!schema) {
      console.log("Can't find definition schema for type " + type);
    }
    return schema;
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
            ...this.getSchemaForNode(this.selectedNode)
          },
          this.getUiSchemaForNode(this.selectedNode),
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
