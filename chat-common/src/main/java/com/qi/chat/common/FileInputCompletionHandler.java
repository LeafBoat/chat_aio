package com.qi.chat.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.qi.chat.common.net.CheckData;

public class FileInputCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel asChannel;
	private FileOutputStream os;

	public FileInputCompletionHandler(AsynchronousSocketChannel asChannel, FileOutputStream os) {
		this.asChannel = asChannel;
		this.os = os;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		buffer.flip();
		try {
			CheckData.bodySize += result;
			os.write(buffer.array(), 0, result);
			os.flush();
			buffer.clear();
			if (CheckData.isFinished()) {
				os.flush();
				os.close();
//				asChannel.close();
			} else {
				asChannel.read(buffer, buffer, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {

	}

}
