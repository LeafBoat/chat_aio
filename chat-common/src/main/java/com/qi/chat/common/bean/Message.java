package com.qi.chat.common.bean;

import com.qi.chat.common.net.Body;
import com.qi.chat.common.net.Head;
import com.qi.chat.common.net.IPort;

public class Message implements IPort {
	
	Body body;
	Head head;
	public Message(Body body,Head head){
		this.body=body;
		this.head=head;
	}
	@Override
	public byte[] toByte() {
		byte[] _body=body.toByte();
		byte[] _head = head.toByte();
		byte[] _msg=new byte[_body.length+_head.length];
		System.arraycopy(_head, 0, _msg, 0, _head.length);
		System.arraycopy(_body, 0, _msg, _head.length, _body.length);
		return _msg;
	}

}
