package com.chinait.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chinait.domain.Cases;

public interface CasesRepository extends JpaRepository<Cases,Integer>{

	Page<Cases> findByIsHideFalseOrderByCreateTimeDesc(Pageable page);

	Page<Cases> findByIsDeleteFalseAndIsHideFalseOrderByCreateTimeDesc(Pageable page);

	Page<Cases> findByIsDeleteFalseOrderByCreateTimeDesc(Pageable page);
}
