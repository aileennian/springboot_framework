package com.nian.xxx.spider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 自定义业务属性
 * @author admin
 * @Value("${spring.datasource.url}")
 *
 */
@Component
@PropertySource(value = "classpath:config.properties")
public class ConfigProperties {
	
	@Value("${config.app.name}")
	private String appName;
	
	@Value("${config.provider.user}")
	private String providerUser;
	

	public String getAppName() {
		return appName;
	}



	public void setAppName(String appName) {
		this.appName = appName;
	}

	
	


	public String getProviderUser() {
		return providerUser;
	}



	public void setProviderUser(String providerUser) {
		this.providerUser = providerUser;
	}


  
	
	
}
