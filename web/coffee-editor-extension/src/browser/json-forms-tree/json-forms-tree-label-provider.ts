import { CoffeeModel } from "./coffee-model";
import { injectable } from "inversify";
import { JsonFormsTree } from "./json-forms-tree";
import URI from "@theia/core/lib/common/uri";
import { TreeNode } from "@theia/core/lib/browser/tree/tree";


const DEFAULT_COLOR = 'black';

const ICON_CLASSES: Map<string, string> = new Map([
  [ CoffeeModel.Type.AutomaticTask, 'fa-cog ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.BrewingUnit, 'fa-burn ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Component, 'fa-cube ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.ControlUnit, 'fa-server ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Decision, 'fa-code-branch fa-rotate-90 ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Dimension, 'fa-arrows-alt ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.DipTray, 'fa-inbox ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Display, 'fa-tv ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Flow, 'exchange-alt ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Fork, 'fa-code-branch fa-rotate-90 ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Join, 'fa-code-branch fa-rotate-270 ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Machine, 'fa-cogs ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.ManualTask, 'fa-wrench ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Merge, 'fa-code-branch fa-rotate-270 ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Node, 'fa-circle ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Processor, 'fa-microchip ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.RAM, 'fa-memory ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.Task, 'fa-tasks ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.WaterTank, 'fa-water ' + DEFAULT_COLOR],
  [ CoffeeModel.Type.WeightedFlow, 'exchange-alt light-orange'],
]);

 /* Icon for unknown types */
const UNKNOWN_ICON = 'fa-question-circle ' + DEFAULT_COLOR;

@injectable()
export class JsonFormsTreeLabelProvider {

  public getIconClass(node: TreeNode): string {
    if (JsonFormsTree.Node.is(node)) {
      const iconClass = ICON_CLASSES.get(node.jsonforms.type);
      if (iconClass !== undefined) {
        return 'fa ' + iconClass;
      }
    }
    return 'far ' + UNKNOWN_ICON;
  }

  public getName(data: any): string {
    if (data.eClass) {
      switch (data.eClass) {
        case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//Task":
        case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//AutomaticTask":
        case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//ManualTask":
        case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//Machine":
          return data.name;
        default:
          // TODO query title of schema
          const fragment = new URI(data.eClass).fragment;
          if (fragment.startsWith("//")) {
            return fragment.substring(2);
          }
          return fragment;
      }
    }
    // guess
    if (data.nodes) {
      return "Workflow";
    }
    return undefined;
  }
}