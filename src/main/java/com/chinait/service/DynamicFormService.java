package com.chinait.service;

import com.chinait.domain.DynamicForm;
import com.chinait.vo.FormVO;

public interface DynamicFormService {

	DynamicForm existForm(FormVO formVO) throws Exception;

	DynamicForm createDynamicForm(FormVO formVO);

}
