package com.zhangysh.accumulate.back.support.service;

import java.util.List;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;

/**
 * 代码生成 服务层
 *@author 创建者：zhangysh
 *@date 2018年9月16日
 */
public interface IGenerateCodeService
{
	/**
     * 根据条件查询数据库表信息，有表名称是like,其他也是like
     * @param pageInfo 分页对象
     * @param tableInfo 查询条件表信息
     * @return 数据库表列表
     */
	 BsTableDataInfo listPageTables(BsTablePageInfo pageInfo,TableInfo tableInfo);

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
     List<ColumnInfo> getTableColumnsByName(String tableName);
     
    /**
     * 生成代码
     * @param codeParamDto 代码相关参数，例如作者，时间等。已封装为一个对象
     * @return 数据
     */
    byte[] generatorCode(GenerateCodeParamDto codeParamDto);
     
}
