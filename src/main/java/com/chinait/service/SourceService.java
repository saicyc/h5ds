package com.chinait.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.chinait.domain.Source;
import com.chinait.vo.FileResultVO;
import com.chinait.vo.PageVO;
import com.chinait.vo.SourceVO;

public interface SourceService {

	void saveSource(List<FileResultVO> fileResultVOList,int appId,int sourseTypeId);

	void deleteSource(int sourceId);

	public Page<Source> findSourceByApp(int appId, PageVO<SourceVO> pageVo, int sourceTypeId);

	Page<Source> findSystemSourceBySourceType(int sourceTypeId, PageVO<SourceVO> pageVO);

	void saveSource(List<FileResultVO> fileResultVOList, int sourseTypeId);
}
