package com.zhangysh.accumulate.ui.webim.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoInviteDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimTipsInfoVo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoDto;
import com.zhangysh.accumulate.ui.webim.service.ITipsInfoService;

/**
 * 提示消息调用相关方法
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
@Controller
@RequestMapping("/webim/tips_info")
public class TipsInfoController {

    private String prefix="/webim/tips_info";//返回界面路径即前缀

	@Autowired
	private ITipsInfoService tipsInfoService;
	
	/**
	 * 跳转到webim提示消息页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的提示消息页面
	 ****/
	@RequestMapping(value="/to_msgbox/{sid}")
	public String toWebimMsgbox(HttpServletRequest request, ModelMap modelMap,@PathVariable("sid") Long sid) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		modelMap.addAttribute("prefix",prefix);
		return "webim/module/msgbox";
	}
	
	/****
	 * 获取展示提示消息列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/get_msgbox")
    @ResponseBody
	public String getWebimMsgbox(HttpServletRequest request, ModelMap modelMap,Long sid,Integer page) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefwebimTipsInfo tipsInfo=new AefwebimTipsInfo();
		tipsInfo.setToPersonId(sid);
		BsTablePageInfo pageInfo=new BsTablePageInfo();
		pageInfo.setPageNum(page);
		pageInfo.setPageSize(WebimDefineConstant.WEBIM_MSGBOX_PAGE_LIMIT_NUMBER);
		AefwebimTipsInfoDto tipsInfoDto=new AefwebimTipsInfoDto();
		tipsInfoDto.setTipsInfo(tipsInfo);
		tipsInfoDto.setPageInfo(pageInfo);
        return tipsInfoService.getWebimMsgbox(aerfatoken,tipsInfoDto);
	}

	/**
	 * 处理提示消息,接收邀请信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 ****/
	@RequestMapping(value="/accept_invite")
	@ResponseBody
	public String acceptInvite(HttpServletRequest request, ModelMap modelMap, AefwebimTipsInfoInviteDto tipsInfoInviteDto) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return tipsInfoService.acceptInvite(aerfatoken,tipsInfoInviteDto);
	}


	/**
	 * 处理提示消息,拒绝邀请信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 ****/
	@RequestMapping(value="/refuse_invite")
	@ResponseBody
	public String refuseInvite(HttpServletRequest request, ModelMap modelMap,AefwebimTipsInfoInviteDto tipsInfoInviteDto) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return tipsInfoService.refuseInvite(aerfatoken,tipsInfoInviteDto);
	}
}