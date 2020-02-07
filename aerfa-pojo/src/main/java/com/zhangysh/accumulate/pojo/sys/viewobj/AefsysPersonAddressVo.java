package com.zhangysh.accumulate.pojo.sys.viewobj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;

/**
 * 个人联系地址前台展示对象
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
public class AefsysPersonAddressVo extends AefsysPersonAddress{

	private static final long serialVersionUID = 1L;
	
	private String prefixAddress;//联系地省市县拼接的地址前缀，用于前台新增和展示
	
	public String getPrefixAddress() {
		return prefixAddress;
	}
	public void setPrefixAddress(String prefixAddress) {
		this.prefixAddress = prefixAddress;
	}
	
	
}
