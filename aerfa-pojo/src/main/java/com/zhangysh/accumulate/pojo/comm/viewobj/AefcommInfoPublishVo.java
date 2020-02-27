package com.zhangysh.accumulate.pojo.comm.viewobj;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;

import java.util.Date;

/**
 * 发布前台展示对象
 *
 * @author zhangysh
 * @date 2020年02月16日
 */
public class AefcommInfoPublishVo extends AefcommInfoPublish{

	private static final long serialVersionUID = 1L;

	/** 信息内容：保存的时候用 **/
	private String content;
	/** 发布日期字符串：因为modelMap存对象日期显示不对，所以单独列出来，也为了实现保存日期对应 **/
	private String pubDateStr;
	/** 根据全路径地址查看信息发布html **/
	private String fullViewUrl;

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPubDateStr() {
		return pubDateStr;
	}
	public void setPubDateStr(String pubDateStr) {
		this.pubDateStr = pubDateStr;
	}
	
	public String getFullViewUrl() {
		return fullViewUrl;
	}

	public void setFullViewUrl(String fullViewUrl) {
		this.fullViewUrl = fullViewUrl;
	}
}
