package com.zhangysh.accumulate.back.sys.base.interceptor;
/**
 *自定义分页拦截器的分页对象
 *@author zhangysh
 *@date 2018年9月14日
 */
public class Pagination {
	
	private Integer totalNumber;// 总条数
	private Integer currentPage;// 当前页数
	private Integer totalPage;// 总页数
	private Integer pageNumber;// 每页显示条数
	
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
		this.count();
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	private void count() {
		this.totalPage = this.totalNumber / this.pageNumber;
		if(this.totalNumber % this.pageNumber > 0) {
			this.totalPage++;
		}
		if(this.totalPage <= 0) {
			this.totalPage = 1;
		}
		if(this.currentPage > this.totalPage) {
			this.currentPage = this.totalPage;
		}
		if(this.currentPage <= 0) {
			this.currentPage = 1;
		}
	}
}
