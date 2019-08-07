package com.eclipsesource.workflow.dsl.ide

import com.eclipsesource.workflow.dsl.WorkflowRuntimeModule
import com.eclipsesource.workflow.dsl.WorkflowStandaloneSetup
import com.eclipsesource.workflow.dsl.ide.server.WorkflowLanguageServer
import com.eclipsesource.workflow.dsl.ide.server.WorkflowServerModule
import com.google.inject.Guice
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.equinox.app.IApplication
import org.eclipse.equinox.app.IApplicationContext
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.util.Modules2

class Application implements IApplication {

	override start(IApplicationContext context) throws Exception {
		println("[WorkflowDSL] Starting up Workflow LSP")
		new Thread(new RunSocketServer()).run()
		return null;
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
			val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", 5017))
			val threadPool = Executors.newCachedThreadPool()

			println("[WorkflowDSL] LSP initialized and ready ")
			while (true) {
				val socketChannel = serverSocket.accept.get
				val in = Channels.newInputStream(socketChannel)
				val out = Channels.newOutputStream(socketChannel)

				val languageServer = injector.getInstance(WorkflowLanguageServer)
				val launcher = Launcher.createIoLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
				languageServer.connect(launcher.remoteProxy)
				launcher.startListening

			}
		}

	}
}
