package com.qi.chat.server;

import java.io.IOException;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.logging.Logger;

import com.qi.chat.common.ReadCompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, ServerContext> {

	@Override
	public void completed(AsynchronousSocketChannel asynchronousSocketChannel, ServerContext context) {
		System.out.println("连接成功");
		context.getServerSocketChannel().accept(context, this);
		try {
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 32 * 1024);
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 32 * 1024);
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
			ByteBuffer byteBuffer = ByteBuffer.allocate(32*1024);
			asynchronousSocketChannel.read(byteBuffer, byteBuffer, new ReadCompletionHandler(asynchronousSocketChannel));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, ServerContext context) {
		context.getServerSocketChannel().accept(context, this);
		System.err.println("客户端连接失败");
	}

}
