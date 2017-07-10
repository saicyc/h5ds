package com.chinait.vo;
/**
 * 分页类
 * @author yachao 
 * */
import java.util.List;

public class PageVO<T> {
	/** 当前页*/
	private int currentPage =0;
	/** app的总量*/
	private long totalApps;
	/** app的总页数*/
	private int totalPages;
	/** 分页每页的app数目*/
	private int pageSize =7;
	/** 分页存放的数据*/
	private List<T> returnList;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public long getTotalApps() {
		return totalApps;
	}
	public void setTotalApps(long totalApps) {
		this.totalApps = totalApps;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getReturnList() {
		return returnList;
	}
	public void setReturnList(List<T> returnList) {
		this.returnList = returnList;
	}
	
}
