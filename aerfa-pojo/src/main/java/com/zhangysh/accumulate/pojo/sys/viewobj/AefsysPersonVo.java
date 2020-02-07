package com.zhangysh.accumulate.pojo.sys.viewobj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

/*****
 * 展示新增修改个人的模型对象，和展示更多属性，所以拓展此VO
 * @author zhangysh
 * @date 2018年8月29日
 *****/
public class AefsysPersonVo extends AefsysPerson{

	private static final long serialVersionUID = 1L;
	
	private String orgName;//单位名称
	private String address;//关联的默认full联系地址

	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
