package com.qi.chat.common;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import com.qi.chat.common.bean.User;
import com.qi.chat.common.service.impl.UserServicesImpl;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		System.out.println(""+result);
		User user = new User();
		register(user );
	} 

	private void register(User user) {
		UserServicesImpl userServices = new UserServicesImpl();
		userServices.insert(user);
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {

	}

}
