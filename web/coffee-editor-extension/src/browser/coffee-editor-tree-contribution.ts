import { NavigatableWidgetOpenHandler } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { JsonFormsTreeEditorWidget } from "./json-forms-tree-editor/json-forms-tree-editor-widget";

export class CoffeeTreeEditorContribution extends NavigatableWidgetOpenHandler<
  JsonFormsTreeEditorWidget
> {
  readonly id = JsonFormsTreeEditorWidget.WIDGET_ID;
  readonly label = JsonFormsTreeEditorWidget.WIDGET_LABEL;

  canHandle(uri: URI): number {
    if (
      uri.path.ext === ".jc" ||
      uri.path.ext === ".xmi" ||
      uri.path.ext === ".coffee"
    ) {
      return 1000;
    }
    return 0;
  }
}
