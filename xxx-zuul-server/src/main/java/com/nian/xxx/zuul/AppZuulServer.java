package com.nian.xxx.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 *  执行入参:
 *  AppEurekaServer --master.hostname=   --backup.hostname=   --server.type=
 * @author nianxiaoling 服务端
 * 
 */
@SpringBootApplication
@EnableZuulProxy
public class AppZuulServer {	
	/**
	 * --master.type=\"master\"
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(AppZuulServer.class);
		app.setAddCommandLineProperties(true);
		app.run(args);
	}

/**
 * 这意味着serviceId“myusers-v1”将映射到路由“/ v1 / myusers / ”。
 * 接受任何正则表达式，但所有命名组必须同时存在于servicePattern和routePattern中。 如果servicePattern与serviceId不匹配，则使用缺省行为。 
 *  在上面的示例中，serviceId“myusers”将映射到路由“/ myusers / ”（未检测到任何版本）。此功能默认情况下处于禁用状态，仅适用于发现的服务。
 *  一、可以使用regexmapper在serviceId和路由之间提供约定。 它使用正则表达式命名的组从serviceId提取变量并将它们注入到路由模式中。
 *  二、regexmapper:用于routepattern转换为servicePattern
 *  三、routepattern:路由正则表达式
 *  四、servicePattern:service正则表达式
 * @return
 */	
//	@Bean
//	public PatternServiceRouteMapper serviceRouteMapper() {
//	    return new PatternServiceRouteMapper(
//	        "(?<name>^.+)-(?<version>v.+$)",
//	        "${version}/${name}");
//	}
//	
}
