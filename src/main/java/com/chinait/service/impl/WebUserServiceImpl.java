package com.chinait.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinait.config.FileProperties;
import com.chinait.dao.RoleRepository;
import com.chinait.dao.WebUserRepository;
import com.chinait.domain.Role;
import com.chinait.domain.User;
import com.chinait.domain.WebUser;
import com.chinait.service.WebUserService;
import com.chinait.utils.Constance;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.PageVO;
import com.chinait.vo.UserVO;
@Service
public class WebUserServiceImpl implements WebUserService{
	private final class PageRequestExtension extends PageRequest {
		private PageRequestExtension(int page, int size) {
			super(page, size);
		}
	}
	@Autowired
	WebUserRepository webUserRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	FileProperties fileProperties;
	@Override
	@Transactional
	public void register(WebUser user) throws IOException {
		user = webUserRepository.save(user);
		String fileFolder = fileProperties.getUploadFolder();
		user.setPersonFileUrl(fileFolder+user.getId());
		FilesUtil.createFolder(user.getPersonFileUrl());
		Role role = roleRepository.findByName(Constance.USERCOMM);
		user.setRole(role);
		webUserRepository.save(user);
	}
	@Override
	public boolean findLoginName(String loginName) {
		WebUser user = webUserRepository.findTop1ByLoginName(loginName);
		if(user==null){
			return false;
		}
		return true;
	}
	@Override
	public boolean findUserName(String userName) {
		WebUser user = webUserRepository.findTop1ByUserName(userName);
		if(user==null){
			return false;
		}
		return false;
	}
	@Override
	@Transactional
	public void updatePassword(String newPassword,int userId) {
		WebUser users = webUserRepository.findOne(userId);
		users.setPassword(newPassword);
		webUserRepository.save(users);
	}
	@Override
	@Transactional
	public void updatePersonInfo(WebUser user) {
		User users = UserAuthentic.getActiveUser();
		WebUser webUsers = webUserRepository.findOne(users.getId());
		webUsers.setNickName(user.getNickName());
		webUsers.setQq(user.getQq());
		webUsers.setPhone(user.getPhone());
		webUsers.setAboutYourself(user.getAboutYourself());
		webUserRepository.save(webUsers);
	}
	@Override
	public WebUser fineOne(WebUser user) {
		return webUserRepository.findOne(user.getId());
	}
	@Override
	@Transactional
	public void deleteWebUser(int userId) {
		WebUser user = webUserRepository.findOne(userId);
		user.setIsDelete(true);
		webUserRepository.save(user);
	}
	@Override
	public List<WebUser> findNewUsers() {
		return webUserRepository.findTop20ByIsDeleteFalseOrderByCreateTimeDesc();
	}
	@Override
	public void findWebUser(PageVO<UserVO> pageVO) {
		Pageable page = new PageRequestExtension(pageVO.getCurrentPage(), pageVO.getPageSize());
		Page<Object[]> pages =webUserRepository.findAllPaged(page);
		List<UserVO> list = new ArrayList<UserVO>();
		for(Object[] obj :pages.getContent()){
			UserVO userVO = new UserVO();
			userVO.setId((int)obj[0]);
			userVO.setUserName((String)obj[1]);
			userVO.setQq((String)obj[2]);
			userVO.setEmail((String)obj[3]);
			userVO.setPhone((String)obj[4]);
			userVO.setAppNum((long)obj[5]);
			list.add(userVO);
		}
		pageVO.setReturnList(list);
    	pageVO.setTotalApps(pages.getTotalElements());
    	pageVO.setTotalPages(pages.getTotalPages());
	}
	@Override
	public void findEmailAndUserName(String userName) {
		
	}
}
