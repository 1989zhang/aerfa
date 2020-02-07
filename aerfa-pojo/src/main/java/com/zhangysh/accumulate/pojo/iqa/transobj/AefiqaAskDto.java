package com.zhangysh.accumulate.pojo.iqa.transobj;

import java.io.Serializable;

/*****
 * 提问问题请求到后台的传输对象
 * @author zhangysh
 * @date 2020年2月3日
 *****/
public class AefiqaAskDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String iqaToken;//请求用户标记32位
	private String askContent;//请求问题内容
	
	public String getIqaToken() {
		return iqaToken;
	}
	public void setIqaToken(String iqaToken) {
		this.iqaToken = iqaToken;
	}
	public String getAskContent() {
		return askContent;
	}
	public void setAskContent(String askContent) {
		this.askContent = askContent;
	}
	
	
}
