package com.chinait.domain;
/**
 * APPS 为其他app的父类
 * @author yachao
 */
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.chinait.domain.User;
import javax.persistence.ManyToOne;

@Entity
public class Apps {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String appName;
	/**留言审核*/
	private Boolean isMessageAudit;
	/** 创建时间*/
	private Date createTime;
	/** 状态（上线:1,下线:0）*/
	private Byte status;
	/**访问次数*/
	private int visitsNumber;
	/**标题*/
	private String title;
	/** App封面图片 */
	private String coverPicture;
	/**售价*/
	private Double price;
	/** 附件地址 */
	private String annexAddress;
	/**描述*/
	private String describtion;
	/**是否隐藏*/
	private Boolean disable; 
	/**当前app的文件路径*/
	private String currentFilePath;
	/**更新时间*/
	private Date currentDate;
	/** 是否删除*/
	private boolean isDelete;
	/** 二维码图片位置*/
	private String qrCode;
	@OneToMany
	private List<Label> label;
	@OneToMany
	private List<AppType> appType;
	/** 查阅次数*/
	private int accessTimes;
	@ManyToOne
	private User user;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Boolean getIsMessageAudit() {
		return isMessageAudit;
	}

	public void setIsMessageAudit(Boolean isMessageAudit) {
		this.isMessageAudit = isMessageAudit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public int getVisitsNumber() {
		return visitsNumber;
	}

	public void setVisitsNumber(int visitsNumber) {
		this.visitsNumber = visitsNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAnnexAddress() {
		return annexAddress;
	}

	public void setAnnexAddress(String annexAddress) {
		this.annexAddress = annexAddress;
	}
	

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public String getCurrentFilePath() {
		return currentFilePath;
	}

	public void setCurrentFilePath(String currentFilePath) {
		this.currentFilePath = currentFilePath;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	public List<AppType> getAppType() {
		return appType;
	}

	public void setAppType(List<AppType> appType) {
		this.appType = appType;
	}

	public int getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(int accessTimes) {
		this.accessTimes = accessTimes;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public User getUser() {
	    return user;
	}

	public void setUser(User param) {
	    this.user = param;
	}
	
}
