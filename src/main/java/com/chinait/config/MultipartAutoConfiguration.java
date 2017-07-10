package com.chinait.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ConditionalOnClass({ Servlet.class, StandardServletMultipartResolver.class,
		MultipartConfigElement.class })
@ConditionalOnProperty(prefix = "multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class MultipartAutoConfiguration {

	@Autowired
	private MultipartProperties multipartProperties = new MultipartProperties();
	@Autowired
	private FileProperties FileUploadProperties;
	@Bean
	@ConditionalOnMissingBean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		if (StringUtils.hasText(multipartProperties.getFileSizeThreshold())) {
			factory.setFileSizeThreshold(multipartProperties.getFileSizeThreshold());
		}
		if (StringUtils.hasText(multipartProperties.getLocation())) {
			factory.setLocation(multipartProperties.getLocation());
		}
		if (StringUtils.hasText(multipartProperties.getMaxRequestSize())) {
			factory.setMaxRequestSize(multipartProperties.getMaxRequestSize());
		}
		if (StringUtils.hasText(FileUploadProperties.getUploadFileSize())) {
			factory.setMaxFileSize(FileUploadProperties.getUploadFileSize());
		}
		return factory.createMultipartConfig();
	}

	@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
	@ConditionalOnMissingBean(value = MultipartResolver.class)
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

}