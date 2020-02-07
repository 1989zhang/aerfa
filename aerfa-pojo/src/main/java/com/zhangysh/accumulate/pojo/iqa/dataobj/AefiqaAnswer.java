package com.zhangysh.accumulate.pojo.iqa.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 解答回答数据对象，对应表： aefiqa_answer
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public class AefiqaAnswer extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	

	/** 所属单位 **/
	private Long belongOrgId;
	/** 所属分类 **/
	private Long categoryId;
	/** 回答内容 **/
	private String content;
	

	public Long getBelongOrgId() {
		return belongOrgId;
	}
	public void setBelongOrgId(Long belongOrgId) {
		this.belongOrgId = belongOrgId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AefiqaAnswer [id=" + id + ",belongOrgId=" + belongOrgId + ",categoryId=" + categoryId + ",content=" + content + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
