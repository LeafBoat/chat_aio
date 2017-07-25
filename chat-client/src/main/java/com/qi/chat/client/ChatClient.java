package com.qi.chat.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.qi.chat.common.FileOutputCompletionHandler;
import com.qi.chat.common.WriteCompletionHandler;
import com.qi.chat.common.bean.Message;
import com.qi.chat.common.bean.MessageHelper;
import com.qi.chat.common.net.Body;
import com.qi.chat.common.net.Head;

public class ChatClient {

	private InetSocketAddress inetSocketAddress;
	private AsynchronousSocketChannel asynchronousSocketChannel;
	private String userID;
	private CountDownLatch latch;
	WriteCompletionHandler writeCompletionHandler = new WriteCompletionHandler();

	private ChatClient(Builder builder) {
		inetSocketAddress = new InetSocketAddress(builder.serverIP, builder.serverPort);
		userID = builder.userID;
	}

	public void connect() {
		try {
			/*
			 * AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
			 * .withFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			 * Executors.defaultThreadFactory()); asynchronousSocketChannel =
			 * AsynchronousSocketChannel.open(channelGroup);
			 */
			asynchronousSocketChannel = AsynchronousSocketChannel.open();
			asynchronousSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			asynchronousSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
			ConnectionCompletionHandler connectionCompletionHandler = new ConnectionCompletionHandler();
			new Thread(new Runnable() {

				@Override
				public void run() {
					latch = new CountDownLatch(1);
					asynchronousSocketChannel.connect(inetSocketAddress, ChatClient.this, connectionCompletionHandler);
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 * @param friendID
	 */
	public void send(String msg, String friendID) {

		if (asynchronousSocketChannel.isOpen()) {
			Head head = new Head();
			Body body = new Body();
			body.setBody(msg);
			head.bodyLength = body.toByte().length;
			Message message = new Message(body, head);
			ByteBuffer byteBuffer = ByteBuffer.wrap(message.toByte());
			asynchronousSocketChannel.write(byteBuffer, byteBuffer, writeCompletionHandler);
		}
	}

	public void sendImage(String path) throws IOException {
		FileOutputCompletionHandler completionHandler = new FileOutputCompletionHandler(asynchronousSocketChannel);
		File file = new File(path);
		Head head = new Head();
		head.bodyLength = file.length();
		head.dataType = 2;
		Body body = new Body();
		FileInputStream is = new FileInputStream(file);
		int len = -1;
		byte[] b = new byte[1024];
		if ((len = is.read(b)) != -1) {
			ByteBuffer bb = ByteBuffer.allocate(len + head.headLength * 8);
			bb.put(head.toByte());
			for (int j = 0; j < len; j++) {
				bb.put(b[j]);
			}
			bb.flip();
			asynchronousSocketChannel.write(bb, is, completionHandler);
		}else {
			is.close();
		}
	}

	public AsynchronousSocketChannel getAsynchronousSocketChannel() {
		return asynchronousSocketChannel;
	}

	public static class Builder {
		private String serverIP;
		private int serverPort = 5555;
		private String userID;

		public Builder setServerIP(String serverIP) {
			this.serverIP = serverIP;
			return this;
		}

		public Builder setServerPort(int serverPort) {
			if (serverPort > 0)
				this.serverPort = serverPort;
			return this;
		}

		public Builder setUserID(String userID) {
			this.userID = userID;
			return this;
		}

		public ChatClient build() {
			return new ChatClient(this);
		}
	}
}
