package com.chinait.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.dao.SourceRepository;
import com.chinait.dao.SourceTypeRepository;
import com.chinait.domain.Source;
import com.chinait.domain.SourceType;
import com.chinait.service.SourceTypeService;
import com.chinait.utils.Constance;
import com.chinait.vo.PageVO;
import com.chinait.vo.SourceVO;
@Service
public class SourceTypeServiceImpl implements SourceTypeService {
	@Autowired 
	SourceTypeRepository sourceTypeRepository;
	@Autowired 
	SourceRepository sourceRepository;
	@Override
	@Transactional
	public void addSourceType(String sourceTypeName) {
		SourceType type = new SourceType();
		type.setSourceTypeName(sourceTypeName);
		type.setIsSystem(1);
		type.setDelete(false);
		sourceTypeRepository.save(type);
	}
	@Override
	@Transactional
	public void deleteSourceType(int sourceTypeId) {
		SourceType sourceType = sourceTypeRepository.findOne(sourceTypeId);
		sourceType.setDelete(true);
		sourceTypeRepository.save(sourceType);
	}
	@Override
	public List<SourceType> findSourceType() {
		return sourceTypeRepository.findByIsDeleteFalseAndIsSystem(Constance.SOURCE_SELF);
	}
	@Override
	public Page<Source> findSource(int sourceTypeId,PageVO<SourceVO> pageVo) {
		SourceType type = sourceTypeRepository.findOne(sourceTypeId);
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		return sourceRepository.findBySourceTypeAndIsDeleteFalseOrderByCreateTimeDesc(type,page);
	}
	@Override
	@Transactional
	public void updateSourceType(int sourceTypeId, String sourceTypeName) {
		SourceType type = sourceTypeRepository.findOne(sourceTypeId);
		type.setSourceTypeName(sourceTypeName);
		sourceTypeRepository.save(type);
	}
	@Override
	public List<SourceType> findSystemSourceType() {
		return sourceTypeRepository.findByIsDeleteFalseAndIsSystem(Constance.SOURCE_SYSTEM);
	}
	@Override
	public void sourceTypeEnable(int sourceType) {
		SourceType type = sourceTypeRepository.findOne(sourceType);
		if(type.isDelete()){
			type.setDelete(true);
		}else{
			type.setDelete(false);
		}
		sourceTypeRepository.save(type);
	}
}
