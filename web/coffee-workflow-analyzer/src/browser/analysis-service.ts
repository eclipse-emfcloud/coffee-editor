import URI from "@theia/core/lib/common/uri";
import { MiniBrowserOpenHandler } from "@theia/mini-browser/lib/browser/mini-browser-open-handler";
import { inject, injectable } from "inversify";
import { FileTypes, IFileServer } from "../common/request-file-protocol";
import { WorkflowAnalyzer } from "../common/workflow-analyze-protocol";

@injectable()
export class AnalysisService {
    constructor(
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler,
        @inject(IFileServer) private readonly fileServer: IFileServer,
        @inject(WorkflowAnalyzer) private readonly workflowAnalyzer: WorkflowAnalyzer
    ) { }

    analyze(uri: URI): void {
        console.log('[WorkflowAnalyzer] Analyze ' + uri)
        const configFilePath = uri.toString();

        const wfFilePath = uri.toString().replace(".wfconfig", ".wf");
        console.log('[WorkflowAnalyzer] Analyze' + wfFilePath + ' with ' + configFilePath)
        const analysisPromise = this.workflowAnalyzer.analyze(wfFilePath, configFilePath);
        console.log('[WorkflowAnalyzer] Request Analysis File ' + FileTypes.WORKFLOW_ANALYSIS_HTML)
        const htmlFilePromise = this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML);
        Promise.all([analysisPromise, htmlFilePromise]).then(async ([jsonFile, htmlFile]) => {
            console.log('[WorkflowAnalyzer] Analysis Result Ready: ' + jsonFile)
            const urlWithQuery = htmlFile + "?json=" + escape(jsonFile);
            console.log('[WorkflowAnalyzer] Open Analysis Result')
            return await this.openHandler.open(undefined, { name: "Workflow Analysis", startPage: urlWithQuery, toolbar: 'hide' });
        });
    }
}