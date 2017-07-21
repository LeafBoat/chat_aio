package com.qi.chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;

public class ChatServer {

	private AsynchronousServerSocketChannel serverSocketChannel;
	private AcceptCompletionHandler acceptCompletionHandler = new AcceptCompletionHandler();
	private ServerContext context = new ServerContext();

	private ChatServer(Builder builder) {
		ExecutorService executorSevice = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
				Executors.defaultThreadFactory());
		try {
			/*
			 * withThreadPool(executor)与withCachedThreadPool(executor,0)是一样的。
			 * 如果withCachedThreadPool(executor,initialSize)中initialSize为0，
			 * 那么线程不会阻塞，导致接收服务器终止， 但是可以创建一个CountDownLatch阻塞线程或开启一个循环线程，这样程序不会终止。
			 */
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executorSevice, 0);
			serverSocketChannel = AsynchronousServerSocketChannel.open(group);
			context.setServerSocketChannel(serverSocketChannel);
			serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);
			serverSocketChannel = AsynchronousServerSocketChannel.open(group);
			serverSocketChannel.bind(builder.inetSocketAddress, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		// startBeart();
		serverSocketChannel.accept(context, acceptCompletionHandler);
	}

	private void startBeart() {
		Thread checkHeartbeatThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						// System.out.println("发起心跳");
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		 checkHeartbeatThread.setDaemon(true);
		 checkHeartbeatThread.setPriority(Thread.MIN_PRIORITY);
		checkHeartbeatThread.start();
	}

	public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
		return serverSocketChannel;
	}

	public static class Builder {
		String serverIP = null;
		int serverPort = 5555;
		InetSocketAddress inetSocketAddress = null;

		public Builder setServerIP(String ip) {
			serverIP = ip;
			return this;
		}

		/**
		 * 如果设置了port，则用设置的port
		 * 
		 * @param port
		 * @return
		 */
		public Builder serServerPort(int port) {
			if (port > 0)
				serverPort = port;
			return this;
		}

		public ChatServer build() {
			if (StringUtils.isBlank(serverIP)) {
				inetSocketAddress = new InetSocketAddress(serverPort);
			} else {
				inetSocketAddress = new InetSocketAddress(serverIP, serverPort);
			}
			return new ChatServer(this);
		}
	}
}
