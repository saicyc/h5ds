package com.chinait.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinait.domain.DynamicForm;
import com.chinait.domain.DynamicFormContent;

public interface DynamicFormContentRepository extends JpaRepository<DynamicFormContent,Integer>{


	int countByDynamicForm(DynamicForm form);

	List<DynamicFormContent> findByDynamicFormOrderByCreateTimeDesc(DynamicForm dynamicForm);
	
}
