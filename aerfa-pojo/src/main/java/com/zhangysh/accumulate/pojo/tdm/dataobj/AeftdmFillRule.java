package com.zhangysh.accumulate.pojo.tdm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 模板填充规则数据对象，对应表： aeftdm_fill_rule
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmFillRule extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 模板id **/
	private Long templateId;
	/** 数据源SQLid **/
	private Long sqlId;
	/** 数据源映射id **/
	private Long fieldId;
	/** excel数据填充所在行 **/
	private Long fillRowNumber;
	/** excel数据填充所在列 **/
	private Long fillColNumber;
	/** word替换文字 **/
	private String replaceChar;
	/** 值填充类型：string numeric date boolean image rqcode二维快速响应码  barcode条形码 **/
	private String showType;
	/** 水平排列:left center right **/
	private String horizontalAlign;
	/** 字体:黑体、宋体等 **/
	private String fontName;
	/** 字体大小:默认12 **/
	private Long fontSize;
	/** 是否加粗:1加粗，默认0不加粗 **/
	private Long isBlock;

	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getSqlId() {
		return sqlId;
	}
	public void setSqlId(Long sqlId) {
		this.sqlId = sqlId;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Long getFillRowNumber() {
		return fillRowNumber;
	}
	public void setFillRowNumber(Long fillRowNumber) {
		this.fillRowNumber = fillRowNumber;
	}
	public Long getFillColNumber() {
		return fillColNumber;
	}
	public void setFillColNumber(Long fillColNumber) {
		this.fillColNumber = fillColNumber;
	}
	public String getReplaceChar() {
		return replaceChar;
	}
	public void setReplaceChar(String replaceChar) {
		this.replaceChar = replaceChar;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getHorizontalAlign() {
		return horizontalAlign;
	}
	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public Long getFontSize() {
		return fontSize;
	}
	public void setFontSize(Long fontSize) {
		this.fontSize = fontSize;
	}
	public Long getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(Long isBlock) {
		this.isBlock = isBlock;
	}
	

	@Override
	public String toString() {
		return "AeftdmFillRule [id=" + id + ",templateId=" + templateId + ",sqlId=" + sqlId + ",fieldId=" + fieldId + ",fillRowNumber=" + fillRowNumber + ",fillColNumber=" + fillColNumber + ",replaceChar=" + replaceChar + ",showType=" + showType + ",horizontalAlign=" + horizontalAlign + ",fontName=" + fontName + ",fontSize=" + fontSize + ",isBlock=" + isBlock + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
