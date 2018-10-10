import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, Command, SelectionService } from "@theia/core/lib/common";
import { injectable, inject } from "inversify";

import { Workspace } from "@theia/languages/lib/browser";
import { EditorManager } from "@theia/editor/lib/browser";

import { UriAwareCommandHandler, UriCommandHandler } from "@theia/core/lib/common/uri-command-handler"
import URI from "@theia/core/lib/common/uri";
import { WorkspaceStorageServiceFilesystem } from "coffee-workspace-filesystem-storage-service/lib/browser/workspace-storage-service-filesystem";
import { CoffeeModel, Workflow } from './CofeeModel';
export const GENERATE_WORKFLOW_COMMAND: Command = {
    id: "workflow.generate.command",
    label: "Generate workflow model"
}
@injectable()
export class WorkflowGenerateCommandContribution implements CommandContribution, MenuContribution {

    constructor(
        @inject(Workspace) protected readonly workspace: Workspace,
        @inject(EditorManager) protected readonly editorManager: EditorManager,
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(WorkspaceStorageServiceFilesystem) private readonly workpaceFilesystemService: WorkspaceStorageServiceFilesystem
    ) { }

    registerMenus(menus: MenuModelRegistry): void {

        menus.registerMenuAction([...['navigator-context-menu'], '0_addition'], {
            commandId: GENERATE_WORKFLOW_COMMAND.id
        });
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(GENERATE_WORKFLOW_COMMAND, this.newUriAwareCommandHandler({
            execute: async (uri: URI) => {
                const jFilePath = uri.path.toString();
                const jFileContent = this.workpaceFilesystemService.readFileContent(new URI(jFilePath));

                const outputFilePath = uri.path.toString().replace(".jc", ".wf");

                jFileContent.then(content => {
                    const structuralModel = JSON.parse(content) as CoffeeModel;
                    const workflow = {
                        'revision': 0,
                        'type': "graph",
                        'id': uri.path.name,
                        'children': []
                    } as Workflow;

                    this.generateActivities(structuralModel, workflow);
                    this.workpaceFilesystemService.setFileContent(new URI(outputFilePath), JSON.stringify(workflow), false);

                });
            },
            isVisible: (uri: URI) => uri.toString().endsWith("jc"),
            isEnabled: (uri: URI) => uri.toString().endsWith("jc")
        }),
        );
    }
    private getParentReference(parent:CoffeeModel):string {
        if(parent.eClass){
            const eClass = parent.eClass;
            return eClass.substr(eClass.lastIndexOf('/')+1);
        }
        return parent.name;
    }
    private generateActivities(rootElement: CoffeeModel, workflow: Workflow): void {
        if (rootElement.activities) {
            rootElement.activities.forEach(a => workflow.children.push(
                this.createTask(a.name,this.getParentReference(rootElement), `task${workflow.children.length}`, workflow.children.length * 100 + 10, 100)
            ));
        }
        if (rootElement.children) {
            rootElement.children.forEach(c => this.generateActivities(c, workflow));
        }
    }

    private createTask(name: string,reference:string, taskId: string, xPosition: number, yPosition: number): Object {
        return {
            name: name,
            expanded: false,
            duration: Math.random() * 50,
            taskType: "automated",
            reference: reference,
            layout: "vbox",
            position: {
                x: xPosition,
                y: yPosition
            },
            type: "node:task",
            id: taskId,
            children: [
                {
                    layout: "hbox",
                    type: "comp:header",
                    id: taskId + "_header",
                    children: [
                        {
                            layout: "stack",
                            position: {
                                x: 0.0,
                                y: 0.0
                            },
                            layoutOptions: {
                                resizeContainer: false,
                                hAlign: "center"
                            },
                            type: "icon",
                            id: taskId + "_icon",
                            children: [
                                {
                                    text: "A",
                                    type: "label:icon",
                                    id: taskId + "_ticon"
                                }
                            ]
                        },
                        {
                            text: name,
                            type: "label:heading",
                            id: taskId + "_classname"
                        }
                    ]
                }
            ]
        };
    }

    protected newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler)

    };
}
