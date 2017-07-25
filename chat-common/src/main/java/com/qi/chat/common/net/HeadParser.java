package com.qi.chat.common.net;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;

import com.qi.chat.common.math.BinaryToNumber;
import com.qi.chat.common.math.NumberToBinary;

public class HeadParser {

	/**
	 * 解析数据头
	 * 
	 * @param byteBuffer
	 * @throws Exception
	 */
	public static Head parseHead(ByteBuffer byteBuffer) throws Exception {
		Head head = null;
		byteBuffer.flip();
		byte[] src = byteBuffer.array();
		byte[] _SF1 = new byte[16];
		byte[] _SF2 = new byte[16];
		byte[] _headLengthBinary = new byte[8];
		byte[] _bodyLengthBinary = new byte[64];
		byte[] _dataType_Binary = new byte[8];
		int len = 0;
		System.arraycopy(src, len, _SF1, 0, _SF1.length);
		len += _SF1.length;
		System.arraycopy(src, len, _SF2, 0, _SF2.length);
		len += _SF2.length;
		if (compareFlag(_SF1, Head.SF1) && compareFlag(_SF2, Head.SF2)) {
			// 数据头的起始位置
			head = new Head();
			// 读取数据头的长度
			System.arraycopy(src, len, _headLengthBinary, 0, _headLengthBinary.length);
			head.headLength = BinaryToNumber.binaryToByte(_headLengthBinary);
			// 读取数据体的长度
			len += _headLengthBinary.length;
			System.arraycopy(src, len, _bodyLengthBinary, 0, _bodyLengthBinary.length);
			head.bodyLength = BinaryToNumber.binaryToLong(_bodyLengthBinary);
			// 读取数据类型
			len += _bodyLengthBinary.length;
			System.arraycopy(src, len, _dataType_Binary, 0, _dataType_Binary.length);
			head.dataType = BinaryToNumber.binaryToByte(_dataType_Binary);
			return head;
		} else {
			throw new Exception("数据头解析错误");
		}
	}

	/**
	 * 判断二进制是否是字符的ASCII的编码
	 * 
	 * @param _SF1
	 * @return
	 */
	private static boolean compareFlag(byte[] _flagBinary, char flag) {
		byte[] flagBinary = new byte[16];
		NumberToBinary.charToBinary(flag, flagBinary, 0);
		for (int i = 0; i < _flagBinary.length; i++) {
			if (_flagBinary[i] != flagBinary[i]) {
				return false;
			}
		}
		return true;
	}
}
