package com.zhangysh.accumulate.ui.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*****
 * ui全局配置控制文件
 * @author zhangysh
 * @date 2019年5月29日
 *****/
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer{
	
	@Bean
	public InterceptorConfig interceptorConfig() {
		return new InterceptorConfig();
	}
	
	/***
	 * 跨域是图片服务器nginx配置解决，目前未遇到本系统跨域问题
	 * 全局拦截器,后面深入测试
	 ***/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(interceptorConfig());
		// 拦截配置
		addInterceptor.addPathPatterns("/**");
		// 排除配置
		addInterceptor.excludePathPatterns("/aerfa/**");//排除css和js等
		addInterceptor.excludePathPatterns("/");//排除登录
		addInterceptor.excludePathPatterns("/login/**");//排除登录
	}

}
