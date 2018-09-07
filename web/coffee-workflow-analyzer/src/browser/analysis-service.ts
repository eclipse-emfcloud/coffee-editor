import URI from "@theia/core/lib/common/uri";
import { MiniBrowserOpenHandler } from "@theia/mini-browser/lib/browser/mini-browser-open-handler";
import { IFileServer,FileTypes } from "../common/request-file-protocol";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";
import { injectable, inject } from "inversify";

@injectable()
export class AnalysisService {
    constructor(
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler,
        @inject(IFileServer) private readonly fileServer: IFileServer,
        @inject(WorkspaceStorageServiceFilesystem) private readonly workpaceFilesystemService: WorkspaceStorageServiceFilesystem
    ) { }

    analyze(uri: URI): void {
        const configFilePath = uri.path.toString();
        const configFileContent = this.workpaceFilesystemService.readFileContent(new URI(configFilePath));

        const wfFilePath = uri.path.toString().replace(".wfconfig", ".wf");
        const wfFileContent = this.workpaceFilesystemService.readFileContent(new URI(wfFilePath));


        Promise.all([configFileContent, wfFileContent]).then((result) => {
            const configContent = result[0];
            const wfContent = result[1];
            const content = {
                'graph': JSON.parse(wfContent),
                'config': configContent
            };
            const xhttp = new XMLHttpRequest();
            xhttp.open("POST", "http://localhost:9090/services/backend/wfanalysis", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.setRequestHeader("Accept", "application/json");
            xhttp.send(JSON.stringify(content));

            xhttp.onreadystatechange = async (e) => {
                const htmlFile = await this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML);
                const jsonFile = escape(xhttp.responseText);
                const urlWithQuery = htmlFile + "?json=" + jsonFile;
                return await this.openHandler.open(undefined, { name: "Workflow Analysis", startPage: urlWithQuery, toolbar: 'hide' });
            }

        });
    }
}