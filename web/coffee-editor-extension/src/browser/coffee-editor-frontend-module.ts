/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import {
  CommandContribution,
  MenuContribution,
  ResourceProvider
} from "@theia/core/lib/common";
import {
  TreeEditorWidget,
  TreeEditorWidgetOptions,
  ResourceSaveable,
  TheiaTreeEditorContribution
} from 'theia-tree-editor/lib/browser';
import { WidgetFactory } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { CoffeeApp, initStore } from './coffee-editor';
import { getData } from '@jsonforms/core';
import { OpenHandler } from '@theia/core/lib/browser';

import '../../src/browser/style/index.css';

export default new ContainerModule(bind => {
  bind(TreeEditorWidget).toSelf();

  // value is cached
  bind(TheiaTreeEditorContribution).toSelf().inSingletonScope();
  [CommandContribution, MenuContribution, OpenHandler].forEach(serviceIdentifier =>
    bind(serviceIdentifier).toService(TheiaTreeEditorContribution)
  );

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(ctx => ({
    id: 'theia-tree-editor',
    async createWidget(uri: string): Promise<TreeEditorWidget> {
      const { container } = ctx;
      const resource = await container.get<ResourceProvider>(ResourceProvider)(new URI(uri));
      const store = await initStore();
      const onResourceLoad = (content: string): Promise<any> => {
        let parsedContent;
        try {
          parsedContent = JSON.parse(content);
        } catch (err) {
          console.warn('Invalid content', err);
          parsedContent = {};
        }
        return Promise.resolve(parsedContent);
      };
      const saveable = new ResourceSaveable(resource, () => getData(store.getState()));
      const child = container.createChild();
      child.bind<TreeEditorWidgetOptions>(TreeEditorWidgetOptions)
        .toConstantValue({ resource,
                                 store,
                                 EditorComponent: CoffeeApp,
                                 fileName: new URI(uri).path.base,
                                 saveable,
                                 onResourceLoad});
      return child.get(TreeEditorWidget);
    }
  }));
});
