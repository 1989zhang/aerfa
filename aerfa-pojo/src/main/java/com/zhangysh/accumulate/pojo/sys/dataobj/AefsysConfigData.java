package com.zhangysh.accumulate.pojo.sys.dataobj;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 配置数据对象，对应表： aefsys_config_data
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
public class AefsysConfigData extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 参数编码 **/
	private String dataCode;
	/** 参数值 **/
	private String dataValue;
	/** 排序号 **/
	private Long orderNo;
	/** 备注描述 **/
	private String remark;

	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefsysConfigData [id=" + id + ",dataCode=" + dataCode + ",dataValue=" + dataValue + ",orderNo=" + orderNo + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
