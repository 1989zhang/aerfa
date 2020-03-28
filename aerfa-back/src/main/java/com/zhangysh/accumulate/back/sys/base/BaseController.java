package com.zhangysh.accumulate.back.sys.base;

import javax.servlet.ServletContext;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangysh.accumulate.back.sys.util.SpringContextUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.pojo.CodeMsg;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.StringUtil;

/*****
 * controller相关通用方法
 * @author zhangysh
 * @date 2018年10月28日
 *****/
public class BaseController {

	/**
	 *获取service的具体实现bean，由于要启动加载用public标记
	 *@param beanName 实现名称，service用标记：@Service(value = "DirFile")
	 *@param context ServletContext对象
	 */
	public Object getBean(String beanName, ServletContext context) {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		Object obj = wac.getBean(beanName);
		return obj;
	}

	/**
	 *获取service的具体实现bean，由于要启动加载用public标记
	 *@param beanName 实现名称，service用标记：@Service(value = "DirFile")
	 */
	public Object getBean(String beanName) {
		Object bean = SpringContextUtil.getBean(beanName);
		return bean;
	}

	/***
	 * 根据数据影响条数返回字符串：
	 * 结果条数大于0为ResultVo操作成功，否则操作失败。
	 * @param rows 影响结果条数
	 *****/
	protected String toHandlerResultStr(int rows) {
		if(rows>0) {
			return JSON.toJSONString(ResultVo.success("操作成功"));
		}else {
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_HANDLER_ERROR));
		}
	}

	/***
	 * 根据成功失败标志返回字符串：如果是long就加判断转为boolean型
	 * true为ResultVo操作成功，false操作失败。
	 * @param mark 成功失败标记
	 *****/
	protected String toHandlerResultStr(boolean mark) {
		if( mark ) {
			return JSON.toJSONString(ResultVo.success("操作成功"));
		}else {
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_HANDLER_ERROR));
		}
	}
	
	/***
	 * 根据标记boolean的值返回字符串：
	 * @param ret 标记true为成功，false为失败
	 * @param trueRet 标记true时返回的对象，可传null，默认为"操作成功"
	 * @param falseCodeMsg 标记false时返回的失败信息，可传null。两个错误都没有，默认SYS_DATA_HANDLER_ERROR操作失败
	 * @param falseStr 标记false时返回的失败提示详细信息，两个错误都没有，默认SYS_DATA_HANDLER_ERROR操作失败
	 *****/
	protected String toHandlerResultStr(boolean ret,Object trueRet,CodeMsg falseCodeMsg,String falseStr) {
		if(ret) {
			if(StringUtil.isNotNull(trueRet)){
				return JSON.toJSONString(ResultVo.success(trueRet));
			}
			return JSON.toJSONString(ResultVo.success("操作成功"));
		}else {
			if(StringUtil.isNotNull(falseCodeMsg)) {
				return JSON.toJSONString(ResultVo.error(falseCodeMsg));
			}else if(StringUtil.isNotEmpty(falseStr)) {
				return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_OPERATE_ERROR.fillArgs(falseStr)));
			}
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_HANDLER_ERROR));
		}
	}
	
	/***
	 * 把返回对象按日期格式化并返回正确对象
	 * @param retObject 返回的对象
	 * @param datePattern 对象日期格式
	 * @param wirteNull 是否输出null字符
	 * **/
	protected String toHandlerResultStr(Object retObject,String datePattern,boolean wirteNull) {
		if( StringUtil.isNotEmpty(datePattern) && wirteNull ) {
			return JSON.toJSONStringWithDateFormat(ResultVo.success(retObject),datePattern,SerializerFeature.WriteMapNullValue);
		}else if( StringUtil.isNotEmpty(datePattern) ){
			return JSON.toJSONStringWithDateFormat(ResultVo.success(retObject),datePattern);
		}
		return JSON.toJSONString(ResultVo.success(retObject));
	}
			
	/***
	 * 根据数据条数返回唯一性校验结果字符串：
	 * 结果条数大于0为ResultVo唯一验证成功，否则验证失败。
	 * @param rows 结果条数
	 *****/
	protected String toUniqueResultStr(int rows) {
		if(rows>0) {
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_REPEAT_ERROR));
		}else {
			return JSON.toJSONString(ResultVo.success("数据不重复"));
		}
	}
	
	
}
