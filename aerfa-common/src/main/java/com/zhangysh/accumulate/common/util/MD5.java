package com.zhangysh.accumulate.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *字符串和流的MD5加密;不能被解密.只能加密比对.
 *@author 创建者：zhangysh
 *@date 2018年9月12日
 */
public class MD5 {
	
	private static final String MD5_ALGORITHM_NAME = "MD5";

	private static final char[] HEX_CHARS =
	{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 把字符串通过md5加密为16进制字符串
	 * @param str 要通过md5加密的字符串
	 * @return 16进制加密后字符串
	 */
	public static String stringToHexStr(String str) {
		return byteToHexStr(str.getBytes());
	}
	
	/**
	 * 把byte数组通过md5加密为16进制字符串
	 * @param str 要通过md5加密的字符串
	 * @return 16进制加密后字符串
	 */
	public static String byteToHexStr(byte[] bytes) {
		return digestAsHexString(MD5_ALGORITHM_NAME, bytes);
	}

	/**
	 * 把inputStream通过md5加密为16进制字符串
	 * @param inputStream 输入流
	 * @return 16进制加密后字符串
	 */
	public static String inputStreamToHexStr(InputStream inputStream)throws IOException {
		return digestAsHexString(MD5_ALGORITHM_NAME, inputStream);
	}
	
	/**
	 *byte通过md5加密具体实现 
	 ***/
	private static String digestAsHexString(String algorithm, byte[] bytes) {
		byte[] digest = getDigest(algorithm).digest(bytes);;
		char[] hexDigest =encodeHex(digest);
		return new String(hexDigest);
	}
	/**
	 *inputStream通过md5加密具体实现 
	 ***/
	private static String digestAsHexString(String algorithm, InputStream inputStream) throws IOException{
		MessageDigest messageDigest = getDigest(algorithm);
		final byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			messageDigest.update(buffer, 0, bytesRead);
		}
		byte[] digest = messageDigest.digest();
		char[] hexDigest =encodeHex(digest);
		return new String(hexDigest);
	}
	/**
	 *遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
	 *****/
	private static char[] encodeHex(byte[] bytes) {
		char[] chars = new char[32];
		for (int i = 0; i < chars.length; i = i + 2) {
			byte b = bytes[i / 2];
			chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
			chars[i + 1] = HEX_CHARS[b & 0xf];
		}
		return chars;
	}
	/**
	 * Create a new {@link MessageDigest} with the given algorithm.
	 * Necessary because {@code MessageDigest} is not thread-safe.
	 */
	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
		}
	}
}
