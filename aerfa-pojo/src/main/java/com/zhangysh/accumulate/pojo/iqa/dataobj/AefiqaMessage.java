package com.zhangysh.accumulate.pojo.iqa.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseMessageObj;

/**
 * 智能问答消息记录数据对象，对应表： aefiqa_message
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public class AefiqaMessage extends BaseMessageObj{

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "AefiqaMessage [id=" + id + ",fromPersonId=" + fromPersonId + ",toPersonId=" + toPersonId + ",type=" + type + ",content=" + content + ",status=" + status + ",sendTime=" + sendTime + ",handleTime=" + handleTime + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
