package com.qi.chat.common.net;

import java.nio.ByteBuffer;

import com.qi.chat.common.math.NumberToBinary;

/**
 * 1、开始标志 长度 1 byte 2、包头的长度 1 byte 3、整个包的长度 2 byte 4、包的类型 1 byte 5、分包序号 6、 分包总数
 * 
 * @author Administrator
 *
 */
public class Head implements IPort {
	// 开始标志
	public static char SF1 = 0x7F;// 2个字节
	public static char SF2 = 0x7F;// 2个字节
	public byte headLength = 14; // 1个字节
	public long bodyLength;// 8个字节
	/**
	 * 数据类型 1，文本 ;2，文件
	 */
	public byte dataType;// 1个字节

	@Override
	public byte[] toByte() {
		ByteBuffer heads = ByteBuffer.allocate(headLength * 8);
		byte[] SF1_Binary = new byte[16];
		NumberToBinary.charToBinary(SF1, SF1_Binary, 0);
		byte[] SF2_Binary = new byte[16];
		NumberToBinary.charToBinary(SF1, SF2_Binary, 0);
		byte[] headLength_Binary = new byte[8];
		NumberToBinary.byteToBinary(headLength, headLength_Binary, 0);
		byte[] bodyLength_Binary = new byte[64];
		NumberToBinary.longToBinary(bodyLength, bodyLength_Binary, 0);
		byte[] dataType_Binary = new byte[8];
		NumberToBinary.byteToBinary(dataType, dataType_Binary, 0);
		heads.put(SF1_Binary);
		heads.put(SF2_Binary);
		heads.put(headLength_Binary);
		heads.put(bodyLength_Binary);
		heads.put(dataType_Binary);
		heads.flip();
		byte[] dst = new byte[heads.limit()];
		heads.get(dst , 0, heads.limit());
		return dst;
	}
}
