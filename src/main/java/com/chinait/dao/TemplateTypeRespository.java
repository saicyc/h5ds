package com.chinait.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.TemplateType;

public interface TemplateTypeRespository extends CrudRepository<TemplateType,Integer>{

	TemplateType findByTypeName(String typeName);

	List<TemplateType> findByIsSystemTrueAndIsDeleteFalseAndIsHideFalse();

	TemplateType findByIsSystemTrueAndId(int simpleTemplateTypeId);

	List<TemplateType> findByIsDeleteFalseAndIsHideFalse();

	List<TemplateType> findByIsSystemFalseAndIsHideFalseAndIsDeleteFalse();

	List<TemplateType> findByIsSystemTrueAndIsHideFalseAndIsDeleteFalse();

	List<TemplateType> findByIsDeleteFalseAndIsSystemTrueAndIsHideFalse();

}
