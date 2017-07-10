package com.chinait.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 文件工具类
 * @author yachao
 */
public class FilesUtil extends FileUtils{
	/**
	 * 创建文件使用
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String url) throws IOException{
		File file = new File(url);
		//文件夹不存在时创建
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		//文件不存在时创建
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	/**
	 * 创建文件夹使用
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static void createFolder(String url) throws IOException{
		File file = new File(url);
		//文件夹不存在时创建
		if(!file.exists()){
			file.mkdirs();
		}
	}
	/**
	 * 复制文件
	 * @param inFile
	 * @param outFile
	 */
	public void copeFile(String inFile,String outFile){
		//通过通道来实现文件的copy
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(inFile);
            fo = new FileOutputStream(outFile);
            //得到对应的文件通道
            in = fi.getChannel();
            //得到对应的文件通道
            out = fo.getChannel();
            //连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
	}
	/**
	 * 变量 为String
	 * 读取文件内容转换成String 类型
	 * @param filePath
	 * @return
	 */
	public String  fileToString(String filePath){
		String htmlStr = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
			htmlStr = IOUtils.toString(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlStr;
	}
	/**
	 * 变量 为String
	 * 读取文件内容转换成String 类型
	 * @param filePath
	 * @return
	 */
	public String  fileToString(File file){
		String htmlStr = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			htmlStr = IOUtils.toString(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlStr;
	}
}
