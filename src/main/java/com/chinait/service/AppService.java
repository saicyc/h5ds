package com.chinait.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.chinait.domain.AppTrack;
import com.chinait.domain.Apps;
import com.chinait.domain.User;
import com.chinait.vo.AppsVO;
import com.chinait.vo.PageVO;
import com.chinait.vo.UserVO;


public interface AppService {
	public Map<String, String> createApp(User user) throws IOException;

	public Apps findOneApp(int appId);

	public void deleteApp(int appId);

	public List<AppTrack> appTracks(int appId);

	public AppTrack appTrackOne(int trackId);

	public void updateInfo(String title, String description, int appId,String coverPicture);

	public boolean checkUser(int appId, User user);

	public Page<Apps> findAllApp(PageVO<AppsVO> pageVo);

	public Apps findOne(int appId);

	public void createAppByCase(int caseId) throws IOException;

	public void CopeApp(User user, Apps app) throws IOException;

	public Page<Apps> findPersonApps(PageVO<AppsVO> pageVo);

}
