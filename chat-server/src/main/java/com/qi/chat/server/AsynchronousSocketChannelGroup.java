package com.qi.chat.server;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashSet;

public class AsynchronousSocketChannelGroup {
	
	private HashSet<AsynchronousSocketChannel> socketChannels = new HashSet<>();
	
	public boolean addSocketChannel(AsynchronousSocketChannel socketChannel){
		return socketChannels.add(socketChannel);
	}
	

}
