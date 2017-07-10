package com.chinait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.DigestUtils;

import com.chinait.config.FileProperties;
import com.chinait.dao.PrivilegeRepository;
import com.chinait.dao.RoleRepository;
import com.chinait.dao.SourceTypeRepository;
import com.chinait.dao.TemplateTypeRespository;
import com.chinait.dao.UserRepository;
import com.chinait.domain.Privilege;
import com.chinait.domain.Role;
import com.chinait.domain.SourceType;
import com.chinait.domain.TemplateType;
import com.chinait.domain.User;
import com.chinait.utils.Constance;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.Protect;
/**
     * 初始化用户
     * @author cyc
     * @param ctx
     */
/**
 * 权限 初始化角色  初始化用户
 * @author cyc
 *
 */
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) throws Exception {
    	
    	
    	
    	
    	
    	
    	/*if(Protect.getProtect()){*/
    		//ApplicationContext ctx = SpringApplication.run(Application.class, args);
    		//init(ctx);
    	/*}else{
    		throw new Exception("请在h5ds注册！");
    	}*/
    }
	/**
     * 初始化用户
     * @author cyc
     * @param ctx
     * @throws IOException 
     */
    @Transactional
    public static void init(ApplicationContext ctx) throws IOException{
    	UserRepository userRepository = ctx.getBean(UserRepository.class);
    	FileProperties fileProperties = ctx.getBean(FileProperties.class);
    	SourceTypeRepository sourceTypeRepository = ctx.getBean(SourceTypeRepository.class);
    	TemplateTypeRespository templateRepository = ctx.getBean(TemplateTypeRespository.class); 
    	List<User> list = (List<User>) userRepository.findAll();
    	//如果数据为空，则初始化数据
    	if(list.isEmpty()){
        	RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        	PrivilegeRepository privilegeRepository = ctx.getBean(PrivilegeRepository.class);
        	//权限
        	Privilege privilege = new Privilege();
    		privilege.setValue("APP_LOGIN");
    		privilege.setName("登录权限");
    		Privilege privileges = new Privilege();
    		privileges.setValue("APP_PERMISSIONS");
    		privileges.setName("后台管理权限");
    		privilege = privilegeRepository.save(privilege);
    		privileges = privilegeRepository.save(privileges);
    		List<Privilege> permList = new ArrayList<Privilege>();
    		List<Privilege> permLists = new ArrayList<Privilege>();
    		permList.add(privilege);
    		permList.add(privileges);
    		permLists.add(privilege);
    		//初始化角色
    		Role role = new Role();
    		role.setName("管理员");
    		Role role2 = new Role();
    		role2.setName("普通用户");
    		role.setPrivileges(permList);
    		role2.setPrivileges(permLists);
    		role = roleRepository.save(role);
    		role2 = roleRepository.save(role2);
        	//初始化用户
        	User user = new User();
    		user.setLoginName("admin@qq.com");
    		user.setUserName("陈亚超");
    		user.setPassword(DigestUtils.md5DigestAsHex("365811".getBytes()));
    		user.setRole(role);
    		user.setIsDelete(false);
    		user.setManager(true);
    		user = userRepository.save(user);
    		//生成admin User的文件夹
    		String personFileUrl = fileProperties.getUploadFolder()+user.getId();
    		user.setPersonFileUrl(personFileUrl); 
    		userRepository.save(user);
    		//初始化素材类型
    		SourceType type = new SourceType();
    		type.setSourceTypeName(Constance.SOURCE);
    		type.setIsSystem(0);
    		type.setDelete(false);
    		sourceTypeRepository.save(type);
    		//初始化单一模板类型
    		TemplateType  templateType = new TemplateType();
    		templateType.setTypeName(Constance.TEMPLATE);
    		templateRepository.save(templateType);
    		//初始化用户的文件
    		FilesUtil.createFolder(personFileUrl);
    		//初始化app文件夹
    		FilesUtil.createFolder(fileProperties.getUploadFolder());
    		//初始化模板
    		File outFile = FilesUtil.createFile(fileProperties.getTempleInitFile());
    		/*File inFile = new File("src/main/resource/templates/publicTpl/app.html");
    		FilesUtil.copyFile(inFile, outFile);*/
    	}
    }
}