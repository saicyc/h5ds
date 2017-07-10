package com.chinait.service.impl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.config.FileProperties;
import com.chinait.dao.AppRepository;
import com.chinait.dao.SourceRepository;
import com.chinait.dao.SourceTypeRepository;
import com.chinait.domain.Apps;
import com.chinait.domain.Source;
import com.chinait.domain.SourceType;
import com.chinait.domain.User;
import com.chinait.service.SourceService;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.UUIDUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.FileResultVO;
import com.chinait.vo.PageVO;
import com.chinait.vo.SourceVO;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class SourceServiceImpl implements SourceService{
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private SourceTypeRepository sourceTypeRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	FileProperties fileProperties;
	@Override
	@Transactional
	public void saveSource(List<FileResultVO> fileResultVOList, int appId, int sourseTypeId) {
		Apps app = appRepository.findOne(appId);
		SourceType  type = sourceTypeRepository.findOne(sourseTypeId);
		User user = UserAuthentic.getActiveUser();
		for(FileResultVO fileResultVO:fileResultVOList){
			Source source = new Source();
			source.setUser(user);
			source.setApps(app);
			source.setWidth(fileResultVO.getWidth());
			source.setHeight(fileResultVO.getHeight());
			source.setFilePath(fileResultVO.getFilePath());
			source.setSourceType(type);
			source = sourceRepository.save(source);
			String cutPath = user.getId()+"/"+app.getId()+"/"+fileProperties.getCutFile()+"/"+UUIDUtils.getUUIDString()+".jpg";
		    try {
				FilesUtil.createFile(fileProperties.getUploadFolder()+cutPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			source.setCutPath(fileProperties.getUploadFolderUrlPrefix()+cutPath);
			sourceRepository.save(source);
			fileResultVO.setSourceId(source.getId());
			fileResultVO.setCutPath(cutPath);
		}
	}
	@Transactional
	public void deleteSource(int sourceId) {
		Source source = sourceRepository.findOne(sourceId);
		source.setDelete(true);
		sourceRepository.save(source);
	}
	@Override
	public Page<Source> findSourceByApp(int appId, PageVO<SourceVO> pageVo, int sourceTypeId) {
		Apps app = appRepository.findOne(appId);
		SourceType type = sourceTypeRepository.findOne(sourceTypeId);
		Pageable page = new PageRequest(pageVo.getCurrentPage(), pageVo.getPageSize());
		if(type.getId()!=1){
			return sourceRepository.findByIsDeleteFalseAndSourceTypeOrderByCreateTimeDesc(type,page);
		}
		return sourceRepository.findByAppsAndIsDeleteFalseAndSourceTypeOrderByCreateTimeDesc(app,type,page);
	}
	@Override
	public Page<Source> findSystemSourceBySourceType(int sourceTypeId,PageVO<SourceVO> pageVO) {
		Pageable page = new PageRequest(pageVO.getCurrentPage(), pageVO.getPageSize());
		return sourceRepository.findBySourceTypeIdAndIsDeleteFalse(sourceTypeId,page);
	}
	@Override
	@Transactional
	public void saveSource(List<FileResultVO> fileResultVOList, int sourseTypeId) {
		SourceType  type = sourceTypeRepository.findOne(sourseTypeId);
		User user = UserAuthentic.getActiveUser();
		for(FileResultVO fileResultVO:fileResultVOList){
			Source source = new Source();
			source.setUser(user);
			source.setWidth(fileResultVO.getWidth());
			source.setHeight(fileResultVO.getHeight());
			source.setFilePath(fileResultVO.getFilePath());
			source.setCutPath(fileResultVO.getCutPath());
			source.setSourceType(type);
			source = sourceRepository.save(source);
			fileResultVO.setSourceId(source.getId());
		}
	}
	
}
