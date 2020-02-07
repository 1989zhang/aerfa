package com.zhangysh.accumulate.ui.comm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.ui.sys.controller.LogController;

/**
 *信息发布相关方法
 *@author 创建者：zhangysh
 *@date 2019年4月4日
 */
@Controller
@RequestMapping("/comm/info_publish")
public class InfoPublishController {

	private String prefix="/comm/info_publish";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(LogController.class);

	/**
	 *跳转到common下信息发布页面
	 *@param request 请求对象
	 *@param modelMapl spring的mvc返回对象
	 *@return templates下的信息发布页面
	 ****/
	@RequestMapping(value="/to_info_publish")
	public String toSysOperLog(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/info_publish";
	}
	
	/**
	 *获取信息发布列表信息，且分页显示
	 *@param request 请求对象
	 *@param modelMapl spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequestMapping(value="/info_publish_list")
	@ResponseBody
	public BsTableDataInfo getInfoPublishList(HttpServletRequest request, ModelMap modelMap,String pageSize,String pageNum,String orderByColumn,String isAsc,AefcommInfoPublish searchInfoPublish) {
		logger.info("分页信息:当前"+pageNum+"页，每页"+pageSize+"条");
		logger.info("排序列"+orderByColumn+","+isAsc);
		logger.info("查询对象:标题"+searchInfoPublish.getTitle());
		
		List<AefcommInfoPublish> list=new ArrayList<AefcommInfoPublish>();
		for(int i=0;i<10;i++) {
			AefcommInfoPublish vo=new AefcommInfoPublish();
			vo.setTitle("标题"+i);
			vo.setInfoType("信息公示");
			list.add(vo);
		}
		BsTableDataInfo rspData = new BsTableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(100);
          
		return rspData;
	}
}
