package com.qi.chat.common.net;

public class Body implements IPort {

	private byte[] body;

	public void setBody(String msg) {
		body = msg.getBytes();
	}

	@Override
	public byte[] toByte() {
		return body;
	}

}
