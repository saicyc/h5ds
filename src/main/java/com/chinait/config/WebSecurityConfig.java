package com.chinait.config;
/**
 * spring Security javaconfig
 * @author cyc
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chinait.filter.UserFilter;
import com.chinait.service.MyUserDetailsService;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private FileProperties fileProperties;
	@Autowired
	private MyUserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.antMatchers("/admin/**").hasAuthority("APP_PERMISSIONS")
        	.antMatchers("/forward/notChrome","/app/showCaseList","/draw/image","/user/checkEmail","/user/register","/login","/","/publicStatic/**","/publicTpl/**",fileProperties.getUploadFolderUrlPrefix()+"**").permitAll()
			.antMatchers("/app/**","/upload/**").access("hasAuthority('APP_PERMISSIONS') or hasAuthority('APP_LOGIN')")
        	.anyRequest().authenticated();
        http
	        .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("/")
	            .permitAll()
	            .and()
		        .logout()
		        .logoutSuccessUrl("/")
	            .permitAll();
        //http.rememberMe();
        //http.addFilterBefore(new UserFilter(),UsernamePasswordAuthenticationFilter.class);
        //限制只有一个人可以登录
        http.sessionManagement().maximumSessions(1).expiredUrl("/app/findApps");
        //取消跨域问题
        http.headers().disable();
        http.csrf().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }
}