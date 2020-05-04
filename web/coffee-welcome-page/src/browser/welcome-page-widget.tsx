/********************************************************************************
 * Copyright (C) 2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/

import * as React from 'react';
import URI from '@theia/core/lib/common/uri';
import { injectable, inject, postConstruct } from 'inversify';
import { ReactWidget } from '@theia/core/lib/browser/widgets/react-widget';
import { CommandRegistry } from '@theia/core/lib/common';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { OpenerService, open, ApplicationShell, SelectableTreeNode } from '@theia/core/lib/browser';
import { ApplicationInfo, ApplicationServer } from '@theia/core/lib/common/application-protocol';
import { EXPLORER_VIEW_CONTAINER_ID, FileNavigatorWidget, FILE_NAVIGATOR_ID } from '@theia/navigator/lib/browser';
import { ANALYZE_COMMAND } from 'coffee-workflow-analyzer/lib/browser/command-contribution';
import { CODEGEN_COMMAND } from 'coffee-java-extension/lib/browser/command-contribution';

@injectable()
export class WelcomePageWidget extends ReactWidget {

    static readonly ID = 'getting.started.widget';
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

    @inject(ApplicationShell) protected readonly shell: ApplicationShell;

    @postConstruct()
    protected async init(): Promise<void> {
        this.id = WelcomePageWidget.ID;
        this.title.label = WelcomePageWidget.LABEL;
        this.title.caption = WelcomePageWidget.LABEL;
        this.title.closable = true;
        this.title.iconClass = 'fa fa-info';

        this.applicationInfo = await this.appServer.getApplicationInfo();
        this.update();
    }

    protected render(): React.ReactNode {
        return <div className='gs-container'>
            {this.renderHeader()}
            <hr className='gs-hr' />
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderFeatureSection('Diagram Editor', 'fa fa-project-diagram', (
                    <p>The example diagram editor allows specifying the behavior of a coffee machine using a flow chart like notation.
                    The diagram editor is based on <a href='https://www.eclipse.org/glsp/' target='_blank'>the graphical language server platform
                    (Eclipse GLSP)</a>. Double click the file "superbrewer3000.coffeenotation"&quot;" in the coffee editor or click
                        the header try out the diagram editor!</p>), this.openDiagram)}
                </div>
            </div>
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderFeatureSection('Form/Tree Editor', 'fab fa-wpforms', (
                        <p>This editor allows to edit elements in a form-based view along with a tree showing the
                        hierarchy of the model instances. This allows to efficiently browse the model and enter
                        data. The form editor is based on <a href='https://jsonforms.io' target='_blank'>JSON Forms</a>. Double
                         click the file "superbrewer3000.coffee" in the coffee editor or click the header to try
                          out the editor!</p>), this.openTreeEditor)}
                </div>
            </div>
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderFeatureSection('Textual DSL', 'fas fa-indent', (
                        <p>The textual DSL editor allows you to specify model constraints and supports syntax highlighting
                            and auto completion. It is based on <a href='https://www.eclipse.org/Xtext/' target='_blank'>Xtext</a>. Double click
                            the file "superbrewer3000.wfconfig" in the coffee editor or click the header to try out the textual DSl!'</p>)
                            , this.openTextualDSL)}
                </div>
            </div>
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderFeatureSection('Model Analysis', 'fas fa-chart-pie', (
                    <p>Based on the constraints described in the textual DSL, the coffee editor provides an example model analysis.
                        The result is visualized as a "sun burst" chart. The analysis is an external component written in Kotlin, the
                        chart is based on D3. Select the file "superbrewer3000.wfconfig" in the coffee editor, press F1, type "Analyze
                        workflow model" and hit enter to see the model analysis in action. Alternatively do a right click in the open
                        textual DSL editor or click the header above.</p>), this.runModelAnalysis)}
                </div>
            </div>
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderFeatureSection('Code Generator', 'fas fa-cogs', (
                    <p>The coffee editor allows generating example code based on the current model. The code generator itself is written
                        using Xtend. Right click the file "superbrewer3000.coffee" in the coffee editor and select "Generate Workflow code".
                        Browse the generated code in the "src" and "src-gen" folder, the coffee editor also provides extensive language support
                        for Java!</p>), this.runCodeGenerator)}
                </div>
            </div>

            <div className='flex-grid'>
                <div className='col'>
                    {this.renderHelp()}
                </div>
            </div>
            <div className='flex-grid'>
                <div className='col'>
                    {this.renderVersion()}
                </div>
            </div>
        </div>;
    }

    protected renderHeader(): React.ReactNode {
        return <div className='gs-header'>
            <h1>Coffee Editor <span className='gs-sub-header'>Getting Started</span></h1>
            <p>The "coffee editor" is a comprehensive example of a web-based modeling tool based on <a href='https://www.eclipse.org/emfcloud/' target='_blank'>EMF.cloud
            </a> and Eclipse Theia.
            Please see the sections below to get an overview of the available features and use the links to directly see them in action.
            Alternatively, <a href='#' onClick={() => this.openFileExplorer()}>open the file explorer</a> to
            the left and browse the example workspace. See the "Help and more information" section below for further pointers.</p>
        </div>;
    }

    protected renderFeatureSection(title: string, icon: string, description: JSX.Element, opener: () => void): React.ReactNode {
        return <div className='gs-section'>
            <a href='#' onClick={opener}>   <h3 className='gs-section-header'>
                <i className={icon}></i>
                {title}
            <span style={{marginLeft: '5px'}}>
                <i className='fas fa-external-link-alt' />
            </span>
            </h3></a>
            {description}
            <div className='gs-action-container'>

            </div>
        </div>;
    }

    protected renderHelp(): React.ReactNode {
        return <div className='gs-section'>
            <h3 className='gs-section-header'>
                <i className='fa fa-question-circle'></i>
                Help and more information
            </h3>
            <div className='gs-action-container'>
                <a href='https://www.eclipse.org/emfcloud/contact/' target='_blank'>Ask a question</a>
            </div>
            <div className='gs-action-container'>
                <a href='https://github.com/eclipsesource/coffee-editor/issues' target='_blank'>Report an issue</a>
            </div>
            <div className='gs-action-container'>
                <a href='https://github.com/eclipsesource/coffee-editor/' target='_blank'>Browse the source code</a>
            </div>
            <div className='gs-action-container'>
                <a href='https://eclipsesource.com/technology/eclipse-theia/' target='_blank'>Get support for building your own custom tool based on Eclipse Theia</a>
            </div>
        </div>;
    }

    protected renderVersion(): React.ReactNode {
        return <div className='gs-section'>
            <div className='gs-action-container'>
                <p className='gs-sub-header gs-version' > {this.applicationInfo ? 'Version ' + this.applicationInfo.version : ''}</p>
            </div>
        </div>;
    }

    protected openDiagram = () => open(this.openerService, new URI(`${this.workspaceService.workspace?.uri}/superbrewer3000.coffeenotation`));
    protected openTreeEditor = () => open(this.openerService, new URI(`${this.workspaceService.workspace?.uri}/superbrewer3000.coffee`));
    protected openTextualDSL = () => open(this.openerService, new URI(`${this.workspaceService.workspace?.uri}/superbrewer3000.wfconfig`));
    protected openFileExplorer = () => this.shell.revealWidget(EXPLORER_VIEW_CONTAINER_ID);
    protected runModelAnalysis = () => {
        open(this.openerService, new URI(`${this.workspaceService.workspace?.uri}/superbrewer3000.wfconfig`))
            .then(() => { this.commandRegistry.executeCommand(ANALYZE_COMMAND.id); });

    }
    protected runCodeGenerator = () => {
        this.shell.revealWidget(FILE_NAVIGATOR_ID).then(widget => {
            const { model } = widget as FileNavigatorWidget;
            const uri = new URI(`${this.workspaceService.workspace?.uri}/superbrewer3000.coffee`);
            model.revealFile(uri).then(node => {
                if (SelectableTreeNode.is(node)) {
                    model.selectNode(node);
                }
                setTimeout(() => this.commandRegistry.executeCommand(CODEGEN_COMMAND.id, uri), 1000);
            });
        });
    }
}
