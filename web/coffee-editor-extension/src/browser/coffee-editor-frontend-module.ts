/**
 * Generated using theia-extension-generator
 */
import { getData } from '@jsonforms/core';
import { LabelProviderContribution, OpenHandler, WidgetFactory, WidgetManager, NavigatableWidgetOptions } from '@theia/core/lib/browser';
import { CommandContribution, MenuContribution, MessageService, ResourceProvider } from "@theia/core/lib/common";
import URI from '@theia/core/lib/common/uri';
import { ContainerModule } from "inversify";
import { DirtyResourceSavable, TreeEditorWidget, TreeEditorWidgetOptions } from 'theia-tree-editor';
import '../../src/browser/style/index.css';
import { CoffeeApp, initStore } from './coffee-editor';
import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';
import { CoffeeLabelProviderContribution } from "./CoffeeLabelProvider";
export default new ContainerModule(bind => {
  bind(TreeEditorWidget).toSelf();
  bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
  // value is cached
  bind(CoffeeTreeEditorContribution).toSelf().inSingletonScope();
  [CommandContribution, MenuContribution, OpenHandler].forEach(serviceIdentifier =>
    bind(serviceIdentifier).toService(CoffeeTreeEditorContribution)
  );

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(ctx => ({
    id: 'theia-tree-editor',
    async createWidget(options: NavigatableWidgetOptions): Promise<TreeEditorWidget> {
      const { container } = ctx;
      const uri = new URI(options.uri);
      const resource = await container.get<ResourceProvider>(ResourceProvider)(uri);
      const widgetManager = await container.get<WidgetManager>(WidgetManager);
      const messageService = await container.get<MessageService>(MessageService);
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
      const saveable = new DirtyResourceSavable(resource,
                                                () => getData(store.getState()),
                                                widgetManager,
                                                messageService);
      const child = container.createChild();
      child.bind<TreeEditorWidgetOptions>(TreeEditorWidgetOptions)
        .toConstantValue({
          resource,
          store,
          EditorComponent: CoffeeApp,
          fileName: uri.path.base,
          saveable,
          onResourceLoad
        });
      return child.get(TreeEditorWidget);
    }
  }));
});
