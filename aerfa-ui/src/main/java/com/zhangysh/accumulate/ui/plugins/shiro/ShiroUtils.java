package com.zhangysh.accumulate.ui.plugins.shiro;

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
}
