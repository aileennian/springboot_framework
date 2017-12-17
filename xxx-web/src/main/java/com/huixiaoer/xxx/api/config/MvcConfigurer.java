package com.huixiaoer.xxx.api.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.huixiaoer.xxx.api.config.aop.CurrentUserResolver;
import com.huixiaoer.xxx.api.config.aop.LoginInterceptor;

@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/**
	 * 配置拦截器
	 * 
	 * @author lance
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new CurrentUserResolver());
	}

	/**
	 * this is a method of the WebMvcConfigurerAdapter.class we can override the
	 * default value/achievements of spring boot and customize our own
	 * HttpMessageConverters.
	 * 
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		/*
		 * using the StringHttpMessageConverter to handle with simple String
		 * message.
		 */

		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(stringConverter);
		/*
		 * using the FastJsonHttpMessageConverter to handle these below. 1.
		 * text/html;charset=UTF-8 a page(htm/html/jsp etc.) 2.
		 * application/json;charset=utf-8 json data type response 3.
		 * text/plain;charset=UTF-8 a text or string etc. 4.
		 * application/x-www-form-urlencoded;charset=utf-8 standard encoding
		 * type. convert form data to a key-value. ...
		 */
		FastJsonHttpMessageConverter4 fastJsonConverter = new FastJsonHttpMessageConverter4();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

		fastJsonConverter.setFastJsonConfig(fastJsonConfig);

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType text_plain = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
		MediaType text_html = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
		MediaType x_www_form_urlencoded_utf8 = new MediaType(MediaType.APPLICATION_FORM_URLENCODED,Charset.forName("UTF-8"));
		supportedMediaTypes.add(text_html);
		supportedMediaTypes.add(text_plain);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(x_www_form_urlencoded_utf8);

		fastJsonConverter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(fastJsonConverter);
		super.configureMessageConverters(converters);

	}

	// /**
	// * 自定义异常处理路径
	// * @return
	// */
	// @Bean
	// public EmbeddedServletContainerCustomizer containerCustomizer() {
	// return new EmbeddedServletContainerCustomizer() {
	// @Override
	// public void customize(ConfigurableEmbeddedServletContainer
	// configurableEmbeddedServletContainer) {
	// configurableEmbeddedServletContainer.addErrorPages(new
	// ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
	// configurableEmbeddedServletContainer.addErrorPages(new
	// ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));
	// configurableEmbeddedServletContainer.addErrorPages(new
	// ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
	// configurableEmbeddedServletContainer.addErrorPages(new
	// ErrorPage(java.lang.Throwable.class, "/error/500"));
	// }
	// };
	// }
}
