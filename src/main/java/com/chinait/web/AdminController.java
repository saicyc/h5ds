package com.chinait.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinait.config.FileProperties;
import com.chinait.domain.AppSingleTemplate;
import com.chinait.domain.Apps;
import com.chinait.domain.Source;
import com.chinait.domain.SourceType;
import com.chinait.domain.TemplateType;
import com.chinait.domain.WebUser;
import com.chinait.service.AppService;
import com.chinait.service.AppTrackService;
import com.chinait.service.CaseService;
import com.chinait.service.MusicService;
import com.chinait.service.MusicTypeService;
import com.chinait.service.SourceService;
import com.chinait.service.SourceTypeService;
import com.chinait.service.TemplateService;
import com.chinait.service.TemplateTypeService;
import com.chinait.service.WebUserService;
import com.chinait.utils.Constance;
import com.chinait.utils.QRCodeUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.AppsVO;
import com.chinait.vo.CasesVO;
import com.chinait.vo.MusicVO;
import com.chinait.vo.PageVO;
import com.chinait.vo.SingleTemplateVO;
import com.chinait.vo.SourceTypeVO;
import com.chinait.vo.SourceVO;
import com.chinait.vo.UserVO;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private SourceTypeService sourceTypeService;
	@Autowired
	private WebUserService webUserService;
	@Autowired
	FileProperties fileProperties;
	@Autowired
	CaseService caseService;
	@Autowired
	AppService appService;
	@Autowired
	AppTrackService appTrackService;
	@Autowired
	TemplateService templateService;
	@Autowired
	TemplateTypeService templateTypeService;
	@Autowired
	MusicService musicService;
	@Autowired
	SourceService sourceService;
	@Autowired
	MusicTypeService musicTypeService;
	/**
     * 登录后台
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/skipBackstage")
    public String skipBackstage(){
    	return "adminTpl/index";
    }
	/**
     * 添加素材分类
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/addSourceType")
    @ResponseBody
    public boolean addSourceType(String sourceTypeName){
    	try{
    		sourceTypeService.addSourceType(sourceTypeName);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 删除素材分类，这里是假删除。
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/deleteSourceType")
    @ResponseBody
    public boolean deleteSourceType(int sourceTypeId){
    	try{
    		sourceTypeService.deleteSourceType(sourceTypeId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
   /**
    * 查询素材类型以及类型下的素材
    * @param map
    * @return
    */
    @RequestMapping("/findSourceType")
    public String findSourceType(ModelMap map){
    	List<SourceType> list =sourceTypeService.findSourceType();
    	Comparator<SourceType> comparator = new Comparator<SourceType>() {
			@Override
			public int compare(SourceType o1, SourceType o2) {
				return o1.getIsSystem()<o2.getIsSystem()?1:o1.getIsSystem()>o2.getIsSystem()?-1:0;
			}
		};
    	Collections.sort(list, comparator);
    	map.put("sourceList", list);
    	return "adminTpl/backstage";
    }
    /**
     * 查询素材类下面的素材
     * @param sourceTypeId
     * @param pageVo
     * @return
     */
    @RequestMapping("/findSources")
    @ResponseBody
    public PageVO<SourceVO> findSourceType(int sourceTypeId,PageVO<SourceVO> pageVo){
    	pageVo.setPageSize(20);
    	Page<Source> page= sourceTypeService.findSource(sourceTypeId,pageVo);
    	List<SourceVO> sourceVOList = new ArrayList<SourceVO>();
    	for(Source source:page.getContent()){
    		SourceVO sourceListVO = new SourceVO();
    		sourceListVO.setHeight(source.getHeight());
    		sourceListVO.setWidth(source.getWidth());
    		sourceListVO.setSourcePath(source.getFilePath());
    		sourceListVO.setCutPath(source.getCutPath());
    		sourceVOList.add(sourceListVO);
    	}
    	pageVo.setReturnList(sourceVOList);
    	pageVo.setTotalApps(page.getTotalElements());
    	pageVo.setTotalPages(page.getTotalPages());
    	return pageVo;
    }
   /**
    * 删除素材
    */
    @RequestMapping("/deleteSource")
    @ResponseBody
    public boolean deleteSource(int sourceId){
    	try{
    		sourceService.deleteSource(sourceId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询所有的app
     */
    @RequestMapping("/findAllApp")
    public String findAllApp(ModelMap map,PageVO<AppsVO> pageVo) throws IOException{
    	pageVo.setPageSize(20);
    	Page<Apps> page = appService.findAllApp(pageVo);
    	List<AppsVO> returnList = new ArrayList<AppsVO>();
    	for(Apps app:page.getContent()){
    		AppsVO appsVO = new AppsVO();
    		appsVO.setId(app.getId());
    		appsVO.setAppName(app.getAppName());
    		appsVO.setCreateTime(app.getCreateTime());
    		appsVO.setCoverPicture(app.getCoverPicture());
    		appsVO.setQrCode(app.getQrCode());
    		appsVO.setUserId(app.getUser().getId());
    		appsVO.setUserName(app.getUser().getUserName());
    		String current = app.getCurrentFilePath();
    		if(current!=null){
    			current = fileProperties.getUploadFolderUrlPrefix()+app.getUser().getId()+"/"+app.getId()+"/"+ current.substring(current.indexOf(".")-14);
    		}
    		appsVO.setCurrentPath(current);
    		returnList.add(appsVO);
    	}
    	pageVo.setReturnList(returnList);
    	pageVo.setTotalApps(page.getTotalElements());
    	pageVo.setTotalPages(page.getTotalPages());
    	map.put("page", pageVo);
    	return "adminTpl/allAppList";
    }
    /**
     * 分享为案列
     * @throws Exception 
     */
    @RequestMapping("/shareCase")
    @ResponseBody
    public boolean shareCase(int userId,int appId,QRCodeUtils utils){
    	Apps app = appService.findOne(appId);
    	String qrUrl = app.getQrCode();
    	qrUrl = qrUrl.substring(qrUrl.lastIndexOf("/")+1);
    	String fileUrl = app.getCoverPicture();
    	//判断是否文件是系统提供的
    	boolean isSystem = true;
    	if(!StringHelper.isEmpty(fileUrl)){
	    	if(fileUrl.indexOf("publicStatic")==-1){
	    		isSystem = false;
	    	}
	    	if(!StringHelper.isEmpty(fileUrl)){
	    		fileUrl.substring(fileUrl.lastIndexOf("/")+1);
	    	}
    	}
    	try{
    		caseService.saveCases(qrUrl,app.getCurrentFilePath().substring(app.getCurrentFilePath().lastIndexOf("/")+1),utils,fileUrl,isSystem,fileProperties.getUploadFolder()+userId+"/"+appId,app);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询会员
     * @param pageVO
     * @param map
     * @return
     */
    @RequestMapping("/findWebUser")
    public String findWebUser(PageVO<UserVO> pageVO,ModelMap map){
    	pageVO.setPageSize(20);
    	webUserService.findWebUser(pageVO);
    	map.put("page", pageVO);
    	return "adminTpl/userList";
    }
    /**
     * 删除会员
     * @param userId
     * @return
     */
    @RequestMapping("/deleteWebUser")
    @ResponseBody
    public boolean deleteWebUser(int userId){
    	try{
    		webUserService.deleteWebUser(userId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 删除案例
     * @param casesId
     * @return
     */
    @RequestMapping("/deleteCases")
    @ResponseBody
    public boolean deleteCases(int casesId){
    	try{
    		caseService.deleteCases(casesId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 案列上架下架
     */
    @RequestMapping("/hideCases")
    @ResponseBody
    public boolean hideCases(int casesId){
    	try{
    		caseService.hideCases(casesId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 管理员后台
     * 删除App
     * @param appId
     * @return
     */
    @RequestMapping("/deleteApp")
    @ResponseBody
    public boolean deleteApp(int appId){
    	try{
    		appService.deleteApp(appId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询一个系统类型的素材
     * @param SourceTypeId
     * @param pageVO
     * @return
     */
    @RequestMapping("/findSystemSource")
    @ResponseBody
    public PageVO<SourceVO> findSystemSource(int sourceTypeId,PageVO<SourceVO> pageVO){
    	pageVO.setPageSize(30);
    	Page<Source> page = sourceService.findSystemSourceBySourceType(sourceTypeId,pageVO);
    	List<SourceVO> returnList = new ArrayList<SourceVO>();
    	for(Source source:page.getContent()){
    		SourceVO sourceVO = new SourceVO();
    		sourceVO.setId(source.getId());
    		sourceVO.setSourcePath(source.getFilePath());
    		sourceVO.setHeight(source.getHeight());
    		sourceVO.setWidth(source.getWidth());
    		sourceVO.setCutPath(source.getCutPath());
    		returnList.add(sourceVO);
    	}
    	pageVO.setReturnList(returnList);
    	pageVO.setTotalApps(page.getTotalElements());
    	pageVO.setTotalPages(page.getTotalPages());
    	return pageVO;
    }
    /**
     * 查询系统素材的类型
     * @return
     */
    @RequestMapping("/findSystemSourceType")
    public String findSystemSourceType(ModelMap map){
    	List<SourceType> SourceTypeList = sourceTypeService.findSystemSourceType();
    	List<SourceTypeVO> returnList = new ArrayList<SourceTypeVO>();
    	for(SourceType sourceType:SourceTypeList){
    		SourceTypeVO type = new SourceTypeVO();
    		type.setSourceTypeName(sourceType.getSourceTypeName());
    		type.setId(sourceType.getId());
    		type.setDelete(sourceType.isDelete());
    		returnList.add(type);
    	}
    	map.put("list", returnList);
    	return "adminTpl/sourceList";
    }
    /**
     * 素材启用或者关闭
     */
    @RequestMapping("/sourceTypeEnable")
    @ResponseBody
    public boolean sourceTypeEnable(int sourceTypeId){
    	try{
    		sourceTypeService.sourceTypeEnable(sourceTypeId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 添加音乐类型
     */
   /* @RequestMapping("/addMusicType")
    @ResponseBody
    public String addMusicType(String musicTypeName){
    	musicTypeService.addMusicType(musicTypeName);
    	return "adminTpl/sourceMp3List";
    }*/
    /**
     * 查询音乐素材的类型
     */
   /* @RequestMapping("/findMusicType")
    public String findMusicType(ModelMap map){
    	List<MusicType> list = musicTypeService.findMusicType();
    	List<MusicTypeVO> returnList = new ArrayList<MusicTypeVO>();
    	for(MusicType type:list){
    		MusicTypeVO musicTypeVO = new MusicTypeVO();
    		musicTypeVO.setId(type.getId());
    		musicTypeVO.setMusicTypeName(type.getMusicTypeName());
    		returnList.add(musicTypeVO);
    	}
    	map.put("list", returnList);
    	return "adminTpl/sourceMp3List";
    }*/
    /**
     * 查询音乐
     * @return
     */
    @RequestMapping("/findMusic")
    public String findMusic(ModelMap map,PageVO<MusicVO> page){
    	page.setPageSize(20);
    	musicService.findUserMusic(page);
    	map.put("page", page);
    	return "adminTpl/sourceMp3List";
    }
    
    
    /**
     * 删除音乐
     */
    @RequestMapping("/deleteMusic")
    @ResponseBody
    public boolean deleteMusic(int musicId){
    	try{
    		musicService.deleteMusic(musicId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 修改音乐名称
     */
    @RequestMapping("/updateMusic")
    @ResponseBody
    public boolean updateMusic(int musicId,String musicName){
    	try{
    		musicService.updateMusic(musicId,musicName);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
   /**
    * 修改素材的名字
    * @param sourceTypeId
    * @param sourceTypeName
    * @return
    */
    @RequestMapping("/updateSourceType")
    @ResponseBody
    public boolean updateSourceType(int sourceTypeId,String sourceTypeName){
    	try{
    		sourceTypeService.updateSourceType(sourceTypeId,sourceTypeName);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询新增的用户
     * @return
     */
    @RequestMapping("/findNewUsers")
    public List<UserVO> findNewUsers(){
    	List<WebUser> userList = webUserService.findNewUsers();
    	List<UserVO> returnList = new ArrayList<UserVO>();
    	for(WebUser user:userList){
    		UserVO userVO = new UserVO();
    		userVO.setId(user.getId());
    		userVO.setUserName(user.getUserName());
    		userVO.setCreateTime(user.getCreateTime());
    		returnList.add(userVO);
    	}
    	return returnList;
    }
    /**
     * 重置密码
     * @param userId
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public boolean resetPassword(int userId){
    	try{
    		webUserService.updatePassword(Constance.REPASSWORD, userId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查看案列
     * @param page
     * @param map
     * @return
     */
    @RequestMapping("/findAllCase")
    public String findAllCase(PageVO<CasesVO> page,ModelMap map){
    	caseService.findAllCase(page);
    	map.put("page",page);
		return "adminTpl/casesList";
    }
    /**
     * 查询模板类型（系统）
     * @param map
     * @return
     */
    @RequestMapping("/findSingleTemplateType")
    public String findSingleTemplateType(ModelMap map){
    	List<TemplateType> list = templateTypeService.findTemplateTypes();
    	map.put("list", list);
    	return "adminTpl/singleTplList";
    }
    /** 
     * 是否启用模板
     */
   /* @RequestMapping("/singleTemplateEnable")
    public boolean singleTemplateEnable(int templateId){
    	try{
    		templateService.singleTemplateEnable(templateId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }*/
    /**
     * 通过模板类型来查询模板（系统）
     */
    @RequestMapping("/findSingleTemplates")
    @ResponseBody
    public PageVO<SingleTemplateVO> findSimpleTemplates(int simpleTemplateTypeId,PageVO<SingleTemplateVO> pageVO){
    	pageVO.setPageSize(20);
    	Page<AppSingleTemplate> page = templateService.findSimpleTemplates(simpleTemplateTypeId,pageVO);
    	List<SingleTemplateVO> list = new ArrayList<SingleTemplateVO>();
    	for(AppSingleTemplate template:page.getContent()){
    		SingleTemplateVO singleTemplateVO = new SingleTemplateVO();
    		singleTemplateVO.setId(template.getId());
    		singleTemplateVO.setFileUrl(template.getFileUrl());
    		singleTemplateVO.setUserName(template.getUser().getUserName());
    		list.add(singleTemplateVO);
    	}
    	pageVO.setReturnList(list);
    	pageVO.setTotalApps(page.getTotalElements());
    	pageVO.setTotalPages(page.getTotalPages());
    	return pageVO;
    }
    /**
     * 添加模板类型（系统）
     */
    @RequestMapping("/addSingleTemplatesType")
    @ResponseBody
    public boolean addSingleTemplatesType(String templatesTypeName){
    	try{
    		templateTypeService.addSingleTemplatesType(templatesTypeName);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 修改模板类型的名称（系统）
     */
    @RequestMapping("/updateTemplatesType")
    @ResponseBody
    public boolean updateTemplatesType(int simpleTemplateTypeId,String templatesTypeName){
    	try{
    		templateTypeService.updateTemplatesType(simpleTemplateTypeId,templatesTypeName);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 删除模板类型（系统）
     */
    @RequestMapping("/deleteTemplatesType")
    @ResponseBody
    public boolean deleteTemplatesType(int simpleTemplateTypeId){
    	try{
    		templateTypeService.deleteTemplatesType(simpleTemplateTypeId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询所有的模板
     */
    @RequestMapping("/findTemplates")
    public String findTemplates(ModelMap map,PageVO<SingleTemplateVO> page){
    	templateService.findTemplates(page);
    	map.put("page", page);
    	map.put("list", templateService.findSystemTemplateType());
    	return "adminTpl/singleTplListAll";
    }
    
    /** 
     * 设置模板为系统模版
     */
    @RequestMapping("/setSystemTemplates")
    @ResponseBody
    public boolean setSystemTemplates(int templateId,int templateTypeId){
    	try{
    		templateService.setSystemTemplates(templateId,templateTypeId);
	    }catch(Exception e){
			e.printStackTrace();
			return false;
		}
	return true;
    }
    
    /**
     * 删除模板
     */
    @RequestMapping("/deleteTemplate")
    @ResponseBody
    public boolean deleteTemplate(int templateId){
    	try{
    		templateService.deleteTemplate(templateId);
	    }catch(Exception e){
			e.printStackTrace();
			return false;
		}
    	return true;
    }
}
