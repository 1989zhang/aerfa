package com.zhangysh.accumulate.back.iqa.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.iqa.service.IReplyService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.IqaDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaAskDto;

/*****
 * 智能问答，回复相关方法
 * @author zhangysh
 * @date 2020年2月3日
 *****/
@Controller
@RequestMapping("/iqa")
public class IqaReplyController extends BaseController{
	
	@Autowired 
	private IReplyService replyService;
	
    private static final Logger logger=LoggerFactory.getLogger(IqaReplyController.class);
    
	/****
	 * 根据问题和用户标示ID，获取问题的答案
	 * @param request 请求对象
     * @param askDto 请求的用户标识和问题
     * @return 获取到的答案
	 ****/
	@RequestMapping(value="/reply",method = RequestMethod.POST)
    @ResponseBody
	public String getReply(@RequestBody AefiqaAskDto askDto) {
		Long orgId=1L;
		String replyStr=replyService.getReply(orgId, askDto.getAskContent());
		if(StringUtil.isNotEmpty(replyStr)) {
			return toHandlerResultStr(true, replyStr, null, null);
		}
		return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(IqaDefineConstant.ASK_NO_QUESTION_ANSWER), null);
	}
}
