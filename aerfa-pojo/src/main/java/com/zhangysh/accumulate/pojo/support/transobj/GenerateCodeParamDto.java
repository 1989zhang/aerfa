package com.zhangysh.accumulate.pojo.support.transobj;

import java.io.Serializable;

/*****
 * 代码生成功能，前后台参数传递对象
 * @author zhangysh
 * @date 2019年5月12日
 *****/
public class GenerateCodeParamDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 表全名  **/
	private String tableName;
	/** 自动删除表名的前缀表达式:用它获取类名等，DO是不去除的 **/
	private String reomveTablePrefix;
	/** 默认包路径 **/
	private String packageName;
	/** 代码创建者 **/
	private String author;

	/** 查询列 **/
	private String searchColumns;
	/** 唯一验证列  **/
	private String onlyCheckColumns;
	/** 列表展示列  **/
	private String tableShowColumns;
	/** 下拉字典列  **/
	private String pullDicColumns;
	/** 一一对应数据字典类型  **/
	private String pullDicTypeColumns;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getReomveTablePrefix() {
		return reomveTablePrefix;
	}

	public void setReomveTablePrefix(String reomveTablePrefix) {
		this.reomveTablePrefix = reomveTablePrefix;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(String searchColumns) {
		this.searchColumns = searchColumns;
	}

	public String getOnlyCheckColumns() {
		return onlyCheckColumns;
	}

	public void setOnlyCheckColumns(String onlyCheckColumns) {
		this.onlyCheckColumns = onlyCheckColumns;
	}

	public String getTableShowColumns() {
		return tableShowColumns;
	}

	public void setTableShowColumns(String tableShowColumns) {
		this.tableShowColumns = tableShowColumns;
	}

	public String getPullDicColumns() {
		return pullDicColumns;
	}

	public void setPullDicColumns(String pullDicColumns) {
		this.pullDicColumns = pullDicColumns;
	}

	public String getPullDicTypeColumns() {
		return pullDicTypeColumns;
	}

	public void setPullDicTypeColumns(String pullDicTypeColumns) {
		this.pullDicTypeColumns = pullDicTypeColumns;
	}
}
