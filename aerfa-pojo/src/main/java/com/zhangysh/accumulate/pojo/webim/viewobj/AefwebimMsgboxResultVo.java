package com.zhangysh.accumulate.pojo.webim.viewobj;

import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.pojo.CodeMsg;

/*****
 * 即时通webim消息盒子输出结果显示对象,只针对
 * @author zhangysh
 * @date 2020年03月16日
 *****/
public class AefwebimMsgboxResultVo<T>{

	private Integer code;
	private String msg;
	private T data;
	private Integer pages;


	/**
	 * 成功时候的调用,code为0
	 * @param data 成功信息对象
	 * */
	public static <T> AefwebimMsgboxResultVo<T> success(T data,Integer pages){
		return new AefwebimMsgboxResultVo<T>(MarkConstant.MARK_RESULT_VO_SUCESS,data,pages);//默认成功是0
	}

	/**
	 * 失败时候的调用，传入CodeMsg对象
	 * @param codeMsg 错误信息codeMsg
	 * */
	public static  <T> AefwebimMsgboxResultVo<T> error(CodeMsg codeMsg){
		return new AefwebimMsgboxResultVo<T>(codeMsg);
	}

	private AefwebimMsgboxResultVo(Integer code,T data,Integer pages) {
		this.code=code;
		this.data = data;
		this.pages = pages;
	}

	private AefwebimMsgboxResultVo(CodeMsg codeMsg) {
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
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
}
