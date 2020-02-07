package com.zhangysh.accumulate.common.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;
/**
 * 几种方式获取唯一标示UUID.
 * @author zhangysh
 * @date 2018年7月7日
 * */
public class UuidUtil {
	private static final int IP;
	private static short counter = 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	/*****
	 * IP+系统毫秒级时间+调用次数
	 * 获取唯一标识的UUID方法，有一定规律
	 * @return 32位混合组成的字符串UUID
	 *****/
	public static String getUUID(){
		UuidUtil UuidHex = new UuidUtil();
		String s1 = (String)UuidHex.generate();
        return s1;
	}
	/*****
	 * 通过系统毫秒时间+随机3位数生产唯一uid,有一定规律
	 * @return 15位数字组成的字符串uid
	 *****/
	public static String getUMID() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}
	/*****
	 * 通过系统纳秒时间+随机3位数生产唯一uid,有一定规律
	 * @return 18位数字组成的字符串uid
	 *****/
	public static String getUNID() {
		//取当前时间的长整形值包含纳秒
		long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}
	/**
	 * JAVA自带ID生成器
	 * 获取唯一标识的ID方法
	 * @return 32位毫无规律的混合组成的字符串
	 */
	public static String getID() {
		String id = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		return id;
	}
	
	
	//初始化IP地址
	static {
		int i;
		try {
			i = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception exception) {
			i = 0;
		}
		IP = i;
	}
	
	//生产UUID
	private Serializable generate() {
		StringBuffer stringbuffer = (new StringBuffer(32)).append(format(getIP()))
					 .append(format(getJVM())).append(format(getHiTime()))
					 .append(format(getLoTime())).append(format(getCount()));
		
		return stringbuffer.toString();
	}
	//byte转int
	private static int toInt(byte abyte0[]) {
		int i = 0;
		for (int j = 0; j < 4; j++) {
			i = ((i << 8) - -128) + abyte0[j];
		}
		return i;
	}
	private int getIP() {
		return IP;
	}
	private int getJVM() {
		return JVM;
	}
	private short getHiTime() {
		return (short) (int) (System.currentTimeMillis() >>> 32);
	}

	private int getLoTime() {
		return (int) System.currentTimeMillis();
	}
	private short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}
	private String format(int i) {
		String s = Integer.toHexString(i);
		StringBuffer stringbuffer = new StringBuffer("00000000");
		stringbuffer.replace(8 - s.length(), 8, s);
		return stringbuffer.toString();
	}
	private String format(short word0) {
		String s = Integer.toHexString(word0);
		StringBuffer stringbuffer = new StringBuffer("0000");
		stringbuffer.replace(4 - s.length(), 4, s);
		return stringbuffer.toString();
	}
	
}
