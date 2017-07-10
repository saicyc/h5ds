package com.chinait.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinait.config.FileProperties;

@Controller
public class HelloController {

	@Autowired
	FileProperties fileProperties;
	
    @RequestMapping("/")
    public String index() {
        return "publicTpl/index";
    }
    
    @RequestMapping("/appEdit")
    public String appEdit() {
    	return "privateTpl/appEdit";
    }
    
    @RequestMapping("/loginPage")
    public String loginPage() {
    	return "publicTpl/index";
    }
    
    @RequestMapping("/test")
    public String test() {
    	return "publicTpl/saveAppTest";
    }
    
    @RequestMapping("/zhuanfor")
    public String zhuanfor(ModelMap map) {
    	return "publicTpl/ok";
    }
    
    @RequestMapping("/casePerson")
    public String casePerson() {
    	return "publicTpl/casePresentation";
    }
    /**
     * 跳转到非谷歌浏览器
     */
    @RequestMapping("/forward/notChrome")
    public String notChrome(){
    	return "publicTpl/notChrome";
    }
}
