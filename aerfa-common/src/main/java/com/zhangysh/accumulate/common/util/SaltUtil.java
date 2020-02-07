package com.zhangysh.accumulate.common.util;

import java.security.SecureRandom;
import java.util.Random;
/**
 *盐加密相关的util
 *@author 创建者：zhangysh
 *@date 2018年9月12日
 */
public class SaltUtil {

	/****
	 *获取随机的salt字符串
	 *@param  saltLength 需要返回的字符长度
	 *@return salt字符串
	 ****/
	public static String getRandomSalt(int saltLength){
		char[] chars="0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		char[] saltchars=new char[saltLength];
		Random RANDOM = new SecureRandom();
		for(int i=0;i<saltLength;i++){
			int n=RANDOM.nextInt(chars.length);
			saltchars[i]=chars[n];
		}
		String salt=new String(saltchars);
		return salt;
	}
	/****
	 *盐加密字符串:取5位盐的前两位拼在前，后两位拼在后
	 *@param inputStr 需要加密的字符串
	 *@param salt 大于5位的盐
	 ***/
	public static String encryptStrWithSalt(String inputStr,String salt) {
	   	//开始要是字符，不然就是数字加，和预想不一样
    	String str = ""+salt.charAt(0)+salt.charAt(1) + inputStr +salt.charAt(3) + salt.charAt(4);
    	return MD5.stringToHexStr(str);
	}
}
