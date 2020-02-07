package com.zhangysh.accumulate.common.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 * easyui的tree（树）节点模型对象
 * @author zhangysh
 * @date 2018年7月7日
 *****/
public class EuTreeNode{

	private String id;//树唯一标识
	private String text;//显示文字
	private String iconCls;//文字前面的自定义icon图标
	private String checked;//是否被勾选，要和tree的checkbox true配合使用
	private String state;//显示状态为展开或关闭open closed
	private Map<String, String> attributes = new HashMap<String, String>();//自定义属性
	private List<EuTreeNode> children;//子节点，属性相同
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public List<EuTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<EuTreeNode> children) {
		this.children = children;
	}
}
