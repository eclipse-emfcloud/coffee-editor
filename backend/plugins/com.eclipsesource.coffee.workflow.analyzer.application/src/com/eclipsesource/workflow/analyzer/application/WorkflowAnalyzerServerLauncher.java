package com.eclipsesource.workflow.analyzer.application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.lsp4j.jsonrpc.Launcher;

public class WorkflowAnalyzerServerLauncher {

	private ServerThread serverThread;

	public void start(final String hostName, final int port) {
		serverThread = new ServerThread(hostName, port);
		serverThread.start();
	}

	public void shutdown() {
		if (serverThread != null) {
			serverThread.shutdown();
		}
	}

	private final class ServerThread extends Thread {

		private final String host;
		private final int port;

		private ExecutorService threadPool;
		private boolean shouldRun = true;
		private AsynchronousSocketChannel socketChannel;

		ServerThread(final String host, final int port) {
			super("Workflow Analyzer Server");
			this.host = host;
			this.port = port;
		}

		@Override
		public void run() {
			threadPool = Executors.newCachedThreadPool();
			try (AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
					.bind(new InetSocketAddress(host, port))) {
				while (shouldRun) {
					socketChannel = serverSocket.accept().get();
					final WorkflowAnalyzerServerImpl server = new WorkflowAnalyzerServerImpl();
					final InputStream input = Channels.newInputStream(socketChannel);
					final OutputStream output = Channels.newOutputStream(socketChannel);
					final Launcher<WorkflowAnalysisClient> launcher = Launcher.createIoLauncher(server,
							WorkflowAnalysisClient.class, input, output, threadPool, msg -> msg);
					final WorkflowAnalysisClient client = launcher.getRemoteProxy();
					server.connect(client);
					CompletableFuture.supplyAsync(() -> startLauncher(launcher)).thenRun(server::dispose);
					System.out.println("Workflow Analysis Server connected client " + socketChannel.getRemoteAddress());
				}
			} catch (IOException | InterruptedException | ExecutionException e) {
				System.err.println("Workflow Analysis Server encountered an error at accepting new client");
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
			this.shouldRun = false;
			if (socketChannel != null) {
				try {
					socketChannel.close();
					System.out.println("Workflow Analysis Server shut down");
				} catch (final IOException exception) {
					System.err.println("Workflow Analysis  Server encountered an error at shutdown");
					exception.printStackTrace();
				}
				if (threadPool != null) {
					threadPool.shutdownNow();
				}
			}
		}
	}
}
