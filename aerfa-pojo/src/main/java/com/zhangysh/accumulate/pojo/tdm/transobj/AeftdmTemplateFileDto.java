package com.zhangysh.accumulate.pojo.tdm.transobj;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;

/*****
 * 模板定义模板文件数据传输
 * @author zhangysh
 * @date 2019年6月20日
 *****/
public class AeftdmTemplateFileDto extends AeftdmTemplate {

	private static final long serialVersionUID = 1L;
	
	private String fileBase64Data;//文件流进行base64编码的字符串
	
	public String getFileBase64Data() {
		return fileBase64Data;
	}
	public void setFileBase64Data(String fileBase64Data) {
		this.fileBase64Data = fileBase64Data;
	}
	
	
}
