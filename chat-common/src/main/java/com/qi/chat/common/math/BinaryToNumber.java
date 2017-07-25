package com.qi.chat.common.math;

import org.apache.commons.lang3.StringUtils;

/**
 * 将二进制数转换为基本数据类型
 * 
 * @author Administrator
 *
 */
public class BinaryToNumber {
	/**
	 * 将二进制数转换为字符
	 * 
	 * @param binary
	 * @return
	 * @throws Exception
	 */
	public static char binaryToCharater(String binary) throws Exception {
		if (StringUtils.isBlank(binary) || binary.length() > 16) {
			throw new Exception("二进制长度错误");
		}
		int sum = 0;
		for (int i = 0; i < binary.length(); i++) {
			int integer = Integer.valueOf(binary.charAt(i) + "");
			sum += integer * (int) Math.pow(2, i);
		}
		return (char) sum;
	}

	/**
	 * 将二进制数转换为字节
	 * 
	 * @param binary
	 * @return
	 * @throws Exception
	 */
	public static byte binaryToByte(String binary) throws Exception {
		if (StringUtils.isBlank(binary) || binary.length() > 8) {
			throw new Exception("二进制长度错误");
		}
		byte sum = 0;
		for (int i = 0; i < binary.length(); i++) {
			int integer = Integer.valueOf(binary.charAt(i) + "");
			sum += integer * (int) Math.pow(2, i);
		}
		return sum;
	}

	public static byte binaryToByte(byte[] binary) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < binary.length; i++) {
			sb.append(String.valueOf(binary[i]));
		}
		return binaryToByte(sb.toString());
	}

	/**
	 * 将二进制数转换为short类型
	 * 
	 * @param binary
	 * @return
	 * @throws Exception
	 */
	public static short binaryToShort(String binary) throws Exception {
		if (StringUtils.isBlank(binary) || binary.length() > 16) {
			throw new Exception("二进制长度错误");
		}
		short sum = 0;
		for (int i = 0; i < binary.length(); i++) {
			int integer = Integer.valueOf(binary.charAt(i) + "");
			sum += integer * (int) Math.pow(2, i);
		}
		return sum;
	}

	public static short binaryToShort(byte[] binary) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < binary.length; i++) {
			sb.append(String.valueOf(binary[i]));
		}
		return binaryToShort(sb.toString());
	}

	public static long binaryToLong(String binary) throws Exception {
		if (StringUtils.isBlank(binary) || binary.length() > 64) {
			throw new Exception("二进制长度错误");
		}
		long sum = 0;
		for (int i = 0; i < binary.length(); i++) {
			int integer = Integer.valueOf(binary.charAt(i) + "");
			sum += integer * (int) Math.pow(2, i);
		}
		return sum;
	}
	
	public static long binaryToLong(byte[] binary) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < binary.length; i++) {
			sb.append(String.valueOf(binary[i]));
		}
		return binaryToLong(sb.toString());
	}

}
