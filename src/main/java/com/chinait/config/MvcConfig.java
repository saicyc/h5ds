package com.chinait.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ConfigurationProperties(prefix = "mtsee")
public class MvcConfig extends WebMvcConfigurerAdapter{
	private String staticFolder;
	@Autowired
	FileProperties fileProperties;
	public void setStaticFolder(String staticFolder) {
		this.staticFolder = staticFolder;
	}

	@Bean
	@ConditionalOnMissingBean(CharacterEncodingFilter.class)
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("index");
    	registry.addViewController("/login").setViewName("login");
    	registry.addViewController("/publicTpl/register").setViewName("publicTpl/register");
    	registry.addViewController("/publicTpl/ok").setViewName("publicTpl/ok");
    	registry.addViewController("/publicTpl/sourceTest").setViewName("/publicTpl/sourceTest");
    	registry.addViewController("/publicTpl/notChrome").setViewName("/publicTpl/notChrome");
    	registry.addViewController("/publicTpl/test_fileupload").setViewName("/publicTpl/test_fileupload");
    	registry.addViewController("/publicTpl/test_form").setViewName("/publicTpl/test_form");
    }
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**").addResourceLocations(staticFolder);
		registry.addResourceHandler(fileProperties.getUploadFolderUrlPrefix()+"**").addResourceLocations("file:"+fileProperties.getUploadFolder());
	}
}
