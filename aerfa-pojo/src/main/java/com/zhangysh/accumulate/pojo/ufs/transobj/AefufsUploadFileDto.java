package com.zhangysh.accumulate.pojo.ufs.transobj;

import java.io.Serializable;

/**
 *保存文件数据传输对象
 *@author 创建者：zhangysh
 *@date 2018年9月10日
 */
public class AefufsUploadFileDto implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private String fileBase64Data;//文件流进行base64编码的字符串
	private String fileName;
	private String createBy;//创建者标记如账号等，为以后验证上传下载权限，次数等
	
	public String getFileBase64Data() {
		return fileBase64Data;
	}
	public void setFileBase64Data(String fileBase64Data) {
		this.fileBase64Data = fileBase64Data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}
