package com.chinait.vo;
/**
 * 文件VO
 * @author yachao
 *
 */
public class FileResultVO {
	/** 文件路径*/
	private String filePath;
	/** 缩略图*/
	private String cutPath;
	/** 文件名称*/
	private String fileName;
	/** 文件的压缩文件的路径*/
	private String fileThumbnail;
	/** 图片的高*/
	private int height;
	/** 图片的宽*/
	private int width;
	/** 素材的Id*/
	private int sourceId;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileThumbnail() {
		return fileThumbnail;
	}
	public void setFileThumbnail(String fileThumbnail) {
		this.fileThumbnail = fileThumbnail;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getCutPath() {
		return cutPath;
	}
	public void setCutPath(String cutPath) {
		this.cutPath = cutPath;
	}
	
}
