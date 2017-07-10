package com.chinait.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.WebUser;

public interface WebUserRepository extends CrudRepository<WebUser, Integer>{

	WebUser findTop1ByLoginName(String loginName);

	WebUser findTop1ByUserName(String userName);

	List<WebUser> findTop20ByIsDeleteFalseOrderByCreateTimeDesc();
	
	@Query("select u.id,u.userName,u.qq,u.email,u.phone,sum(case when a.isDelete=0 then 1 else 0 end) as appNum from WebUser as u left join u.apps as a where u.isDelete = 0 and u.isManager = 0 GROUP BY u.id ORDER BY u.createTime desc")
	Page<Object[]> findAllPaged(Pageable page);
}
