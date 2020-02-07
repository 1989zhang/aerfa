package com.zhangysh.accumulate.pojo.support.dataobj;

import java.util.List;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;
/**
 * 数据库表信息
 * @author zhangysh
 * @date 2018年9月16日
 */
public class TableInfo extends BaseDataObj{
	
    private static final long serialVersionUID = 1L;
    
    /** 表全名称,例如:aefsys_person,aefsys_data_dic */
    private String tableName;
    
    /**去掉表名前缀的表名,jsp文件名和请求地址要用.例如:person,data_dic****/
    private String removePrefixTableName;
    
    /** 表描述 */
    private String tableComment;

    /** 表的主键列信息 */
    private ColumnInfo primaryKey;

    /** 表的列名(不包含主键) */
    private List<ColumnInfo> columns;
    
    /** 类名(第一个字母大写);service和interface的拼装要用，例如PersonDao */
    private String className;

    /** 类名(第一个字母小写);用来拼装类后面的变量等，例如 personDao*/
    private String classname;

    /**因为DO名称要和数据库表名一致，不去掉前缀，所有有单独的类名称,Dto自动生成查询对象要用*/
    /**DO类名(第一个字母大写),例如 AefsysPerson***/
    private String doclassName;
    
    /**DO类名(第一个字母小写)***/
    private String doclassname;
    
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getRemovePrefixTableName() {
		return removePrefixTableName;
	}

	public void setRemovePrefixTableName(String removePrefixTableName) {
		this.removePrefixTableName = removePrefixTableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public ColumnInfo getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ColumnInfo primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public String getDoclassName() {
		return doclassName;
	}

	public void setDoclassName(String doclassName) {
		this.doclassName = doclassName;
	}

	public String getDoclassname() {
		return doclassname;
	}

	public void setDoclassname(String doclassname) {
		this.doclassname = doclassname;
	}

	@Override
	public String toString() {
		return "TableInfo [tableName=" + tableName + ", tableComment=" + tableComment + ", primaryKey=" + primaryKey
				+ ", columns=" + columns + ", className=" + className + ", classname=" + classname + ", doclassName="
				+ doclassName + ", doclassname=" + doclassname + "]";
	}

}
