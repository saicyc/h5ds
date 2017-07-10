package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class DynamicForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/** app中的创建时间戳*/
	private String timeStamp;
	@ManyToOne(fetch=FetchType.LAZY)
	private Apps apps;
	/** 表单名称*/
	private String formName;
	/** 表单列名称*/
	@Lob
	private String cols;
	/**创建时间*/
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Apps getApps() {
		return apps;
	}
	public void setApps(Apps apps) {
		this.apps = apps;
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
