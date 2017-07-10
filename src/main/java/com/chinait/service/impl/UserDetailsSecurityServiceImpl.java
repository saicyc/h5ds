package com.chinait.service.impl;

/**
 * Spring Security权限管理
 * @author cyc
 */
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chinait.config.UserInfo;
import com.chinait.dao.UserRepository;
import com.chinait.domain.Privilege;
import com.chinait.domain.Role;
import com.chinait.domain.User;
import com.chinait.service.MyUserDetailsService;

@Service
@Transactional
public class UserDetailsSecurityServiceImpl implements MyUserDetailsService{
	@Autowired
	public UserRepository userRepository;
	@Override
	public UserInfo loadUserByUsername(String loginName)throws UsernameNotFoundException {
		User user = userRepository.findByLoginNameAndIsDeleteIsFalse(loginName);
		if (user == null) {
				throw new UsernameNotFoundException(loginName + " 不存在！");
		}
		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>(); 
		Role role = user.getRole();
		List<Privilege> privilegelist = role.getPrivileges();
		for(Privilege item:privilegelist){
			authsList.add(new SimpleGrantedAuthority(item.getValue()));			
		}
		return new UserInfo(user,loginName,user.getPassword(),authsList);
	}
	
}
