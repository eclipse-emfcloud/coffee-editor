import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, Command, SelectionService, CommandService, DisposableCollection } from "@theia/core/lib/common";
import { injectable, inject } from "inversify";
import { Workspace } from "@theia/languages/lib/common";
import { EditorManager } from "@theia/editor/lib/browser";
import { UriAwareCommandHandler, UriCommandHandler } from "@theia/core/lib/common/uri-command-handler"
import URI from "@theia/core/lib/common/uri";
import { OpenerService, PreferenceService, CommonMenus } from "@theia/core/lib/browser";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";
import { FileSystem } from "@theia/filesystem/lib/common";

export const CODEGEN_COMMAND: Command = {
    id: "workflow.generate.code.command",
    label: "Generate Workflow code"
}

export const AUTO_CODEGEN_COMMAND: Command = {
    id: "workflow.autogenerate.code.command",
    label: "Auto-Generate Code"
}

@injectable()
export class WorkflowCommandContribution implements CommandContribution, MenuContribution {
    public static readonly AUTO_CODEGEN_PREFERENCE: string = "editor.autoGenerateCode";

    protected readonly toDispose = new DisposableCollection();

    constructor(
        @inject(Workspace)
        protected readonly workspace: Workspace,
        @inject(EditorManager)
        protected readonly editorManager: EditorManager,
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(OpenerService) protected readonly openHandler: OpenerService,
        @inject(WorkspaceStorageServiceFilesystem) protected readonly wsStorage: WorkspaceStorageServiceFilesystem,
        @inject(CommandService) protected readonly commandService: CommandService,
        @inject(FileSystem) protected readonly fileSystem: FileSystem,
        @inject(PreferenceService) protected readonly preferenceService: PreferenceService
    ) {
        const event = this.workspace.onDidSaveTextDocument
        if (event) {
            this.toDispose.push(event(textDocument => {
                const fileUri = new URI(textDocument.uri)
                if (isWorkflowFile(fileUri)) {
                    console.log("Saved " + fileUri.path.base + ", autogenerate is set to: " + this.isAutoGenerateOn());
                    if (this.isAutoGenerateOn()) {
                        console.log("Generate code for " + fileUri);
                        this.commandService.executeCommand(CODEGEN_COMMAND.id, fileUri);
                    }
                }
            }))
        }
    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...['navigator-context-menu'], '0_addition'], {
            commandId: CODEGEN_COMMAND.id
        });
        menus.registerMenuAction([...CommonMenus.EDIT, '4_autogenerate'], {
            commandId: AUTO_CODEGEN_COMMAND.id,
            order: '6'
        });
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(AUTO_CODEGEN_COMMAND, {
            isToggled: () => this.isAutoGenerateOn(),
            execute: () => this.toggleAutoGenerate()
        });
        registry.registerCommand(CODEGEN_COMMAND, this.newUriAwareCommandHandler({
            execute: async (uri) => {
                const workspaceURI = this.wsStorage.getWorkspaceURI();
                if (!workspaceURI) {
                    return
                }
                const packageName = workspaceURI.path.name
                this.wsStorage.readFileContent(uri)
                    .then(fileContent => {
                        if (fileContent.length > 0) {
                            const xhttp = new XMLHttpRequest();
                            xhttp.open("POST", "http://localhost:9090/services/backend/generate", true);
                            const load = {
                                packageName: packageName,
                                sourceFile: uri.path.base.toString(),
                                content: JSON.parse(fileContent)
                            }
                            xhttp.setRequestHeader("Content-type", "application/json")
                            xhttp.setRequestHeader("Accept", "application/json");
                            xhttp.send(JSON.stringify(load));

                            xhttp.onreadystatechange = (e) => {
                                var fileList = JSON.parse(xhttp.responseText)
                                // clear all parent directories
                                this.deleteDirectories(fileList).then(fileList => {
                                    // (re-)generate code
                                    fileList.forEach((generatedFile: any) => {
                                        this.wsStorage.setWorkspaceFileContent(
                                            generatedFile.fileName,
                                            generatedFile.content,
                                            generatedFile.overwrite)
                                    });
                                });
                            }
                        }
                    });
            },
            isVisible: isWorkflowFile,
            isEnabled: isWorkflowFile
        }));
    }

    protected newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler)
    };

    async deleteDirectories(fileList: any): Promise<any> {
        for (const generatedFile of fileList) {
            const fileUri = this.wsStorage.getWorkspaceFileURI(generatedFile.fileName);
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

    private isAutoGenerateOn(): boolean {
        const autoSave = this.preferenceService.get(WorkflowCommandContribution.AUTO_CODEGEN_PREFERENCE);
        return autoSave === 'on' || autoSave === undefined;
    }

    private async toggleAutoGenerate(): Promise<void> {
        this.preferenceService.set(WorkflowCommandContribution.AUTO_CODEGEN_PREFERENCE, this.isAutoGenerateOn() ? 'off' : 'on');
    }
}

function isWorkflowFile(fileUri: URI): boolean {
    return fileUri.toString().endsWith(".wf");
}