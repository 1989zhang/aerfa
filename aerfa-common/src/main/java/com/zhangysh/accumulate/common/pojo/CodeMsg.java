package com.zhangysh.accumulate.common.pojo;

/*****
 * 封装错误信息对象
 * ResultVo的error配合使用返回错误信息，code建议大于1000开始
 * @author zhangysh
 * @date 2018年7月7日
 *****/
public class CodeMsg {

	private Integer code;
	private String msg;
	
	public CodeMsg( Integer code,String msg ) {
		this.code = code;
		this.msg = msg;
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
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
}
