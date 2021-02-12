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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide.server;

import java.util.concurrent.ExecutorService;

import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.xtext.ide.ExecutorServiceProvider;
import org.eclipse.xtext.ide.server.DefaultProjectDescriptionFactory;
import org.eclipse.xtext.ide.server.IMultiRootWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.IProjectDescriptionFactory;
import org.eclipse.xtext.ide.server.MultiRootWorkspaceConfigFactory;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.ResourceServiceProviderServiceLoader;
import org.eclipse.xtext.resource.containers.ProjectDescriptionBasedContainerManager;

import com.google.inject.AbstractModule;

@SuppressWarnings("restriction")
public class WorkflowServerModule extends AbstractModule {

	@Override
	protected void configure() {
		binder().bind(ExecutorService.class).toProvider(ExecutorServiceProvider.class);

		bind(LanguageServer.class).to(WorkflowLanguageServer.class);
		bind(IResourceServiceProvider.Registry.class).toProvider(ResourceServiceProviderServiceLoader.class);
		bind(IMultiRootWorkspaceConfigFactory.class).to(MultiRootWorkspaceConfigFactory.class);
		bind(IProjectDescriptionFactory.class).to(DefaultProjectDescriptionFactory.class);
		bind(IContainer.Manager.class).to(ProjectDescriptionBasedContainerManager.class);
	}
}
