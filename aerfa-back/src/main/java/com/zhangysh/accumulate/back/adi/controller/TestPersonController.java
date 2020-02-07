package com.zhangysh.accumulate.back.adi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhangysh.accumulate.back.adi.service.ITestPersonService;
/**
 *@author 创建者：zhangysh
 */
@Controller
public class TestPersonController {

	@Autowired
	private ITestPersonService personService;
	
	@RequestMapping(value="/person/info")
	@ResponseBody
	public String getPersonInfo() {
		return personService.getPersonInfo();
	}
}
