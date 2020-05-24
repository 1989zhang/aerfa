package com.zhangysh.example.tools.thread;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MyTestThread  implements Runnable {
   
	private String str1;
	private String str2;
	private CountDownLatch doneSignal;
	private Map<String,Object> retMap;
	
	public MyTestThread(String str1, String str2, Map<String,Object> retMap, CountDownLatch doneSignal){
		this.str1=str1;
		this.str2=str2;
		this.doneSignal=doneSignal;
		this.retMap=retMap;
	}
	/**
	 *启动线程执行各种操作
	 */
	public void run(){
		try {
			System.out.println("线程开启传入参数为:"+str1+","+str2);
			retMap.put(str1,str2);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			doneSignal.countDown();
		}
	}
}
