/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver.app;

import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.modelserver.emf.common.RecordingModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.configuration.ServerConfiguration;

import com.google.common.base.Strings;
import com.google.inject.Inject;

public class CoffeeModelResourceManager extends RecordingModelResourceManager {

	@Inject
	public CoffeeModelResourceManager(Set<EPackageConfiguration> configurations, AdapterFactory adapterFactory,
			ServerConfiguration serverConfiguration) {
		super(configurations, adapterFactory, serverConfiguration);
	}

	@Override
	public String adaptModelUri(final String modelUri) {
		URI uri = URI.createURI(modelUri, true);
		if (uri.isRelative()) {
			if (serverConfiguration.getWorkspaceRootURI().isFile()) {
				return uri.resolve(serverConfiguration.getWorkspaceRootURI()).toString();
			}
			return URI.createFileURI(modelUri).toString();
		}
		// Create file URI from path if modelUri is already absolute path (file:/ or
		// full path file:///)
		// to ensure consistent usage of org.eclipse.emf.common.util.URI
		if (uri.hasDevice() && !Strings.isNullOrEmpty(uri.device())) {
			return URI.createFileURI(uri.device() + uri.path()).toString();
		}
		// In case of Windows: we cannot skip the scheme (e.g. C:)
		// therefore we check if scheme is no file: scheme and then use the whole uri as
		// fileURI
		if (URI.validScheme(uri.scheme()) && !uri.isFile()) {
			return URI.createFileURI(uri.toString()).toString();
		}
		return URI.createFileURI(uri.path()).toString();
	}

	// TODO do we need custom loadSourceResources method??

	@Override
	public ResourceSet getResourceSet(final String modeluri) {
		if (createURI(modeluri).fileExtension().equals("coffeenotation")) {
			URI semanticUri = createURI(modeluri).trimFileExtension().appendFileExtension("coffee");
			return resourceSets.get(semanticUri);
		}
		return resourceSets.get(createURI(modeluri));
	}

	@Override
	public boolean save(final String modeluri) {
		boolean result = false;
		for (Resource resource : getResourceSet(modeluri).getResources()) {
			result = saveResource(resource);
		}
		if (result) {
			getEditingDomain(getResourceSet(modeluri)).saveIsDone();
		}
		return result;
	}
}
