import { injectable, inject } from "inversify";
import { OpenHandler, OpenerOptions, Endpoint } from "@theia/core/lib/browser";
import { SelectionService, ResourceProvider, MaybePromise } from "@theia/core";
import { MiniBrowserOpenHandler } from "@theia/mini-browser/lib/browser/mini-browser-open-handler";

import { LocationMapper } from "@theia/mini-browser/lib/browser/location-mapper-service";
import URI from "@theia/core/lib/common/uri";
import { IFileServer } from "../common/request-file-protocol";
import { FileTypes } from "../node/file-server";

export namespace AnalysisEditor {
    export const FILE_EXTENSION = '.wfanalysis'
}
@injectable()
export class AnalysisEditorOpenHandler implements OpenHandler {
    constructor(
        @inject(SelectionService) readonly selectionService: SelectionService,
        @inject(ResourceProvider) private readonly resourceProvider: ResourceProvider,
        @inject(IFileServer) private readonly fileServer: IFileServer,
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler) {
    }
    canHandle(uri: URI, options?: OpenerOptions): MaybePromise<number> {

        if (uri.path.ext == AnalysisEditor.FILE_EXTENSION) {
            return 1000;
        }
        return 0;
    }
    open(uri: URI, options?: OpenerOptions): MaybePromise<object | undefined> {
        return this.resourceProvider(uri).then(resource => {
            return resource.readContents().then(async content => {
                const htmlFile = await this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML)
                if (this.openHandler.canHandle(new URI(htmlFile))) {
                    const jsonFile = escape(content);
                    const editorName = resource.uri.displayName.replace(AnalysisEditor.FILE_EXTENSION, "")
                    const urlWithQuery = htmlFile + "?json=" + jsonFile;
                    return await this.openHandler.open(undefined, { name: editorName, startPage: urlWithQuery, toolbar: 'hide' });
                }
            });
        });
    }
    readonly id = "anaylsis-opener"
    label = "Workflow Analysis Editor"
    iconClass = "analysis-icon"

}

@injectable()
export class WorkflowFileLocationMapper implements LocationMapper {
    canHandle(location: string): MaybePromise<number> {
        return location.startsWith('file://') ? 2 : 0;
    }
    map(location: string): MaybePromise<string> {
        return this.toURL(location);
    }

    toURL(uri: String, endpointPath: string = 'mini-browser'): MaybePromise<string> {
        if (!uri.startsWith('file://')) {
            throw new Error(`Only URIs with 'file' scheme can be mapped to an URL. URI was: ${uri}.`)
        }
        let queryIndex = uri.lastIndexOf('?');
        let queryString = uri.substring(queryIndex, uri.length);
        let rawLocation = uri.substring(7, queryIndex)
        if (rawLocation.charAt(0) === '/') {
            rawLocation = rawLocation.substr(1);
        }
        return new Endpoint().getRestUrl().resolve(`${endpointPath}/${rawLocation}`).toString() + queryString

    }
}
