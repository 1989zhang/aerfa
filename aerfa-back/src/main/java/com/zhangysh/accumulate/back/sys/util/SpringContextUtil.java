package com.zhangysh.accumulate.back.sys.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * spring管理环境中的bean
 *
 * @author zhangysh
 * @date 2020年02月12日
 */
@Service("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware {

	//Spring应用上下文环境
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取ApplicationContext对象
	 */
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	/**
	 * 根据bean的唯一标识名称字符串获取对象
	 *
	 * @param name
	 * @return Object
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 根据bean的接口类获取对象
	 ***/
	public static <T> T getBean(Class<T> clz){
		return applicationContext.getBean(clz);
	}


}