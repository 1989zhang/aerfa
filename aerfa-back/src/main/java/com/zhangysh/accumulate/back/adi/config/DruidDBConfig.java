package com.zhangysh.accumulate.back.adi.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.druid.pool.DruidDataSource;
import com.zhangysh.accumulate.back.adi.dydatasource.DataSourceHolder;
import com.zhangysh.accumulate.back.adi.dydatasource.DynamicDataSource;


/**
 * DruidDBConfig类被@Configuration标注，用作配置信息； DataSource对象被@Bean声明，为Spring容器所管理，
 * 
 * @Primary表示这里定义的DataSource将覆盖其他来源的DataSource。
 * 
 */
@Configuration
@EnableTransactionManagement
public class DruidDBConfig {

	//默认主项目数据库连接信息
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	
	@Bean // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	@Qualifier("mainDataSource")
	public DataSource dataSource() throws SQLException {
		DruidDataSource datasource = new DruidDataSource();
		// 基础连接信息
		datasource.setUrl(this.url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		// 连接池连接信息
		datasource.setInitialSize(5);//配置连接数初始化大小
		datasource.setMinIdle(5);//配置连接数初始化最小
		datasource.setMaxActive(50);//配置连接数初始化最大
		datasource.setMaxWait(3000);//配置获取连接等待超时的时间
		//其他优化配置信息
		datasource.setRemoveAbandoned(true); //是否移除泄露的连接超过时间限制是否回收。
		datasource.setRemoveAbandonedTimeout(180); //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里180秒=3分钟
		datasource.setTimeBetweenEvictionRunsMillis(60000); //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		datasource.setMinEvictableIdleTimeMillis(300000); //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为5分钟300000
		/*暂时不用待测试优化配置
		datasource.setPoolPreparedStatements(true); //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
		datasource.setMaxPoolPreparedStatementPerConnectionSize(50);
		datasource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout=60000");//对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
		datasource.setTestOnBorrow(true); //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
		datasource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		String validationQuery = "select 1 from dual";
		datasource.setValidationQuery(validationQuery); //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
		datasource.setFilters("stat,wall");//属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
		datasource.setKeepAlive(true); //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
		datasource.setLogAbandoned(true); ////移除泄露连接发生是是否记录日志
		*/
		return datasource;
	}
	
	/*初始化默认主数据源*/
	@Bean(name = "dynamicDataSource")
	@Qualifier("dynamicDataSource")
	public DataSource dynamicDataSource() throws SQLException {
		//设置默认数据源
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
	    dynamicDataSource.setDefaultTargetDataSource(dataSource());
		//设置总数据源
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put("mainDataSource", dataSource());
		dynamicDataSource.setTargetDataSources(targetDataSources);
		//线程安全的数据源名称设置,貌似不管用
		DataSourceHolder.setDataSource("mainDataSource");
		return dynamicDataSource;
	}

	/*初始化默认主jdbcTemplate*/
	@Bean(name = "dynamicJdbcTemplate")
	@Qualifier("dynamicJdbcTemplate")
	public NamedParameterJdbcTemplate dynamicJdbcTemplate(@Qualifier("dynamicDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
}
