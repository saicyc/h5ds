package com.chinait.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mtsee")
public class FileProperties {
	private String uploadFolder;
	private String uploadFolderUrlPrefix;
	private String uploadFileSize;
	private String templeInitFile;
	private String source;
	private String cases;
	private String music;
	private String website;
	private String cutFile;
	public String getUploadFolder() {
		return uploadFolder;
	}
	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}
	public String getUploadFolderUrlPrefix() {
		return uploadFolderUrlPrefix;
	}
	public void setUploadFolderUrlPrefix(String uploadFolderUrlPrefix) {
		this.uploadFolderUrlPrefix = uploadFolderUrlPrefix;
	}
	public String getUploadFileSize() {
		return uploadFileSize;
	}
	public void setUploadFileSize(String uploadFileSize) {
		this.uploadFileSize = uploadFileSize;
	}
	public String getTempleInitFile() {
		return templeInitFile;
	}
	public void setTempleInitFile(String templeInitFile) {
		this.templeInitFile = templeInitFile;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCases() {
		return cases;
	}
	public void setCases(String cases) {
		this.cases = cases;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public String getCutFile() {
		return cutFile;
	}
	public void setCutFile(String cutFile) {
		this.cutFile = cutFile;
	}
	
}
