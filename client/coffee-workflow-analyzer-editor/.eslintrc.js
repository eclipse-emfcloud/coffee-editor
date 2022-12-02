const year = new Date().getFullYear();
/** @type {import('eslint').Linter.Config} */
module.exports = {
    extends: '@eclipse-glsp',
    parserOptions: {
        tsconfigRootDir: __dirname,
        project: 'tsconfig.json'
    },
    overrides: [
        {
            files: ['*.ts', '*.tsx'],
            rules: {
                // eslint-plugin-header
                'header/header': [
                    2,
                    'block',
                    [
                        {
                            pattern: '[\n\r]+ \\* Copyright \\([cC]\\) \\d{4}(-\\d{4})? .*[\n\r]+',
                            template: `
 * Copyright (c) ${year} EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 `
                        }
                    ]
                ]
            }
        }
    ]
};
