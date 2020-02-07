package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据字典数据传输条件对象
 * 
 * @author zhangysh
 * @date 2019年04月14日
 */
public class AefsysDataDicDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysDataDic dataDic;
	private BsTablePageInfo pageInfo;
	
	public AefsysDataDic getDataDic() {
		return dataDic;
	}
	public void setDataDic(AefsysDataDic dataDic) {
		this.dataDic = dataDic;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
