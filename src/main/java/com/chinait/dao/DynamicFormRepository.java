package com.chinait.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinait.domain.DynamicForm;

public interface DynamicFormRepository extends JpaRepository<DynamicForm,Integer>{

	List<DynamicForm> findByAppsIdAndTimeStamp(int appId, String timeStamp);


}
