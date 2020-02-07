package com.zhangysh.accumulate.pojo.iqa.dataobj;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 提问问题数据对象，对应表： aefiqa_question
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public class AefiqaQuestion extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 所属单位 **/
	private Long belongOrgId;
	/** 所属分类 **/
	private Long categoryId;
	/** 解答回答id **/
	private Long answerId;
	/** 问题内容 **/
	private String content;
	/** 标准问题1是标准0否相似 **/
	private Long standard;
	/** 命中次数 **/
	private Long hitCounts;
	/** 有帮助次数 **/
	private Long helpCounts;
	/** 没有有帮助次数 **/
	private Long noHelpCounts;
	/** 状态1有效0无效 **/
	private Long status;
	/** 排序号 **/
	private Long orderNo;
	
	
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
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getStandard() {
		return standard;
	}
	public void setStandard(Long standard) {
		this.standard = standard;
	}
	public Long getHitCounts() {
		return hitCounts;
	}
	public void setHitCounts(Long hitCounts) {
		this.hitCounts = hitCounts;
	}
	public Long getHelpCounts() {
		return helpCounts;
	}
	public void setHelpCounts(Long helpCounts) {
		this.helpCounts = helpCounts;
	}
	public Long getNoHelpCounts() {
		return noHelpCounts;
	}
	public void setNoHelpCounts(Long noHelpCounts) {
		this.noHelpCounts = noHelpCounts;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	

	@Override
	public String toString() {
		return "AefiqaQuestion [id=" + id + ",belongOrgId=" + belongOrgId + ",categoryId=" + categoryId + ",answerId=" + answerId + ",content=" + content + ",standard=" + standard + ",hitCounts=" + hitCounts + ",helpCounts=" + helpCounts + ",noHelpCounts=" + noHelpCounts + ",status=" + status + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
