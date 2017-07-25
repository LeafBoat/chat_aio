package com.qi.chat.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.qi.chat.common.net.CheckData;
import com.qi.chat.common.net.Head;
import com.qi.chat.common.net.HeadParser;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel asChannel;
	private FileOutputStream fileOutputStream;

	public ReadCompletionHandler(AsynchronousSocketChannel asChannel) {
		this.asChannel = asChannel;
		try {
			fileOutputStream = new FileOutputStream("D:/DingTalk_3.5.1.5.exe");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void completed(Integer result, ByteBuffer byteBuffer) {
		try {
			Head head = HeadParser.parseHead(byteBuffer);
			CheckData.head = head;
			if (head.dataType == 1) {
				asChannel.read(byteBuffer, byteBuffer, this);
			} else if (head.dataType == 2) {
				byte[] dst = new byte[result - head.headLength * 8];
				System.arraycopy(byteBuffer.array(), head.headLength * 8, dst, 0, dst.length);
				CheckData.bodySize += dst.length;
				fileOutputStream.write(dst);
				fileOutputStream.flush();
				byteBuffer.clear();
				if (CheckData.isFinished()) {
					fileOutputStream.close();
//					asChannel.close();
				} else {
					asChannel.read(byteBuffer, byteBuffer, new FileInputCompletionHandler(asChannel, fileOutputStream));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer asChannel) {

	}

}
