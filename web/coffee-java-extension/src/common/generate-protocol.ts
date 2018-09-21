

import { JsonRpcServer } from '@theia/core/lib/common/messaging';

export const CodeGenServer= Symbol('CodeGenServer');
export const CODEGEN_SERVICE_PATH= '/services/codegen';

export interface CodeGenServer extends JsonRpcServer<undefined> {
    generateCode(sourceFile:string, targetFolder:string, packageName:string): Promise<string>
}
