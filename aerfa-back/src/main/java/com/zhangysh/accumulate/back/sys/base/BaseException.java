package com.zhangysh.accumulate.back.sys.base;

import java.util.Arrays;

/*****
 * 基础异常类
 * @author zhangysh
 * @date 2019年6月23日
 *****/
public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
     * 所属功能模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 默认错误消息
     */
    private String defaultMessage;
    
    public BaseException(String module, String code, Object[] args, String defaultMessage){
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String defaultMessage){
        this(null, null, null, defaultMessage);
    }
    
    @Override
    public String getMessage(){
        String message = null;
        if (message == null){
            message = defaultMessage;
        }
        return message;
    }

    /*****get和set方法***/
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	@Override
	public String toString() {
		return "BaseException [module=" + module + ", code=" + code + ", args=" + Arrays.toString(args)
				+ ", defaultMessage=" + defaultMessage + "]";
	}
}
