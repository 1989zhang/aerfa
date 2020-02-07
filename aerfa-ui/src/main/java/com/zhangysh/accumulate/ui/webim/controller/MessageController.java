package com.zhangysh.accumulate.ui.webim.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *聊天内容主方法
 *@author 创建者：zhangysh
 */
@Controller
public class MessageController {

	private Logger logger = LoggerFactory.getLogger(MessageController.class);

	/*@Autowired
	private IMessageService messageService;
	
	@RequestMapping(value="/message/sendinfoget",method=RequestMethod.GET)
    @ResponseBody
    public String sendInfoGet(
    	@RequestParam(value="sessionid",required=true) String sessionid,
        @RequestParam(value="message",required=true) String message){
        Boolean ret=false;
		try {
			logger.info("sendinfoget发送消息:"+sessionid+"|"+message);
			String[] sessionidArr= sessionid.split(",");
			for(int i=0;i<sessionidArr.length;i++) {
				ret = messageService.sendMessageToUser(sessionidArr[i], message);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        if(ret){
            return "发送成功";
        }else{
            return "发送失败";
        }   
    }
	@RequestMapping(value="/message/sendinfopost",method = RequestMethod.POST)
	@ResponseBody
	public String sendInfoPost(@RequestBody String content) {
		Boolean ret=false;
		logger.info("sendInfoPost发送消息:"+content);
		JSONObject jsonObj=JSON.parseObject(content);
		String sessionid=jsonObj.getString("sessionid");
		String message=jsonObj.getString("message");
		try {
			logger.info("sendInfoPost发送消息:"+sessionid+"|"+message);
			String[] sessionidArr= sessionid.split(",");
			for(int i=0;i<sessionidArr.length;i++) {
				ret = messageService.sendMessageToUser(sessionidArr[i], message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        if(ret){
            return "发送成功";
        }else{
            return "发送失败";
        }   
	}*/
	
	/**
	 * 跳转到webim测试页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的测试页面
	 ****/
	@RequestMapping(value="/to_test")
	public String toTest(HttpServletRequest request, ModelMap modelMap) {
		return "/sys/test/index";
	}
}
