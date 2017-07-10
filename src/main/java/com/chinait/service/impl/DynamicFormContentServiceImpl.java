package com.chinait.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinait.dao.DynamicFormContentRepository;
import com.chinait.domain.DynamicForm;
import com.chinait.domain.DynamicFormContent;
import com.chinait.service.DynamicFormContentService;
import com.chinait.vo.FormVO;
@Service
public class DynamicFormContentServiceImpl implements DynamicFormContentService {
	@Autowired
	DynamicFormContentRepository dynamicFormContentRepository;
	@Override
	@Transactional
	public void saveDynamicFormContent(FormVO formVO) {
		DynamicFormContent dynamicFormContent = new DynamicFormContent();
		dynamicFormContent.setContent(formVO.getContent());
		dynamicFormContent.setCreateTime(new Date());
		dynamicFormContent.setDynamicForm(formVO.getDynamicForm());
		dynamicFormContentRepository.save(dynamicFormContent);
	}
	@Override
	public int existContent(DynamicForm form) {
		return dynamicFormContentRepository.countByDynamicForm(form);
	}
	@Override
	public List<DynamicFormContent> findDynamicFormContent(DynamicForm dynamicForm) {
		return dynamicFormContentRepository.findByDynamicFormOrderByCreateTimeDesc(dynamicForm);
	}
}
