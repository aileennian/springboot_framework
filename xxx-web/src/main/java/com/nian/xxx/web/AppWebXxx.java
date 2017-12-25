package com.nian.xxx.web;

import java.util.EnumSet;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import com.nian.xxx.web.config.RichFreeMarkerView;

import freemarker.template.utility.XmlEscape;

/**
 * 
 * @Description @SpringBootApplication：：：
 *              由于大量项目都会在主要的配置类上添加@Configuration, @EnableAutoConfiguration, @ComponentScan三个注解。
 *              运行： mvn spring.boot:run 或 java -jar target/xxx.jar 注意，是s在项目路径下执行。
 *              开启远程调试支持： java -Xdebug -Xrunjdwp : server = y , transport =
 *              dt_socket , address = 8000 , suspend = n -jar target / myproject
 *              - 0 . 0 . 1 - SNAPSHOT . jar
 * 
 * @author nianxiaoling
 * 
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan
@ServletComponentScan
@EnableDiscoveryClient
public class AppWebXxx {
	@Autowired
	private freemarker.template.Configuration freemarkerconfiguration;

	@Autowired
	private FreeMarkerViewResolver freeMarkerViewResolver;

	/**
	 * 启动
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AppWebXxx.class);
		// app.setAddCommandLineProperties(false);
		// app.addListeners(new MyAppListener());
		// app.setBannerMode(Banner.Mode.OFF);
		app.run(args);		
		// SpringApplication.run(SampleTomcatSslApplication.class, args);

	}


	@PostConstruct
	@ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true)
	public void setSharedVariable() {
		freemarkerconfiguration.setSharedVariable("xml_escape", new XmlEscape());
	}

	@Bean
	public FilterRegistrationBean urlRewriterFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new UrlRewriteFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("confPath", "classpath:urlrewrite.xml");
		registration.addInitParameter("modRewriteConf", "false");
		registration.setName("urlRewriteFilter");
		registration.setOrder(1);

		EnumSet<DispatcherType> set = EnumSet.allOf(DispatcherType.class);
		set.clear();
		set.add(DispatcherType.FORWARD);
		set.add(DispatcherType.REQUEST);
		registration.setDispatcherTypes(set);
		return registration;
	}

	
	
	
	@Bean
	@ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true)
	public FreeMarkerViewResolver getFreemarkViewResolver() {
		if (freeMarkerViewResolver == null) {
			freeMarkerViewResolver = new FreeMarkerViewResolver();
		}
		// freeMarkerViewResolver.setCache(false);
		// freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
		// freeMarkerViewResolver.setOrder(1);
		freeMarkerViewResolver.setViewClass(RichFreeMarkerView.class);
		return freeMarkerViewResolver;
	}
	
	
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
