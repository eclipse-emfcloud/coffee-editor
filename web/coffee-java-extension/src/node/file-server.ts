import { IFileServer, IFileClient, TypeNotFound } from "../common/request-file-protocol";
import * as path from 'path'
import { injectable } from "inversify";
export namespace FileTypes {
    export const WORKFLOW_ANALYSIS_HTML = "wfanalysis"
}

@injectable()
export class WorkflowFileServer implements IFileServer {
    protected client?: IFileClient;

    requestFile(type: string): Promise<string> {
        if (type == FileTypes.WORKFLOW_ANALYSIS_HTML) {
            let fileName = path.resolve(__dirname, "../../wf-analyzer-web-app/index.html")
            return Promise.resolve("file://" + fileName)
        }
        return Promise.resolve(TypeNotFound)
    }
    dispose(): void {
        //no-op
    }
    setClient(client?: IFileClient): void {
        this.client = client
    }
}