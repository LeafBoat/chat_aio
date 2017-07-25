package com.qi.chat.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import com.qi.chat.common.ReadCompletionHandler;

public class ConnectionCompletionHandler implements CompletionHandler<Void, ChatClient> {

	@Override
	public void completed(Void result, ChatClient chatClient) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(3072);
	/*	ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler(
				chatClient.getAsynchronousSocketChannel());
		chatClient.getAsynchronousSocketChannel().read(byteBuffer, byteBuffer, readCompletionHandler);*/
		try {
			chatClient.sendImage("D:\\360Downloads\\Software/DingTalk_3.5.1.5.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, ChatClient attachment) {
		System.err.println("连接失败");
	}

}
