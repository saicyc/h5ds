package com.chinait.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.config.FileProperties;
import com.chinait.dao.CasesRepository;
import com.chinait.domain.Apps;
import com.chinait.domain.Cases;
import com.chinait.service.CaseService;
import com.chinait.utils.Constance;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.QRCodeUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.CasesVO;
import com.chinait.vo.PageVO;
@Service
public class CaseServiceImpl implements CaseService{
	@Autowired
	FileProperties fileProperties;
	@Autowired
	CasesRepository casesRepository;
	@Override
	@Transactional
	public int saveCases(String qrUrl,String fileUrl,QRCodeUtils utils,String url,boolean isSystem,String fileIn,Apps app) throws Exception {
		Cases cases =new Cases();
		cases = casesRepository.save(cases);
		//二维码文件路径
		String urls =null;
		if(!StringHelper.isEmpty(url)){
			urls= fileProperties.getUploadFolderUrlPrefix()+fileProperties.getCases()+"/"+cases.getId()+"/"+fileProperties.getSource()+"/"+url.substring(url.lastIndexOf("/")+1);
		}
		if(isSystem){
			urls=url;
		}
		cases.setFileUrl(urls);
		cases.setCreateTime(new Date());
		cases.setDelete(false);
		cases.setHide(false);
		cases.setUser(UserAuthentic.getActiveUser());
		String fileOut = fileProperties.getUploadFolder()+fileProperties.getCases()+"/"+cases.getId();
    	FilesUtil.createFolder(fileOut);
    	FilesUtil.copyDirectory(new File(fileIn),new File(fileOut));
		//生成二维码图片
    	String fileFile = fileProperties.getUploadFolder()+fileProperties.getCases()+"/"+cases.getId()+"/";
    	String content = fileProperties.getWebsite()+fileProperties.getUploadFolderUrlPrefix()+fileProperties.getCases()+"/"+cases.getId()+"/"+Constance.FILENAME;
    	FilesUtil.createFile(fileProperties.getUploadFolder()+fileProperties.getCases()+"/"+cases.getId()+"/"+Constance.FILENAME);
    	boolean bool = utils.createQRCode(String.valueOf(cases.getId()), fileFile, content);
    	if(!bool){
    		throw new Exception("二维码生成失败");
    	}
    	cases.setQrCodeUrl(fileProperties.getUploadFolderUrlPrefix()+fileProperties.getCases()+"/"+cases.getId()+"/"+String.valueOf(cases.getId())+Constance.QrCodeImg);
    	cases.setCaseName(app.getAppName());
    	String urlFiles = (app.getCurrentFilePath()).substring((app.getCurrentFilePath()).lastIndexOf(".")-14);
    	cases.setCasePath(fileOut+"/"+urlFiles);
    	String currentPath = app.getCurrentFilePath();
    	cases.setCaseUrl(fileProperties.getUploadFolderUrlPrefix()+fileProperties.getCases()+"/"+cases.getId()+"/"+currentPath.substring(currentPath.lastIndexOf(".")-14));
    	casesRepository.save(cases);
    	return cases.getId();
	}
	@Override
	public void findAll(PageVO<CasesVO> pageVo) {
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		Page<Cases> casePage =  casesRepository.findByIsDeleteFalseAndIsHideFalseOrderByCreateTimeDesc(page);
		List<CasesVO> casesList = new ArrayList<CasesVO>();
		for(Cases cases: casePage.getContent()){
			CasesVO casesVO = new CasesVO();
			casesVO.setUserName(cases.getUser().getUserName());
			casesVO.setCaseUrl(cases.getCaseUrl());
			casesVO.setCheckNumber(cases.getCheckNumber());
			casesVO.setCreateTime(cases.getCreateTime());
			casesVO.setFileUrl(cases.getFileUrl());
			casesVO.setId(cases.getId());
			casesVO.setMessageNumber(cases.getMessageNumber());
			casesVO.setPayAttentionNumber(cases.getPayAttentionNumber());
			casesVO.setQrCodeUrl(cases.getQrCodeUrl());
			casesVO.setCaseName(cases.getCaseName());
			casesList.add(casesVO);
		}
		pageVo.setReturnList(casesList);
		pageVo.setTotalApps(casePage.getTotalElements());
    	pageVo.setTotalPages(casePage.getTotalPages());
	}
	@Override
	@Transactional
	public Cases findOneCases(int casesId) {
		Cases cases = casesRepository.findOne(casesId);
		cases.setCheckNumber(cases.getCheckNumber()+1);
		cases = casesRepository.save(cases);
		return cases;
	}
	@Override
	@Transactional
	public void deleteCases(int casesId) {
		Cases cases = casesRepository.findOne(casesId);
		cases.setDelete(true);
		casesRepository.save(cases);
	}
	@Override
	public void findAllCase(PageVO<CasesVO> pageVo) {
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		Page<Cases> casePage =  casesRepository.findByIsDeleteFalseOrderByCreateTimeDesc(page);
		List<CasesVO> casesList = new ArrayList<CasesVO>();
		for(Cases cases: casePage.getContent()){
			CasesVO casesVO = new CasesVO();
			casesVO.setUserName(cases.getUser().getUserName());
			casesVO.setCaseUrl(cases.getCaseUrl());
			casesVO.setCheckNumber(cases.getCheckNumber());
			casesVO.setCreateTime(cases.getCreateTime());
			casesVO.setFileUrl(cases.getFileUrl());
			casesVO.setId(cases.getId());
			casesVO.setHide(cases.isHide());
			casesVO.setMessageNumber(cases.getMessageNumber());
			casesVO.setPayAttentionNumber(cases.getPayAttentionNumber());
			casesVO.setQrCodeUrl(cases.getQrCodeUrl());
			casesVO.setCaseName(cases.getCaseName());
			casesList.add(casesVO);
		}
		pageVo.setReturnList(casesList);
		pageVo.setTotalApps(casePage.getTotalElements());
    	pageVo.setTotalPages(casePage.getTotalPages());
	}
	@Override
	@Transactional
	public void hideCases(int casesId) {
		Cases cases = casesRepository.findOne(casesId);
		if(cases.isHide()){
			cases.setHide(false);
		}else{
			cases.setHide(true);
		}
		casesRepository.save(cases);
	}

}
