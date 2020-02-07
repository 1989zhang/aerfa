package com.zhangysh.accumulate.back.sys.base.aspect;

import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.service.IOperLogService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.LogConstant;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;


/**
 *@author 创建者：zhangysh
 */
@Aspect
@Component
@EnableAsync//异步线程执行开启
public class LogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    
    // 配置织入点,里面无需任何代码
    @Pointcut("@annotation(com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log)")
    public void logPointCut()
    {    }
    
    /**
     * 拦截操作:controller有返回值后，记录日志
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturn(JoinPoint joinPoint){
        handleLog(joinPoint, null);
    }

    /**
     * 拦截操作：controller有异常信息时，记录日志
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterException(JoinPoint joinPoint, Exception e){
        handleLog(joinPoint, e);
    }
    
    //异步线程执行
    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e)
    {
        try{   
        	// 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null){return;}
            
        	AefsysOperLog operLog =new AefsysOperLog();
        	//处理Log注解的参数信息
        	setLogAnnotationParm(operLog,controllerLog);
        	//处理方法上面相关的参数信息
        	setMethodRelatedParm(operLog,controllerLog,joinPoint);
        	//处理异常信息
        	if (e != null) {
        		operLog.setLogType(LogConstant.LOG_TYPE_ERROR);
                operLog.setLogContent(StringUtil.substring(e.getMessage(), 0, 2000));
            }
    		operLogService.insertOperLog(operLog);
        }catch(Exception exp) {
        	//操作日志保存异常，记录到本地异常日志
        	exp.printStackTrace();
        	logger.error("操作日志LogAspect方法handleLog出现异常，异常信息:{}", exp.getMessage());
        }
    }
    
    /**
     *通过切面接入点获取是否存在Log注解，如果存在就获取注解对象
     *@param joinPoint 切面接入点
     *@return Log注解对象
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null){
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /****
     *设置注解@Log后面带的参数
     *@param operLog 要保存的操作日志对象
     *@param controllerLog long注解对象包含的参数
     ***/
    private void setLogAnnotationParm(AefsysOperLog operLog,Log controllerLog) {
    	operLog.setLogType(controllerLog.type());
    	operLog.setChannel(controllerLog.channel());
    	operLog.setOperSystem(controllerLog.system());
    	operLog.setOperModule(controllerLog.module());
    	operLog.setOperMenu(controllerLog.menu());
    	operLog.setOperButton(controllerLog.button());
    }
    
    /****
     *设置方法上相关的参数
     *@param operLog 要保存的操作日志对象
     *@param joinPoint 切面接入点，用此获取方法名等
     ***/
    private void setMethodRelatedParm(AefsysOperLog operLog,Log controllerLog,JoinPoint joinPoint) {
    	operLog.setOperTime(DateOperate.getCurrentUtilDate());
    	String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        //解析方法里面的参数
        StringBuffer parmStringBuffer=new StringBuffer();
        Object[] argsObj=joinPoint.getArgs();
        for(Object obj:argsObj) {
        	if(obj instanceof HttpServletRequest) {
        		//下面的为空判断主要是为了支持一些没传token却要记录日志的方法
        		HttpServletRequest request=(HttpServletRequest)obj;
        		String aerfatoken=HttpStorageUtil.getToken(request); 
        		Map<String, Object> redisSessionMap=redisRelatedService.getRedisSessionMapByToken(aerfatoken);
        		if(StringUtil.isNotEmpty(redisSessionMap)) {
        			AefsysPerson operPerson=new AefsysPerson();
        			AefsysOrg operOrg=new AefsysOrg();
        			String personObjectJson = redisSessionMap.get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
        			String orgObjectJson = redisSessionMap.get(CacheConstant.TOKENMODEL_SESSION_KEY_ORG)+"";
        			if(StringUtil.isNotEmpty(personObjectJson)) {
        				operPerson=JSON.parseObject(personObjectJson, AefsysPerson.class);;
        			}
        			if(StringUtil.isNotEmpty(orgObjectJson)) {
        				operOrg=JSON.parseObject(orgObjectJson, AefsysOrg.class);;
        			}
            		operLog.setOperName(operPerson.getPersonName());
            		operLog.setOrgName(operOrg.getFullName());
        		}
        		//考虑到是UI请求back所以不设置ip和location,后面是否拓展
        	}else {
        		//当注解设置保存参数时，才转参数json
        		if(controllerLog.saveParam()) {
        			parmStringBuffer.append(JSON.toJSONString(obj));
        		}
        	}
        }
        //设置日志对象属性
        operLog.setOperMethod(className + "." + methodName + "()");
        String parmString=parmStringBuffer.toString();
        if(controllerLog.saveParam()&&!StringUtil.isEmpty(parmString)) {
        	operLog.setOperParam(StringUtil.substring(parmString,0,2000));
        }
       
    }
}
