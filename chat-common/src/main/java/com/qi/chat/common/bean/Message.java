package com.qi.chat.common.bean;

public class Message {
	
	public Head head;
	public Body body;

	public static class Head {
		public String from;
		public String toFriend;
		public int bodySize;
	}

	public static class Body {
		public String body;
	}
}
