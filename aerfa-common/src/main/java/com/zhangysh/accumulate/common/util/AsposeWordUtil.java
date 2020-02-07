package com.zhangysh.accumulate.common.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.aspose.words.Document;
import com.aspose.words.License;
/**
 * Aspose工具类,主要word转pdf。
 * 由于ASPOSE比较吃内存，操作大一点的文件就会堆溢出，所以请先设置好java虚拟机参数
 */
public class AsposeWordUtil {
	/**
	* word转pdf文件
	* @param wordFullPath 输入word全路径
	* @param pdfFullPath 输出pdf全路径
	***/
	public static boolean wordToPdf(String wordFullPath, String pdfFullPath) {
		//验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getLicense()) {return false;}
		try {
            long old = System.currentTimeMillis();
            FileOutputStream fileOS = new FileOutputStream(new File(pdfFullPath));
            Document doc = new Document(wordFullPath);
            doc.save(fileOS, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            fileOS.close();
            System.out.println("执行wordToPdf方法共耗时：" + ((now - old) / 1000.0) + "秒");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
   /**
	*授权校验，若不验证则转化出的pdf文档会有水印产生 
	***/
    private static boolean getLicense() {
        boolean result = false;
        try {
        	InputStream is = InputStreamUtil.Base64ToInputStream(getWordLicenseBase64());
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /****
     * 获取word的校验文件，为方便工具使用直接返回文件流base64字符
     * 具体文件在:aerfa-back/src/main/resources/profile/aspose/wordlicense.xml
     * @return excel校验文件
     ***/
    private static String getWordLicenseBase64() {
    	return "PExpY2Vuc2U+DQogIDxEYXRhPg0KICAgIDxQcm9kdWN0cz4NCiAgICAgIDxQcm9kdWN0PkFzcG9zZS5Ub3RhbCBmb3IgSmF2YTwvUHJvZHVjdD4NCiAgICAgIDxQcm9kdWN0PkFzcG9zZS5Xb3JkcyBmb3IgSmF2YTwvUHJvZHVjdD4NCiAgICA8L1Byb2R1Y3RzPg0KICAgIDxFZGl0aW9uVHlwZT5FbnRlcnByaXNlPC9FZGl0aW9uVHlwZT4NCiAgICA8U3Vic2NyaXB0aW9uRXhwaXJ5PjIwOTkxMjMxPC9TdWJzY3JpcHRpb25FeHBpcnk+DQogICAgPExpY2Vuc2VFeHBpcnk+MjA5OTEyMzE8L0xpY2Vuc2VFeHBpcnk+DQogICAgPFNlcmlhbE51bWJlcj4yM2RjYzc5Zi00NGVjLTRhMjMtYmUzYS0wM2MxNjMyNDA0ZTk8L1NlcmlhbE51bWJlcj4NCiAgPC9EYXRhPg0KICA8U2lnbmF0dXJlPjJzTkxMS0dNVWRGMHI4TzFrS2lsV0FHZGdmczJCdkpiLzJYcDhwNWl1RFZmWlhtaHBwbytkMFJhbjFQOVRLZGpWNEFCd0FnS1h4SjNqY1FUcUUvMklSZnF3blBmOGl0TjhhRlpsVjNUSlBZZUQzeVdFN0lUNTVHejZFaWpVcEM3YUtlb29oVGI0dzJmcG94NTh3V29GM1NOcDZzSzZqRGZpQVVHRUhZSjlwalU9PC9TaWduYXR1cmU+DQo8L0xpY2Vuc2U+";
    }
}
