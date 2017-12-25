package com.nian.auth;

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
public class AuthProperties {
	
	@Value("${config.provider.user}")
	private String providerUser;
	


	public String getProviderUser() {
		return providerUser;
	}



	public void setProviderUser(String providerUser) {
		this.providerUser = providerUser;
	}


  
	
	
}
