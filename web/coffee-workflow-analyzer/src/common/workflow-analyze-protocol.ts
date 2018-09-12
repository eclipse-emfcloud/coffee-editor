import { JsonRpcServer } from '@theia/core/lib/common/messaging';

export const WorkflowAnalyzer = Symbol('WorkflowAnalyzer');
export const workflowServicePath = '/services/workflowAnalyzer';

export interface WorkflowAnalyzer extends JsonRpcServer<undefined> {
    analyze(wfFileUri: string, wfConfigFileUri: string): Promise<string>
}