package com.chinait.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinait.config.FileProperties;
import com.chinait.domain.Music;
import com.chinait.domain.User;
import com.chinait.service.MusicService;
import com.chinait.service.SourceService;
import com.chinait.utils.Constance;
import com.chinait.utils.FilePathUtil;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.UUIDUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.FileResultVO;

import net.coobird.thumbnailator.Thumbnails;


@Controller
@RequestMapping(value="/upload")
public class UploadController { 
	@Autowired
	FileProperties fileProperties;
	@Autowired
	SourceService sourceService;
	@Autowired
	MusicService musicService;
	/**
	 * 长传图片或文件(素材)
	 * @author cyc
	 * @throws Exception 
	 */
	@RequestMapping(value="/source", method=RequestMethod.POST,headers="content-type=multipart/form-data")
	@ResponseBody
    public List<FileResultVO> UploadFileImage(@RequestParam("fileList") MultipartFile[] files,HttpServletRequest request,int sourceTypeId,int appId) throws Exception{
		User user = UserAuthentic.getActiveUser();
		List<FileResultVO> fileResultVOList = new ArrayList<FileResultVO>();
		for(MultipartFile file:files){
			FileResultVO returnVO = new FileResultVO();
			if (!file.isEmpty()) {
				String fileSuffixName = FilePathUtil.getFileSuffixName(new StringBuilder(file.getOriginalFilename()));
				if(fileSuffixName.contains(Constance.IMG_FORMAT)||fileSuffixName.toUpperCase().contains(Constance.IMG_FORMAT)){
					throw new Exception("文件格式不正确");
				}
				String fileUrl ="/"+appId+"/"+fileProperties.getSource()+"/"+UUIDUtils.getUUIDString()+fileSuffixName;
				String filePath = user.getPersonFileUrl()+fileUrl;
		    	File uploadTmpFolder=new File(filePath);
		    	FilesUtil.createFile(filePath);
		    	InputStream in=file.getInputStream();
			    FileOutputStream fos=new FileOutputStream(uploadTmpFolder);
			    IOUtils.copyLarge(in, fos);
			    try{			   
				    IOUtils.copyLarge(in, fos);
			    }finally{
			    	 fos.close();
			    	 in.close();
			    }
			    
			    File inFile = new File(filePath);
				BufferedImage sourceImg = ImageIO.read(inFile);
			    returnVO.setFileName(uploadTmpFolder.getName());
			    returnVO.setFilePath(fileProperties.getUploadFolderUrlPrefix()+user.getId()+fileUrl);
			    returnVO.setHeight(sourceImg.getHeight());
			    returnVO.setWidth(sourceImg.getWidth());
			    fileResultVOList.add(returnVO);
			}
		}
		if(files!=null&&files.length>0&&!fileResultVOList.isEmpty()){
			sourceService.saveSource(fileResultVOList,appId,sourceTypeId);
			for(int i=0;i<files.length;i++){
				MultipartFile file = files[i];
				String fileUrl = fileProperties.getUploadFolder()+fileResultVOList.get(i).getCutPath();
				Thumbnails.of(file.getInputStream()).scale(1f).toFile(fileUrl);
				fileResultVOList.get(i).setCutPath(fileProperties.getUploadFolderUrlPrefix()+fileResultVOList.get(i).getCutPath());
			}
		}
		return fileResultVOList;
	}
	/**
	 * 长传图片或文件(素材)
	 * @author cyc
	 * @throws Exception 
	 */
	@RequestMapping(value="/music", method=RequestMethod.POST,headers="content-type=multipart/form-data")
	@ResponseBody
    public FileResultVO UploadFileMusic(@RequestParam("file") MultipartFile files,HttpServletRequest request) throws Exception{
 		String fileSuffixName = FilePathUtil.getFileSuffixName(new StringBuilder(files.getOriginalFilename()));
		if(fileSuffixName.contains(Constance.IMG_FORMAT)||fileSuffixName.toUpperCase().contains(Constance.IMG_FORMAT)){
			throw new Exception("文件格式不正确");
		}
		//判断文件夹是否存在
		String fileUrl = Constance.MUSICFILE+"/"+UUIDUtils.getUUIDString()+FilePathUtil.getFileSuffixName(new StringBuilder(files.getOriginalFilename()));
		File file;
		FileResultVO returnVO =null;
		try {
			returnVO = new FileResultVO();
			file = FilesUtil.createFile(fileProperties.getUploadFolder()+fileUrl);
			InputStream in=files.getInputStream();
		    FileOutputStream fos=new FileOutputStream(file);
		    IOUtils.copyLarge(in, fos);
		    String UrlPrefix = fileProperties.getUploadFolderUrlPrefix()+fileUrl;
		    returnVO.setFilePath(UrlPrefix);
		    Music music = new Music();
		    music.setMusicName(files.getOriginalFilename());
		    music.setUser(UserAuthentic.getActiveUser());
		    music.setFilePath(UrlPrefix);
		    musicService.saveMusic(music);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnVO;
	}
	/**
	 * 长传图片或文件(素材)
	 * @author cyc
	 * @throws Exception 
	 */
	@RequestMapping(value="/systemSource", method=RequestMethod.POST,headers="content-type=multipart/form-data")
	@ResponseBody
    public List<FileResultVO> sysTemSource(@RequestParam("fileList") MultipartFile[] files,HttpServletRequest request,int sourceTypeId) throws Exception{
		List<FileResultVO> fileResultVOList = new ArrayList<FileResultVO>();
		for(MultipartFile file:files){
			FileResultVO returnVO = new FileResultVO();
			if (!file.isEmpty()) {
				String fileSuffixName = FilePathUtil.getFileSuffixName(new StringBuilder(file.getOriginalFilename()));
				if(fileSuffixName.contains(Constance.IMG_FORMAT)||fileSuffixName.toUpperCase().contains(Constance.IMG_FORMAT)){
					throw new Exception("文件格式不正确");
				}
				String fileUrl =fileProperties.getSource()+"/"+UUIDUtils.getUUIDString()+FilePathUtil.getFileSuffixName(new StringBuilder(file.getOriginalFilename()));
				String filePath = fileProperties.getUploadFolder()+fileUrl;
		    	File uploadTmpFolder=new File(filePath);
		    	FilesUtil.createFile(filePath);
		    	InputStream in=file.getInputStream();
			    FileOutputStream fos=new FileOutputStream(uploadTmpFolder);
			    IOUtils.copyLarge(in, fos);
			    try{			   
				    IOUtils.copyLarge(in, fos);
			    }finally{
			    	 fos.close();
			    	 in.close();
			    }
			    String cutPath = fileProperties.getCutFile()+"/"+UUIDUtils.getUUIDString()+".jpg";
			    FilesUtil.createFile(fileProperties.getUploadFolder()+cutPath);
			    Thumbnails.of(file.getInputStream()).scale(1f).toFile(fileProperties.getUploadFolder()+cutPath);
			    File inFile = new File(filePath);
				BufferedImage sourceImg = ImageIO.read(inFile);
			    returnVO.setFileName(uploadTmpFolder.getName().substring(0,uploadTmpFolder.getName().indexOf(".")-1));
			    returnVO.setFilePath(fileProperties.getUploadFolderUrlPrefix()+fileUrl);
			    returnVO.setHeight(sourceImg.getHeight());
			    returnVO.setWidth(sourceImg.getWidth());
			    returnVO.setCutPath(fileProperties.getUploadFolderUrlPrefix()+cutPath);
			    fileResultVOList.add(returnVO);
			}
		}
		if(files!=null&&files.length>0&&!fileResultVOList.isEmpty()){
			sourceService.saveSource(fileResultVOList,sourceTypeId);
		}
		return fileResultVOList;
	}
}
