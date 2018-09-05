import URI from "@theia/core/lib/common/uri";
import { FileSystem } from "@theia/filesystem/lib/common";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";
import { injectable, inject } from "inversify";
import { Workspace } from "@theia/languages/lib/common";
import { DisposableCollection } from "@theia/core";
import { PreferenceService } from "@theia/core/lib/browser";

@injectable()
export class GenerateCodeService {
    private readonly AUTO_CODEGEN_PREFERENCE: string = "editor.autoGenerateCode";
    private readonly toDispose = new DisposableCollection();
    constructor(
        @inject(Workspace) private readonly workspace: Workspace,
        @inject(PreferenceService) private readonly preferenceService: PreferenceService,
        @inject(FileSystem) private readonly fileSystem: FileSystem,
        @inject(WorkspaceStorageServiceFilesystem) private readonly wsStorage: WorkspaceStorageServiceFilesystem,
    ){
        const event = this.workspace.onDidSaveTextDocument
        if (event) {
            this.toDispose.push(event(textDocument => {
                const fileUri = new URI(textDocument.uri)
                if (this.isWorkflowFile(fileUri)) {
                    console.log("Saved " + fileUri.path.base + ", autogenerate is set to: " + this.isAutoGenerateOn());
                    if (this.isAutoGenerateOn()) {
                        console.log("Generate code for " + fileUri);
                        this.generateCode(fileUri);
                    }
                }
            }))
        }
    }
    public generateCode(uri: URI): void {
        const workspaceURI = this.wsStorage.getWorkspaceURI();
        if (!workspaceURI) {
            return
        }
        const generationDirectory = uri.parent;
        const packageName = generationDirectory.path.name;
        this.wsStorage.readFileContent(uri)
            .then(async fileContent => {
                if (fileContent.length > 0) {
                    const xhttp = new XMLHttpRequest();
                    xhttp.open("POST", "http://localhost:9090/services/backend/generate", true);
                    const load = {
                        packageName: packageName,
                        sourceFile: uri.path.base.toString(),
                        content: JSON.parse(fileContent)
                    }
                    xhttp.setRequestHeader("Content-type", "application/json");
                    xhttp.setRequestHeader("Accept", "application/json");
                    xhttp.send(JSON.stringify(load));

                    xhttp.onreadystatechange = (e) => {
                        var fileList = JSON.parse(xhttp.responseText)
                        // clear all parent directories
                        this.deleteDirectories(fileList, generationDirectory).then(fileList => {
                            // (re-)generate code
                            fileList.forEach((generatedFile: any) => {
                                this.wsStorage.setFileContent(
                                    generationDirectory.resolve(generatedFile.fileName),
                                    generatedFile.content,
                                    generatedFile.overwrite)
                            });
                        });
                    }
                }
            });
    }

    private async deleteDirectories(fileList: any, generationDirectory: URI): Promise<any> {
        for (const generatedFile of fileList) {
            const fileUri = generationDirectory.resolve(generatedFile.fileName);
            if (fileUri) {
                const exists = await this.fileSystem.exists(fileUri.parent.toString());
                if (exists) {
                    console.log("Delete " + fileUri.parent.toString() + "...")
                    await this.fileSystem.delete(fileUri.parent.toString());
                    console.log("Done deleting " + fileUri.parent.toString() + ".")
                }
            }
        }
        return fileList;
    }

    dispose(): void {
        this.toDispose.dispose();
    }

    public isAutoGenerateOn(): boolean {
        const autoSave = this.preferenceService.get(this.AUTO_CODEGEN_PREFERENCE);
        return autoSave === 'on' || autoSave === undefined;
    }

    public async toggleAutoGenerate(): Promise<void> {
        this.preferenceService.set(this.AUTO_CODEGEN_PREFERENCE, this.isAutoGenerateOn() ? 'off' : 'on');
    }

    public isWorkflowFile(fileUri: URI): boolean {
        return fileUri.toString().endsWith(".wf");
    }
}



