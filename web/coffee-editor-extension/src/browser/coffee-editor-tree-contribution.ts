import { NavigatableWidgetOpenHandler, NavigatableWidget } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { JsonFormsTreeEditorWidget } from "./json-forms-tree-editor/json-forms-tree-editor-widget";

export class CoffeeTreeEditorContribution extends NavigatableWidgetOpenHandler<
NavigatableWidget
> {
  readonly id = JsonFormsTreeEditorWidget.WIDGET_ID;
  readonly label = JsonFormsTreeEditorWidget.WIDGET_LABEL;

  canHandle(uri: URI): number {
    if (uri.path.ext === ".jc") {
      return 1000;
    }
    return 0;
  }
}
