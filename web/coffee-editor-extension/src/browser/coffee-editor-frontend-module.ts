import "../../src/browser/style/index.css";
import {
  // OpenHandler,
  // WidgetFactory,
  // NavigatableWidgetOptions,
  LabelProviderContribution
} from "@theia/core/lib/browser";
// import URI from "@theia/core/lib/common/uri";
import { ContainerModule } from "inversify";
// import { CoffeeTreeEditorContribution } from "./coffee-editor-tree-contribution";
import {
  JsonFormsTreeEditorWidget,
//   JsonFormsTreeEditorWidgetOptions
} from "./json-forms-tree-editor/json-forms-tree-editor-widget";
import { createJsonFormsTreeWidget } from "./json-forms-tree/json-forms-tree-container";
import { JsonFormsTreeWidget } from "./json-forms-tree/json-forms-tree-widget";
import { CoffeeLabelProviderContribution } from "./CoffeeLabelProvider";
export default new ContainerModule(bind => {
  bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
  bind(JsonFormsTreeWidget).toDynamicValue(context =>
    createJsonFormsTreeWidget(context.container)
  );
  // bind(OpenHandler).to(CoffeeTreeEditorContribution);
  bind(JsonFormsTreeEditorWidget).toSelf();

  // bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
  //   id: JsonFormsTreeEditorWidget.WIDGET_ID,
  //   createWidget: (options: NavigatableWidgetOptions) => {
  //     const { container } = context;
  //     const child = container.createChild();

  //     const uri = new URI(options.uri);
  //     child
  //       .bind<JsonFormsTreeEditorWidgetOptions>(
  //         JsonFormsTreeEditorWidgetOptions
  //       )
  //       .toConstantValue({
  //         uri: uri
  //       });

  //     return child.get(JsonFormsTreeEditorWidget);
  //   }
  // }));
});
