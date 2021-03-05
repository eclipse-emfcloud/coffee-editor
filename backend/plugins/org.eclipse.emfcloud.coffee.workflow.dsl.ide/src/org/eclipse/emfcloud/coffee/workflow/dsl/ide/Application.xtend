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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide

import org.eclipse.emfcloud.coffee.workflow.dsl.WorkflowRuntimeModule
import org.eclipse.emfcloud.coffee.workflow.dsl.WorkflowStandaloneSetup
import org.eclipse.emfcloud.coffee.workflow.dsl.ide.server.WorkflowLanguageServer
import org.eclipse.emfcloud.coffee.workflow.dsl.ide.server.WorkflowServerModule
import com.google.inject.Guice
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import org.eclipse.equinox.app.IApplication
import org.eclipse.equinox.app.IApplicationContext
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.util.Modules2

class Application implements IApplication {

	override start(IApplicationContext context) throws Exception {
		
		if(startSocketServer(context))
			new Thread(new RunSocketServer()).run()
		else 
			new Thread(new RunStreamServer()).run()
		return null;
	}
	
	def startSocketServer(IApplicationContext context) {
		val args = context.getArguments().get(IApplicationContext.APPLICATION_ARGS) as String[];
		for (var i = 0; i < args.length; i++) {
			val arg = args.get(i);
			if(arg.equals('-startSocket'))
				return true
		}
		return false
	}

	override stop() {
	}

	static class RunSocketServer implements Runnable {

		override run() {
			WorkflowStandaloneSetup.doSetup
			new WorkflowIdeSetup() {
				override createInjector() {
					Guice.createInjector(Modules2.mixin(new WorkflowRuntimeModule, new WorkflowIdeModule))
				}
			}.createInjectorAndDoEMFRegistration
			val injector = Guice.createInjector(Modules2.mixin(new WorkflowServerModule, new WorkflowRuntimeModule, [
				bind(IResourceServiceProvider.Registry).toProvider(IResourceServiceProvider.Registry.RegistryProvider)
			]))
			val threadPool = Executors.newCachedThreadPool()
			try (val serverSocket = AsynchronousServerSocketChannel.open()
					.bind(new InetSocketAddress("localhost", 5017))) {
	
				println("[WorkflowDSL] Ready to accept client requests ")
				while (true) {
					val socketChannel = serverSocket.accept.get
					val in = Channels.newInputStream(socketChannel)
					val out = Channels.newOutputStream(socketChannel)
	
					val languageServer = injector.getInstance(WorkflowLanguageServer)
					val launcher = Launcher.createLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
					languageServer.connect(launcher.remoteProxy)
					launcher.startListening.get
					System.out.println("[WorkflowDSL] Connected client " + socketChannel.getRemoteAddress());
				}
			} catch (IOException | InterruptedException | ExecutionException e) {
				System.err.println("[WorkflowAnalysisServer] Encountered an error at accepting new client");
				e.printStackTrace();
			}
		}

	}
	
	static class RunStreamServer implements Runnable {

		override run() {
			WorkflowStandaloneSetup.doSetup
			new WorkflowIdeSetup() {
				override createInjector() {
					Guice.createInjector(Modules2.mixin(new WorkflowRuntimeModule, new WorkflowIdeModule))
				}
			}.createInjectorAndDoEMFRegistration
			val injector = Guice.createInjector(Modules2.mixin(new WorkflowServerModule, new WorkflowRuntimeModule, [
				bind(IResourceServiceProvider.Registry).toProvider(IResourceServiceProvider.Registry.RegistryProvider)
			]))
			val threadPool = Executors.newCachedThreadPool()

			val languageServer = injector.getInstance(WorkflowLanguageServer)
			val launcher = Launcher.createLauncher(languageServer, LanguageClient, System.in, System.out, threadPool, [it])
			languageServer.connect(launcher.remoteProxy)
			launcher.startListening.get

		}
	}
}
