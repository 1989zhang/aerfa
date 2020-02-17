package com.zhangysh.accumulate.ui.config;

import com.zhangysh.accumulate.ui.plugins.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro配置文件
 *
 * @author zhangysh
 * @date 2020年02月17日
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("mySecurityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // Shiro连接资源的定义
        LinkedHashMap<String, String> filterDefinitionMap = new LinkedHashMap<String, String>();
        // 对静态资源static设置匿名访问不拦截
        filterDefinitionMap.put("/favicon.ico**", "anon");
        filterDefinitionMap.put("/aerfa/**", "anon");

        //需要权限访问资源
        filterDefinitionMap.put("/*/*/*", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name="mySecurityManager")
    public DefaultWebSecurityManager getdefaultDefaultWebSecurityManager(@Qualifier("myUserRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name="myUserRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

}
