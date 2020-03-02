package com.zhangysh.accumulate.back.iqa.service;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;

/*****
 * 智能问答，回复相关方法
 * @author zhangysh
 * @date 2020年2月3日
 *****/
public interface IReplyService {

	/***
	 * 根据标识部门id和问题，获取到识别的答案
	 * @param orgId 问题答案所属部门id
	 * @param askContent 问题内容
	 * @return 匹配到的答案，如果没有匹配到就返回空内容字符;
	 * ***/
	String getReply(Long orgId,String askContent);

	/**
	 * 根据token即sessionId获取用户json信息
	 * @param iqaToken 用户session标识
	 * @return 获取到的用户结果
	 ****/
	AefsysPersonVo getPerson(String iqaToken);

	/**
	 * 根据标记处理命中或非命中的问题
	 * @param hitTar 是否命中标记
	 * @param question 问题hitTar为true时候question要有id
	 */
	boolean dealWithQuestionHit(boolean hitTar, AefiqaQuestion question);
}
