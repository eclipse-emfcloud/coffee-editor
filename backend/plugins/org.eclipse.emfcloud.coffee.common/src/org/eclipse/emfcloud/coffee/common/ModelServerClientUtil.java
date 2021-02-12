/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.common;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emfcloud.modelserver.client.ModelServerClient;

public final class ModelServerClientUtil {
	private static final String FORMAT = "xmi";
	private static final String MODEL_SERVER_BASE_URL = "http://localhost:8081/api/v1/";

	private ModelServerClientUtil() {
	}
	
	public static EObject loadResource(URI uri) throws Exception {
		ModelServerClient client = new ModelServerClient(MODEL_SERVER_BASE_URL);
		return client.get(Paths.get(uri).getFileName().toString(), FORMAT).get().body();
	}
	
	public static <T> Optional<T> loadResource(URI uri, Class<T> clazz) throws Exception {
		EObject eObject = loadResource(uri);
		return clazz.isInstance(eObject) ? Optional.of(clazz.cast(eObject)) : Optional.empty();
	}
}
