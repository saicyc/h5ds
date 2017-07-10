package com.chinait.service;

import java.util.List;

import com.chinait.domain.TemplateType;

public interface TemplateTypeService {

	List<TemplateType> findTemplatetype();

	void addSingleTemplatesType(String templatesTypeName);

	void updateTemplatesType(int simpleTemplateTypeId, String templatesTypeName);

	void deleteTemplatesType(int simpleTemplateTypeId);
	
	List<TemplateType> findTemplateTypes();
}
