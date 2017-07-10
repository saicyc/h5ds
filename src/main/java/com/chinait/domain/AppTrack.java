package com.chinait.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.chinait.domain.Apps;
import javax.persistence.ManyToOne;

/**
 * app 跟进类
 * 记录用户对app 的使用情况
 * @author yachao
 *
 */
@Entity
public class AppTrack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**app的文件路径*/
	private String filePath;
	/**app的标志，1可以删除，0不可以删除*/
	private boolean mark;
	private Date createTime;
	@ManyToOne
	private Apps apps;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Apps getApps() {
	    return apps;
	}
	public void setApps(Apps param) {
	    this.apps = param;
	}
	
}
