package com.huixiaoer.xxx.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



/**
 * springfox.documentation.swagger.v2.path: /api-docs_
 * http://blog.csdn.net/gebitan505/article/details/51614805
 * https://www.cnblogs.com/xiekeli/p/5607107.html
 * https://github.com/ScienJus/spring-authorization-manager
 * @author admin
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	   public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.huixiaoer.xxx.auth";
	   public static final String VERSION = "1.0.0";


	    @Bean
	    public Docket createRestApi(){
	        return new Docket(DocumentationType.SWAGGER_2)
	        		.apiInfo(apiInfo())
	        		//.groupName("huixiaoer")  
	        		.genericModelSubstitutes(DeferredResult.class)  
	        		.useDefaultResponseMessages(false)  
	        		.forCodeGeneration(true)  
	                .select()
	                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
	                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))  
	                .paths(PathSelectors.any()) 
	                .build()
	                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
	                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
	                ;
	                
	    }
	    
/*	    @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
	                .build();
	    }*/
	    
	    /**
	     * 构建文档
	     * @return
	     */
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Auth API")
	                .description("This is to show api description")
	                .license("Apache 2.0")
	                .contact("念小玲")
	                //.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
	                //.termsOfServiceUrl("http://www.nianxl.com")
	                .version(VERSION)
	                .build();
	    }
	    
	    

}
