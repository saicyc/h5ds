package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import com.chinait.domain.User;
import javax.persistence.ManyToOne;
import com.chinait.domain.TemplateType;

@Entity
public class AppSingleTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Lob
	private String templateHtml;
	private Date createTime;
	/** 是否为系统的模板*/
	private boolean isSystem;
	/** 是否删除该模板*/
	private boolean isDelete;
	/** 封面地址*/
	private String fileUrl;
	@ManyToOne
	private User user;
	@ManyToOne
	private TemplateType templateType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTemplateHtml() {
		return templateHtml;
	}
	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public boolean isSystem() {
		return isSystem;
	}
	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
	public User getUser() {
	    return user;
	}
	public void setUser(User param) {
	    this.user = param;
	}
	public TemplateType getTemplateType() {
	    return templateType;
	}
	public void setTemplateType(TemplateType param) {
	    this.templateType = param;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
