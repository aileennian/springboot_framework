package com.huixiaoer.xxx.auth;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.huixiaoer.spring.SpringContextHolder;

/**
 * 
 * @Description @SpringBootApplication：：：
 *              由于大量项目都会在主要的配置类上添加@Configuration, @EnableAutoConfiguration, @ComponentScan三个注解。
 *              运行： mvn spring.boot:run 或 java -jar target/xxx.jar 注意，是在项目路径下执行。
 *              开启远程调试支持： java -Xdebug -Xrunjdwp : server = y , transport =
 *              dt_socket , address = 8000 , suspend = n -jar target / myproject
 *              - 0 . 0 . 1 - SNAPSHOT . jar
 *              
 *  添加@EnableDiscoveryClient注解后，项目就具有了服务注册的功能。启动工程后，就可以在注册中心的页面看到SPRING-CLOUD-PRODUCER服务。
 * 
 * @author nianxiaoling
 * //@ServletComponentScan
 * @ComponentScan(basePackages = "com.huixiaoer")
 */
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan
@EnableDiscoveryClient
public class AppProviderUser {
		
	
	/**
	 * 启动
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppProviderUser.class);
		// app.setAddCommandLineProperties(false);
		// app.addListeners(new MyAppListener());
		 System.setProperty("spring.devtools.restart.enabled", "false");//不重新启动
		 app.setBannerMode(Banner.Mode.OFF);
		 ApplicationContext applicationContext = app.run(args);		
		 SpringContextHolder.setContext(applicationContext);		
	}
 
}
