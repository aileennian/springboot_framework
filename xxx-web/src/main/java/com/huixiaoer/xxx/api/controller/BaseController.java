package com.huixiaoer.xxx.api.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.UserMode;
import com.huixiaoer.utils.json.JacksonMapper;
import com.huixiaoer.xxx.api.config.ConfigProperties;

@Controller
public class BaseController {

	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	protected ConfigProperties properties;

	@Autowired
	protected RestTemplate restTemplate;
//	
	
	/**
	 * eureka.user.provider.url
	 * 可以将域名批向一个VIP，做IP漂移
	 */
	@Value("eureka.user.provider.url")
	protected String userProvider;

	public String getUserProvider() {
		return userProvider;
	}

	public void setUserProvider(String userProvider) {
		this.userProvider = userProvider;
	}

//	public RestTemplate getRestTemplate() {
//		return restTemplate;
//	}
//
//	public void setRestTemplate(RestTemplate restTemplate) {
//		this.restTemplate = restTemplate;
//	}
	
	
}
