package com.qi.chat.common.net;

public class CheckData {

	public static Head head;
	public static int bodySize;

/*	public void setHead(Head head) {
		this.head = head;
	}*/

/*	public void addBodySize(int bodySize) {
		this.bodySize += bodySize;
	}*/

	public static boolean isFinished() {
		return head.bodyLength == bodySize;
	}
}
