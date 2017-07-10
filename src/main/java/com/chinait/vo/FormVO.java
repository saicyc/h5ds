package com.chinait.vo;

import com.chinait.domain.Apps;
import com.chinait.domain.DynamicForm;

public class FormVO {
	//总共有多少列数据
	private int totalCols;
	//所在的app
	private int appId;
	//form 表单所属的app
	private Apps apps;
	//form表单的头
	private String cols;
	//表单的名称
	private String formName;
	//提交的内容
	private String content;
	//提交的表单
	private DynamicForm dynamicForm;
	//时间戳
	private String timeStamp;
	public int getTotalCols() {
		return totalCols;
	}
	public void setTotalCols(int totalCols) {
		this.totalCols = totalCols;
	}

	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public Apps getApps() {
		return apps;
	}
	public void setApps(Apps apps) {
		this.apps = apps;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public DynamicForm getDynamicForm() {
		return dynamicForm;
	}
	public void setDynamicForm(DynamicForm dynamicForm) {
		this.dynamicForm = dynamicForm;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
