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
package org.eclipse.emfcloud.coffee.workflow.analyzer.application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.lsp4j.jsonrpc.Launcher;

public class WorkflowAnalyzerServerLauncher {

	private SocketServerThread socketServerThread;
	private StreamServerThread streamServerThread;

	public void start(final String hostName, final Integer port) {
		if (hostName != null && port != null) {
			socketServerThread = new SocketServerThread(hostName, port);
			socketServerThread.run();
		} else {
			streamServerThread = new StreamServerThread();
			streamServerThread.run();
		}

	}

	public void shutdown() {
		if (socketServerThread != null) {
			socketServerThread.shutdown();
		}
		if (streamServerThread != null) {
			streamServerThread.shutdown();
		}
	}

	private final class SocketServerThread extends Thread {

		private final String host;
		private final int port;

		private ExecutorService threadPool;
		private AsynchronousSocketChannel socketChannel;

		SocketServerThread(final String host, final int port) {
			super("Workflow Analyzer Server");
			this.host = host;
			this.port = port;
		}

		@Override
		public void run() {
			threadPool = Executors.newCachedThreadPool();
			try (AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
					.bind(new InetSocketAddress(host, port))) {
				System.out.println("[WorkflowAnalysisServer] Ready to accept client requests");
				socketChannel = serverSocket.accept().get();
				final WorkflowAnalyzerServerImpl server = new WorkflowAnalyzerServerImpl();
				final InputStream input = Channels.newInputStream(socketChannel);
				final OutputStream output = Channels.newOutputStream(socketChannel);
				final Launcher<WorkflowAnalysisClient> launcher = Launcher.createIoLauncher(server,
						WorkflowAnalysisClient.class, input, output, threadPool, msg -> msg);
				final WorkflowAnalysisClient client = launcher.getRemoteProxy();
				server.connect(client);
				startLauncher(launcher);
			} catch (IOException | InterruptedException | ExecutionException e) {
				System.err.println("[WorkflowAnalysisServer] Encountered an error at accepting new client");
				e.printStackTrace();
			}

		}

		private Void startLauncher(final Launcher<WorkflowAnalysisClient> launcher) {
			try {
				return launcher.startListening().get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Workflow Analysis Server encountered an error at accepting new client");
				e.printStackTrace();
			}
			return null;
		}

		private void shutdown() {
			if (socketChannel != null) {
				try {
					socketChannel.close();
					System.out.println("[WorkflowAnalysisServer] Shut down");
				} catch (final IOException exception) {
					System.err.println("[WorkflowAnalysisServer] Encountered an error at shutdown");
					exception.printStackTrace();
				}
				if (threadPool != null) {
					threadPool.shutdownNow();
				}
			}
		}
	}

	private final class StreamServerThread extends Thread {

		private ExecutorService threadPool;

		StreamServerThread() {
			super("Workflow Analyzer Stream Server");
		}

		@Override
		public void run() {
			threadPool = Executors.newCachedThreadPool();
			final WorkflowAnalyzerServerImpl server = new WorkflowAnalyzerServerImpl();
			final InputStream input = System.in;
			final OutputStream output = System.out;
			final Launcher<WorkflowAnalysisClient> launcher = Launcher.createLauncher(server,
					WorkflowAnalysisClient.class, input, output, threadPool, msg -> msg);
			final WorkflowAnalysisClient client = launcher.getRemoteProxy();
			server.connect(client);
			startLauncher(launcher);
		}

		private Void startLauncher(final Launcher<WorkflowAnalysisClient> launcher) {
			try {
				return launcher.startListening().get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Workflow Analysis Server encountered an error at accepting new client");
				e.printStackTrace();
			}
			return null;
		}

		private void shutdown() {
			if (threadPool != null) {
				threadPool.shutdownNow();
			}
		}
	}
}
