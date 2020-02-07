package com.zhangysh.accumulate.normal;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.util.HttpClientUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

/**
 * 测试调用controller的方法
 *@author 创建者：zhangysh
 */
public class TestCallController {

	public static void main(String args[]) throws UnsupportedEncodingException {
		/*Map<String,String> header=new HashMap<String,String>();
		header.put(CacheConstant.REDIS_AERFATOKEN_PREFIX, "abefw123far33r3234369ee");
		String url="http://127.0.0.1:8080/sys/oper_log/test_log";
		System.out.println(HttpClientUtil.doGet(url,null,header));*/
		
		//如果穿中文要用UTF-8方式URLEncoder
		Map<String,String> header=new HashMap<String,String>();
		header.put(CacheConstant.REDIS_AERFATOKEN_PREFIX, URLEncoder.encode("abefw123far33r张","utf-8"));
		String url="http://127.0.0.1:8080/sys/oper_log/test_log";
		AefsysPerson person=new AefsysPerson();
		person.setPersonName("哈哈哈aa");
		String json=JSON.toJSONString(person);
		System.out.println(HttpClientUtil.doPostJson(url, json, header));
		
		/*String url="http://127.0.0.1:8080/redis/states";
		Map<String,String> header=new HashMap<String,String>();
		header.put(CacheConstant.REDIS_AERFATOKEN_PREFIX, "abefw123far33r3234369ee");
		System.out.println(HttpClientUtil.doPost(url,null,header));*/
	}
}
