/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver;

import java.net.MalformedURLException;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParameters;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV1;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.XmiCodec;

import com.google.common.collect.ImmutableSet;

public class CoffeeModelServerClient extends ModelServerClient {

	private static final Set<String> DEFAULT_SUPPORTED_FORMATS = ImmutableSet.of(ModelServerPathParameters.FORMAT_JSON,
			ModelServerPathParameters.FORMAT_XMI, CoffeeResource.FILE_EXTENSION);

	private static Logger LOGGER = Logger.getLogger(CoffeeModelServerClient.class.getSimpleName());

	public CoffeeModelServerClient(final String baseUrl) throws MalformedURLException {
		super(baseUrl);
	}

	@Override
	protected boolean isSupportedFormat(final String format) {
		return DEFAULT_SUPPORTED_FORMATS.contains(format);
	}

	@Override
	public Optional<EObject> decode(final String payload, final String format) {
		try {
			if (format.equals(CoffeeResource.FILE_EXTENSION)) {
				return new CoffeeCodec().decode(payload);
			} else if (format.equals(ModelServerPathParametersV1.FORMAT_XMI)) {
				return new XmiCodec().decode(payload);
			}
			return new DefaultJsonCodec().decode(payload);
		} catch (DecodingException e) {
			LOGGER.error("Decoding of " + payload + " with " + format + " format failed");
		}
		return Optional.empty();
	}

}
