package com.zhangysh.accumulate.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;


/**
 *word文档doc工具类
 */
public class WordUtil {
	
	/**
     * 读取word文档结构，用于界面显示
     * @param path 文件路径 
     * @return 获取到的转化为html的内容
     */
	public static String readWordContent(String path){
		InputStream stream = null;
		String content = null;
		try {
			if ( path.endsWith(".doc") ) {
				stream = new FileInputStream(path);
	    		HWPFDocument wordDocument = new HWPFDocument(stream);
	    		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
	    		wordToHtmlConverter.processDocument(wordDocument);
	    		 //创建html页面并将文档中内容写入页面
	   		  	Document htmlDocument = wordToHtmlConverter.getDocument();
	   		  	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	   		  	DOMSource domSource = new DOMSource(htmlDocument);
	   		  	StreamResult streamResult = new StreamResult(outStream);
	   		  	TransformerFactory tf = TransformerFactory.newInstance();
	   		  	Transformer serializer = tf.newTransformer();
	   		  	serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
	   		  	serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	   		  	serializer.setOutputProperty(OutputKeys.METHOD, "html");
	   		  	serializer.transform(domSource, streamResult);
	   		  	outStream.close();
	   		  	content = new String(outStream.toString("UTF-8"));
			} else if (path.endsWith(".docx")) {
	            
	        } else {
	            System.out.println("此文件不是word文件"+ path);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}
}
