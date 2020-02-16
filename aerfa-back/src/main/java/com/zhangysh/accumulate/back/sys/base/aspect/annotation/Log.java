package com.zhangysh.accumulate.back.sys.base.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自动记录系统操作日志注解，注解到controller
 *
 * @author 创建者：zhangysh
 * @date 2018年10月28日
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log{

	/**类型 debug info warn error**/
    String type() default "info";
    
    /**来源 manage后台用户 mobile手机端用户 other其它**/
    String channel() default "manage";
    
    /**所属系统名称**/
    String system() default "";

    /**所属模块名称**/
    String module() default "";
    
    /**所属菜单名称**/
    String menu() default "";
    
    /**所属方法名称**/
    String button() default "";
    
    /***是否保存请求的参数，如果保存以JSON格式保存，参数值过大会截取保存********/
    boolean saveParam() default false;
}
