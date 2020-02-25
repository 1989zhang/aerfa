package com.zhangysh.accumulate.back.sys.base.aspect;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.DataPermission;
import com.zhangysh.accumulate.back.sys.service.IDataPermissionService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
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
    @Autowired
    private IDataPermissionService dataPermissionService;

    //过滤反射对象属性serialVersionUID
    public static final String SERIAL_VERSION_UID = "serialVersionUID";
    //过滤反射对象属性pageInfo
    public static final String PAGE_INFO = "pageInfo";

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
        try {
            logger.info(joinPoint.toString());
            logger.info(currentPerson.toString());
            logger.info(tableNameIdentify.toString());
            //组装追加的sql,要和权限控制一起完成
            //dataPermissionService.listDataPermission()

            String dataPermissionSql="1=1";
            //配置追加的sql参数
            Object[] argsObj = joinPoint.getArgs();
            for (Object obj : argsObj) {
                if (!(obj instanceof HttpServletRequest)) {
                    //通过属性反射得到其查询对象
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String name = field.getName();
                        //非serial和分页对象即为查询对象
                        if(!SERIAL_VERSION_UID.equals(name)&&!PAGE_INFO.equals(name)){
                            field.setAccessible(true); // 私有属性必须设置访问权限
                            Object resultValue = field.get(obj);
                            BaseDataObj baseDataObj=(BaseDataObj)resultValue;
                            Map<String, Object> paramsMap=baseDataObj.getParams();
                            //参数有可能为null要报异常，当为空时创建个查询对象
                            if(paramsMap==null){
                                paramsMap=new HashMap<String, Object>();
                                baseDataObj.setParams(paramsMap);
                            }
                            baseDataObj.getParams().put(MarkConstant.DATA_PERMISSION,dataPermissionSql);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
