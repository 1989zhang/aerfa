package com.zhangysh.accumulate.pojo.iqa.viewobj;

import java.util.List;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;

/*****
 * 知识展示对象,两个内容分开,且包含非标准问法集合
 * @author zhangysh
 * @date 2019年12月25日
 *****/
public class AefiqaKnowledgeVo extends AefiqaQuestion{

	private static final long serialVersionUID = 1L;
	
	private String questionContent;
	private String answerContent;
	private String categoryName;
	private List<AefiqaQuestion> nonstandard;
	
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<AefiqaQuestion> getNonstandard() {
		return nonstandard;
	}
	public void setNonstandard(List<AefiqaQuestion> nonstandard) {
		this.nonstandard = nonstandard;
	}
	
}
