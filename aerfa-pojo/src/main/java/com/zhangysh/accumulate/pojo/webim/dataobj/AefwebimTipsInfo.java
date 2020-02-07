package com.zhangysh.accumulate.pojo.webim.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseMessageObj;

/**
 * 提示消息数据对象，对应表： aefwebim_tips_info
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
public class AefwebimTipsInfo extends BaseMessageObj{

	private static final long serialVersionUID = 1L;
	
	/** 请求备注 **/
	private String remark;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	@Override
	public String toString() {
		return "AefwebimTipsInfo [id=" + id + ", remark=" + remark + ", fromPersonId=" + fromPersonId + ", toPersonId="
				+ toPersonId + ", type=" + type + ", content=" + content + ", status=" + status + ", sendTime="
				+ sendTime + ", handleTime=" + handleTime + ", createBy=" + createBy + ", createTime=" + createTime
				+ ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
	}
	
}
