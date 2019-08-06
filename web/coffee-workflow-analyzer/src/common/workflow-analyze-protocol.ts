import { JsonRpcServer } from "@theia/core/lib/common/messaging";

export const WorkflowAnalyzer = Symbol('WorkflowAnalyzer');
export const workflowServicePath = '/services/workflowAnalyzer';

export interface WorkflowAnalyzer extends JsonRpcServer<WorkflowAnalysisClient> {
    analyze(wfFileUri: string, wfConfigFileUri: string): Promise<string>
}

export interface WorkflowAnalysisStatus {
    status: 'ok' | 'error';
    message: string;
}

export interface WorkflowAnalysisClient {
    reportStatus(status: WorkflowAnalysisStatus): void;
}
