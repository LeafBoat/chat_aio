package com.qi.chat.common.math;

public class NumberToBinary {

	/**
	 * 将数字转换为二进制，二进制的高位存放在数组的高位
	 * 
	 * @param s
	 * @param bytes
	 * @param i
	 *            该参数必须为0；
	 */
	public static void charToBinary(char s, byte[] bytes, int i) {
		if (s != 1) {
			bytes[i] = (byte) (s % 2);
			i++;
			charToBinary((char) (s / 2), bytes, i);
		} else {
			bytes[i] = 1;
			for (int j = 0; j < 15 - i; j++) {
				bytes[i + j + 1] = 0;
			}
		}
	}

	public static void byteToBinary(byte s, byte[] bytes, int i) {
		if (s != 1) {
			bytes[i] = (byte) (s % 2);
			i++;
			byteToBinary((byte) (s / 2), bytes, i);
		} else {
			bytes[i] = 1;
			for (int j = 0; j < 7 - i; j++) {
				bytes[i + j + 1] = 0;
			}
		}
	}

	public static void longToBinary(long s, byte[] bytes, int i) {
		if (s != 1) {
			bytes[i] = (byte) (s % 2);
			i++;
			longToBinary(s / 2, bytes, i);
		} else {
			bytes[i] = 1;
			for (int j = 0; j < 63 - i; j++) {
				bytes[i + j + 1] = 0;
			}
		}
	}
}
