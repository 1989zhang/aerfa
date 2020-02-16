package com.zhangysh.accumulate.back.sys.base.aspect;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.DataPermission;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据权限切面实现方法
 *
 * @author zhangysh
 * @date 2020年02月16日
 */
@Aspect
@Component
public class DataPermissionAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataPermissionAspect.class);

    @Autowired
    private IRedisRelatedService redisRelatedService;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataPermission";

    // 配置织入点
    @Pointcut("@annotation(com.zhangysh.accumulate.back.sys.base.aspect.annotation.DataPermission)")
    public void dataPermissionPointCut() {
    }

    @Before("dataPermissionPointCut()")
    public void doBefore(JoinPoint point){
        handleDataPermission(point);
    }

    /**
     * 数据权限过滤初步处理
     ***/
    public void handleDataPermission(final JoinPoint joinPoint) {
        // 获得注解
        DataPermission controllerDataPermission = getAnnotationDataPermission(joinPoint);
        if (controllerDataPermission == null) {
            return;
        }
        // 获取当前的用户和参数
        AefsysPerson currentPerson = new AefsysPerson();
        Object[] argsObj=joinPoint.getArgs();
        for(Object obj:argsObj) {
            if (obj instanceof HttpServletRequest) {
                HttpServletRequest request=(HttpServletRequest)obj;
                String aerfatoken= HttpStorageUtil.getToken(request);
                Map<String, Object> redisSessionMap=redisRelatedService.getRedisSessionMapByToken(aerfatoken);
                if(StringUtil.isNotEmpty(redisSessionMap)) {
                    String personObjectJson = redisSessionMap.get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON) + "";
                    if(StringUtil.isNotEmpty(personObjectJson)) {
                        currentPerson= JSON.parseObject(personObjectJson, AefsysPerson.class);;
                    }
                }
            }
        }
        // 不是超级管理员才进行数据权限过滤
        //if(){
            dataPermissionFilter(joinPoint, currentPerson, controllerDataPermission.tableNameIdentify());
        //}
    }

    /**
     * 数据权限过滤正式处理
     *
     * @param joinPoint 切入点目标
     */
    public static void dataPermissionFilter(JoinPoint joinPoint, AefsysPerson currentPerson, String tableNameIdentify){
        logger.info(joinPoint.toString());
        logger.info(currentPerson.toString());
        logger.info(tableNameIdentify.toString());
    }

    /**
     * 是否存在数据权限注解，如果存在就获取
     */
    private DataPermission getAnnotationDataPermission(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(DataPermission.class);
        }
        return null;
    }

}
