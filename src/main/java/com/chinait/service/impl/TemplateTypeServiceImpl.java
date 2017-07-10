package com.chinait.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinait.dao.TemplateTypeRespository;
import com.chinait.domain.TemplateType;
import com.chinait.service.TemplateTypeService;
@Service
public class TemplateTypeServiceImpl implements TemplateTypeService{
	@Autowired
	private TemplateTypeRespository  templateTypeRespository;
	@Override
	public List<TemplateType> findTemplatetype() {
		return (List<TemplateType>) templateTypeRespository.findByIsDeleteFalseAndIsHideFalse();
	}
	@Override
	public List<TemplateType> findTemplateTypes() {
		return (List<TemplateType>) templateTypeRespository.findByIsDeleteFalseAndIsSystemTrueAndIsHideFalse();
	}
	@Override
	@Transactional
	public void addSingleTemplatesType(String templatesTypeName) {
		TemplateType type = new TemplateType();
		type.setTypeName(templatesTypeName);
		type.setDelete(false);
		type.setSystem(true);
		templateTypeRespository.save(type);
	}
	@Override
	@Transactional
	public void updateTemplatesType(int simpleTemplateTypeId, String templatesTypeName) {
		TemplateType template = templateTypeRespository.findByIsSystemTrueAndId(simpleTemplateTypeId);
		template.setTypeName(templatesTypeName);
		templateTypeRespository.save(template);
	}
	@Override
	@Transactional
	public void deleteTemplatesType(int simpleTemplateTypeId) {
		TemplateType template = templateTypeRespository.findOne(simpleTemplateTypeId);
		template.setDelete(true);
		templateTypeRespository.save(template);
	}
	
}
