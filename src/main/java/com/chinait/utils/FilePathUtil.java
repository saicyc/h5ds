package com.chinait.utils;

public class FilePathUtil {
	/**
	 * 获取文件的后缀名
	 */
	public static String getFileSuffixName(StringBuilder originalFilename){
		return originalFilename.substring(originalFilename.indexOf("."));
	}
}
