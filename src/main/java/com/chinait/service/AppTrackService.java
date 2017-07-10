package com.chinait.service;

import com.chinait.domain.AppTrack;

public interface AppTrackService {

	void save(String path, int appId);

	AppTrack findOneAppTrack(int trackId);

	AppTrack findAppByAppId(int appId);

}
