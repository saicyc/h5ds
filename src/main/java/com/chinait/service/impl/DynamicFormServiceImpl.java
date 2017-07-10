package com.chinait.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinait.dao.DynamicFormRepository;
import com.chinait.domain.DynamicForm;
import com.chinait.service.DynamicFormService;
import com.chinait.vo.FormVO;
@Service
public class DynamicFormServiceImpl implements DynamicFormService {

	@Autowired
	DynamicFormRepository dynamicFormRepository;
	@Override
	public DynamicForm existForm(FormVO formVO) throws Exception {
		List<DynamicForm> list =  dynamicFormRepository.findByAppsIdAndTimeStamp(formVO.getAppId(),formVO.getTimeStamp());
		if(list.isEmpty()){
			return null;
		}
		if(list.size()>1){
			throw new Exception("数据错误请处理错误数据！");
		}
		return list.get(0);
	}
	@Override
	@Transactional
	public DynamicForm createDynamicForm(FormVO formVO) {
		DynamicForm form = new DynamicForm();
		form.setApps(formVO.getApps());
		form.setCols(formVO.getCols());
		form.setFormName(formVO.getFormName());
		form.setTimeStamp(formVO.getTimeStamp());
		form.setCreateTime(new Date());
		return dynamicFormRepository.save(form);
	}

}
