package com.zhangysh.accumulate.pojo.ufs.transobj;

import java.io.Serializable;

/**
 *保存文件数据传输对象
 *@author 创建者：zhangysh
 *@date 2018年9月10日
 */
public class AefufsUploadFileDto implements Serializable{
 
	private static final long serialVersionUID = 1L;

	/** 文件流进行base64编码的字符串 **/
	private String fileBase64Data;
	/** 文件名称 **/
	private String fileName;
	/** 创建者名称，也可做：创建者UUID或账号标记使用 **/
	private String createBy;
	/** 创建人员ID标记，为以后验证上传下载权限，次数等 **/
	private Long createUserId;
	/** 创建人员单位ID **/
	private Long createOrgId;
	/** 自定义实现方式可传空 **/
	private AefufsCustomRealizeDto customRealize;


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
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCreateOrgId() {
		return createOrgId;
	}
	public void setCreateOrgId(Long createOrgId) {
		this.createOrgId = createOrgId;
	}
	public AefufsCustomRealizeDto getCustomRealize() {
		return customRealize;
	}
	public void setCustomRealize(AefufsCustomRealizeDto customRealize) {
		this.customRealize = customRealize;
	}

}
