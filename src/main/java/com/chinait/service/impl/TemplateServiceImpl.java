package com.chinait.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.dao.TemplateRepository;
import com.chinait.dao.TemplateTypeRespository;
import com.chinait.domain.AppSingleTemplate;
import com.chinait.domain.TemplateType;
import com.chinait.service.TemplateService;
import com.chinait.utils.Constance;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.PageVO;
import com.chinait.vo.SingleTemplateVO;
@Service
public class TemplateServiceImpl implements TemplateService{
	@Autowired
	TemplateTypeRespository templateTypeRespository;
	@Autowired
	TemplateRepository templateRepository;
	@Override
	@Transactional
	public void saveSingleTemplate(String html,String qrCode,String fileUrl) {
		TemplateType type = templateTypeRespository.findByTypeName(Constance.TEMPLATE);
		AppSingleTemplate template = new AppSingleTemplate();
		template.setFileUrl(fileUrl);
		template.setCreateTime(new Date());
		template.setTemplateHtml(html);
		template.setSystem(false);
		template.setUser(UserAuthentic.getActiveUser());
		template.setTemplateType(type);
		templateRepository.save(template);
	}
	@Override
	public List<AppSingleTemplate> findSimpleTemplateByType(int typeId) {
		TemplateType type = templateTypeRespository.findOne(typeId);
		if(type.isSystem()){
			return templateRepository.findByTemplateTypeIdAndIsSystemTrueAndIsDeleteFalse(typeId);
		}
		return templateRepository.findByTemplateTypeIdAndIsSystemFalseAndIsDeleteFalseAndUser(typeId,UserAuthentic.getActiveUser());
	}
	@Override
	public AppSingleTemplate referenceTemplate(int templateId) {
		return templateRepository.findByIsDeleteFalse(templateId);
	}
	@Override
	public Page<AppSingleTemplate> findSimpleTemplates(int simpleTemplateTypeId, PageVO<SingleTemplateVO> pageVO) {
		Pageable page = new PageRequest(pageVO.getCurrentPage(), pageVO.getPageSize());
		return templateRepository.findByTemplateTypeIdAndIsDeleteFalseAndIsSystemTrue(simpleTemplateTypeId,page);
	}
	@Override
	public void findTemplates(PageVO<SingleTemplateVO> pageVO) {
		Pageable page = new PageRequest(pageVO.getCurrentPage(), pageVO.getPageSize());
		Page<AppSingleTemplate> pages = templateRepository.findByIsSystemFalseAndIsDeleteFalse(page);
		List<SingleTemplateVO> list = new ArrayList<SingleTemplateVO>();
		for(AppSingleTemplate template:pages.getContent()){
			SingleTemplateVO temp = new SingleTemplateVO();
			temp.setId(template.getId());
			temp.setFileUrl(template.getFileUrl());
			temp.setUserName(template.getUser().getUserName());
			list.add(temp);
		}
		pageVO.setReturnList(list);
		pageVO.setTotalApps(pages.getTotalElements());
    	pageVO.setTotalPages(pages.getTotalPages());
	}
	@Override
	@Transactional
	public void setSystemTemplates(int teplateId,int templateTypeId) {
		AppSingleTemplate temp = templateRepository.findByIsSystemFalseAndIsDeleteFalseAndId(teplateId);
		TemplateType type = templateTypeRespository.findOne(templateTypeId);
		AppSingleTemplate template = new AppSingleTemplate();
		template.setCreateTime(new Date());
		template.setDelete(false);
		template.setSystem(true);
		template.setTemplateHtml(temp.getTemplateHtml());
		template.setTemplateType(type);
		template.setUser(UserAuthentic.getActiveUser());
		template.setFileUrl(temp.getFileUrl());
		templateRepository.save(template);
	}
	@Override
	@Transactional
	public void deleteTemplate(int templateId) {
		AppSingleTemplate template = templateRepository.findOne(templateId);
		template.setDelete(true);
		templateRepository.save(template);
	}
	@Override
	public List<TemplateType> findSystemTemplateType() {
		return templateTypeRespository.findByIsSystemTrueAndIsHideFalseAndIsDeleteFalse();
	}
	
}
