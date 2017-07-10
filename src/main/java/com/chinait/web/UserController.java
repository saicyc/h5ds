package com.chinait.web;
/**
 * 用户处理的控制器
 * @author yachao 
 */
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinait.domain.User;
import com.chinait.domain.WebUser;
import com.chinait.service.WebUserService;
import com.chinait.utils.Constance;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.ResponseVO;
import com.chinait.vo.UserVO;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	WebUserService webUserService;
	@RequestMapping("/register")
    public String register(WebUser user,String checkCode,HttpSession session,ModelMap map) throws IOException{
		ResponseVO<String> responseVO = new ResponseVO<String>();
		String checkcodeCurrent = (String) session.getAttribute(Constance.CHECK_CODE);
		try{
			if(checkcodeCurrent.equalsIgnoreCase(checkCode)){
				user.setEmail(user.getLoginName());
				user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
				user.setIsDelete(false);
				user.setManager(false);
				webUserService.register(user);
				responseVO.setMessage("注册成功！");
				responseVO.setStatus(true);
				session.removeAttribute("checkCode");
			}else{
				responseVO.setMessage("验证码输入错误！");
				responseVO.setStatus(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			responseVO.setMessage("用户名重复！");
			responseVO.setStatus(false);
		}
		map.put("responseVO", responseVO);
		if(responseVO.isStatus()){
			return "publicTpl/regOk";
		}else{
			return "publicTpl/register";
		}
    }
	@RequestMapping("/checkEmail")
	@ResponseBody
    public boolean check(String loginName) throws IOException{
		boolean bool = webUserService.findLoginName(loginName);
		return bool;
    }
	@RequestMapping("/checkUserName")
	@ResponseBody
    public boolean checkUserName(String userName) throws IOException{
		boolean bool = webUserService.findUserName(userName);
		return bool;
    }
	@RequestMapping("/jumpPersonInfo")
	public String jumpPersonInfo() throws IOException{
		return "privateTpl/personInfo";
    }
	 /**
     * 查看密码是否正确
     * @param newPassword
     */
    @RequestMapping("/findPersonPassword")
    @ResponseBody
    public boolean findPersonPassword(String newPassword){
    	User user = UserAuthentic.getActiveUser();
    	if(user.getPassword().equals(DigestUtils.md5DigestAsHex(newPassword.getBytes()))){
    		return true;
    	}
    	return false;
    }
    /**
     * 修改密码
     * @param newPassword
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public boolean updatePassword(String newPassword){
    	try{
    		webUserService.updatePassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()),UserAuthentic.getActiveUser().getId());
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @RequestMapping("/updatePersonInfo")
    @ResponseBody
    public boolean updatePersonInfo(WebUser user){
    	try{
    		webUserService.updatePersonInfo(user);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询个人信息
     * @param user
     * @return
     */
    @RequestMapping("/findPersonInfo")
    @ResponseBody
    public UserVO findPersonInfo(){
    	WebUser user = (WebUser)UserAuthentic.getActiveUser();
    	user = webUserService.fineOne(user);
    	UserVO userVo = new UserVO();
    	userVo.setAboutYourself(user.getAboutYourself());
    	userVo.setEmail(user.getEmail());
    	userVo.setNike(user.getNickName());
    	userVo.setPhone(user.getPhone());
    	userVo.setQq(user.getQq());
    	
    	return userVo;
    }
}
