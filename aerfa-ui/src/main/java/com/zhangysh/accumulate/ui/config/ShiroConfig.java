package com.zhangysh.accumulate.ui.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zhangysh.accumulate.ui.plugins.shiro.UserRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;

/**
 * shiro配置文件
 *
 * @author zhangysh
 * @date 2020年02月17日
 */
@Configuration
public class ShiroConfig {

    //身份认证失败返回出现的登录页面地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;
    //权限认证失败返回出现的页面地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("mySecurityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        // Shiro连接资源的定义
        LinkedHashMap<String, String> filterDefinitionMap = new LinkedHashMap<String, String>();
        // 对静态资源static设置匿名访问不拦截
        filterDefinitionMap.put("/favicon.ico**", "anon");
        filterDefinitionMap.put("/aerfa/**", "anon");

        // 退出 quit地址，shiro去清除session,执行后会直接跳转到设置的url；不走doSysQuit
        filterDefinitionMap.put("/quit", "logout");

        // 不需要登录（身份认证成功）放行的访问可参照如下
        filterDefinitionMap.put("/", "anon");//登录
        filterDefinitionMap.put("/login", "anon");//登录
        filterDefinitionMap.put("/login/*", "anon");//登录
        filterDefinitionMap.put("/sys/login/check", "anon");//账号密码校验

        //开启权限控制的连接在controller更加直观，所以不在此处添加
        //filterDefinitionMap.put("/comm/info_publish/save_add", "perms[comm:infoPublish:add]");

        // 所有请求需要认证
        filterDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name="mySecurityManager")
    public SecurityManager getdefaultDefaultWebSecurityManager(@Qualifier("myUserRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
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

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启Shiro注解通知器，不然@RequiresPermissions无效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("mySecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
