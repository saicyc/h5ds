package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
/**
 * app的用户
 * @author cyc
 *
 */
@Entity
public class WebUser extends User{
	/** 省*/
	private String province;
	/** 行业*/
	private String industry;
	/** 城市*/
	private String city;
	/** 人气*/
	private Integer popularity;
	/** email*/
	private String email;
	/** 昵称*/
	private String nickName;
	/** 真是名字*/
	private String realName;
	/** QQ号码*/
	private String qq;
	/** phone*/
	private String phone;
	/** 关于自己*/
	private String aboutYourself;
	/** 用户的类型*/
	private boolean userType;
	/** 注册时间*/
	private Date createTime;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getPopularity() {
		return popularity;
	}
	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAboutYourself() {
		return aboutYourself;
	}
	public void setAboutYourself(String aboutYourself) {
		this.aboutYourself = aboutYourself;
	}
	public boolean isUserType() {
		return userType;
	}
	public void setUserType(boolean userType) {
		this.userType = userType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
