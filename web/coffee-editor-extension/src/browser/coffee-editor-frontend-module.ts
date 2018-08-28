/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import {
  CommandContribution, MenuContribution, Resource,
  ResourceProvider
} from "@theia/core/lib/common";
import { TheiaTreeEditorContribution, ResourceSaveable, TreeEditorWidget, TreeEditorWidgetOptions } from 'theia-tree-editor/lib/browser';
import {OpenHandler, WidgetFactory} from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { CoffeeApp, initStore } from './coffee-editor';

import '../../src/browser/style/index.css';
import {getData} from "@jsonforms/core";

class MyResourceSaveable extends ResourceSaveable {
  constructor(resource: Resource, getData: () => any) {
    super(resource, getData);
  }

  onSave(data: any) {
    console.log("I was called during save");
    return super.onSave(data);
  }
}

export default new ContainerModule(bind => {
  bind<WidgetFactory>(WidgetFactory).toDynamicValue(ctx => ({
    id: 'theia-tree-editor',
    async createWidget(uri: string): Promise<TreeEditorWidget> {
      const { container } = ctx;
      const resource = await container.get<ResourceProvider>(ResourceProvider)(new URI(uri));
      const store = await initStore();
      const child = container.createChild();
      child.bind<TreeEditorWidgetOptions>(TreeEditorWidgetOptions)
        .toConstantValue({
          resource,
          store,
          EditorComponent: CoffeeApp,
          fileName: new URI(uri).path.base,
          saveable: new MyResourceSaveable(resource, () => getData(store.getState())),
          onResourceLoad: contentAsString => {
            console.log("I was called during resource load");
            return JSON.parse(contentAsString)
          }
        });
      return child.get(TreeEditorWidget);
    }
  }));
  bind(TreeEditorWidget).toSelf();

  bind(TheiaTreeEditorContribution).toSelf().inSingletonScope();
  [CommandContribution, MenuContribution, OpenHandler].forEach(serviceIdentifier =>
    bind(serviceIdentifier).toService(TheiaTreeEditorContribution)
  );
});
