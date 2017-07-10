package com.chinait.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.chinait.domain.AppSingleTemplate;
import com.chinait.domain.TemplateType;
import com.chinait.vo.PageVO;
import com.chinait.vo.SingleTemplateVO;

public interface TemplateService {

	void saveSingleTemplate(String html, String qrCode, String url);

	List<AppSingleTemplate> findSimpleTemplateByType(int typeId);

	AppSingleTemplate referenceTemplate(int templateId);

	Page<AppSingleTemplate> findSimpleTemplates(int simpleTemplateTypeId,PageVO<SingleTemplateVO> pageVO);

	void findTemplates(PageVO<SingleTemplateVO> page);

	void setSystemTemplates(int templateId, int templateTypeId);

	void deleteTemplate(int templateId);

	List<TemplateType> findSystemTemplateType();

}
