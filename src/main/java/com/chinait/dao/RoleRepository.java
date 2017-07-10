package com.chinait.dao;

import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	public Role findByName(String name);

}
