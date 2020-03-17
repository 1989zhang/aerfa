package com.zhangysh.accumulate.common.pojo;

import com.zhangysh.accumulate.common.constant.MarkConstant;

/*****
 * controller返回给调用方对象
 * 只要code不为0都是错误，和CodeMsg配合使用
 * @author zhangysh
 * @date 2018年7月7日
 *****/
public class ResultVo<T> {
	
	private Integer code;
	private String msg;
	private T data;

	/**
	 * 成功时候的调用,code为0
	 * @param data 成功信息对象
	 * */
	public static  <T> ResultVo<T> success(T data){
		return new ResultVo<T>(MarkConstant.MARK_RESULT_VO_SUCESS,data);//默认成功是0
	}
	/**
	 * 提示信息时候的调用,code为1
	 * @param data 提示信息对象
	 * */
	public static  <T> ResultVo<T> warn(T data){
		return new ResultVo<T>(1,data);//默认提示是1
	}
	/**
	 * 失败时候的调用，传入CodeMsg对象
	 * @param codeMsg 错误信息codeMsg
	 * */
	public static  <T> ResultVo<T> error(CodeMsg codeMsg){
		return new ResultVo<T>(codeMsg);
	}
	
	private ResultVo(Integer code,T data) {
		this.code=code;
		this.data = data;
	}
	private ResultVo(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
