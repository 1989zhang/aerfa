package com.zhangysh.accumulate.back.iqa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.common.constant.IqaDefineConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.util.NumberUtil;
import com.zhangysh.accumulate.pojo.iqa.viewobj.AefiqaKnowledgeVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.back.iqa.service.IAnswerService;
import com.zhangysh.accumulate.back.iqa.service.IReplyService;
import com.zhangysh.accumulate.back.iqa.service.IQuestionService;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;

import com.zhangysh.accumulate.common.util.DateOperate;

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
	@Autowired
	private IPersonService personService;

	//这个智能问答目前实现得十分简单，后续加入人工智能
	@Override
	public String getReply(Long orgId, String askContent) {
		AefiqaQuestion searchQuestion=new AefiqaQuestion();
		searchQuestion.setBelongOrgId(orgId);
		searchQuestion.setContent(askContent);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(IqaDefineConstant.QUESTION_MARK_REPLY, SysDefineConstant.DIC_COMMON_STATUS_YES);
		searchQuestion.setParams(params);

		List<AefiqaQuestion> listQuestion=questionService.listMatchContentQuestion(searchQuestion);
		boolean hitTar=false;//命中标记
		String replyStr="";//返回的回答内容
		AefiqaQuestion hitQuestion=new AefiqaQuestion();//后续命中或没命中处理

		if(listQuestion.size()>0){//有命中的情况
			int retQuestionCur=NumberUtil.getRangeInt(0,listQuestion.size()-1);
			AefiqaQuestion question=listQuestion.get(retQuestionCur);
			hitTar=true;
			replyStr=answerService.getAnswerById(question.getAnswerId()).getContent();
			hitQuestion=question;
		}

		if(!hitTar){
			hitQuestion.setContent(askContent);
			hitQuestion.setStandard(SysDefineConstant.DIC_COMMON_STATUS_YES);
			hitQuestion.setBelongOrgId(orgId);
			hitQuestion.setCreateTime(DateOperate.getCurrentUtilDate());
		}
		dealWithQuestionHit(hitTar,hitQuestion);
		return replyStr;
	}

	@Override
	public AefsysPersonVo getPerson(String iqaToken){
		Long personId=Long.valueOf(iqaToken.substring(32,iqaToken.length()));
		AefsysPersonVo personVo=personService.getPersonWithOrgNameById(personId);
		return personVo;
	}

	@Override
	public boolean dealWithQuestionHit(boolean hitTar, AefiqaQuestion question){
		//命中的话，原问题命中率+1
		if(hitTar){
			AefiqaKnowledgeVo knowledgeVo=questionService.getQuestionById(question.getId());
			AefiqaQuestion updateQuestion=new AefiqaQuestion();
			BeanUtils.copyProperties(knowledgeVo,updateQuestion);
			updateQuestion.setHitCounts(updateQuestion.getHitCounts()==null?1L:updateQuestion.getHitCounts()+1L);
			questionService.updateQuestion(updateQuestion);
		}else{
			questionService.insertQuestion(question);
		}
		return true;
	}
}
