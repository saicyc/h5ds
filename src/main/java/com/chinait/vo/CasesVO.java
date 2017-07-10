package com.chinait.vo;

import java.util.Date;

public class CasesVO {
	private String userName;
	private int id;
	private String fileUrl;
	/** 查看量*/
	private int checkNumber;
	/** 留言量*/
	private int messageNumber;
	/** 关注量*/
	private int payAttentionNumber;
	/** 案例的路径*/
	private String caseUrl;
	/** 二维码路径*/
	private String qrCodeUrl;
	/** 创建时间*/
	private Date createTime;
	/** 案列名称*/
	private String caseName;
	private boolean isHide;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}
	public int getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}
	public int getPayAttentionNumber() {
		return payAttentionNumber;
	}
	public void setPayAttentionNumber(int payAttentionNumber) {
		this.payAttentionNumber = payAttentionNumber;
	}
	public String getCaseUrl() {
		return caseUrl;
	}
	public void setCaseUrl(String caseUrl) {
		this.caseUrl = caseUrl;
	}
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public boolean isHide() {
		return isHide;
	}
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}
	
}
