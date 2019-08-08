import { ModelServerClient } from "@modelserver/theia/lib/common";
import { BaseWidget, Message, Navigatable, Saveable, SplitPanel, Widget } from "@theia/core/lib/browser";
import { Emitter, Event, ILogger } from "@theia/core/lib/common";
import URI from "@theia/core/lib/common/uri";
import { WorkspaceService } from "@theia/workspace/lib/browser/workspace-service";
import { inject, injectable } from "inversify";
import * as React from "react";
import * as ReactDOM from "react-dom";

import { JsonFormsTree } from "../json-forms-tree/json-forms-tree";
import { JsonFormsTreeWidget } from "../json-forms-tree/json-forms-tree-widget";
import { JSONFormsWidget } from "./json-forms-widget";

export const JsonFormsTreeEditorWidgetOptions = Symbol(
  'JsonFormsTreeEditorWidgetOptions'
);
export interface JsonFormsTreeEditorWidgetOptions {
  uri: URI;
}

@injectable()
export class JsonFormsTreeEditorWidget extends BaseWidget
  implements Navigatable, Saveable {
  public dirty: boolean = false;
  public autoSave: 'off';
  private splitPanel: SplitPanel;

  protected readonly onDirtyChangedEmitter = new Emitter<void>();
  get onDirtyChanged(): Event<void> {
    return this.onDirtyChangedEmitter.event;
  }

  public selectedNode: JsonFormsTree.Node;

  protected instanceData: any;

  constructor(
    @inject(JsonFormsTreeEditorWidgetOptions)
    protected readonly options: JsonFormsTreeEditorWidgetOptions,
    @inject(JsonFormsTreeWidget)
    private readonly treeWidget: JsonFormsTreeWidget,
    @inject(JSONFormsWidget)
    private readonly formWidget: JSONFormsWidget,
    @inject(ModelServerClient)
    protected readonly modelServerApi: ModelServerClient,
    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService,
    @inject(ILogger) private readonly logger: ILogger
  ) {
    super();
    this.id = JsonFormsTreeEditorWidget.WIDGET_ID;
    this.title.label = options.uri.path.base;
    this.title.caption = JsonFormsTreeEditorWidget.WIDGET_LABEL;
    this.title.closable = true;
    this.title.iconClass = 'fa coffee-icon dark-purple';
    this.splitPanel = new SplitPanel();
    this.addClass('json-forms-tree-editor');
    this.splitPanel.addClass('json-forms-tree-editor-sash');
    this.treeWidget.addClass('json-forms-tree-editor-tree');
    this.formWidget.addClass('json-forms-tree-editor-forms');
    this.formWidget.onChange(data => {
      this.treeWidget.updateDataForNode(this.selectedNode, data);
      if (!this.dirty) {
        this.dirty = true;
        this.onDirtyChangedEmitter.fire(undefined);
      }
    });
    this.toDispose.push(
      this.treeWidget.onSelectionChange(ev => this.treeSelectionChanged(ev))
    );

    this.toDispose.push(this.onDirtyChangedEmitter);

    this.modelServerApi.get(this.getModelIDToRequest()).then(response => {
      if (response.statusCode === 200) {
        this.instanceData = response.body;
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
        ' ' +
        response.statusMessage
      );
      this.instanceData = undefined;
      return;
    });
  }

  public uri(): URI {
    return this.options.uri;
  }

  public save(): Promise<void> {
    this.logger.info('Save data to server');
    this.logger.debug(this.instanceData);
    return this.modelServerApi
      .update(this.getModelIDToRequest(), JSON.stringify(this.instanceData))
      .then(() => {
        // TODO check for success
        this.dirty = false;
        this.onDirtyChangedEmitter.fire();
      });
  }

  getModelIDToRequest(): string {
    const rootUriLength = this.workspaceService
      .getWorkspaceRootUri(this.options.uri)
      .toString().length;
    return this.options.uri.toString().substring(rootUriLength + 1);
  }

  getResourceUri(): URI | undefined {
    return this.options.uri;
  }

  createMoveToUri(resourceUri: URI): URI | undefined {
    return this.options.uri && this.options.uri.withPath(resourceUri.path);
  }

  protected renderError(errorMessage: string): void {
    ReactDOM.render(
      <React.Fragment>{errorMessage}</React.Fragment>,
      this.formWidget.node
    );
  }

  protected treeSelectionChanged(
    selectedNodes: readonly Readonly<JsonFormsTree.Node>[]
  ) {
    if (selectedNodes.length === 0) {
      this.selectedNode = undefined;
    } else {
      this.selectedNode = selectedNodes[0];
      this.formWidget.setSelection(this.selectedNode);
    }
    this.update();
  }

  protected onAfterAttach(msg: Message): void {
    this.splitPanel.addWidget(this.treeWidget);
    this.splitPanel.addWidget(this.formWidget);
    this.splitPanel.setRelativeSizes([1, 4]);
    Widget.attach(this.splitPanel, this.node);
    this.treeWidget.activate();
    super.onAfterAttach(msg);
  }
}

export namespace JsonFormsTreeEditorWidget {
  export const WIDGET_ID = 'json-forms-tree-editor';
  export const WIDGET_LABEL = 'JSONForms Tree Editor';

  export namespace Styles {
    export const JSONFORMS_TREE_EDITOR_CLASS = 'json-forms-tree-editor';
  }
}
