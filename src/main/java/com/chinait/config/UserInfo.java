package com.chinait.config;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
/**
 * SpringSecurity user管理
 * @author cyc
 *
 */

public class UserInfo extends User{
	private static final long serialVersionUID = 1L;
	public com.chinait.domain.User user;
		
	public UserInfo(com.chinait.domain.User user,String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.user=user;
	}
	public com.chinait.domain.User getUser() {
		return user;
	}
	public void setUser(com.chinait.domain.User user) {
		this.user = user;
	}
}
