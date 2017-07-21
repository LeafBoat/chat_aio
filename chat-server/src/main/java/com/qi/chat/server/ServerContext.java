package com.qi.chat.server;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * 服务器上下文
 * 
 * @author Administrator
 *
 */
public class ServerContext {

	private AsynchronousSocketChannelGroup ascg = new AsynchronousSocketChannelGroup();
	private AsynchronousServerSocketChannel serverSocketChannel;

	public boolean addSocketChannel(AsynchronousSocketChannel socketChannel) {
		return ascg.addSocketChannel(socketChannel);
	}

	public void setServerSocketChannel(AsynchronousServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
	}

	public AsynchronousServerSocketChannel getServerSocketChannel() {
		return serverSocketChannel;
	}
}
