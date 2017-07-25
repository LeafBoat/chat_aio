package com.qi.chat.common;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	@Override
	public void completed(Integer result, ByteBuffer attachment) {

	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {

	}

}
