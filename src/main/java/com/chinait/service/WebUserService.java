package com.chinait.service;

import java.io.IOException;
import java.util.List;

import com.chinait.domain.WebUser;
import com.chinait.vo.PageVO;
import com.chinait.vo.UserVO;

public interface WebUserService {

	public void register(WebUser user) throws IOException;

	public boolean findLoginName(String loginName);

	public boolean findUserName(String userName);

	public void updatePassword(String newPassword,int userId);

	public void updatePersonInfo(WebUser user);

	public WebUser fineOne(WebUser user);

	public void deleteWebUser(int userId);

	public List<WebUser> findNewUsers();

	public void findWebUser(PageVO<UserVO> pageVO);

	public void findEmailAndUserName(String userName);

}
