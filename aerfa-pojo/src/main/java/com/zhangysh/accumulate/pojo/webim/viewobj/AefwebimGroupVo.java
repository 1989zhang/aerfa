package com.zhangysh.accumulate.pojo.webim.viewobj;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;

/**
 * 群组前台展示对象
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public class AefwebimGroupVo extends AefwebimGroup{

	private static final long serialVersionUID = 1L;

	private String type;//还有申请完成添加至面板，凭属性分类
	private String groupname;//群名称(layim要求字段不能修改字段)

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
