package com.zhangysh.accumulate.back.support.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;

/**
 *代码生成相关方法类
 *@author 创建者：zhangysh
 *@date 2018年9月16日
 */
@Mapper
public interface GenerateCodeDao {
	/**
     * 根据条件查询数据库表信息，有表名称是相等,其他是like
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
     List<TableInfo> listTables(TableInfo tableInfo);

    /**
     * 根据表名称查询信息，表名称是相等
     * @param tableName 表名称
     * @return 表信息
     */
     TableInfo getTableByName(String tableName);

    /**
     * 根据表名称查询列信息，表名称是相等
     * @param tableName 表名称
     * @return 列信息
     */
     List<ColumnInfo> getTableColumnsByTableName(String tableName);
}
