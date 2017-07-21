package com.qi.chat.client;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import com.qi.chat.common.ReadCompletionHandler;

public class ConnectionCompletionHandler implements CompletionHandler<Void, ChatClient> {

	@Override
	public void completed(Void result, ChatClient chatClient) {
		/*ByteBuffer byteBuffer = ByteBuffer.allocate(3072);
		ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler();
		chatClient.getAsynchronousSocketChannel().read(byteBuffer, byteBuffer, readCompletionHandler);*/
		int count = 0;
		while (chatClient.getAsynchronousSocketChannel().isOpen()) {
			if (count == 2)
				break;
			chatClient.send("你好", "jim");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		count++;
	}

	@Override
	public void failed(Throwable exc, ChatClient attachment) {
		System.err.println("连接失败");
	}

}
