package com.zhangysh.accumulate.pojo.iqa.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;

/*****
 * 知识传输对象包括:保存提问和回答知识、分页查询知识列表
 * @author zhangysh
 * @date 2019年12月22日
 *****/
public class AefiqaKnowledgeDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//保存填写的标准知识数据传输对象
	private Long id;//即questionId;th:field="*{id}"渲染回id了,只能用这个
	private Long categoryId;
	private String questionContent;
	private String[] unStandardQuestionContent;
	private String answerContent;
	//分页查询知识列表
	private BsTablePageInfo pageInfo;
	private AefiqaQuestion question;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public AefiqaQuestion getQuestion() {
		return question;
	}
	public void setQuestion(AefiqaQuestion question) {
		this.question = question;
	}
	public String[] getUnStandardQuestionContent() {
		return unStandardQuestionContent;
	}
	public void setUnStandardQuestionContent(String[] unStandardQuestionContent) {
		this.unStandardQuestionContent = unStandardQuestionContent;
	}
	
}
