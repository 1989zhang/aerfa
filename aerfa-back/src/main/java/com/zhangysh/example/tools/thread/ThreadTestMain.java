package com.zhangysh.example.tools.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTestMain {

	
	public static void main(String args[]) throws InterruptedException {
		int cnt = 5;  //启动的线程数量
		ExecutorService es = Executors.newFixedThreadPool(cnt);
		CountDownLatch doneSignal = new CountDownLatch(cnt);

		Map<String,Object> retMap=new HashMap<String,Object>();
		for(int i=0;i<5;i++){
			MyTestThread thread=new MyTestThread("zhang"+i,"wang"+i,retMap,doneSignal);
			es.execute(thread);
		}

		//等线程执行完后执行后续方法
		doneSignal.await();
		System.out.println("等线程执行完后执行后续方法");
		for(Map.Entry<String, Object> entry : retMap.entrySet()){
			String mapKey = entry.getKey();
			Object mapValue = entry.getValue();
			System.out.println(mapKey+":"+mapValue);
		}
	}
}
