package com.chinait.service.impl;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyc
 */
import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.config.FileProperties;
import com.chinait.dao.AppRepository;
import com.chinait.dao.AppTrackRepository;
import com.chinait.dao.AppTypeRepository;
import com.chinait.dao.CasesRepository;
import com.chinait.dao.UserRepository;
import com.chinait.dao.WebUserRepository;
import com.chinait.domain.AppTrack;
import com.chinait.domain.Apps;
import com.chinait.domain.Cases;
import com.chinait.domain.User;
import com.chinait.service.AppService;
import com.chinait.utils.Constance;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.QRCodeUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.AppsVO;
import com.chinait.vo.PageVO;
@Service
public class AppServiceImpl implements AppService{
	@Autowired
	public AppRepository appRepository;
	@Autowired
	public WebUserRepository webUserRepository;
	@Autowired
	public  AppTrackRepository appTrackRepository;
	@Autowired
	public FileProperties fileProperties;
	@Autowired
	public AppTypeRepository appTypeRepository;
	@Autowired
	public CasesRepository casesRepository;
	@Autowired
	public UserRepository userRepository;
	@Override
	@Transactional
	public Map<String,String> createApp(User user) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		Apps app = new Apps ();
		app.setAppName("未命名");
		app.setCreateTime(new Date());
		app = appRepository.save(app);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileUrl = user.getPersonFileUrl()+"/"+app.getId();
		String currentUrl = fileUrl+"/"+sdf.format(new Date())+Constance.FILESUFFIX;
		app.setCurrentFilePath(currentUrl);
		app.setQrCode(fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+app.getId()+"/"+app.getId()+Constance.QrCodeImg);
		app.setUser(user);
		appRepository.save(app);
		map.put("currentUrl",currentUrl);
		map.put("fileUrl",fileUrl);
		map.put("appId",String.valueOf(app.getId()));
		return map;
	}
	@Override
	public Apps findOneApp(int appId) {
		Apps app = appRepository.findByIdAndUserAndIsDeleteFalse(appId,UserAuthentic.getActiveUser());
		return app;
	}
	@Override
	@Transactional
	public void deleteApp(int appId) {
		Apps app = appRepository.findOne(appId);
		app.setDelete(true);
		appRepository.save(app);
	}
	@Override
	public List<AppTrack> appTracks(int appId) {
		Apps app = appRepository.findOne(appId);
		return appTrackRepository.findTop20ByAppsOrderByCreateTimeDesc(app);
	}
	@Override
	public AppTrack appTrackOne(int trackId) {
		AppTrack appTrack =appTrackRepository.findOne(trackId);
		return appTrack;
	}
	@Override
	@Transactional
	public void updateInfo(String title,String description,int appId,String coverPicture) {
		Apps app = appRepository.findOne(appId);
		app.setAppName(title);
		app.setDescribtion(description);
		app.setCoverPicture(coverPicture);
		appRepository.save(app);
	}
	@Override
	public boolean checkUser(int appId, User user) {
		Apps app = appRepository.findByIdAndUserAndIsDeleteFalse(appId, user);
		if(app == null){
			return false;
		}
		return true;
	}

	@Override
	public Page<Apps> findAllApp(PageVO<AppsVO> pageVo) {
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		return appRepository.findByIsDeleteFalseOrderByCreateTimeDesc(page);
	}
	@Override
	public Apps findOne(int appId) {
		return appRepository.findByIdAndIsDeleteFalse(appId);
	}
	@Override
	@Transactional
	public void createAppByCase(int caseId) throws IOException {
		Cases cases = casesRepository.findOne(caseId);
		Apps app= new Apps();
		app.setAppName("未命名");
		app.setCreateTime(new Date());
		User user = UserAuthentic.getActiveUser();
		app.setUser(user);
		appRepository.save(app);
		FilesUtil.createFolder(fileProperties.getUploadFolder()+user.getId()+"/"+app.getId());
		String currentFileUrl = cases.getCaseUrl();
		app.setCurrentFilePath(fileProperties.getUploadFolder()+user.getId()+"/"+app.getId()+"/"+currentFileUrl.substring(currentFileUrl.lastIndexOf("/")+1));
		String indexFile = cases.getCasePath();
		String fileOut = user.getPersonFileUrl()+"/"+app.getId();
		app.setCurrentFilePath(fileOut+"/"+indexFile.substring(indexFile.lastIndexOf("/")+1));
		//复制文件到app下
		String inFile = fileProperties.getUploadFolder()+fileProperties.getCases()+"/"+cases.getId();
		String outFile = fileProperties.getUploadFolder()+user.getId()+"/"+app.getId();
		FilesUtil.copyDirectory(new File(inFile), new File(outFile));
		//生成二维码
		QRCodeUtils utils = new QRCodeUtils();
		String qrFileUrl = fileProperties.getUploadFolder()+user.getId()+"/"+app.getId();
		String content = fileProperties.getWebsite()+"/"+fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+app.getId()+"/"+Constance.FILENAME;
		utils.createQRCode(String.valueOf(app.getId()), qrFileUrl, content);
		//获取主图
		String fileUrl = cases.getFileUrl();
		if(fileUrl!=null){
			fileUrl = fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+app.getId()+"/"+fileProperties.getSource()+"/"+fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		}else{
			fileUrl =null;
		}
		app.setQrCode(fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+app.getId()+"/"+app.getId()+Constance.QrCodeImg);
		app.setCoverPicture(fileUrl);
		app.setDelete(false);
		appRepository.save(app);
	}

	@Override
	public void CopeApp(User user, Apps oldApp) throws IOException {
		Apps newApp = new Apps();
		newApp.setAppName("未命名");
		appRepository.save(newApp);
		//copy 文件夹
		String oldPath = oldApp.getCurrentFilePath();
		String fileIn = user.getPersonFileUrl()+"/"+oldApp.getId();
		String fileOut = user.getPersonFileUrl()+"/"+newApp.getId();
		try {
			FilesUtil.copyDirectory(new File(fileIn), new File(fileOut));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//生成二维码
		FilesUtil.createFile(user.getPersonFileUrl()+"/"+newApp.getId()+"/"+oldPath.substring(oldPath.lastIndexOf("/")+1));
		//生成二维码图片
		QRCodeUtils utils = new QRCodeUtils();
		String qrFileUrl = fileProperties.getUploadFolder()+user.getId()+"/"+newApp.getId()+"/";
		String content = fileProperties.getWebsite()+fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+newApp.getId()+"/"+Constance.FILENAME;
		utils.createQRCode(String.valueOf(newApp.getId()), qrFileUrl, content);
		newApp.setQrCode(fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+newApp.getId()+"/"+newApp.getId()+Constance.QrCodeImg);
		//指定当前的file路径
		newApp.setCurrentFilePath(user.getPersonFileUrl()+"/"+newApp.getId()+"/"+oldPath.substring(oldPath.lastIndexOf("/")+1));
		newApp.setDelete(false);
		newApp.setUser(user);
		newApp.setCreateTime(new Date());
		//封面
		String fileUrl = oldApp.getCoverPicture();
		if(!StringHelper.isEmpty(fileUrl)){
			newApp.setCoverPicture(fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+newApp.getId()+"/"+fileProperties.getSource()+"/"+fileUrl.substring(fileUrl.lastIndexOf("/")+1));
		}
		appRepository.save(newApp);
	}
	@Override
	@Transactional
	public Page<Apps> findPersonApps(PageVO<AppsVO> pageVo) {
		User user = UserAuthentic.getActiveUser();
		user = userRepository.findOne(user.getId());
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		Page<Apps> pages = appRepository.findByUserAndIsDeleteFalseOrderByCreateTimeDesc(user,page);
		return pages;
	}
}
