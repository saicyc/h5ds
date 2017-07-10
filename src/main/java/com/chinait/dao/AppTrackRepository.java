package com.chinait.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chinait.domain.AppTrack;
import com.chinait.domain.Apps;

public interface AppTrackRepository extends CrudRepository<AppTrack,Integer>{

	List<AppTrack> findTop20ByAppsOrderByCreateTimeDesc(Apps app);

	AppTrack findTop1ByAppsOrderByCreateTimeDesc(Apps app);

}
