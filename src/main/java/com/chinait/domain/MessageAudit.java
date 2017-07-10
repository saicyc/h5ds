package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
	 * 是否审核
	 */
	private  Boolean isMessageAudit;
	/**
	 * 提交时间
	 */
	private Date submitTime;
	/**
	 * 留言内容
	 */
	private String messageAuditContent;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsMessageAudit() {
		return isMessageAudit;
	}

	public void setIsMessageAudit(Boolean isMessageAudit) {
		this.isMessageAudit = isMessageAudit;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getMessageAuditContent() {
		return messageAuditContent;
	}

	public void setMessageAuditContent(String messageAuditContent) {
		this.messageAuditContent = messageAuditContent;
	}
	
}
