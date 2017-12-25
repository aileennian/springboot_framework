package com.nian.xxx.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 
 * 自定义业务属性
 * @author admin
 *
 */
@Component
@PropertySource(value = "classpath:config.properties")
public class ConfigProperties {
	@Value("${config.app.name}")
	private String appName;

	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	@Override
	public String toString() {
		return "ConfigProperties [appName=" + appName + "]";
	}
}
