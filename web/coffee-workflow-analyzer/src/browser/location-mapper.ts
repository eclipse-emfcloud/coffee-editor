/*
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { MaybePromise } from '@theia/core';
import { Endpoint } from '@theia/core/lib/browser';
import { LocationMapper } from '@theia/mini-browser/lib/browser/location-mapper-service';
import { injectable } from 'inversify';

@injectable()
export class WorkflowFileLocationMapper implements LocationMapper {
    canHandle(location: string): MaybePromise<number> {
        return location.startsWith('file://') ? 2 : 0;
    }
    map(location: string): MaybePromise<string> {
        return this.toURL(location);
    }

    toURL(uri: string, endpointPath = 'mini-browser'): MaybePromise<string> {
        if (!uri.startsWith('file://')) {
            throw new Error(`Only URIs with 'file' scheme can be mapped to an URL. URI was: ${uri}.`);
        }
        const queryIndex = uri.lastIndexOf('?');
        const queryString = uri.substring(queryIndex, uri.length);
        let rawLocation = uri.substring(7, queryIndex);
        if (rawLocation.charAt(0) === '/') {
            rawLocation = rawLocation.substr(1);
        }
        return new Endpoint().getRestUrl().resolve(`${endpointPath}/${rawLocation}`).toString() + queryString;
    }
}
