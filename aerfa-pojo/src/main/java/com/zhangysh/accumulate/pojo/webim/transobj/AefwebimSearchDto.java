package com.zhangysh.accumulate.pojo.webim.transobj;

import java.io.Serializable;

/*****
 * 搜索朋友和群组信息的参数对象，layim要求字段不能修改字段
 * @author zhangysh
 * @date 2019年10月20日
 *****/
public class AefwebimSearchDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String type;//搜索类型:好友friend,群组group
	private String value;//搜索条件参数
	private Long page;//显示数据当前页数
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}

}
