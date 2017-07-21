package com.qi.chat.client;

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
import com.qi.chat.common.WriteCompletionHandler;
import com.qi.chat.common.bean.Message;
import com.qi.chat.common.bean.MessageHelper;

public class ChatClient {

	private InetSocketAddress inetSocketAddress;
	private AsynchronousSocketChannel asynchronousSocketChannel;
	private String userID;
	private CountDownLatch latch;
	private ChatClient(Builder builder) {
		inetSocketAddress = new InetSocketAddress(builder.serverIP, builder.serverPort);
		userID = builder.userID;
	}
	
	public void connect() {
		try {
			/*AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
					.withFixedThreadPool(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());
			asynchronousSocketChannel = AsynchronousSocketChannel.open(channelGroup);*/
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

	public void send(String msg,String friendID) {
		if(asynchronousSocketChannel.isOpen()){
			Message message = MessageHelper.getMessage(userID, friendID, msg);
			String string = new Gson().toJson(message);
			WriteCompletionHandler writeCompletionHandler = new WriteCompletionHandler();
			ByteBuffer byteBuffer = ByteBuffer.wrap(string.getBytes());
			asynchronousSocketChannel.write(byteBuffer, byteBuffer, writeCompletionHandler);
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

		public Builder setUserID(String userID){
			this.userID = userID;
			return this;
		}
		
		public ChatClient build() {
			return new ChatClient(this);
		}
	}
}
