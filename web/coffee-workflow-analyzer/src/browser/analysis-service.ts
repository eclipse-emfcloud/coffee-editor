/*!
 * Copyright (C) 2019 EclipseSource and others.
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
 */
import { ILogger, MessageService, Progress } from '@theia/core';
import { ConfirmDialog } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { MiniBrowserOpenHandler } from '@theia/mini-browser/lib/browser/mini-browser-open-handler';
import { inject, injectable } from 'inversify';

import { FileTypes, IFileServer } from '../common/request-file-protocol';
import { WorkflowAnalysisClient, WorkflowAnalysisStatus, WorkflowAnalyzer } from '../common/workflow-analyze-protocol';

@injectable()
export class AnalysisService {

    constructor(
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler,
        @inject(IFileServer) private readonly fileServer: IFileServer,
        @inject(WorkflowAnalyzer) private readonly workflowAnalyzer: WorkflowAnalyzer,
        @inject(MessageService) protected readonly messageService: MessageService,
        @inject(ILogger) private readonly logger: ILogger
    ) { }

    analyze(uri: URI): void {
        this.logger.info('Analyze ' + uri);
        this.messageService.showProgress(
            { text: `Analyzing ${uri.parent.relative(uri)}`, options: { cancelable: false } }
        ).then(progress => this.runAnalysis(uri, progress));
    }

    private async runAnalysis(uri: URI, progress: Progress) {
        const configFilePath = uri.toString();
        const wfFilePath = uri.toString().replace('.wfconfig', '.coffee');
        this.logger.info('[WorkflowAnalyzer] Analyze ' + wfFilePath + ' with ' + configFilePath);
        this.logger.info('[WorkflowAnalyzer] Request Analysis File ' + FileTypes.WORKFLOW_ANALYSIS_HTML);
        try {
            const jsonFile = await this.workflowAnalyzer.analyze(wfFilePath, configFilePath);
            const htmlFile = await this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML);
            progress.report({ message: 'Finished analysis, opening result ...' });
            this.logger.info('[WorkflowAnalyzer] Analysis Result Ready: ' + jsonFile);
            const urlWithQuery = htmlFile + '?json=' + escape(jsonFile);
            this.logger.info('[WorkflowAnalyzer] Open Analysis Result');
            await this.openHandler.open(new URI(undefined), { name: 'Workflow Analysis', startPage: urlWithQuery, toolbar: 'hide' });
        } catch (error) {
            this.messageService.error('The workflow analysis failed', 'Show details')
                .then(result => {
                    if (result === 'Show details') {
                        showErrorDialog(error);
                    }
                });
        } finally {
            progress.cancel();
        }
    }
}

@injectable()
export class WorkflowAnalysisClientImpl implements WorkflowAnalysisClient {
    constructor(@inject(MessageService) protected readonly messageService: MessageService) { }
    reportStatus(status: WorkflowAnalysisStatus): void {
        switch (status.status) {
            case 'ok': this.messageService.info(status.message); break;
            case 'error': this.messageService.error(status.message); break;
        }
    }
}

export function showErrorDialog(error: Error) {
    new ConfirmDialog({
        title: error.name,
        msg: getDetails(error)
    }).open();
}

export function getDetails(error: Error): HTMLElement {
    const pre = document.createElement('pre');
    if (isErrorWithData(error) && typeof error.data === 'string') {
        pre.textContent = error.data;
    } else {
        pre.textContent = error.stack ? error.stack : 'Sorry, no stacktrace is available';
    }
    return pre;
}

export function isErrorWithData(arg: object | undefined): arg is ErrorWithData {
    return arg ? 'data' in arg : false;
}

interface ErrorWithData extends Error {
    data: string;
}
