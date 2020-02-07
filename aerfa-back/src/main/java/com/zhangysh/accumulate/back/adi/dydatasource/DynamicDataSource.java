package com.zhangysh.accumulate.back.adi.dydatasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.zhangysh.accumulate.back.adi.exception.DataSourceException;
import com.zhangysh.accumulate.pojo.adi.dataobj.AefadiDataSource;

/**
 * 动态数据源切换主方法
 * @author 创建者：zhangysh
 */
@Service
public class DynamicDataSource extends AbstractRoutingDataSource{

    private static final Logger logger=LoggerFactory.getLogger(DynamicDataSource.class);

	private Map<Object, Object> dynamicTargetDataSources;//缓存所有连接的数据源
	private Object dynamicDefaultTargetDataSource; //当前默认数据源
	@Override
	protected Object determineCurrentLookupKey() {
		String datasource = DataSourceHolder.getDataSource();
		if (this.dynamicTargetDataSources.containsKey(datasource)) {
			logger.info("---当前数据源：" + datasource + "---");
		} else {
			logger.info("---未创建新的数据源，将使用默认数据源---");
		}
		return datasource;
	}
    /**
     * 设置当前和父默认数据源
     ***/
	@Override
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
	}

	/**
	 * 设置当前和父所有数据源
	 ***/
	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		this.dynamicTargetDataSources = targetDataSources;
	} 
	
	/**
	 * 测试数据源连接是否有效
	 ***/
	public boolean testDatasource(String key, String driveClass, String url, String username, String password) {
		try {
			Class.forName(driveClass);
			DriverManager.getConnection(url, username, password);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	/***
	 * 创建数据源
	 ****/
	public boolean createDataSource(String key, String driveClass, String url, String username, String password) {
		try {
			//非正常返回，排除连接不上的错误
			if(!testDatasource(key,driveClass,url,username,password)) {
				return false;
			}
			DruidDataSource druidDataSource = new DruidDataSource();
			druidDataSource.setName(key);
			druidDataSource.setDriverClassName(driveClass);
			druidDataSource.setUrl(url);
			druidDataSource.setUsername(username);
			druidDataSource.setPassword(password);
			druidDataSource.setInitialSize(5);
			druidDataSource.setMinIdle(5);
			druidDataSource.setMaxActive(50);
			druidDataSource.setMaxWait(3000);
			druidDataSource.setRemoveAbandoned(true); //超过时间限制是否回收
			druidDataSource.setRemoveAbandonedTimeout(180); //连接超时时间；单位为秒。180秒=3分钟

			druidDataSource.setTimeBetweenEvictionRunsMillis(60000);//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
			druidDataSource.setMinEvictableIdleTimeMillis(300000);//配置一个连接在池中最小生存的时间，单位是毫秒 
			druidDataSource.init();
			this.dynamicTargetDataSources.put(key, druidDataSource);//加入map
			this.setTargetDataSources(this.dynamicTargetDataSources);//将map赋值给父类的TargetDataSources
			super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
			logger.info(key+"数据源初始化成功");
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	/***
	 * 删除数据源
	 * */ 
	public boolean delDatasources(String key) {
		if (this.dynamicTargetDataSources.containsKey(key)) {
			Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
			for (DruidDataSource dataSource : druidDataSourceInstances) {
				if (key.equals(dataSource.getName())) {
					this.dynamicTargetDataSources.remove(key);
					DruidDataSourceStatManager.removeDataSource(dataSource);
					this.setTargetDataSources(this.dynamicTargetDataSources);// 将map赋值给父类的TargetDataSources
					super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	/***
	 * 先检查再创建数据源，建议用此方法创建数据源对象
	 * @throws Exception 
	 ****/
	public void createDataSourceWithCheck(AefadiDataSource dataSource) throws Exception {
		String dataSourceId = dataSource.getDataSourceId();
		logger.info("准备创建数据源"+dataSourceId);
		if (this.dynamicTargetDataSources.containsKey(dataSourceId)) {
			logger.info("数据源"+dataSourceId+"之前已经创建，准备测试数据源是否正常...");
			DataSource druidDataSource = (DataSource) this.dynamicTargetDataSources.get(dataSourceId);
			boolean rightFlag = true;
			Connection connection = null;
			try {
				connection = druidDataSource.getConnection();
				logger.info("数据源"+dataSourceId+"正常");
			} catch (Exception e) {
				logger.error(e.getMessage()); //把异常信息打印到日志文件
				logger.info("缓存数据源"+dataSourceId+"已失效，准备删除...");
				if(this.delDatasources(dataSourceId)) {
					logger.info("缓存数据源删除成功");
				} else {
					logger.info("缓存数据源删除失败");
				}
				rightFlag = false;
			} finally {
				if(null != connection) {
					connection.close();
				}
			}
			if(rightFlag) {
				logger.info("不需要重新创建数据源");
				return;
			} else {
				logger.info("准备重新创建数据源...");
				createDataSource(dataSource);
				logger.info("重新创建数据源完成");
			}
		} else {
			createDataSource(dataSource);
		}
	}
	
	/****
	 * 根据参数创建数据源
	 ***/
	private  void createDataSource(AefadiDataSource dataSource) throws Exception {
		String dataSourceId = dataSource.getDataSourceId();
		logger.info("准备创建数据源"+dataSourceId);
		String dataBaseType = dataSource.getType();
		String username = dataSource.getUsername();
		String password = dataSource.getPassword();
		String url = dataSource.getUrl();
		String driveClass = "";
		if("mysql".equalsIgnoreCase(dataBaseType)) {
			driveClass = "com.mysql.jdbc.Driver";
		} else if("oracle".equalsIgnoreCase(dataBaseType)){
			driveClass = "oracle.jdbc.driver.OracleDriver";
		}
		if(testDatasource(dataSourceId,driveClass,url,username,password)) {
			boolean result = this.createDataSource(dataSourceId, driveClass, url, username, password);
			if(!result) {
				throw new DataSourceException("数据源"+dataSourceId+"配置正确，但是创建失败");
			}
		} else {
			throw new DataSourceException("数据源配置有错误");
		}
	}
	
	
	/**
	 * @return the dynamicTargetDataSources
	 */
	public Map<Object, Object> getDynamicTargetDataSources() {
		return dynamicTargetDataSources;
	}

	/**
	 * @param dynamicTargetDataSources
	 *            the dynamicTargetDataSources to set
	 */
	public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
		this.dynamicTargetDataSources = dynamicTargetDataSources;
	}

	/**
	 * @return the dynamicDefaultTargetDataSource
	 */
	public Object getDynamicDefaultTargetDataSource() {
		return dynamicDefaultTargetDataSource;
	}

	/**
	 * @param dynamicDefaultTargetDataSource
	 *            the dynamicDefaultTargetDataSource to set
	 */
	public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
		this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
	}
}
