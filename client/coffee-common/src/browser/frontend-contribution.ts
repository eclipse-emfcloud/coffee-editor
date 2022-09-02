/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { PreferenceService } from '@theia/core/lib/browser';
import { FrontendApplicationContribution } from '@theia/core/lib/browser/frontend-application';
import { ThemeService } from '@theia/core/lib/browser/theming';
import { ThemeType } from '@theia/core/lib/common/theme';
import { inject, injectable } from 'inversify';

@injectable()
export class CoffeeCommonFrontendContribution implements FrontendApplicationContribution {
    @inject(PreferenceService) protected readonly preferenceService: PreferenceService;
    @inject(ThemeService) protected readonly themeService: ThemeService;

    static readonly darkImagesCss = require('../../src/browser/css/images-dark.useable.css');
    static readonly lightImagesCss = require('../../src/browser/css/images-light.useable.css');

    onStart(): void {
        this.updateTheme();
        this.themeService.onDidColorThemeChange(() => this.updateTheme());
    }

    protected updateTheme(): void {
        const themeType: ThemeType = this.themeService.getCurrentTheme().type;
        if (themeType === 'dark' || themeType === 'hc') {
            // unload light
            CoffeeCommonFrontendContribution.lightImagesCss.unuse();
            // load dark
            CoffeeCommonFrontendContribution.darkImagesCss.use();
        } else if (themeType === 'light') {
            // unload dark
            CoffeeCommonFrontendContribution.darkImagesCss.unuse();
            // load light
            CoffeeCommonFrontendContribution.lightImagesCss.use();
        }
    }
}
