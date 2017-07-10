package com.chinait.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinait.domain.DynamicForm;
import com.chinait.domain.DynamicFormContent;
import com.chinait.service.AppService;
import com.chinait.service.DynamicFormContentService;
import com.chinait.service.DynamicFormService;
import com.chinait.utils.Constance;
import com.chinait.vo.DynamicFormContentVO;
import com.chinait.vo.FormVO;

@Controller
@RequestMapping(value="/form")
public class FormController {
	@Autowired
	DynamicFormContentService dynamicFormContentService;
	@Autowired
	DynamicFormService dynamicFormService;
	@Autowired
	AppService appService;
	/**
	 * 保存表单数据
	 * @param request
	 * @param formVO
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveFormContent")
	public String saveFormContent(HttpServletRequest request,FormVO formVO,ModelMap model){
		formVO.setContent(getContent(request,formVO));
		try {
			formVO.setApps(appService.findOne(formVO.getAppId()));
			DynamicForm form = dynamicFormService.existForm(formVO);
			//动态表是否存入
			if(form==null){
				form = dynamicFormService.createDynamicForm(formVO);
			}
			formVO.setDynamicForm(form);
			//存入内容
			dynamicFormContentService.saveDynamicFormContent(formVO);
		} catch (Exception e) {
			e.printStackTrace();
			return "publicTpl/test_no";
		}
		return "publicTpl/test_ok";
	}
	/**
	 * 查看用户填的表单
	 * @param formVO
	 * @throws Exception 
	 */
	@RequestMapping("/findFormContent")
	@ResponseBody
	public Map findFormContent(FormVO formVO){
		Map map = new HashMap();
		DynamicForm dynamicForm;
		try {
			dynamicForm = dynamicFormService.existForm(formVO);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			return map;
		}
		List<DynamicFormContent> dynamicFormContentList = dynamicFormContentService.findDynamicFormContent(dynamicForm);
		List<DynamicFormContentVO> dynamicFormContentVOList = new ArrayList<DynamicFormContentVO>();
		for(DynamicFormContent dynamicFormContent:dynamicFormContentList){
			DynamicFormContentVO dynamicFormContentVO = new DynamicFormContentVO();
			dynamicFormContentVO.setContent(dynamicFormContent.getContent());
			dynamicFormContentVO.setCreateTime(dynamicFormContent.getCreateTime());
			dynamicFormContentVOList.add(dynamicFormContentVO);
		}
		map.put("flag", true);
		map.put("thead", dynamicForm.getCols());
		map.put("tbody", dynamicFormContentVOList);
		return map;
	}
	/**
	 * 是否存在动态表单
	 * @param request
	 * @param formVO
	 * @return
	 */
	@RequestMapping("/existForm")
	@ResponseBody
	public Map existForm(HttpServletRequest request,FormVO formVO){
		Map map = new HashMap();
		try{
			DynamicForm form = dynamicFormService.existForm(formVO);
			if(form!=null){
				int count = dynamicFormContentService.existContent(form);
				if(count>0){
					map.put("flag", false);
					map.put("message", "存在用户提交表单");
					return map;
				}
			}
			map.put("flag", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("flag", false);
			map.put("message", "数据错误请处理错误数据");
		}
		return map;
	}
	public String getContent(HttpServletRequest request,FormVO formVO){
		StringBuilder str = new StringBuilder();
		for(int i=1;i<=formVO.getTotalCols();i++){
			str.append(request.getParameter("content"+i)+Constance.DYNAMIC_FORM);
		}
		return str.toString();
	}
}
