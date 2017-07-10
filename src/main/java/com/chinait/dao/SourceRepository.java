package com.chinait.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.Apps;
import com.chinait.domain.Source;
import com.chinait.domain.SourceType;

public interface SourceRepository extends CrudRepository<Source, Integer>{

	Page<Source> findBySourceTypeAndIsDeleteFalseOrderByCreateTimeDesc(SourceType type, Pageable page);

	Page<Source> findByAppsAndIsDeleteFalseAndSourceTypeOrderByCreateTimeDesc(Apps app, SourceType type, Pageable page);

	Page<Source> findBySourceTypeIdAndIsDeleteFalse(int sourceTypeId, Pageable page);

	Page<Source> findByIsDeleteFalseAndSourceTypeOrderByCreateTimeDesc(SourceType type, Pageable page);
}
