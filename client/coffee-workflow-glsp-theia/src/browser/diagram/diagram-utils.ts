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
import URI from '@theia/core/lib/common/uri';

const coffeeFileExtension = '.coffee';
const notationFileExtension = '.notation';

export function getCoffeeUriString(uriString: string): string {
    if (uriString.endsWith(notationFileExtension)) {
        return uriString.replace(notationFileExtension, coffeeFileExtension);
    } else if (uriString.endsWith(coffeeFileExtension)) {
        return uriString;
    }
    throw Error(`Unexpected uriString: ${uriString}! Expected uriString ending in ${coffeeFileExtension} or ${notationFileExtension}!`);
}

export function getCoffeeUri(uri: URI): URI {
    const uriString = uri.toString();
    const coffeeString = getCoffeeUriString(uriString);
    return new URI(coffeeString);
}

export function getNotationUriString(uriString: string): string {
    if (uriString.endsWith(coffeeFileExtension)) {
        return uriString.replace(coffeeFileExtension, notationFileExtension);
    } else if (uriString.endsWith(notationFileExtension)) {
        return uriString;
    }
    throw Error(`Unexpected uriString: ${uriString}! Expected uriString ending in ${coffeeFileExtension} or ${notationFileExtension}!`);
}

export function getNotationUri(uri: URI): URI {
    const uriString = uri.toString();
    const notationString = getNotationUriString(uriString);
    return new URI(notationString);
}
