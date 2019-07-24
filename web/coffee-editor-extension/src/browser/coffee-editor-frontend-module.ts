/**
 * Generated using theia-extension-generator
 */
import { LabelProviderContribution } from '@theia/core/lib/browser';
import { ContainerModule } from "inversify";
import '../../src/browser/style/index.css';
import { CoffeeLabelProviderContribution } from "./CoffeeLabelProvider";
export default new ContainerModule(bind => {
  bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
  // value is cached

});
