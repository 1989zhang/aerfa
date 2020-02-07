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

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
