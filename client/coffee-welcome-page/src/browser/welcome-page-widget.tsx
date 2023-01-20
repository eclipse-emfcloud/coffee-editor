/*
 * Copyright (c) 2020-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ApplicationShell, codicon, open, OpenerService, SelectableTreeNode, Widget } from '@theia/core/lib/browser';
import { ReactWidget } from '@theia/core/lib/browser/widgets/react-widget';
import { CommandRegistry } from '@theia/core/lib/common';
import { ApplicationInfo, ApplicationServer } from '@theia/core/lib/common/application-protocol';
import URI from '@theia/core/lib/common/uri';
import { DebugConfigurationManager } from '@theia/debug/lib/browser/debug-configuration-manager';
import { DebugCommands } from '@theia/debug/lib/browser/debug-frontend-application-contribution';
import { GitDiffCommands } from '@theia/git/lib/browser/diff/git-diff-contribution';
import { EXPLORER_VIEW_CONTAINER_ID, FileNavigatorWidget, FILE_NAVIGATOR_ID } from '@theia/navigator/lib/browser';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { CODEGEN_COMMAND as CODEGEN_CPP_COMMAND } from 'coffee-cpp-extension/lib/browser/command-contribution';
import { CODEGEN_COMMAND as CODEGEN_JAVA_COMMAND } from 'coffee-java-extension/lib/browser/command-contribution';
import { ANALYZE_COMMAND } from 'coffee-workflow-analyzer/lib/browser/command-contribution';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from '@theia/core/shared/react';

/* eslint-disable no-invalid-this */

@injectable()
export class WelcomePageWidget extends ReactWidget {
    static readonly ID = 'welcome.page.widget';
    static readonly LABEL = 'Getting Started';

    protected applicationInfo: ApplicationInfo | undefined;

    @inject(ApplicationServer)
    protected readonly appServer: ApplicationServer;

    @inject(CommandRegistry)
    protected readonly commandRegistry: CommandRegistry;

    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService;

    @inject(OpenerService)
    protected readonly openerService: OpenerService;

    @inject(DebugConfigurationManager)
    protected readonly debugConfigurationManager: DebugConfigurationManager;

    @inject(ApplicationShell) protected readonly shell: ApplicationShell;

    @postConstruct()
    protected async init(): Promise<void> {
        this.id = WelcomePageWidget.ID;
        this.title.label = WelcomePageWidget.LABEL;
        this.title.caption = WelcomePageWidget.LABEL;
        this.title.closable = true;
        this.title.iconClass = codicon('info');

        this.applicationInfo = await this.appServer.getApplicationInfo();
        this.update();
    }

    protected render(): React.ReactNode {
        return (
            <div className='gs-container'>
                {this.renderHeader()}
                <hr className='gs-hr' />
                <div className='flex-grid'>
                    <div className='col'>{this.renderHelp()}</div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Diagram Editor',
                            'codicon codicon-type-hierarchy-sub',
                            <p>
                                The example diagram editor allows specifying the behavior of a coffee machine using a flow chart like
                                notation. The diagram editor is based on{' '}
                                <a href='https://www.eclipse.org/glsp/' target='_blank' rel='noreferrer'>
                                    the graphical language server platform (Eclipse GLSP)
                                </a>
                                . Open the file &quot;superbrewer3000.notation&quot; via the file explorer or click the header to try out
                                the diagram editor!
                            </p>,
                            this.openDiagram,
                            'Open "superbrewer3000.notation" with diagram editor'
                        )}
                    </div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Form/Tree Editor',
                            'codicon codicon-preview',
                            <p>
                                This editor allows to edit elements in a form-based view along with a tree showing the hierarchy of the
                                model instances. This allows to efficiently browse the model and enter data. The form editor is based on
                                &nbsp;
                                <a href='https://jsonforms.io' target='_blank' rel='noreferrer'>
                                    JSON Forms
                                </a>
                                . Open the file &quot;superbrewer3000.coffee&quot; via the file explorer or click the header to try out the
                                editor!
                            </p>,
                            this.openTreeEditor,
                            'Open "superbrewer3000.coffee" with Tree editor'
                        )}
                    </div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Textual DSL',
                            'codicon codicon-symbol-string',
                            <p>
                                The textual DSL editor allows you to specify model constraints and supports syntax highlighting and auto
                                completion. It is based on{' '}
                                <a href='https://www.eclipse.org/Xtext/' target='_blank' rel='noreferrer'>
                                    Xtext
                                </a>
                                . Open the file &quot;superbrewer3000.wfconfig&quot; via the file explorer or click the header to try out
                                the textual DSL!
                            </p>,
                            this.openTextualDSL,
                            'Open "superbrewer3000.wfconfig" with the Workflow DSL editor'
                        )}
                    </div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Model Analysis',
                            'codicon codicon-pie-chart',
                            <p>
                                Based on the constraints described in the textual DSL, the coffee editor provides an example model analysis.
                                The result is visualized as a &quot;sun burst&quot; chart. The analysis is an external component written in
                                Kotlin, the chart is based on D3. Select the file &quot;superbrewer3000.wfconfig&quot; in the file explorer,
                                press F1, type &quot;Analyze workflow model&quot; and hit enter to see the model analysis in action.
                                Alternatively, do a right click in the open textual DSL editor and select &quot;Perform Analysis&quot; or
                                click the header above.
                            </p>,
                            this.runModelAnalysis,
                            'Trigger Workflow Model Analysis'
                        )}
                    </div>
                </div>
                <details>
                    <summary>{this.renderDetailSummary('Java support', 'java-icon medium-purple gs-language-icon')}</summary>
                    <div>
                        As the Java LSP can take some time to start and there is no queue for tasks, we recommend that you wait until the
                        Java LSP is completely started. You can find the status in the status bar on the left hand side.
                    </div>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'Java Code Generator',
                                'codicon codicon-github-action',
                                <p>
                                    The coffee editor allows generating example code based on the current model. The code generator itself
                                    is written using Xtend. Right click the file &quot;superbrewer3000.coffee&quot; in the file explorer and
                                    select &quot;Generate Workflow code&quot;. Browse the generated code in the &quot;src&quot; and
                                    &quot;src-gen&quot; folder, the coffee editor also provides extensive language support for Java!
                                </p>,
                                this.runJavaCodeGenerator,
                                'Run Java code generation for "superbrewer3000.coffee"'
                            )}
                        </div>
                    </div>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'Java Code Editing',
                                'codicon codicon-file-code',
                                <p>
                                    The coffee editor provides full-fleged Java tooling including syntax highlighting and auto completion.
                                    This is based on the Monaco code editor and a Java language server connected via LSP. Make sure you have
                                    generated the code first (see above). Then, open any Java file in the src folder (or click above) and
                                    start modifying the code, e.g. by adding &quot;sysout&quot; statements.
                                </p>,
                                this.openJavaCode,
                                'Open "SuperBrewer3000Runner.java" in Java editor'
                            )}
                        </div>
                    </div>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'Java Debugging',
                                'codicon codicon-bug',
                                <p>
                                    The coffee editor allows executing and debugging Java code by integrating the debug adapter protocol
                                    (DAP). Make sure you have generated the code (see above) and set a break point in any Java file by
                                    double clicking on the left border of the code editor. Press &quot;F5&quot;, start the launch
                                    configuration &quot;Debug SuperBrewer Java&quot; or click above to start debugging the example. This
                                    will automatically open the integrated debug view and show all outputs of the example code in the
                                    console!
                                </p>,
                                this.startDebugJava,
                                'Debug "SuperBrewer3000Runner.java"'
                            )}
                        </div>
                    </div>
                </details>
                <details>
                    <summary>{this.renderDetailSummary('C++ support', 'cpp-icon medium-blue gs-language-icon')}</summary>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'C++ Code Generator',
                                'codicon codicon-github-action',
                                <p>
                                    The coffee editor allows generating example code based on the current model. The code generator itself
                                    is written using Xtend. Right click the file &quot;superbrewer3000.coffee&quot; in the file explorer and
                                    select &quot;Generate C++ Workflow code&quot;. Browse the generated code in the &quot;cpp&quot; folder,
                                    the coffee editor also provides extensive language support for C++!
                                </p>,
                                this.runCppCodeGenerator,
                                'Run C++ code generation for "superbrewer3000.coffee"'
                            )}
                        </div>
                    </div>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'C++ Code Editing',
                                'codicon codicon-file-code',
                                <p>
                                    The coffee editor provides full-fleged C++ tooling including syntax highlighting and auto completion.
                                    This is based on the Monaco code editor and the &quot;clangd&quot; C++ language server connected via
                                    LSP. Make sure you have generated the code first (see above). Then, open any C++ file in the cpp/src
                                    folder (or click above) and start modifying the code, e.g. by adding &quot;std::cout&quot; statements.
                                </p>,
                                this.openCppCode,
                                'Open "SuperBrewer3000Runner.cpp" in C++ editor'
                            )}
                        </div>
                    </div>
                    <div className='flex-grid gs-nested-section'>
                        <div className='col'>
                            {this.renderFeatureSection(
                                'C++ Debugging',
                                'codicon codicon-bug',
                                <p>
                                    The coffee editor allows executing and debugging C++ code by integrating the debug adapter protocol
                                    (DAP). Make sure you have generated the code (see above) and set a break point in any C++ file by double
                                    clicking on the left border of the code editor. Press &quot;F5&quot;, start the launch configuration
                                    &quot;Debug SuperBrewer C++&quot; or click above to start debugging the example. This will automatically
                                    open the integrated debug view and show all outputs of the example code in the console!
                                </p>,
                                this.startDebugCpp,
                                'Debug "SuperBrewer3000Runner.cpp"'
                            )}
                        </div>
                    </div>
                </details>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Model validation',
                            'codicon codicon-warning',
                            <div>
                                <p>
                                    For the model validation the project uses EMF validation via the Model Server and displays the results
                                    in the GLSP diagram editor, as well as the Theia problems view. The editor has live validation enabled,
                                    so the Model Server validates immediately after changes are made to the diagram. Validation results will
                                    be displayed as problem markers on the model and in the problems view. The validation rules are:
                                </p>
                                <ul className='gs-section-listing'>
                                    <li>
                                        <div className='gs-action-container'>
                                            A task name may only contain letters, number, - and whitespaces
                                        </div>
                                    </li>
                                    <li>
                                        <div className='gs-action-container'>
                                            Every task should have at most 1 incoming and 1 outgoing flow
                                        </div>
                                    </li>
                                    <li>
                                        <div className='gs-action-container'>
                                            A decision node should have exactly 1 incoming and 2 outgoing flows
                                        </div>
                                    </li>
                                    <li>
                                        <div className='gs-action-container'>
                                            A merge node should have exactly 2 incoming and 1 outgoing flows
                                        </div>
                                    </li>
                                    <li>
                                        <div className='gs-action-container'>A task should be used (have at least one connection)</div>
                                    </li>
                                    <li>
                                        <div className='gs-action-container'>A workflow should not contain a cycle</div>
                                    </li>
                                </ul>
                                <p>
                                    Open the file &quot;superbrewer3000.notation&quot; via the file explorer or click the header to open the
                                    diagram editor and change the model. Try out the Model Validation by breaking any of the above mentioned
                                    validation rules!
                                </p>
                            </div>,
                            this.openDiagram,
                            'Open "superbrewer3000.notation" with diagram editor'
                        )}
                    </div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>
                        {this.renderFeatureSection(
                            'Model comparison',
                            'codicon codicon-files',
                            <p>
                                The model comparison is done via the{' '}
                                <a
                                    href='https://github.com/eclipsesource/model-compare/tree/master/client/comparison-extension'
                                    target='_blank'
                                    rel='noreferrer'
                                >
                                    comparison-extension
                                </a>
                                , which uses EMF Compare. The coffee-editor adds to the comparison extension and provides a git integration.
                                With this, the current file can be compared to its HEAD file. This can be achieved by right-clicking on the
                                &quot;.notation&quot; or &quot;.coffee&quot; file and selecting &quot;Compare with HEAD...&quot; , with the
                                latter opening the tree comparison and the right click on the &quot;.notation&quot; file the graphical
                                comparison. Note, that this requires the current workspace to be a Git repository with at least one commit
                                that contains the selected file. Clicking the header opens the comparison for
                                &quot;superbrewer3000.notation&quot; with HEAD.
                            </p>,
                            this.compareDiagramToHead,
                            'Compare "superbrewer3000.notation" with HEAD'
                        )}
                    </div>
                </div>
                <div className='flex-grid'>
                    <div className='col'>{this.renderVersion()}</div>
                </div>
            </div>
        );
    }

    protected renderHeader(): React.ReactNode {
        return (
            <div className='gs-header'>
                <h1>
                    Coffee Editor <span className='gs-sub-header'>Getting Started</span>
                </h1>
                <p>
                    The &quot;coffee editor&quot; is a comprehensive example of a web-based modeling tool based on{' '}
                    <a href='https://www.eclipse.org/emfcloud/' target='_blank' rel='noreferrer'>
                        EMF.cloud
                    </a>{' '}
                    and Eclipse Theia. Please see the sections below to get an overview of the available features and use the links to
                    directly see them in action. Alternatively,{' '}
                    <a onClick={() => this.openFileExplorer()} rel='noreferrer'>
                        open the file explorer
                    </a>{' '}
                    to the left and browse the example workspace. See the &quot;Help and more information&quot; section below for further
                    pointers.
                </p>
            </div>
        );
    }

    protected renderDetailSummary(title: string, icon: string): React.ReactNode {
        return (
            <div className='gs-section gs-detail-summary'>
                {' '}
                <h3 className='gs-section-header'>
                    <i className={'gs-section-header-icon ' + icon}></i>
                    {title}
                </h3>
                <div className='gs-action-container'></div>
            </div>
        );
    }

    protected renderFeatureSection(
        title: string,
        icon: string,
        description: React.ReactNode,
        opener: () => void,
        actionTitle?: string
    ): React.ReactNode {
        return (
            <div className='gs-section' title={actionTitle}>
                <a onClick={opener}>
                    {' '}
                    <h3 className='gs-section-header'>
                        <i className={'gs-section-header-icon ' + icon}></i>
                        {title}
                        <span className='gs-link-icon'>
                            <i className='codicon codicon-link-external' />
                        </span>
                    </h3>
                </a>
                {description}
                <div className='gs-action-container'></div>
            </div>
        );
    }

    protected renderHelp(): React.ReactNode {
        return (
            <div className='gs-section'>
                <h3 className='gs-section-header'>
                    <i className='gs-section-header-icon codicon codicon-question'></i>
                    Help and more information
                </h3>
                <ul className='gs-section-listing'>
                    <li>
                        <div className='gs-action-container'>
                            <a href='https://www.eclipse.org/emfcloud/contact/' target='_blank' rel='noreferrer'>
                                Visit the EMF.cloud Website
                            </a>
                        </div>
                    </li>
                    <li>
                        <div className='gs-action-container'>
                            <a href='https://github.com/eclipse-emfcloud/emfcloud/discussions' target='_blank' rel='noreferrer'>
                                Ask a question in the EMF.cloud discussions
                            </a>
                        </div>
                    </li>
                    <li>
                        <div className='gs-action-container'>
                            <a href='https://github.com/eclipsesource/coffee-editor/issues' target='_blank' rel='noreferrer'>
                                Report an issue
                            </a>
                        </div>
                    </li>
                    <li>
                        <div className='gs-action-container'>
                            <a href='https://github.com/eclipsesource/coffee-editor/' target='_blank' rel='noreferrer'>
                                Github project with code and more information
                            </a>
                        </div>
                    </li>
                    <li>
                        <div className='gs-action-container'>
                            <a href='https://eclipsesource.com/technology/eclipse-theia/' target='_blank' rel='noreferrer'>
                                Get support for building your own custom tool based on Eclipse Theia
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
        );
    }

    protected renderVersion(): React.ReactNode {
        return (
            <div className='gs-section'>
                <div className='gs-action-container'>
                    <p className='gs-sub-header gs-version'> {this.applicationInfo ? 'Version ' + this.applicationInfo.version : ''}</p>
                </div>
            </div>
        );
    }

    private getSuperBrewer3000FileURI(extension: string): URI {
        return new URI(`${this.workspaceService.workspace?.resource}/superbrewer3000.${extension}`);
    }

    protected openDiagram = (): Promise<object | undefined> => open(this.openerService, this.getSuperBrewer3000FileURI('notation'));
    protected openTreeEditor = (): Promise<object | undefined> => open(this.openerService, this.getSuperBrewer3000FileURI('coffee'));
    protected openTextualDSL = (): Promise<object | undefined> => open(this.openerService, this.getSuperBrewer3000FileURI('wfconfig'));
    protected openFileExplorer = (): Promise<Widget | undefined> => this.shell.revealWidget(EXPLORER_VIEW_CONTAINER_ID);
    protected runModelAnalysis = (): void => {
        open(this.openerService, this.getSuperBrewer3000FileURI('wfconfig')).then(() => {
            this.commandRegistry.executeCommand(ANALYZE_COMMAND.id);
        });
    };
    protected compareDiagramToHead = (): void => {
        this.commandRegistry.executeCommand(GitDiffCommands.OPEN_FILE_DIFF.id, this.getSuperBrewer3000FileURI('notation'));
    };

    protected runJavaCodeGenerator = (): void => {
        this.shell.revealWidget(FILE_NAVIGATOR_ID).then(widget => {
            const { model } = widget as FileNavigatorWidget;
            const uri = this.getSuperBrewer3000FileURI('coffee');
            model.revealFile(uri).then(node => {
                if (SelectableTreeNode.is(node)) {
                    model.selectNode(node);
                }
                setTimeout(() => this.commandRegistry.executeCommand(CODEGEN_JAVA_COMMAND.id, uri), 1000);
            });
        });
    };

    protected runCppCodeGenerator = (): void => {
        this.shell.revealWidget(FILE_NAVIGATOR_ID).then(widget => {
            const { model } = widget as FileNavigatorWidget;
            const uri = this.getSuperBrewer3000FileURI('coffee');
            model.revealFile(uri).then(node => {
                if (SelectableTreeNode.is(node)) {
                    model.selectNode(node);
                }
                setTimeout(() => this.commandRegistry.executeCommand(CODEGEN_CPP_COMMAND.id, uri), 1000);
            });
        });
    };

    private getJavaSuperBrewer3000RunnerFileURI(): URI {
        return new URI(`${this.workspaceService.workspace?.resource}/src/SuperBrewer3000/tests/SuperBrewer3000Runner.java`);
    }

    protected openJavaCode = (): void => {
        this.shell.revealWidget(FILE_NAVIGATOR_ID).then(widget => {
            const { model } = widget as FileNavigatorWidget;
            model.revealFile(this.getJavaSuperBrewer3000RunnerFileURI()).then(node => {
                if (SelectableTreeNode.is(node)) {
                    model.openNode(node);
                }
            });
        });
    };

    private getCppSuperBrewer3000RunnerFileURI(): URI {
        return new URI(`${this.workspaceService.workspace?.resource}/cpp/src/SuperBrewer3000Runner.cpp`);
    }

    protected openCppCode = (): void => {
        this.shell.revealWidget(FILE_NAVIGATOR_ID).then(widget => {
            const { model } = widget as FileNavigatorWidget;
            model.revealFile(this.getCppSuperBrewer3000RunnerFileURI()).then(node => {
                if (SelectableTreeNode.is(node)) {
                    model.openNode(node);
                }
            });
        });
    };

    protected startDebugJava = (): Promise<unknown> => this.startDebug('Debug SuperBrewer Java');
    protected startDebugCpp = (): Promise<unknown> => this.startDebug('Debug SuperBrewer C++');

    private startDebug = (configName: string): Promise<unknown> => {
        const config = Array.from(this.debugConfigurationManager.all).find(c => c.configuration && c.configuration.name === configName);
        return this.commandRegistry.executeCommand(DebugCommands.START.id, config);
    };
}
