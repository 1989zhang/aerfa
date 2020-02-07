package com.zhangysh.accumulate.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.zhangysh.accumulate.common.constant.UtilConstant;

/*****
 * HttpClient调用方法工具
 * @author zhangysh
 * @date 2018年7月14日
 *****/
public class HttpClientUtil {
	
	/***
	 *Http调用get传参请求，且包含参数 
	 *@param url 请求地址
	 *@param param 方法包含的Map参数,没有传null
	 *@param headers HTTP的首部,没有传null
	 *@return 返回的响应结果,已经utf-8编码
	 *****/
	public static String doGet(String url, Map<String,String> param,Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null && param.size() > 0) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			// 设置header
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	/***
	 *Http调用post传参请求，且包含参数 
	 *@param url 请求地址
	 *@param param 对应的Map参数
	 *@param headers HTTP的首部,没有传null
	 *@return 返回的响应结果,已经utf-8编码
	 *****/
	public static String doPost(String url, Map<String, String> param,Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 设置header
			if (headers != null && headers.size() > 0) {
				httpPost.setHeader("Content-type", "application/json");
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	
	/***
	 *Http调用post请求，传递json对象,考虑到字符问题所以只支持post
	 *@param url 请求地址
	 *@param json 参数json字符串
	 *@param headers HTTP的首部,没有传null
	 *@return 返回的响应结果,已经utf-8编码
	 *****/
	public static String doPostJson(String url, String json, Map<String, String> headers) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 设置header
			if (headers != null && headers.size() > 0) {
				httpPost.setHeader("Content-type", "application/json");
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/***
	 * 把map参数转化为X=x&的形式不含方便拼接到http的get方法的url后面，key要为英文值已经URLEncoder
	 * @param params 键值映射的参数
	 * @throws UnsupportedEncodingException 
	 ****/
	public static String createLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException{
        if(StringUtil.isEmpty(params)) {
        	return "";
        }
		List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String retStr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            value = URLEncoder.encode(value, UtilConstant.UTF_8);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
            	retStr = retStr + key + "=" + value;
            } else {
            	retStr = retStr + key + "=" + value + "&";
            }
        }
        return retStr;
    }
	
	/****
	 * 通过http的方式获取到base64文件内容，最后文件名为英文需要URLEncoder才能获取到，如果前面英文要报错后面处理
	 * @param urlString url路径
	 * @param fileNameIsChinese 最后的文件名称是否为中文
	 ***/
	public static String getBase64FileByHttpUrl(String urlString,boolean fileNameIsChinese) {
		try {
            String filepath  = urlString;
            if(fileNameIsChinese) {
            	//最后的中文文件名
            	String fileName = filepath.substring(filepath.lastIndexOf("/") + 1);
            	//重新组装地址
            	filepath = filepath.substring(0,filepath.lastIndexOf("/"))+"/" + URLEncoder.encode(fileName,UtilConstant.UTF_8);
            }
            URL url = new URL(filepath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            conn.setConnectTimeout(3000);
            InputStream inputStream = null;
            // 正常响应时获取输入流, 在这里也就是图片对应的字节流
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
            }
            // 释放资源
            return InputStreamUtil.InputStreamToBase64(inputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 发送 http put 请求，参数以原生字符串进行提交 
	 * @param url 地址
	 * @param headers 头部参数文件
	 * @param strJson json内容字符串
	 * @param encode 编码格式
	 * @return 返回结果
	 */
	public static String httpPut(String url,Map<String, String> headers,String strJson,String encode) {
		if (StringUtil.isEmpty(encode)) {
			encode = UtilConstant.UTF_8;
		}
		// since 4.3 不再使用 DefaultHttpClient
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpPut httpput = new HttpPut(url);

		// 设置header
		httpput.setHeader("Content-type", "application/json");
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpput.setHeader(entry.getKey(), entry.getValue());
			}
		}
		// 组织请求参数
		StringEntity stringEntity = new StringEntity(strJson, encode);
		httpput.setEntity(stringEntity);
		String content = null;
		CloseableHttpResponse httpResponse = null;
		try {
			// 响应信息
			httpResponse = closeableHttpClient.execute(httpput);
			content = EntityUtils.toString(httpResponse.getEntity(), encode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			closeableHttpClient.close(); // 关闭连接、释放资源
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 发送http delete请求
	 * @param url 地址
	 * @param headers 头部参数文件
	 * @param encode 编码格式
	 */
	public static String httpDelete(String url, Map<String, String> headers, String encode) {
		if (StringUtil.isEmpty(encode)) {
			encode = UtilConstant.UTF_8;
		}
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpDelete httpdelete = new HttpDelete(url);
		
		// 设置header
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpdelete.setHeader(entry.getKey(), entry.getValue());
			}
		}
		String content = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = closeableHttpClient.execute(httpdelete);
			content = EntityUtils.toString(httpResponse.getEntity(), encode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try { // 关闭连接、释放资源
			closeableHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
