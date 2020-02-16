package com.zhangysh.accumulate.back.sys.base.aspect.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解，注解到controller
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /** 表名标识用来查找数据权限sql配置 **/
    String tableNameIdentify() default "";
}
