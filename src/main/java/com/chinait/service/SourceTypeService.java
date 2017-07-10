package com.chinait.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.chinait.domain.Source;
import com.chinait.domain.SourceType;
import com.chinait.vo.PageVO;
import com.chinait.vo.SourceVO;

public interface SourceTypeService {

	void addSourceType(String sourceTypeName);

	void deleteSourceType(int sourceTypeId);

	public List<SourceType> findSourceType();

	Page<Source> findSource(int sourceTypeId, PageVO<SourceVO> pageVo);

	void updateSourceType(int sourceTypeId, String sourceTypeName);

	List<SourceType> findSystemSourceType();

	void sourceTypeEnable(int sourceType);

}
