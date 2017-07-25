package com.qi.chat.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class FileOutputCompletionHandler implements CompletionHandler<Integer, FileInputStream> {

	private AsynchronousSocketChannel asynchronousSocketChannel;

	public FileOutputCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
		this.asynchronousSocketChannel = asynchronousSocketChannel;
	}

	@Override
	public void completed(Integer result, FileInputStream is) {
		int len = -1;
		byte[] b = new byte[1024];
		try {
			if ((len = is.read(b)) != -1) {
				ByteBuffer bb = ByteBuffer.allocate(len);
				for (int j = 0; j < len; j++) {
					bb.put(b[j]);
				}
				bb.flip();
				asynchronousSocketChannel.write(bb, is, this);
			}else {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, FileInputStream is) {
		// TODO Auto-generated method stub

	}

}
