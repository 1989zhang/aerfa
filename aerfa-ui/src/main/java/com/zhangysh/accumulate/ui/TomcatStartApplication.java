package com.zhangysh.accumulate.ui;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*****
 * tomcat打war包要指定启动类，不然无法启动springboot
 * @author zhangysh
 * @date 2018年8月7日
 *****/
public class TomcatStartApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(UiApplication.class);
    }
}
