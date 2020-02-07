package com.zhangysh.accumulate.back.sys.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/*****
 * 类作用说明
 * @author zhangysh
 * @date 2019年6月23日
 *****/
@Mapper
public interface MybatisQuerySqlDao {
    
	/***
	 * 自定义查询sql查出结果
	 * @param sqlContent sql语句内容
	 * @return 结果集合:为保证查询的字段值有序（存入与取出顺序一致）所以采用LinkedHashMap
	 ***/
	List<LinkedHashMap<String, Object>> customQuerySqlSelect(String sqlContent);
	
	/***
	 * 自定义查询sql查出结果
	 * @param sqlParamMap sql语句和参数封装的Map
	 * @return 结果集合:为保证查询的字段值有序（存入与取出顺序一致）所以采用LinkedHashMap
	 ***/
	List<LinkedHashMap<String, Object>> customQuerySqlSelectWithParam(Map<String,Object> sqlParamMap);
	
	/**
	 * 执行自定义新增sql
	 * @param sqlContent sql语句内容
	 * @return 新增条数
	 ***/
	int customQuerySqlInsert(String sqlContent);
	
	/**
	 * 执行自定义新增sql
	 * @param sqlParamMap sql语句和参数封装的Map
	 * @return 新增条数
	 ***/
	int customQuerySqlInsertWithParam(Map<String,Object> sqlParamMap);
	
	/**
	 * 执行自定义修改sql
	 * @param sqlContent sql语句内容
	 * @return 修改条数
	 ***/
	int customQuerySqlUpdate(String sqlContent);
	
	/**
	 * 执行自定义修改sql
	 * @param sqlParamMap sql语句和参数封装的Map
	 * @return 修改条数
	 ***/
	int customQuerySqlUpdateWithParam(Map<String,Object> sqlParamMap);
	
	/**
	 * 执行自定义删除sql
	 * @param sqlContent sql语句内容
	 * @return 删除条数
	 ***/
	int customQuerySqlDelete(String sqlContent);
	
	/**
	 * 执行自定义删除sql
	 * @param sqlParamMap sql语句和参数封装的Map
	 * @return 删除条数
	 ***/
	int customQuerySqlDeleteWithParam(Map<String,Object> sqlParamMap);
    
}
