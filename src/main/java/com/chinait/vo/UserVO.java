package com.chinait.vo;

import java.util.Date;

public class UserVO {
	private int id;
	private String qq;
	private String userName;
	private String email;
	private String phone;
	private String nike;
	private String aboutYourself;
	private Date createTime;
	private long appNum;
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNike() {
		return nike;
	}
	public void setNike(String nike) {
		this.nike = nike;
	}
	public String getAboutYourself() {
		return aboutYourself;
	}
	public void setAboutYourself(String aboutYourself) {
		this.aboutYourself = aboutYourself;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getAppNum() {
		return appNum;
	}
	public void setAppNum(long appNum) {
		this.appNum = appNum;
	}
}
