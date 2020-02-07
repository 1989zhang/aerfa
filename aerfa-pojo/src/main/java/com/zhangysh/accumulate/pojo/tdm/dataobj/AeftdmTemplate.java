package com.zhangysh.accumulate.pojo.tdm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 模板定义数据对象，对应表： aeftdm_template
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmTemplate extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 模板名称和说明用途一样 **/
	private String explainName;
	/** 传入文件名称 **/
	private String fileName;
	/** 存储文件名称 **/
	private String saveName;
	/** 文件类型 **/
	private String fileType;
	/** 备注描述 **/
	private String remark;

	public String getExplainName() {
		return explainName;
	}
	public void setExplainName(String explainName) {
		this.explainName = explainName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AeftdmTemplate [id=" + id + ",explainName=" + explainName + ",fileName=" + fileName + ",saveName=" + saveName + ",fileType=" + fileType + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
