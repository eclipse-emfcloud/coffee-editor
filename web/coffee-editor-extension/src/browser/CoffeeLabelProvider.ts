import { LabelProviderContribution } from "@theia/core/lib/browser";
import { FileStat } from "@theia/filesystem/lib/common";
import URI from "@theia/core/lib/common/uri";
import { MaybePromise } from "@theia/core";
import { injectable } from "inversify";

@injectable()
export class CoffeeLabelProviderContribution implements LabelProviderContribution{
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
    }
    
    getIcon(): MaybePromise<string> {
        return 'database-icon medium-yellow';
    }
    
    getName(uri: URI): string {
        return uri.displayName;
    }
    
    getLongName(uri: URI): string {
        return uri.path.toString();
    }
}
