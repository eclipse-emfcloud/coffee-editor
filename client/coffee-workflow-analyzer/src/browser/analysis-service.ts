/*
 * Copyright (C) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ILogger, MessageService, Progress } from '@theia/core';
import { codicon, ConfirmDialog } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { MiniBrowserOpenHandler } from '@theia/mini-browser/lib/browser/mini-browser-open-handler';
import { inject, injectable } from 'inversify';

import { FileServer, FileTypes } from '../common/request-file-protocol';
import { WorkflowAnalysisClient, WorkflowAnalysisStatus, WorkflowAnalyzer } from '../common/workflow-analyze-protocol';

@injectable()
export class AnalysisService {
    constructor(
        @inject(MiniBrowserOpenHandler) private readonly openHandler: MiniBrowserOpenHandler,
        @inject(FileServer) private readonly fileServer: FileServer,
        @inject(WorkflowAnalyzer) private readonly workflowAnalyzer: WorkflowAnalyzer,
        @inject(MessageService) protected readonly messageService: MessageService,
        @inject(ILogger) private readonly logger: ILogger
    ) {}

    analyze(uri: URI): void {
        this.logger.info('Analyze ' + uri);
        this.messageService
            .showProgress({ text: `Analyzing ${uri.parent.relative(uri)}`, options: { cancelable: false } })
            .then(progress => this.runAnalysis(uri, progress));
    }

    private async runAnalysis(uri: URI, progress: Progress): Promise<void> {
        const configFilePath = uri.toString();
        const wfFilePath = uri.toString().replace('.wfconfig', '.coffee');
        this.logger.info('[WorkflowAnalyzer] Analyze ' + wfFilePath + ' with ' + configFilePath);
        this.logger.info('[WorkflowAnalyzer] Request Analysis File ' + FileTypes.WORKFLOW_ANALYSIS_HTML);
        try {
            const jsonFile = await this.workflowAnalyzer.analyze(wfFilePath, configFilePath);
            const htmlFile = await this.fileServer.requestFile(FileTypes.WORKFLOW_ANALYSIS_HTML);
            progress.report({ message: 'Finished analysis, opening result ...' });
            this.logger.info('[WorkflowAnalyzer] Analysis Result Ready: ' + jsonFile);
            // eslint-disable-next-line deprecation/deprecation
            const urlWithQuery = htmlFile + '?json=' + escape(jsonFile);
            this.logger.info('[WorkflowAnalyzer] Open Analysis Result');
            await this.openHandler.open(new URI(undefined), {
                name: 'Workflow Analysis',
                startPage: urlWithQuery,
                toolbar: 'hide',
                iconClass: codicon('pie-chart')
            });
        } catch (error) {
            if (error !== undefined && error instanceof Error) {
                this.messageService.error('The workflow analysis failed', 'Show details').then(result => {
                    if (result === 'Show details') {
                        showErrorDialog(error as Error);
                    }
                });
            } else {
                throw error;
            }
        } finally {
            progress.cancel();
        }
    }
}

@injectable()
export class WorkflowAnalysisClientImpl implements WorkflowAnalysisClient {
    constructor(@inject(MessageService) protected readonly messageService: MessageService) {}
    reportStatus(status: WorkflowAnalysisStatus): void {
        switch (status.status) {
            case 'ok':
                this.messageService.info(status.message);
                break;
            case 'error':
                this.messageService.error(status.message);
                break;
        }
    }
}

export function showErrorDialog(error: Error): void {
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
