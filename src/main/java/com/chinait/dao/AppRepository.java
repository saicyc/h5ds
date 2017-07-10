package com.chinait.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinait.domain.Apps;
import com.chinait.domain.User;


public interface AppRepository extends PagingAndSortingRepository<Apps, Integer> {

	Page<Apps> findByUserAndIsDeleteFalseOrderByCreateTimeDesc(User user, Pageable page);

	Apps findByIdAndUserAndIsDeleteFalse(int appId, User user);

	Apps findByIdAndIsDeleteFalse(int appId);

	Page<Apps> findByIsDeleteFalseOrderByCreateTimeDesc(Pageable page);
}
