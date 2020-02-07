package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 个人联系地址数据对象，对应表： aefsys_person_address
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
public class AefsysPersonAddress extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 个人id **/
	private Long personId;
	/** 联系地址省 **/
	private String province;
	/** 联系地址市 **/
	private String city;
	/** 联系地址县 **/
	private String district;
	/** 联系地址镇 **/
	private String town;
	/** 详细地址 **/
	private String detailAddress;
	/** 全部地址 **/
	private String fullAddress;
	/** 是否默认1是0否 **/
	private Long isDefault;

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public Long getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Long isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "AefsysPersonAddress [id=" + id + ",personId=" + personId + ",province=" + province + ",city=" + city + ",district=" + district + ",town=" + town + ",detailAddress=" + detailAddress + ",fullAddress=" + fullAddress + ",isDefault=" + isDefault + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
