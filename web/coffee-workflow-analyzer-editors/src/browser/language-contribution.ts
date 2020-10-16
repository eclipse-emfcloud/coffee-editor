/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { FrontendApplication } from '@theia/core/lib/browser';
import { BaseLanguageClientContribution, LanguageClientFactory, Languages, Workspace } from '@theia/languages/lib/browser';
import { inject, injectable } from 'inversify';

@injectable()
export class WorkflowClientContribution extends BaseLanguageClientContribution {

    readonly id = 'wfconfig';
    readonly name = 'WFCONFIG';

    constructor(
        @inject(Workspace) protected readonly workspace: Workspace,
        @inject(Languages) protected readonly languages: Languages,
        @inject(LanguageClientFactory) protected readonly languageClientFactory: LanguageClientFactory
    ) {
        super(workspace, languages, languageClientFactory);
    }

    protected get globPatterns(): string[] {
        return [
            '**/*.wfconfig',
            '**/*.wf',
            '**/*.xmi',
            '**/*.coffee'
        ];
    }

    waitForActivation(app: FrontendApplication): Promise<any> {
        // do not wait until document for this language was opened
        return this.workspace.ready;
    }
}

// register language with monaco on first load
registerDSL();

export function registerDSL(): void {
    // initialize monaco
    monaco.languages.register({
        id: 'wfconfig',
        aliases: ['WFCONFIG', 'wfconfig'],
        extensions: ['.wfconfig'],
        mimetypes: ['text/wfconfig']
    });
    monaco.languages.setLanguageConfiguration('wfconfig', {
        comments: {
            lineComment: '//',
            blockComment: ['/*', '*/']
        },
        brackets: [['{', '}'], ['(', ')']],
        autoClosingPairs: [
            {
                open: '{',
                close: '}'
            },
            {
                open: '(',
                close: ')'
            }]
    });
    monaco.languages.setMonarchTokensProvider('wfconfig', {
        // Set defaultToken to invalid to see what you do not tokenize yet
        // defaultToken: 'invalid',

        keywords: [
            'workflowModel', 'probabilities', 'assertions', 'low', 'medium', 'high'
        ],

        typeKeywords: [
            'boolean', 'number', 'string', 'int'
        ],

        operators: [
            ':', '=', ','
        ],

        // we include these common regular expressions
        symbols: /[=><!~?:&|+\-*/^%]+/,
        escapes: /\\(?:[abfnrtv\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,

        // The main tokenizer for our languages
        tokenizer: {
            root: [
                // identifiers and keywords
                [/[a-z_$][\w$]*/, {
                    cases: {
                        '@typeKeywords': 'keyword',
                        '@keywords': 'keyword',
                        '@default': 'identifier'
                    }
                }],
                [/[A-Z][\w$]*/, 'type.identifier'],  // to show class names nicely

                // whitespace
                { include: '@whitespace' },

                // delimiters and operators
                [/[{}()[\]]/, '@brackets'],
                [/[<>](?!@symbols)/, '@brackets'],
                [/@symbols/, {
                    cases: {
                        '@operators': 'operator',
                        '@default': ''
                    }
                }]
            ],

            whitespace: [
                [/[ \t\r\n]+/, 'white'],
                [/\/\*/, 'comment', '@comment'],
                [/\/\/.*$/, 'comment']
            ],

            comment: [
                [/[^/*]+/, 'comment'],
                [/\/\*/, 'comment.invalid'],
                [/\\*/, 'comment', '@pop'],
                [/[/*]/, 'comment']
            ],

            string: [
                [/[^\\"]+/, 'string'],
                [/@escapes/, 'string.escape'],
                [/\\./, 'string.escape.invalid'],
                [/"/, 'string', '@pop']
            ]
        }
    } as any);
}
