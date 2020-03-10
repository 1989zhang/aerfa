package com.zhangysh.accumulate.normal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.AsposeWordUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.MD5;
import com.zhangysh.accumulate.common.util.StringUtil;

/**
 *@author 创建者：zhangysh
 */
public class UsuallyTest {
	
	public static void main(String args[]) {
		
		try {
			//word模板转换测试建议保留
			/*String templateFileFullPath="D://testaerfa//template//exportFile//11.doc";
			String outFileFullPath="D://testaerfa//template//exportFile//1583459171799276.doc";
			String pdfFileFullPath="D://testaerfa//template//exportFile//22.pdf";
			FileOutputStream fos=new FileOutputStream(outFileFullPath);
			FileInputStream fis=new FileInputStream(new File(templateFileFullPath));
			HWPFDocument wordDocument = new HWPFDocument(fis);
			Range range = wordDocument.getRange(); 
			range.replaceText("${JIAFANGNAME}", "我是一棵树"); 
			wordDocument.write(fos);
			wordDocument.close();
			fis.close();
			fos.close();*/

			String outFileFullPath="D://testaerfa//template//exportFile//22.doc";
			String pdfFileFullPath="D://testaerfa//template//exportFile//22.pdf";
			AsposeWordUtil.wordToPdf(outFileFullPath, pdfFileFullPath);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
