package com.huixiaoer.xxx.service.register;

import java.util.HashMap;
import java.util.Map;

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
//		String serverType="";
//		for (int i=0;i<args.length;i++){
//			String v =  args[i].replaceAll("-","");
//			if (v.startsWith(ConstantProperties.KEY_SERVER_TYPE)){
//				serverType = v.substring(v.lastIndexOf("=")+1);
//			}
//		}
//		
//		if (ConstantProperties.REG_EXP.indexOf(serverType)<0){
//			throw new RuntimeException("请设置参数 --spring.profiles.activee=master或backup");
//		}
		
//		Map<String, Object> p = new HashMap<String,Object>();
//		p.put("spring.profiles.active", serverType);
		//p.put("spring.default.profiles", serverType);
		
		SpringApplication app = new SpringApplication(AppEurekaServer.class);
//		app.setDefaultProperties(p);		
//		Set<Object> sources = app.getSources();
		app.setAddCommandLineProperties(true);
		app.run(args);
		// SpringApplication.run(SampleTomcatSslApplication.class, args);

	}

	
	
}
