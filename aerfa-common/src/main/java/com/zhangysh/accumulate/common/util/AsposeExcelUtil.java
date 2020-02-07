package com.zhangysh.accumulate.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.aspose.cells.License;
import com.aspose.cells.Workbook;
/**
 * Aspose工具类,主要excel转pdf。
 * 由于ASPOSE比较吃内存，操作大一点的文件就会堆溢出，所以请先设置好java虚拟机参数
 */
public class AsposeExcelUtil {

   /**
	*excel转pdf文件
	*@param excelFullPath 输入excel全路径
	*@param pdfFullPath 输出pdf全路径
	***/
	public static boolean excelToPdf(String excelFullPath, String pdfFullPath) {
		//验证License 若不验证则转化出的pdf文档会有水印产生
		if (!getLicense()) {return false;}
		try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(excelFullPath);// 原始excel路径
            File file =new File(pdfFullPath);
            FileOutputStream fileOS = new FileOutputStream(file);
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            System.out.println("执行excelToPdf方法共耗时：" + ((now - old) / 1000.0) + "秒");
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
		    InputStream is = InputStreamUtil.Base64ToInputStream(getExcelLicenseBase64());
		    License aposeLic = new License();
		    aposeLic.setLicense(is);
		    result = true;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return result;
	}
    
    /****
     * 获取excel的校验文件，为方便工具使用直接返回文件流base64字符
     * 具体文件在:aerfa-back/src/main/resources/profile/aspose/excellicense.xml
     * @return excel校验文件
     ***/
    private static String getExcelLicenseBase64() {
	    return "PExpY2Vuc2U+DQogIDxEYXRhPg0KICAgIDxQcm9kdWN0cz4NCiAgICAgIDxQcm9kdWN0PkFzcG9zZS5Ub3RhbCBmb3IgSmF2YTwvUHJvZHVjdD4NCiAgICAgIDxQcm9kdWN0PkFzcG9zZS5Xb3JkcyBmb3IgSmF2YTwvUHJvZHVjdD4NCiAgICA8L1Byb2R1Y3RzPg0KICAgIDxFZGl0aW9uVHlwZT5FbnRlcnByaXNlPC9FZGl0aW9uVHlwZT4NCiAgICA8U3Vic2NyaXB0aW9uRXhwaXJ5PjIwOTkxMjMxPC9TdWJzY3JpcHRpb25FeHBpcnk+DQogICAgPExpY2Vuc2VFeHBpcnk+MjA5OTEyMzE8L0xpY2Vuc2VFeHBpcnk+DQogICAgPFNlcmlhbE51bWJlcj44YmZlMTk4Yy03ZjBjLTRlZjgtOGZmMC1hY2MzMjM3YmYwZDc8L1NlcmlhbE51bWJlcj4NCiAgPC9EYXRhPg0KICA8U2lnbmF0dXJlPnNOTExLR01VZEYwcjhPMWtLaWxXQUdkZ2ZzMkJ2SmIvMlhwOHA1aXVEVmZaWG1ocHBvK2QwUmFuMVA5VEtkalY0QUJ3QWdLWHhKM2pjUVRxRS8ySVJmcXduUGY4aXROOGFGWmxWM1RKUFllRDN5V0U3SVQ1NUd6NkVpalVwQzdhS2Vvb2hUYjR3MmZwb3g1OHdXb0YzU05wNnNLNmpEZmlBVUdFSFlKOXBqVT08L1NpZ25hdHVyZT4NCjwvTGljZW5zZT4=";
    }
    
}
