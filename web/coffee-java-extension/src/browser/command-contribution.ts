import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, Command, SelectionService } from "@theia/core/lib/common";
import { injectable, inject } from "inversify";
import { Workspace } from "@theia/languages/lib/common";
import { EditorManager } from "@theia/editor/lib/browser";
import { UriAwareCommandHandler, UriCommandHandler } from "@theia/core/lib/common/uri-command-handler"
import URI from "@theia/core/lib/common/uri";
import { OpenerService } from "@theia/core/lib/browser";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";

export const CODEGEN_COMMAND: Command = {
    id: "workflow.generate.code.command",
    label: "Generate Workflow code"
}
@injectable()
export class WorkflowCommandContribution implements CommandContribution, MenuContribution {


    constructor(
        @inject(Workspace)
        protected readonly workspace: Workspace,
        @inject(EditorManager)
        protected readonly editorManager: EditorManager,
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(OpenerService) protected readonly openHandler: OpenerService,
        @inject(WorkspaceStorageServiceFilesystem) protected readonly wsStorage: WorkspaceStorageServiceFilesystem
    ) { }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...['navigator-context-menu'], '0_addition'], {
            commandId: CODEGEN_COMMAND.id
        });
    }

    registerCommands(registry: CommandRegistry): void {
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
                                fileList.forEach((generatedFile:any) => {
                                    this.wsStorage.setWorkspaceFileContent(
                                        generatedFile.fileName,
                                        generatedFile.content,
                                        generatedFile.overwrite)
                                })
                            }
                        }
                    });
            },
            isVisible: (uri) => uri.toString().endsWith("wf"),
            isEnabled: (uri) => uri.toString().endsWith("wf")

        }),

        );
    }

    protected newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler)
    };
}
