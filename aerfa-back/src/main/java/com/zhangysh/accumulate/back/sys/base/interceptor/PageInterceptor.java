package com.zhangysh.accumulate.back.sys.base.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mybatis 自定义分页拦截组件
 * @author zhangysh
 * @date 2018年9月14日
 */

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class PageInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)arg0.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
		MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
		String id = mappedStatement.getId();
		//拦截哪些查询方法，名称是mapper的xml配置对应的方法id，这里以xx结尾
		if(id.endsWith("ByPage")||id.contains("list")) {
			logger.info("分页拦截器拦截到方法：{}",id);
			BoundSql boundSql = statementHandler.getBoundSql();
			String sql = boundSql.getSql();
			String countSql = "select count(*) from(" + sql + ")t";
			Connection conn = (Connection)arg0.getArgs()[0];
			PreparedStatement statement = conn.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(statement);
			ResultSet rs = statement.executeQuery();
			Object obj=boundSql.getParameterObject();
			Map<String, Object> paraMap=(Map<String, Object>) obj;//参数以Map的形式存在
			Pagination page =null;
			//当参数的名称为page时，获取分页条件对象
			for (Map.Entry<String, Object> entry : paraMap.entrySet()) { 
				String key = entry.getKey().toString();  
				if("page".equals(key)) {
					page = (Pagination)entry.getValue();
					if(rs.next()) {
						page.setTotalNumber(rs.getInt(1));
					}
				}
			}
            //mysql添加分页limit如下
			String pageSql = sql + " limit " + (page.getCurrentPage() - 1) * page.getPageNumber() + "," + page.getPageNumber();
			metaObject.setValue("delegate.boundSql.sql", pageSql);
		    
			//如果是oracle则执行如下方法
			/*String pageSql = "select * from ( select U.*,rownum rn from (" + sql +") U where rownum <= "+ page.getCurrentPage() * page.getPageNumber()+") where rn > "+(page.getCurrentPage() - 1) * page.getPageNumber(); 
			metaObject.setValue("delegate.boundSql.sql", pageSql);*/
		}
		return arg0.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}

}
