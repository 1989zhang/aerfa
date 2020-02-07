package com.zhangysh.accumulate.back.adi.dydatasource;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.adi.dataobj.AefadiDataSource;

/**
 *@author 创建者：zhangysh
 */
@Service
public class DynamicSqlRepository {

	Logger logger=LoggerFactory.getLogger(DynamicSqlRepository.class);
	
	@Autowired
	@Qualifier("dynamicJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dynamicDataSource")
	private DynamicDataSource dynamicDataSource;

	
	public List<Map<String, Object>> doSelect(AefadiDataSource dataSource, String sql, Map<String, Object> params) throws Exception {
		if(StringUtil.isNotNull(dataSource)) {
			dynamicDataSource.createDataSourceWithCheck(dataSource);
			DataSourceHolder.setDataSource(dataSource.getDataSourceId());
		}
		logger.info("执行sql查询doSelect:" + sql);
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, params);
		return resultList;
	}
	
}
