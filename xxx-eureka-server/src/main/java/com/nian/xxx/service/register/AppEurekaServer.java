package com.nian.xxx.service.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 *  执行入参：
 *  AppEurekaServer --master.hostname=   --backup.hostname=   --server.type=
 * 
 * @author nianxiaoling 服务端
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEurekaServer {
	

	
	/**
	 * --master.type=\"master\"
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {	
		SpringApplication app = new SpringApplication(AppEurekaServer.class);
//		app.setDefaultProperties(p);		
//		Set<Object> sources = app.getSources();
		app.setAddCommandLineProperties(true);
		app.run(args);
	}

	
	
}
