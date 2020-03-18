package com.zhangysh.accumulate.pojo.webim.transobj;

import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;

import java.io.Serializable;

/**
 * 提示消息数据传输，处理接受和拒绝申请，
 * 因为好友通过要新增互为好友所在好友组且传回所在好友组id，所以拓展此属性
 * 
 * @author zhangysh
 * @date 2020年03月18日
 */
public class AefwebimTipsInfoInviteDto extends AefwebimTipsInfo{

	private static final long serialVersionUID = 1L;

	/** 所属群组id **/
	private Long groupId;

	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
