import { DisposableCollection } from "@theia/core";
import { PreferenceService } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { Workspace } from "@theia/languages/lib/browser";
import { inject, injectable } from "inversify";
import { CodeGenServer } from "../common/generate-protocol";

@injectable()
export class GenerateCodeService {
    private readonly AUTO_CODEGEN_PREFERENCE: string = "editor.autoGenerateCode";
    private readonly toDispose = new DisposableCollection();
    constructor(
        @inject(Workspace) private readonly workspace: Workspace,
        @inject(PreferenceService) private readonly preferenceService: PreferenceService,
        @inject(CodeGenServer) private readonly codeGenServer: CodeGenServer
    ) {
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
        const generationDirectory = uri.parent;
        const packageName = generationDirectory.path.name;
        const sourceFile = uri.toString();
        const target = generationDirectory.toString();
        this.codeGenServer.generateCode(sourceFile, target, packageName).then(r => console.log(r));
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



