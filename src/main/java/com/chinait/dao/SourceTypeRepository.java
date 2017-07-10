package com.chinait.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.SourceType;

public interface SourceTypeRepository extends CrudRepository<SourceType, Integer>{

	List<SourceType> findByIsDeleteFalseAndIsSystem(int i);


}
