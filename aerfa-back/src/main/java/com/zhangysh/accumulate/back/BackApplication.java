package com.zhangysh.accumulate.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*****
 *EnableDiscoveryClient 去找服务注册中心,默认已开启的
 *EnableFeignClients 前台可开启FeignClient(name="xxxx"),对HTTP请求Request对象和Response对象进行封装
 *****/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}
}
