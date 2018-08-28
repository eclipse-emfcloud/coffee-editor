package com.eclipsesource.workflow.dsl.ide

import com.eclipsesource.workflow.dsl.WorkflowRuntimeModule
import com.eclipsesource.workflow.dsl.WorkflowStandaloneSetup
import com.eclipsesource.xtext.uml.UmlStandaloneSetup
import com.google.inject.Guice
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.Executors
import org.eclipse.equinox.app.IApplication
import org.eclipse.equinox.app.IApplicationContext
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.xtext.ide.server.LanguageServerImpl
import org.eclipse.xtext.ide.server.ServerModule
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.util.Modules2

class Application implements IApplication {

	override start(IApplicationContext context) throws Exception {
		println("Starting up Workflow LSP")
		new Thread(new RunSocketServer()).run()
		return null;
	}

	override stop() {
	}

	static class RunSocketServer implements Runnable {

		override run() {
			WorkflowStandaloneSetup.doSetup
			UmlStandaloneSetup.doSetup
			new WorkflowIdeSetup() {
				override createInjector() {
					Guice.createInjector(Modules2.mixin(new WorkflowRuntimeModule, new WorkflowIdeModule))
				}
			}.createInjectorAndDoEMFRegistration
			val injector = Guice.createInjector(Modules2.mixin(new ServerModule, [
				bind(IResourceServiceProvider.Registry).toProvider(IResourceServiceProvider.Registry.RegistryProvider)
			]))
			val serverSocket = AsynchronousServerSocketChannel.open.bind(new InetSocketAddress("localhost", 5007))
			val threadPool = Executors.newCachedThreadPool()

			println("LSP initialized and ready ")
			while (true) {
				val socketChannel = serverSocket.accept.get
				val in = Channels.newInputStream(socketChannel)
				val out = Channels.newOutputStream(socketChannel)

				val languageServer = injector.getInstance(LanguageServerImpl)
				val launcher = Launcher.createIoLauncher(languageServer, LanguageClient, in, out, threadPool, [it])
				languageServer.connect(launcher.remoteProxy)
				launcher.startListening

			}
		}

	}
}
