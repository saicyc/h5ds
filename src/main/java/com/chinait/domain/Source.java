package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.chinait.domain.User;
import com.chinait.domain.Apps;

@Entity
public class Source {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private SourceType sourceType;
	/** 素材的位置*/
	private String filePath;
	/** 压缩后的缩略图*/
	private String cutPath;
	/** 是否删除*/
	private boolean isDelete;
	/** 创建时间*/
	private Date createTime;
	@ManyToOne
	private User user;
	@ManyToOne
	private Apps apps;
	private int width;
	private int height;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SourceType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
	    return user;
	}
	public void setUser(User param) {
	    this.user = param;
	}
	public Apps getApps() {
	    return apps;
	}
	public void setApps(Apps param) {
	    this.apps = param;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getCutPath() {
		return cutPath;
	}
	public void setCutPath(String cutPath) {
		this.cutPath = cutPath;
	}
	
}
