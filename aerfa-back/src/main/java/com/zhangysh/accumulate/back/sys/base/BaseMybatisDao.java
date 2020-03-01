package com.zhangysh.accumulate.back.sys.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhangysh.accumulate.back.sys.dao.MybatisQuerySqlDao;

/*****
 * mybatis执行基本sql语句的基础方法
 * @author zhangysh
 * @date 2019年7月2日
 *****/
@Repository
public class BaseMybatisDao {

	@Autowired
	private MybatisQuerySqlDao mybatisQuerySqlDao;
	
	/***
	 * 执行自定义查询sql查出结果
	 * @param sql 执行查询纯sql语句
	 */
	public List<LinkedHashMap<String, Object>> listBySql(String sql){
		return mybatisQuerySqlDao.customQuerySqlSelect(sql);
	}
	
	/***
	 * 执行自定义查询带参数方法
	 * @param sql 执行查询带参数sql语句,例如:select * from aefsys_org where id > #{id} 必须用#
	 * @param paramMap 参数组装集合,例如: paramMap.put("id", 1);
	 */
	public List<LinkedHashMap<String, Object>> listBySqlWithParam(String sql,Map<String,Object> paramMap){
		Map<String,Object> sqlParamMap=new HashMap<String,Object>();
		sqlParamMap.put("sql", sql);
		sqlParamMap.putAll(paramMap);
		return mybatisQuerySqlDao.customQuerySqlSelectWithParam(sqlParamMap);
	}
	
	/****
	 * 执行自定义插入sql
	 * @param sql 纯sql语句
	 * @return 新增条数
	 ***/
	public int insertBySql(String sql) {
		return mybatisQuerySqlDao.customQuerySqlInsert(sql);
	}
	
	/****
	 * 执行自定义插入sql且带参数
	 * @param sql 执行查询带参数sql语句,例如:insert into aefsys_org(status) values(#{status}),必须用#
	 * @param paramMap 参数组装集合,例如: paramMap.put("status", 1);
	 * @return 新增条数
	 ***/
	public int insertBySqlWithParam(String sql,Map<String,Object> paramMap) {
		Map<String,Object> sqlParamMap=new HashMap<String,Object>();
		sqlParamMap.put("sql", sql);
		sqlParamMap.putAll(paramMap);
		return mybatisQuerySqlDao.customQuerySqlInsertWithParam(sqlParamMap);
	}
	
	/****
	 * 执行自定义修改sql
	 * @param sql 纯sql语句
	 * @return 修改条数
	 ***/
	public int updateBySql(String sql) {
		return mybatisQuerySqlDao.customQuerySqlUpdate(sql);
	}
	
	/****
	 * 执行自定义修改sql且带参数
	 * @param sql 执行查询带参数sql语句,例如:update aefsys_org set status=#{status} where id=#{id} 必须用#
	 * @param paramMap 参数组装集合,例如: paramMap.put("status", 2); paramMap.put("id", 6);
	 * @return 修改条数
	 ***/
	public int updateBySqlWithParam(String sql,Map<String,Object> paramMap) {
		Map<String,Object> sqlParamMap=new HashMap<String,Object>();
		sqlParamMap.put("sql", sql);
		sqlParamMap.putAll(paramMap);
		return mybatisQuerySqlDao.customQuerySqlUpdateWithParam(sqlParamMap);
	}
	
	/****
	 * 执行自定义删除sql
	 * @param sql 纯sql语句
	 * @return 删除条数
	 ***/
	public int deleteBySql(String sql) {
		return mybatisQuerySqlDao.customQuerySqlDelete(sql);
	}
	
	/****
	 * 执行自定义删除sql且带参数
	 * @param sql 执行查询带参数sql语句,例如:delete from aefsys_org where id=#{id} 必须用#
	 * @param paramMap 参数组装集合,例如: paramMap.put("id", 6);
	 * @return 删除条数
	 ***/
	public int deleteBySqlWithParam(String sql,Map<String,Object> paramMap) {
		Map<String,Object> sqlParamMap=new HashMap<String,Object>();
		sqlParamMap.put("sql", sql);
		sqlParamMap.putAll(paramMap);
		return mybatisQuerySqlDao.customQuerySqlDeleteWithParam(sqlParamMap);
	}
}
