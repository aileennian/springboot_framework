package com.huixiaoer.xxx.web.config.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.huixiaoer.api.ApiParameter;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.Constant;
import com.huixiaoer.auth.annotation.CurrentUser;
import com.huixiaoer.auth.bo.UserInfo;
import com.huixiaoer.spring.SpringContextHolder;
import com.huixiaoer.utils.json.JacksonMapper;
import com.huixiaoer.xxx.web.config.ConfigProperties;



/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * 
 * @date 2015/7/31.
 */
@Component
@ConditionalOnClass(CurrentUserResolver.class)//判断这个类是否在classpath中存在  
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	protected ConfigProperties properties;

	@Autowired
	protected RestTemplate restTemplate;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果参数类型是User并且有CurrentUser注解则支持
		if (parameter.getParameterType().isAssignableFrom(UserInfo.class)
				&& parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 取出鉴权时存入的登录用户Id
		
			String authtoken = webRequest.getHeader(Constant.AUTHORIZATION);
			if (properties==null){
				properties= SpringContextHolder.getBean(ConfigProperties.class);
			}
			String url=properties.getProviderUser()+"/tokens/currentUser";

	        HttpHeaders headers = new HttpHeaders();
	        headers.set(Constant.AUTHORIZATION, authtoken);;
	        HttpEntity<String> httpEntity = ApiParameter.getHttpEntity(null,headers);
	        if (restTemplate==null){
	        	restTemplate = SpringContextHolder.getBean(RestTemplate.class);
	        }
	        ResponseEntity<String> entity = restTemplate.exchange(url,HttpMethod.GET, httpEntity,String.class);		
			ResultVo<String> vo = JacksonMapper.jsonToBean(entity.getBody(), ResultVo.class);
			if (vo.isSuccess()){
				UserInfo userInfo = ApiParameter.getResultData(vo.getDate(), UserInfo.class);
				return userInfo;
			}
		
		throw new MissingServletRequestPartException(Constant.CURRENT_USER_ID);
	}	
}
