package com.chinait.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinait.dao.AppRepository;
import com.chinait.dao.AppTrackRepository;
import com.chinait.domain.AppTrack;
import com.chinait.domain.Apps;
import com.chinait.service.AppTrackService;
@Service
public class AppTrackServiceImpl implements AppTrackService{
	@Autowired
	AppTrackRepository appTrackRepository;
	@Autowired
	AppRepository appRepository;
	@Override
	@Transactional
	public void save(String path, int appId) {
		Apps app = appRepository.findOne(appId);
		app.setCurrentFilePath(path);
		appRepository.save(app);
		AppTrack appTrack = new AppTrack();
		appTrack.setFilePath(path);
		appTrack.setCreateTime(new Date());
		appTrack.setApps(app);
		appTrackRepository.save(appTrack);
	}
	@Override
	public AppTrack findOneAppTrack(int trackId) {
		return appTrackRepository.findOne(trackId);
	}
	@Override
	public AppTrack findAppByAppId(int appId) {
		Apps app = appRepository.findOne(appId);
		AppTrack appTrack = appTrackRepository.findTop1ByAppsOrderByCreateTimeDesc(app);
		return appTrack;
	}
}
