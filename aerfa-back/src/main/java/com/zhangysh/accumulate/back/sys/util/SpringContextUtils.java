package com.zhangysh.accumulate.back.sys.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring管理环境中的bean
 *
 * @author zhangysh
 * @date 2020年02月12日
 */
public class SpringContextUtils implements ApplicationContextAware {
	
	public static ApplicationContext applicationContext; 

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * 获取ApplicationContext对象
	 */
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	/**
	 * 获取对象
	 *
	 * @param name
	 * @return Object
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}


}