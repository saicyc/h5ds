package com.chinait.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinait.config.FileProperties;
import com.chinait.domain.AppSingleTemplate;
import com.chinait.domain.AppTrack;
import com.chinait.domain.Apps;
import com.chinait.domain.Cases;
import com.chinait.domain.MusicType;
import com.chinait.domain.Source;
import com.chinait.domain.SourceType;
import com.chinait.domain.TemplateType;
import com.chinait.domain.User;
import com.chinait.service.AppService;
import com.chinait.service.AppTrackService;
import com.chinait.service.CaseService;
import com.chinait.service.MusicService;
import com.chinait.service.MusicTypeService;
import com.chinait.service.SourceService;
import com.chinait.service.SourceTypeService;
import com.chinait.service.TemplateService;
import com.chinait.service.TemplateTypeService;
import com.chinait.utils.Base64Utils;
import com.chinait.utils.Constance;
import com.chinait.utils.FilesUtil;
import com.chinait.utils.QRCodeUtils;
import com.chinait.utils.UUIDUtils;
import com.chinait.utils.UserAuthentic;
import com.chinait.vo.AppTrackVO;
import com.chinait.vo.AppsVO;
import com.chinait.vo.CasesVO;
import com.chinait.vo.MusicTypeVO;
import com.chinait.vo.MusicVO;
import com.chinait.vo.PageVO;
import com.chinait.vo.SourceTypeVO;
import com.chinait.vo.SourceVO;
import com.chinait.vo.TemplateTypeVO;

@Controller
@RequestMapping(value="/app")
public class AppController {
	@Autowired
	FileProperties fileProperties;
	@Autowired
	AppTrackService appTrackService;
	@Autowired
	AppService appService;
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceTypeService sourceTypeService;
	@Autowired
	TemplateService templateService;
	@Autowired 
	TemplateTypeService templateTypeService;
	@Autowired
	CaseService caseService;
	@Autowired
	MusicService musicService;
	@Autowired
	MusicTypeService musicTypeService;
	/**
     * 保存app
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/saveHtml")
    @ResponseBody
    public boolean saveHtml(String html,int appId){
    	try{
    		saveApp(html,appId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 单独提出来公用方法使用
     * @param html
     * @param appId
     * @throws IOException
     */
    public void saveApp(String html,int appId) throws IOException{
    	//copy 文件生成历史文件
    	User user = UserAuthentic.getActiveUser();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	File outFile = FilesUtil.createFile(fileProperties.getUploadFolder()+user.getId()+"/"+appId+"/"+sdf.format(new Date())+".html");
    	//将新的内容保存到index文件里面
    	FilesUtil.writeStringToFile(outFile, html);
    	//生成历史版本号
    	appTrackService.save(fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+appId+"/"+sdf.format(new Date())+".html",appId);
    }
    /**
     * 创建app
     * @author cyc
     * @return
     * @throws Exception 
     */
    @RequestMapping("/addApp")
    public String addApp(FilesUtil filesUtil,QRCodeUtils qRCodeUtils) throws Exception {
    	User user = UserAuthentic.getActiveUser();
    	Map<String, String> map = appService.createApp(user);
    	String currentUrl = map.get("currentUrl");
    	String fileUrl = map.get("fileUrl");
    	String indexFile = fileUrl+"/"+Constance.FILENAME;
    	FilesUtil.createFile(currentUrl);
		FilesUtil.createFile(indexFile);
    	String tempUrl = fileProperties.getTempleInitFile();
    	//拷贝文件
    	filesUtil.copeFile(tempUrl, currentUrl);
    	filesUtil.copeFile(tempUrl, indexFile);
    	//生成二维码图片
    	String appId = map.get("appId");
    	String fileFile = user.getPersonFileUrl()+"/"+appId;
    	String content = fileProperties.getWebsite()+fileProperties.getUploadFolderUrlPrefix()+user.getId()+"/"+appId+"/"+Constance.FILENAME;
    	boolean bool = qRCodeUtils.createQRCode(appId, fileFile, content);
    	if(!bool){
    		throw new Exception("该二维码生成失败！");
    	}
    	return "redirect:/app/findApps";
    }
    /**
     * 删除app
     * @param appId
     * @return
     * @throws IOException
     */
    @RequestMapping("/deleteApp")
    public String deleteApp(int appId) throws IOException {
    	appService.deleteApp(appId);
    	return "redirect:/app/findApps";
    }
    /**
     * 查看app
     * @author cyc
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/findApps")
    public String findApps(ModelMap model,PageVO<AppsVO> pageVo) throws IOException{
    	Page<Apps> page = appService.findPersonApps(pageVo);
    	pageVo.setTotalApps(page.getTotalElements());
    	pageVo.setTotalPages(page.getTotalPages());
    	List<AppsVO> AppsListVOLists = new ArrayList<AppsVO>();
    	for(Apps app : page.getContent()){
    		AppsVO appsListVO = new AppsVO();
    		appsListVO.setId(app.getId());
    		appsListVO.setAppName(app.getAppName());
    		appsListVO.setCreateTime(app.getCreateTime());
    		appsListVO.setAccessTimes(app.getAccessTimes());
    		appsListVO.setQrCode(app.getQrCode());
    		appsListVO.setTitle(app.getAppName());
    		appsListVO.setDescription(app.getDescribtion());
    		appsListVO.setCoverPicture(app.getCoverPicture());
    		AppsListVOLists.add(appsListVO);
    	}
    	pageVo.setReturnList(AppsListVOLists);
    	model.put("appLists",pageVo);
    	return "privateTpl/myApps";
    }
    /**
     * 跳转到h5编辑器
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/skipApp")
    public String skipApp(ModelMap model,int appId) throws IOException{
    	User user = UserAuthentic.getActiveUser();
    	boolean bool = appService.checkUser(appId,user);
    	if(!bool){
    		return "redirect:/app/findApps";
    	}
    	model.put("appId",appId);
    	return "privateTpl/appEdit";
    }
    /**
     * 删除素材
     * @author cyc
     * @param html
     * @return
	 * @throws IOException 
     */
    @RequestMapping("/deleteSource")
    @ResponseBody
    public Map<String,Integer> deleteSource(int sourceId) throws IOException{
    	Map<String,Integer> map = new HashMap<String,Integer>();
    	sourceService.deleteSource(sourceId);
    	map.put("status", 1);
    	return map;
    }
    /**
     * 查询的App下面的素材
     * 
     */
    @RequestMapping("/findSources")
    @ResponseBody
    public Map<String,PageVO<SourceVO>> findSources(int appId,PageVO<SourceVO> pageVo,int sourceTypeId) throws IOException{
    	Map<String,PageVO<SourceVO>> map = new HashMap<String,PageVO<SourceVO>>();
    	Page<Source> page = sourceService.findSourceByApp(appId,pageVo,sourceTypeId);
    	List<SourceVO> list = new ArrayList<SourceVO>();
    	for(Source source:page.getContent()){
    		SourceVO sourceVO = new SourceVO();
    		sourceVO.setId(source.getId());
    		sourceVO.setSourcePath(source.getFilePath());
    		sourceVO.setWidth(source.getWidth());
    		sourceVO.setHeight(source.getHeight());
    		sourceVO.setCutPath(source.getCutPath());
    		list.add(sourceVO);
    	}
    	pageVo.setReturnList(list);
    	pageVo.setTotalApps(page.getTotalElements());
    	pageVo.setTotalPages(page.getTotalPages());
    	map.put("sources", pageVo);
    	return map;
    }
    /**
     * 查询素材 
     * @return
     * @throws IOException
     */
    @RequestMapping("/findSourcesType")
    @ResponseBody
    public Map<String,List<SourceTypeVO>> findSourcesType() throws IOException{
    	Map<String,List<SourceTypeVO>> map = new HashMap<String,List<SourceTypeVO>>();
    	List<SourceType> list = sourceTypeService.findSourceType();
    	List<SourceTypeVO> SourceTypeVOList = new ArrayList<SourceTypeVO>();
    	for(SourceType sourceType:list){
    		SourceTypeVO sourceTypeVO = new SourceTypeVO();
    		sourceTypeVO.setId(sourceType.getId());
    		sourceTypeVO.setSourceTypeName(sourceType.getSourceTypeName());
    		SourceTypeVOList.add(sourceTypeVO);
    	}
    	map.put("sourceType", SourceTypeVOList);
    	return map;
    }
    /**
     * 查询具体的app
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/findOneApp")
    @ResponseBody
    public Map<String,String> findOneApp(int appId,FilesUtil filesUtil) throws IOException{
    	Map<String,String> map = new HashMap<String,String>();
    	Apps app = appService.findOneApp(appId);
    	String currentFile = app.getCurrentFilePath();
    	File file = new File(fileProperties.getUploadFolder()+app.getUser().getId()+"/"+app.getId()+"/"+currentFile.substring(currentFile.lastIndexOf(".")-14));
    	if(!file.exists()){
    		map.put("status", "0");
    		map.put("message", "该app的文件不存在！");
    	}else{
	    	String appHtml = filesUtil.fileToString(file);
	    	String startStr = "<body>";
	    	String endStr = "</body>";
	    	int start = appHtml.indexOf(startStr)+startStr.length();
	    	int end = appHtml.indexOf(endStr)-endStr.length();
	    	map.put("status", "1");
	    	map.put("appHtml", appHtml.substring(start,end));
    	}
    	return map;
    }
    /**
     * 查看历史记录
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/findAppHistory")
    @ResponseBody
    public List<AppTrackVO> findAppHistory(ModelMap model,int appId) throws IOException{
    	List<AppTrack> trackList = appService.appTracks(appId);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<AppTrackVO> appTrackVOList = new ArrayList<AppTrackVO>();
    	for(AppTrack track :trackList){
    		AppTrackVO appTrackVO = new AppTrackVO();
    		appTrackVO.setTrackId(track.getId());
    		appTrackVO.setCreateTime(sdf.format(track.getCreateTime()));
    		appTrackVOList.add(appTrackVO);
    	}
    	return appTrackVOList;
    }
   /**
    * 还原到某个历史记录
    * @param model
    * @param appId
    * @return
    * @throws IOException
    */
    @RequestMapping("/restoreAppHistory")
    @ResponseBody
    public Map<String,String> findOneAppHistory(ModelMap model,int trackId,int appId) throws IOException{
    	Map<String,String>map = new HashMap<String,String>();
    	try{
    		//查询需要还原的历史文件
    		AppTrack appTrack = appTrackService.findOneAppTrack(trackId);
	    	File file = new File(appTrack.getFilePath());
	    	String appHtml = FilesUtil.readFileToString(file);
	    	String startStr = "<body>";
	    	String endStr = "</body>";
	    	int start = appHtml.indexOf(startStr)+startStr.length();
	    	int end = appHtml.indexOf(endStr)-endStr.length();
	    	map.put("status", "1");
	    	map.put("appHtml", appHtml.substring(start,end));
    	}catch(Exception e){
    		e.printStackTrace();
    		map.put("status", "0");
    	}
    	return map;
    }
    /**
     * 修改app 信息
     * @param title
     * @param coverPicture
     * @param description
     * @param appId
     * @return
     */
    @RequestMapping("/updateAppsInfo")
    @ResponseBody
    public boolean updateAppsInfo(String title,String description,String coverPicture,int appId){
    	try{
    		appService.updateInfo(title,description,appId,coverPicture);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 查询app 信息
     * @param title
     * @param coverPicture
     * @param description
     * @param appId
     * @return
     */
    @RequestMapping("/findAppsInfo")
    @ResponseBody
    public Map<String,String> findAppsInfo(int appId){
    	Apps app = appService.findOneApp(appId);
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("title", app.getTitle());
    	map.put("description", app.getDescribtion());
    	return map;
    }
    /**
     * 复制App
     * @param appId
     * @return
     */
    @RequestMapping("/copyApp")
    public String copyApp(int appId){
    	Apps app = appService.findOneApp(appId);
    	User user = UserAuthentic.getActiveUser();
    	try {
			appService.CopeApp(user,app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "redirect:/app/findApps";
    }
    /**
     * 发布App
     * @param html
     * @param appId
     * @return
     */
    @RequestMapping("/publishApp")
    @ResponseBody
    public boolean publishApp(String html,int appId){
    	try{
    		
	    	//保存现有的版本
	    	saveApp(html,appId);
			//将现有的版本进行copy 到index.html
	    	File outFile = new File(fileProperties.getUploadFolder()+UserAuthentic.getActiveUser().getId()+"/"+appId+"/"+Constance.FILENAME);
	    	FilesUtil.writeStringToFile(outFile, html);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    /**
     * 预览app
     * @param appId
     * @return
     */
    @RequestMapping("/previewApp")
    @ResponseBody
    public Map<String,String> previewApp(int appId){
    	Map<String,String> map = new HashMap<String,String>();
    	AppTrack appTrack = appTrackService.findAppByAppId(appId);
    	String fileUril = appTrack.getFilePath();
    	String urls = fileUril.substring(fileUril.lastIndexOf(".")-14);
    	String url  = fileProperties.getUploadFolderUrlPrefix()+UserAuthentic.getActiveUser().getId()+"/"+appId+"/"+urls;
    	map.put("appHtml", url);
		return map;
    }
    /**
     * 保存我的app单个模板
     * @throws IOException
     */
    @RequestMapping(value="/saveSingleTemplate", method=RequestMethod.POST)
    @ResponseBody
    public boolean saveSingleTemplate(String html,String file) throws IOException{
    	try{
    		file = file.substring(file.indexOf(",")+1);
    		String fileURL = UserAuthentic.getActiveUser().getId()+"/"+fileProperties.getCases()+"/"+UUIDUtils.getUUIDString()+Constance.FILEFORMAT;
    		String outFile = fileProperties.getUploadFolder()+fileURL;
    		FilesUtil.createFile(outFile);
    		String qrCode = fileProperties.getUploadFolderUrlPrefix()+fileURL;
		    Base64Utils.decoderCreateFile(file, outFile);
    		templateService.saveSingleTemplate(html,qrCode,fileProperties.getUploadFolderUrlPrefix()+fileURL);
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
		return true;
    }
    /**
     * 查看模板的类型
     */
    @RequestMapping("/findSimpleType")
    @ResponseBody
    public List<TemplateType> saveSingleTemplate(){
    	return templateTypeService.findTemplatetype();
    }
    /**
     * 查看模板
     */
    @RequestMapping("/findSimpleTemplateByType")
    @ResponseBody
    public List<TemplateTypeVO> findSimpleTemplateByType(int typeId){
    	List<AppSingleTemplate> list = templateService.findSimpleTemplateByType(typeId);
    	List<TemplateTypeVO> templateTypeVOList = new ArrayList<TemplateTypeVO>();
    	for(AppSingleTemplate template:list){
    		TemplateTypeVO templateVo = new TemplateTypeVO();
    		templateVo.setFileUrl(template.getFileUrl());
    		templateVo.setId(template.getId());
    		templateVo.setHtml(template.getTemplateHtml());;
    		templateTypeVOList.add(templateVo);
    	}
    	return templateTypeVOList;
    }
   /**
    * 引用模板
    */
    @RequestMapping("/referenceTemplate")
    @ResponseBody
    public Map<String,String> referenceTemplate(int templateId){
    	Map<String,String> map = new HashMap<String,String>();
    	AppSingleTemplate template= templateService.referenceTemplate(templateId);
    	if(template ==null){
    		map.put("status", "0");
    		map.put("ms", "该模板未找到!");
    	}else{
    		map.put("status", "1");
    		map.put("ms", "成功找到该模板！");
    		map.put("data", template.getTemplateHtml());
    	}
    	return map;
    }
    /**
     * 使用案列
     */
    @RequestMapping("/userCases")
    public String userCases(int caseId){
    	try{
    		appService.createAppByCase(caseId);
    	}catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    	return "redirect:/app/findApps";
    }
    /**
     * 展示案列列表
     */
    @RequestMapping("/showCaseList")
    public String showCase(PageVO<CasesVO> pageVo,ModelMap map){
    	pageVo.setPageSize(12);
    	caseService.findAll(pageVo);
    	map.put("cases", pageVo);
    	return "publicTpl/casePresentation";
    }
    /** 
     * 查看某个案列
     * @throws IOException 
     */
    @RequestMapping("/findOneCase")
    public String showCase(int casesId,ModelMap map) throws IOException{
    	Cases cases = caseService.findOneCases(casesId);
    	String fileUrl = cases.getCaseUrl();
    	String returnUrl = fileProperties.getUploadFolder()+fileProperties.getCases()+casesId+fileUrl.substring(fileUrl.lastIndexOf("/"));
    	map.put("appHtml", returnUrl);
    	return "publicTpl/casePresentation";
    }
    /**
     * 查询音乐
     * @return
     */
    @RequestMapping("/findMusic")
    @ResponseBody
    public List<MusicVO> findMusic(ModelMap map){
    	List<MusicVO> list = musicService.findUserMusic();
    	return list;
    }
    /**
     * 查询音乐素材的类型
     */
    @RequestMapping("/findMusicType")
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
    }
    /**
     * 帮助
     */
    @RequestMapping("/help")
    public String help(){
    	return "publicTpl/help";
    }
}
