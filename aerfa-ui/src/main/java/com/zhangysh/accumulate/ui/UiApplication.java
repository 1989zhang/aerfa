package com.zhangysh.accumulate.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*****
 * UI启动类
 *EnableDiscoveryClient 去找服务注册中心,默认已开启的
 *EnableFeignClients 前台可开启FeignClient(name="xxxx"),对HTTP请求Request对象和Response对象进行封装
 * @author zhangysh
 * @date 2018年7月25日
 *****/
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class UiApplication {

	/*****
	 *启动主方法
	 *****/
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}

}
