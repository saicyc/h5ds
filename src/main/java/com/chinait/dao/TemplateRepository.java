package com.chinait.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.AppSingleTemplate;
import com.chinait.domain.TemplateType;
import com.chinait.domain.User;

public interface TemplateRepository extends CrudRepository<AppSingleTemplate, Integer>{

	AppSingleTemplate findByIsDeleteFalse(int templateId);

	Page<AppSingleTemplate> findByTemplateTypeIdAndIsDeleteFalseAndIsSystemTrue(int simpleTemplateTypeId,Pageable page);

	List<AppSingleTemplate> findByTemplateTypeAndIsDeleteFalseAndIsSystemFalse(TemplateType type);

	Page<AppSingleTemplate> findByIsSystemFalseAndIsDeleteFalse(Pageable page);

	AppSingleTemplate findByIsSystemFalseAndIsDeleteFalseAndId(int teplateId);

	List<AppSingleTemplate> findByTemplateTypeIdAndIsDeleteFalse(int typeId);

	List<AppSingleTemplate> findByTemplateTypeIdAndIsDeleteFalseAndUser(int typeId, User activeUser);

	List<AppSingleTemplate> findByTemplateTypeIdAndIsSystemFalseAndIsDeleteFalseAndUser(int typeId, User activeUser);

	List<AppSingleTemplate> findByTemplateTypeIdAndIsSystemTrueAndIsDeleteFalse(int typeId);

}
