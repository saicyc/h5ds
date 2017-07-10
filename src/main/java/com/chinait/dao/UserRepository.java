package com.chinait.dao;

import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.User;

public interface  UserRepository extends CrudRepository<User, Integer>{

	User findByLoginNameAndIsDeleteIsFalse(String loginName);
}
