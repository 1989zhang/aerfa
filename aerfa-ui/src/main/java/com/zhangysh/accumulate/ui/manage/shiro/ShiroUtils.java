package com.zhangysh.accumulate.ui.manage.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro基础工具类
 *
 * @author zhangysh
 * @date 2020年02月17日
 */
public class ShiroUtils {

    /**
     * 获取当前主体对象
     **/
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前session对象
     ***/
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 当前主体对象退出登录
     **/
    public static void logout() {
        getSubjct().logout();
    }

    /***
     * 获取token,不能获取用户了因为只存了后台token。
     * 前后台分离来说：后台暴露用户，前台存用户不安全。
     **/
    public static String getToken(){
        Subject tokenSubject= SecurityUtils.getSubject();
        return (String)getSubjct().getPrincipal();
    }
}
