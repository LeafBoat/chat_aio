package com.qi.chat.common.bean;

import com.qi.chat.common.net.Body;
import com.qi.chat.common.net.Head;

public class MessageHelper {
	/**
	 * 
	 * @param userID
	 *            用户ID
	 * @param friendID
	 *            好友ID
	 * @param msg
	 *            消息
	 * @return
	 */
	public static Message getMessage(String userID, String friendID, String msg) {
		Message message = new Message(new Body(),new Head());
//		message.head = new Head();
//		message.head.bodySize = msg.getBytes().length;
//		message.head.from = userID;
//		message.head.toFriend = friendID;
//		message.body = new Body();
		return message;
	}
}
