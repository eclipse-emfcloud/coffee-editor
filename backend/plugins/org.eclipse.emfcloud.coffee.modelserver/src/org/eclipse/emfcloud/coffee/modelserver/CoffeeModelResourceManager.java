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
package org.eclipse.emfcloud.coffee.modelserver;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfcloud.modelserver.emf.common.RecordingModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.configuration.ServerConfiguration;
import org.eclipse.emfcloud.modelserver.glsp.notation.epackage.NotationUtil;

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

	@Override
	protected void loadSourceResources(final String directoryPath) {
		if (directoryPath == null || directoryPath.isEmpty()) {
			return;
		}
		File directory = new File(directoryPath);
		File[] list = directory.listFiles();
		Arrays.sort(list);
		for (File file : list) {
			if (isSourceDirectory(file)) {
				loadSourceResources(file.getAbsolutePath());
			} else if (file.isFile()) {
				URI absolutePath = createURI(file.getAbsolutePath());
				if (CoffeeResource.FILE_EXTENSION.equals(absolutePath.fileExtension())) {
					ResourceSet resourceSet = new ResourceSetImpl();
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
							.put(CoffeeResource.FILE_EXTENSION, CoffeeResource.Factory.INSTANCE);
					resourceSets.put(absolutePath, resourceSet);
				}
				loadResource(absolutePath.toString());
			}
		}
	}

	@Override
	public ResourceSet getResourceSet(final String modeluri) {
		if (createURI(modeluri).fileExtension().equals(NotationUtil.NOTATION_EXTENSION)) {
			URI semanticUri = createURI(modeluri).trimFileExtension()
					.appendFileExtension(CoffeeResource.FILE_EXTENSION);
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
