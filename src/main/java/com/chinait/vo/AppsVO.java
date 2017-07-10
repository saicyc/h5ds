package com.chinait.vo;

import java.util.Date;

/**
 * 显示app的list列表
 * @author yachao
 */
public class AppsVO {
	/** app的名字*/
	private String appName;
	/** app的ID*/
	private int id;
	/** app的浏览量*/
	private int accessTimes;
	/** 创建时间*/
	private Date createTime;
	/** app的二维码路径*/
	private String qrCode;
	/** 封面图片*/
	private String coverPicture;
	/** 标题*/
	private String title;
	/** 描述*/
	private String description;
	/** 用户所属人Id*/
	private int userId;
	/** 用户所属人姓名*/
	private String userName;
	/** 当前文件路径*/
	private String currentPath;
	public String getAppName() {
		return appName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getAccessTimes() {
		return accessTimes;
	}
	public void setAccessTimes(int accessTimes) {
		this.accessTimes = accessTimes;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getCoverPicture() {
		return coverPicture;
	}
	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
	
}
