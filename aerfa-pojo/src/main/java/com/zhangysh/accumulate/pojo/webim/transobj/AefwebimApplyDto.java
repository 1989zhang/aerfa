package com.zhangysh.accumulate.pojo.webim.transobj;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;

/*****
 * 好友申请数据传输对象
 * @author zhangysh
 * @date 2019年10月29日
 *****/
public class AefwebimApplyDto extends AefwebimFriend{

	private static final long serialVersionUID = 1L;
	
	/** 请求备注 **/
	private String remark;
	/** 添加类型是friend还是group **/
	private String type;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
