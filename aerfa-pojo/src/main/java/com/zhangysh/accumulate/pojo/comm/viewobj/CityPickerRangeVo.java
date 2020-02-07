package com.zhangysh.accumulate.pojo.comm.viewobj;

/*****
 * citypicker上面的四个分组属性:A-G、H-K、L-S、T-Z，由于不能有减所以转为此四个字符，
 * 最后输出再字符替换为标准，参见src\main\resources\static\sys\myplugins\city-picker\js\city-picker.data.js
 * @author zhangysh
 * @date 2019年5月26日
 *****/
public class CityPickerRangeVo {

	private Object rangeAtoG;
	private Object rangeHtoK;
	private Object rangeLtoS;
	private Object rangeTtoZ;
	
	public Object getRangeAtoG() {
		return rangeAtoG;
	}
	public void setRangeAtoG(Object rangeAtoG) {
		this.rangeAtoG = rangeAtoG;
	}
	public Object getRangeHtoK() {
		return rangeHtoK;
	}
	public void setRangeHtoK(Object rangeHtoK) {
		this.rangeHtoK = rangeHtoK;
	}
	public Object getRangeLtoS() {
		return rangeLtoS;
	}
	public void setRangeLtoS(Object rangeLtoS) {
		this.rangeLtoS = rangeLtoS;
	}
	public Object getRangeTtoZ() {
		return rangeTtoZ;
	}
	public void setRangeTtoZ(Object rangeTtoZ) {
		this.rangeTtoZ = rangeTtoZ;
	}
	
}
