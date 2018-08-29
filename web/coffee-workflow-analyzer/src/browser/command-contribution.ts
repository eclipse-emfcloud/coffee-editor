import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, Command, SelectionService } from "@theia/core/lib/common";
import { injectable, inject } from "inversify";

import { Workspace } from "@theia/languages/lib/common";
import { EditorManager, EDITOR_CONTEXT_MENU } from "@theia/editor/lib/browser";

import { UriAwareCommandHandler, UriCommandHandler } from "@theia/core/lib/common/uri-command-handler"
import URI from "@theia/core/lib/common/uri";
import { MiniBrowserOpenHandler } from "@theia/mini-browser/lib/browser/mini-browser-open-handler";
import { FileTypes } from "coffee-workflow-analyzer-editors/lib/node/file-server";
import { IFileServer } from "coffee-workflow-analyzer-editors/lib/common/request-file-protocol";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";

export const ANALYZE_COMMAND: Command = {
    id: "workflow.analyze.command",
    label: "Analyze workflow model"
}
@injectable()
export class WorkflowCommandContribution implements CommandContribution, MenuContribution {

    constructor(
        @inject(Workspace) protected readonly workspace: Workspace,
        @inject(EditorManager) protected readonly editorManager: EditorManager,
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler,
        @inject(IFileServer) private readonly fileServer: IFileServer,
        @inject(WorkspaceStorageServiceFilesystem) private readonly workpaceFilesystemService: WorkspaceStorageServiceFilesystem
    ) { }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...EDITOR_CONTEXT_MENU, '0_addition'], {
            commandId: ANALYZE_COMMAND.id,
            label: 'Perform Analysis'
        })
    }
    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(ANALYZE_COMMAND, this.newUriAwareCommandHandler({
            execute: async (uri: URI) => {
                const configFilePath = uri.path.toString();
                const configFileContent = this.workpaceFilesystemService.readFileContent(new URI(configFilePath));

                const wfFilePath = uri.path.toString().replace(".wfconfig", ".wf");
                const wfFileContent = this.workpaceFilesystemService.readFileContent(new URI(wfFilePath));


                Promise.all([configFileContent, wfFileContent]).then((result) => {
                    const configContent = result[0];
                    const wfContent = result[1];
                    const content = {
                        'graph': JSON.parse(wfContent),
                        'config': configContent
                    };
                    const xhttp = new XMLHttpRequest();
                    xhttp.open("POST", "http://localhost:9090/services/backend/wfanalysis", true);
                    xhttp.setRequestHeader("Content-type", "application/json");
                    xhttp.setRequestHeader("Accept", "application/json");
                    xhttp.send(JSON.stringify(content));

                    xhttp.onreadystatechange = async (e) => {
                        const htmlFile = await this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML);
                        const jsonFile = escape(xhttp.responseText);
                        const urlWithQuery = htmlFile + "?json=" + jsonFile;
                        return await this.openHandler.open(undefined, { name: "Workflow Analysis", startPage: urlWithQuery, toolbar: 'hide' });
                    }

                });


            },
            isVisible: (uri: URI) => uri.toString().endsWith("wfconfig"),
            isEnabled: (uri: URI) => uri.toString().endsWith("wfconfig")
        }),
        );
    }

    protected newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler)

    };
}
