

import { JsonRpcServer } from '@theia/core/lib/common/messaging';

export const TypeNotFound="!fileTypeNotFound"
export const IFileServer= Symbol('IFileServer');
export const filePath= '/services/filerequest'

export interface IFileServer extends JsonRpcServer<IFileClient> {
    requestFile(type:string): Promise<string>
}

export const IFileClient = Symbol('IFileClient');

export interface IFileClient {

}