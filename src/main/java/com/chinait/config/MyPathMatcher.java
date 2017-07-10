package com.chinait.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class MyPathMatcher extends AntPathMatcher{
	
	private static Logger log = LoggerFactory.getLogger(MyPathMatcher.class);
	
	@Override
	public boolean match(String pattern, String path) {
		log.info("pattern:{}|path:{}",pattern,path);
		return doMatch(pattern, path, true, null);
	}
}
