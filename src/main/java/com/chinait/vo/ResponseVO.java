package com.chinait.vo;
/**
 * Controller返回数据封装VO
 * @author yachao
 */
import java.util.List;
import java.util.Map;

public class ResponseVO<T> {
	/** 返回map对象*/
	private Map<String,T> returnMap;
	/** 返回List对象*/
	private List<T> returnList;
	/** 返回的信息*/
	private String message;
	/** 返回的状态值(0是错误，1是正常)*/
	private boolean status;
	public Map<String, T> getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(Map<String, T> returnMap) {
		this.returnMap = returnMap;
	}
	public List<T> getReturnList() {
		return returnList;
	}
	public void setReturnList(List<T> returnList) {
		this.returnList = returnList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
