package com.zhangysh.accumulate.pojo.support.dataobj;

import java.io.Serializable;

/**
 * 数据库表的列信息
 * @author zhangysh
 * @date 2018年9月16日
 */
public class ColumnInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    /** 字段名称 */
    private String columnName;

    /** 字段类型、存储数据类型 */
    private String dataType;

    /** 列描述 */
    private String columnComment;

    /** Java属性类型 */
    private String attrType;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName;在拼装VO属性的set方法后要拼装此名称*/
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName;属性、前台展示等都要用到此名称 */
    private String attrname;

    
    
    /** 是否是查询列 */
    private Boolean searchColumn;
    /** 唯一验证列 */
    private Boolean onlyCheckColumn;
    /** 表单展示列***/
    private Boolean tableShowColumn;
    /** 表单展示列的宽度百分百，100除以显示列数取整**/
    private Double tableShowColumnWidth;
    /** 下拉字典列***/
    private Boolean pullDicColumn;
    /** 下拉字典 数据字典类型***/
    private String pullDicType;
    
    
    
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrname() {
		return attrname;
	}
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	public Boolean getSearchColumn() {
		return searchColumn;
	}
	public void setSearchColumn(Boolean searchColumn) {
		this.searchColumn = searchColumn;
	}
	public Boolean getOnlyCheckColumn() {
		return onlyCheckColumn;
	}
	public void setOnlyCheckColumn(Boolean onlyCheckColumn) {
		this.onlyCheckColumn = onlyCheckColumn;
	}
	public Boolean getTableShowColumn() {
		return tableShowColumn;
	}
	public void setTableShowColumn(Boolean tableShowColumn) {
		this.tableShowColumn = tableShowColumn;
	}
	public Double getTableShowColumnWidth() {
		return tableShowColumnWidth;
	}
	public void setTableShowColumnWidth(Double tableShowColumnWidth) {
		this.tableShowColumnWidth = tableShowColumnWidth;
	}
	public Boolean getPullDicColumn() {
		return pullDicColumn;
	}
	public void setPullDicColumn(Boolean pullDicColumn) {
		this.pullDicColumn = pullDicColumn;
	}
	public String getPullDicType() {
		return pullDicType;
	}
	public void setPullDicType(String pullDicType) {
		this.pullDicType = pullDicType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", dataType=" + dataType + ", columnComment=" + columnComment
				+ ", attrType=" + attrType + ", attrName=" + attrName + ", attrname=" + attrname + ", searchColumn="
				+ searchColumn + ", onlyCheckColumn=" + onlyCheckColumn + ", tableShowColumn=" + tableShowColumn
				+ ", pullDicColumn=" + pullDicColumn + ", pullDicType=" + pullDicType + "]";
	}

}
