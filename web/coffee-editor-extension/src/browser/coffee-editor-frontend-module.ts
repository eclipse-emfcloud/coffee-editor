/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import {
  ResourceProvider
} from "@theia/core/lib/common";
import { TreeEditorWidget, TreeEditorWidgetOptions } from 'theia-tree-editor/lib/browser';
import { WidgetFactory } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { CoffeeApp, initStore } from './coffee-editor';

import '../../src/browser/style/index.css';

export default new ContainerModule(bind => {
  bind<WidgetFactory>(WidgetFactory).toDynamicValue(ctx => ({
    id: 'theia-tree-editor',
    async createWidget(uri: string): Promise<TreeEditorWidget> {
      const { container } = ctx;
      const resource = await container.get<ResourceProvider>(ResourceProvider)(new URI(uri));
      const store = await initStore();
      const child = container.createChild();
      child.bind<TreeEditorWidgetOptions>(TreeEditorWidgetOptions)
        .toConstantValue({ resource, store, EditorComponent: CoffeeApp, fileName: new URI(uri).path.base});
      return child.get(TreeEditorWidget);
    }
  }));
});
