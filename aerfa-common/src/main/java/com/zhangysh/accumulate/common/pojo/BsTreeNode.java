package com.zhangysh.accumulate.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyui的tree（树）节点模型对象
 * @author zhangysh
 * @date 2018年8月29日
 */
public class BsTreeNode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;//id属性
	private String text;//显示名称
	private List<BsTreeNode> nodes;//子节点
	private String icon;//文字前面的图标，打开关闭图标是固定的
	private Boolean lazyLoad;//是否懒加载
	
	//to add additional information to the right of each node
	private List<String> tags;//每个树节点右边加的提示信息
	private Map<String,Boolean> state;//checked disabled expanded selected的状态:disabled为只看不点
	private Map<String, String> attributes = new HashMap<String, String>();//自定义属性
	
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
	public List<BsTreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<BsTreeNode> nodes) {
		this.nodes = nodes;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getLazyLoad() {
		return lazyLoad;
	}
	public void setLazyLoad(Boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Map<String, Boolean> getState() {
		return state;
	}
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
