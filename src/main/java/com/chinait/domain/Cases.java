
package com.chinait.domain;
import java.util.Date;

/**
 * 案例
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.chinait.domain.User;
import javax.persistence.ManyToOne;

@Entity
public class Cases {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/** 案列名称*/
	private String caseName;
	/** 主图*/
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
	/** 创建人*/
	@ManyToOne
	private User user;
	/** 是否删除*/
	private boolean isDelete;
	/** 是否上架*/
	private boolean isHide;
	/** case当前app访问的index路径*/
	private String casePath;
	public User getUser() {
	    return user;
	}
	public void setUser(User param) {
	    this.user = param;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCasePath() {
		return casePath;
	}
	public void setCasePath(String casePath) {
		this.casePath = casePath;
	}
	public boolean isHide() {
		return isHide;
	}
	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}
	
}
