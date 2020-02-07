package com.zhangysh.accumulate.common.pojo;

import java.io.Serializable;

import com.zhangysh.accumulate.common.util.StringUtil;

/**
 *BsTable传过来的分页对象数据
 *@author 创建者：zhangysh
 */
public class BsTablePageInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 当前第几页 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;
    
    /***
     *获取order by 条件 
     ****/
    public String getOrderBy(){
        if (StringUtil.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtil.convertToUnderLineCase(orderByColumn) + " " + isAsc;
    }
    
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderByColumn() {
		return orderByColumn;
	}
	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}
	public String getIsAsc() {
		return isAsc;
	}
	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}
}
