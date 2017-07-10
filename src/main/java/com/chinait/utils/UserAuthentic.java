package com.chinait.utils;


import org.springframework.security.core.context.SecurityContextHolder;

import com.chinait.config.UserInfo;
import com.chinait.domain.User;

public class UserAuthentic {
	public static User getActiveUser(){
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
		    .getAuthentication().getPrincipal();
		return userInfo.user;
	}
	
	public static UserInfo getActiveUserInfo(){
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
		    .getAuthentication().getPrincipal();
		return userInfo;
	}
}
