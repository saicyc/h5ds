package com.chinait.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Boolean isDelete;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	private String loginName;
	private String userName;
	@OneToOne
	private Role role;
	@OneToMany
	private List<MessageAudit> messageAudit;
	/** 所有的文件夹*/
	private String personFileUrl;
	private boolean isManager;
	/** 懒加载*/
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "user" )
	private List<Apps> apps;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<MessageAudit> getMessageAudit() {
		return messageAudit;
	}
	public void setMessageAudit(List<MessageAudit> messageAudit) {
		this.messageAudit = messageAudit;
	}
	public String getPersonFileUrl() {
		return personFileUrl;
	}
	public void setPersonFileUrl(String personFileUrl) {
		this.personFileUrl = personFileUrl;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public List<Apps> getApps() {
		return apps;
	}
	public void setApps(List<Apps> apps) {
		this.apps = apps;
	}
}
