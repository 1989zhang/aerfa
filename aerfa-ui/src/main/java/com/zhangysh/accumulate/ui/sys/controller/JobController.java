package com.zhangysh.accumulate.ui.sys.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysJobVo;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysJobDto;
import com.zhangysh.accumulate.ui.sys.service.IJobService;

/**
 * 定时任务调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Controller
@RequestMapping("/sys/job")
public class JobController {

    private String prefix="/sys/job";//返回界面路径即前缀

	@Autowired
	private IJobService jobService;
	
	/**
	 * 跳转到sys定时任务页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的定时任务页面
	 ****/
	@RequestMapping(value="/to_job")
	public String toSysJob(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/job";
	}
	
	/****
	 * 获取展示定时任务列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysJob job) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysJobDto jobDto=new AefsysJobDto();
		jobDto.setPageInfo(pageInfo);jobDto.setJob(job);
		return jobService.getList(aerfatoken,jobDto);
	}
	
	/**
	 * 跳转到sys定时任务新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的定时任务对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param job 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysJob job) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return jobService.saveAdd(aerfatoken, job);
	}
	
	/****
	 * 修改定时任务，先获取定时任务信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retJobStr=jobService.getSingle(aerfatoken, id);
		AefsysJobVo jobVo=JSON.parseObject(retJobStr,AefsysJobVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("job", jobVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除定时任务对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return jobService.deleteJobByIds(aerfatoken, ids);
    }
}