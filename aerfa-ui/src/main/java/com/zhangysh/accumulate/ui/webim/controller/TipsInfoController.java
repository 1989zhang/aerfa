package com.zhangysh.accumulate.ui.webim.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	public String getWebimMsgbox(HttpServletRequest request, ModelMap modelMap,Long sid,Long page) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		List<AefwebimMsgboxVo> msgboxList=new ArrayList<AefwebimMsgboxVo>();
		
		AefwebimMsgboxVo msgboxVo1=new AefwebimMsgboxVo();
		msgboxVo1.setId(76L);
		msgboxVo1.setContent("申请添加你为好友");
		msgboxVo1.setUid(168L);
		msgboxVo1.setFrom(166488L);
		msgboxVo1.setFrom_group(0L);
		msgboxVo1.setType(1L);
		msgboxVo1.setRemark("有问题要问");
		msgboxVo1.setRead(1L);
		msgboxVo1.setTime("刚刚");
		AefwebimFriendVo friendVo1=new AefwebimFriendVo();
		friendVo1.setId(166488L);
		friendVo1.setAvatar("http://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100");
		friendVo1.setUsername("李彦宏");
		msgboxVo1.setUser(friendVo1);
		
		AefwebimMsgboxVo msgboxVo2=new AefwebimMsgboxVo();
		msgboxVo2.setId(75L);
		msgboxVo2.setContent("申请添加你为好友");
		msgboxVo2.setUid(168L);
		msgboxVo2.setFrom(347592L);
		msgboxVo2.setFrom_group(10L);
		msgboxVo2.setType(1L);
		msgboxVo2.setRemark("你好啊!");
		msgboxVo2.setRead(1L);
		msgboxVo2.setTime("刚刚");
		AefwebimFriendVo friendVo2=new AefwebimFriendVo();
		friendVo2.setId(347592L);
		friendVo2.setAvatar("http://q.qlogo.cn/qqapp/101235792/B78751375E0531675B1272AD994BA875/100");
		friendVo2.setUsername("麻花疼");
		msgboxVo2.setUser(friendVo2);
		
		
		AefwebimMsgboxVo msgboxVo3=new AefwebimMsgboxVo();
		msgboxVo3.setId(62L);
		msgboxVo3.setContent("雷军 拒绝了你的好友申请");
		msgboxVo3.setUid(168L);
		msgboxVo3.setTime("10天前");
		
		AefwebimMsgboxVo msgboxVo4=new AefwebimMsgboxVo();
		msgboxVo4.setId(60L);
		msgboxVo4.setContent("马小云 已经同意你的好友申请");
		msgboxVo4.setUid(168L);
		msgboxVo4.setTime("10天前");
		
		AefwebimMsgboxVo msgboxVo5=new AefwebimMsgboxVo();
		msgboxVo5.setId(61L);
		msgboxVo5.setContent("贤心 已经同意你的好友申请");
		msgboxVo5.setUid(168L);
		msgboxVo5.setTime("10天前");
		
		msgboxList.add(msgboxVo1);msgboxList.add(msgboxVo2);msgboxList.add(msgboxVo3);msgboxList.add(msgboxVo4);msgboxList.add(msgboxVo5);
		return JSON.toJSONString(ResultVo.success(msgboxList)); 
	}
}