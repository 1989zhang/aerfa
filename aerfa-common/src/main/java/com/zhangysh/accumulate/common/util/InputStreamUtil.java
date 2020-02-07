package com.zhangysh.accumulate.common.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.zhangysh.accumulate.common.constant.UtilConstant;

/**
 * 流相关的基础工具类
 * @author zhangysh
 * @date 2018年9月10日
 */
public class InputStreamUtil {
	
	/**
	 * InputStream转化为base64字符
	 * @throws IOException 
	 ***/
	public static String InputStreamToBase64(InputStream inputStream) throws IOException {
		if( inputStream == null ) {
			return null;
		}
		Base64.Encoder encoder =Base64.getEncoder();
		byte[] binaryData=InputStreamToByte(inputStream);
		return encoder.encodeToString(binaryData);
	}	
	/**
	 * byte二进制流转化为base64字符
	 ***/
	public static String ByteToBase64(byte[] binaryData) {
		if( binaryData == null ) {
			return null;
		}
		Base64.Encoder encoder =Base64.getEncoder();
		return encoder.encodeToString(binaryData);
	}
	/****
	 *把base64对应的字符串转化为输入流
	 *@param base64string java.util.Base64加密的字符串 
	 *@return InputStream 输入流
	 ****/
	public static InputStream Base64ToInputStream(String base64string){
		if( StringUtil.isEmpty(base64string) ) {
			return null;
		}
		ByteArrayInputStream stream = null;
		Base64.Decoder decoder =  Base64.getDecoder();
		byte[] bytes1 = decoder.decode(base64string);
		stream = new ByteArrayInputStream(bytes1);
		return stream;
	}
	/****
	 *把base64对应的字符串转化为byte数组
	 *@param base64string java.util.Base64加密的字符串 
	 *@return byte 数组
	 ****/
	public static byte[] Base64ToByte(String base64string){
		if( StringUtil.isEmpty(base64string) ) {
			return null;
		}
		Base64.Decoder decoder =  Base64.getDecoder();
		return decoder.decode(base64string);
	}
	
	
	/**
	 * 将InputStream转换成byte数组
	 * @throws IOException 
	 */
	public static byte[] InputStreamToByte(InputStream in) throws IOException{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[UtilConstant.BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, UtilConstant.BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}
		data = null;
		outStream.flush();
		if (in != null) { in.close(); }
		return outStream.toByteArray();
	}
	/**
	 * 将InputStream转换成String,编码为空则默认UTF-8编码
	 **/
	public static String InputStreamToString(InputStream in,String encoding) throws IOException{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[UtilConstant.BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, UtilConstant.BUFFER_SIZE)) != -1) {
			outStream.write(data, 0, count);
		}
		data = null;
		if( StringUtil.isEmpty(encoding) ) {
			return new String(outStream.toByteArray(), "UTF-8");
		}else {
			return new String(outStream.toByteArray(), encoding);
		}
	}
	/**
	 * 将byte数组转换成InputStream
	 ***/
	public static InputStream ByteToInputStream(byte[] binaryData){
		ByteArrayInputStream is = new ByteArrayInputStream(binaryData);
		return is;
	}
	/**
	 * 将byte数组转换成String
	 * @throws IOException 
	 **/ 
	public static String ByteToString(byte[] binaryData) throws IOException{
		InputStream is = ByteToInputStream(binaryData);
		return InputStreamToString(is,null);
	}
}
