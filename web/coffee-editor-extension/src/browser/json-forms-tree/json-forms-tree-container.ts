import { interfaces, Container } from "inversify";
import { createTreeContainer, TreeWidget } from "@theia/core/lib/browser/tree";
import { JsonFormsTreeWidget } from "./json-forms-tree-widget";

function createJsonFormsTreeContainer(parent: interfaces.Container): Container {
  const child = createTreeContainer(parent);

  child.unbind(TreeWidget);
  child.bind(JsonFormsTreeWidget).toSelf();

  return child;
}

export function createJsonFormsTreeWidget(
  parent: interfaces.Container
): JsonFormsTreeWidget {
  return createJsonFormsTreeContainer(parent).get(JsonFormsTreeWidget);
}
