package com.chinait.service;

import java.util.List;

import com.chinait.domain.DynamicForm;
import com.chinait.domain.DynamicFormContent;
import com.chinait.vo.FormVO;

public interface DynamicFormContentService {

	void saveDynamicFormContent(FormVO formVO);

	int existContent(DynamicForm form);

	List<DynamicFormContent> findDynamicFormContent(DynamicForm dynamicForm);
	
}
