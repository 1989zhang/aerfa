package com.zhangysh.accumulate.back.iqa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.back.iqa.service.IAnswerService;
import com.zhangysh.accumulate.back.iqa.service.IReplyService;
import com.zhangysh.accumulate.back.iqa.service.IQuestionService;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;

/*****
 * 智能问答，回复相关方法的实现
 * @author zhangysh
 * @date 2020年2月3日
 *****/
@Service
public class ReplyServiceImpl implements IReplyService{

	@Autowired
	private IQuestionService questionService;
	@Autowired
	private IAnswerService answerService;
	
	//这个智能问答目前实现得十分简单，后续加入人工智能
	@Override
	public String getReply(Long orgId, String askContent) {
		AefiqaQuestion searchQuestion=new AefiqaQuestion();
		searchQuestion.setBelongOrgId(orgId);
		searchQuestion.setContent(askContent);
		List<AefiqaQuestion> listQuestion=questionService.listQuestion(searchQuestion);
		if(StringUtil.isNotEmpty(listQuestion)) {
			for(int i=0;i<listQuestion.size();i++) {
				AefiqaQuestion question=listQuestion.get(i);
				//有答案的才返回
				if(StringUtil.isNotNull(question.getAnswerId())) {
					return answerService.getAnswerById(question.getAnswerId()).getContent();
				}
			}
		}
		return "";
	}

}
