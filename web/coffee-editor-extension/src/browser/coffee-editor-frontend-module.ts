/**
 * Generated using theia-extension-generator
 */
import { ContainerModule } from "inversify";
import {
  CommandContribution,
  MenuContribution,
  MessageService,
  ResourceProvider
} from "@theia/core/lib/common";
import {
  DirtyResourceSavable,
  TreeEditorWidget,
  TreeEditorWidgetOptions,
} from 'theia-tree-editor';
import { WidgetFactory, WidgetManager,LabelProviderContribution } from '@theia/core/lib/browser';
import { MaybePromise } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { FileStat } from '@theia/filesystem/lib/common';
import { CoffeeApp, initStore } from './coffee-editor';
import { getData } from '@jsonforms/core';
import { OpenHandler } from '@theia/core/lib/browser';
import '../../src/browser/style/index.css';
import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';

export default new ContainerModule(bind => {
  bind(TreeEditorWidget).toSelf();
  bind(LabelProviderContribution).toDynamicValue(ctx => ({
    canHandle(uri: object): number {
      let toCheck = uri;
      if(FileStat.is(toCheck)){
        toCheck = new URI(toCheck.uri);
      }
      if (toCheck instanceof URI) {
          if(toCheck.path.ext === '.jc'){
            return 1000;
          }
      }
      return 0;
  },

  getIcon(): MaybePromise<string> {
      return 'database-icon medium-yellow';
  },

  getName(uri: URI): string {
      return uri.displayName;
  },

  getLongName(uri: URI): string {
      return uri.path.toString();
  }
  }));
  // value is cached
  bind(CoffeeTreeEditorContribution).toSelf().inSingletonScope();
  [CommandContribution, MenuContribution, OpenHandler].forEach(serviceIdentifier =>
    bind(serviceIdentifier).toService(CoffeeTreeEditorContribution)
  );

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(ctx => ({
    id: 'theia-tree-editor',
    async createWidget(uri: string): Promise<TreeEditorWidget> {
      const { container } = ctx;
      const resource = await container.get<ResourceProvider>(ResourceProvider)(new URI(uri));
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
          fileName: new URI(uri).path.base,
          saveable,
          onResourceLoad
        });
      return child.get(TreeEditorWidget);
    }
  }));
});
