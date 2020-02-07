package com.zhangysh.accumulate.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*****
 * 本地SQL处理工具类:获取sql参数等
 * @author zhangysh
 * @date 2019年6月23日
 *****/
public class NativeSqlUtil {

	/****
	 * 获取匹配到的sql语句中请求参数，格式例如：request~ywbh~ 
	 * @param dataSourceSql sql语句
	 * @return 匹配到的参数字符串集合
	 ***/
	public static List<String> getRequestStr(String dataSourceSql) {
		List<String> result = new ArrayList<String>();
		Pattern p = Pattern.compile("request[\\s]*~[\\s]*([\\S&&[^~]]+)[\\s]*~?",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(dataSourceSql);
		while (m.find()) {
			result.add(m.group());
		}
		return result;
	}
	
	/**
	 * 获取匹配后字符串包含的参数名称，例如：request~ywbh~得到ywbh
	 ***/
	public static String getParamName(String requestStr) {
		requestStr = requestStr.replaceAll("\\s", "");//去掉空格
		int startIndex = requestStr.indexOf("~");
		int endIndex = requestStr.lastIndexOf("~");
		return requestStr.substring(startIndex + 1, endIndex);
	}

}
