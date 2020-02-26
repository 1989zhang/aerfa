package com.zhangysh.accumulate.pojo.ufs.dataobj;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传二进制内容文件模型对象，独立出来好拓展为一个微服务，并和业务独立分开。
 * @author zhangysh
 * @date 2019年5月14日
 */
public class AefufsUploadFileBlob implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fileName;//文件传入时候的名称
	private String fileType;//文件类型jpg doc 等
	private byte[] fileBlob;//二进制文件内容
	private String implName;//保存接口实现名称
	private String createBy;//创建者名称，也可做：创建者UUID或账号标记使用
	private Date createTime;//创建时间
	/** 创建人员ID标记，为以后验证上传下载权限，次数等 **/
	private Long createUserId;
	/** 创建人员单位ID **/
	private Long createOrgId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getFileBlob() {
		return fileBlob;
	}
	public void setFileBlob(byte[] fileBlob) {
		this.fileBlob = fileBlob;
	}
	public String getImplName() {
		return implName;
	}
	public void setImplName(String implName) {
		this.implName = implName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AefufsUploadFileBlob [id=" + id + ",fileName=" + fileName + ",fileType=" + fileType + ",fileBlob=" + fileBlob + ",implName=" + implName + ",createBy=" + createBy + ",createUserId=" + createUserId + ",createOrgId=" + createOrgId + ",createTime=" + createTime + ",]";
	}
	
}
