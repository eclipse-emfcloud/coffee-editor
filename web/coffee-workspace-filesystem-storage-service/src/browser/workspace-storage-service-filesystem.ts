import { injectable, inject } from 'inversify';
import { FileSystem } from '@theia/filesystem/lib/common';
import URI from '@theia/core/lib/common/uri';
import { Workspace } from "@theia/languages/lib/common";
import { isString } from "util";

@injectable()
export class WorkspaceStorageServiceFilesystem {

    constructor(
        @inject(FileSystem) protected readonly fileSystem: FileSystem,
        @inject(Workspace) protected readonly workspace: Workspace
    ) {
    }

    async readFileContent(uri: URI): Promise<string> {
        const exists = await this.fileSystem.exists(uri.toString());
        if (exists) {
            return this.fileSystem.resolveContent(uri.toString()).then(({ stat, content }) => content);
        }
        console.warn('File ' + uri.toString + ' does not exist.');
        return '';
    }

    async readWorkspaceFileContent(wsRelativePath: string): Promise<string> {
        const fileUri = this.getWorkspaceFileURI(wsRelativePath);
        if (fileUri) {
            return this.readFileContent(fileUri);
        }
        console.warn('Could not determine content of file ' + wsRelativePath + '.');
        return '';
    }

    async setWorkspaceFileContent(wsRelativePath: string, content: string, overwrite: boolean): Promise<void> {
        const fileUri = this.getWorkspaceFileURI(wsRelativePath);
        if (fileUri) {
            this.setFileContent(fileUri, content, overwrite);
            return;
        }
        console.warn('Could not determine file ' + wsRelativePath + '.');
    }

    async setFileContent(fileUri: URI, content: string, overwrite: boolean): Promise<void> {
        const fileStat = await this.fileSystem.getFileStat(fileUri.toString());
        if (fileStat && overwrite) {
            this.fileSystem.updateContent(fileStat, [{ text: content }]).then(() => Promise.resolve());
            console.log("Update content of file '" + fileUri + "'.");
        } else {
            this.fileSystem.createFile(fileUri.toString(), { content });
            console.log("Create file '" + fileUri + "'.");
        }
    }

    getWorkspaceURI(): URI | undefined {
        const rootPath = this.workspace.rootPath
        if (isString(rootPath)) {
            return new URI(rootPath);
        }
        return undefined;
    }

    private getWorkspaceFileURI(filePath: string): URI | undefined {
        const wsURI = this.getWorkspaceURI();
        if (wsURI) {
            return wsURI.withPath(wsURI.path.join(filePath));
        }
    }
}