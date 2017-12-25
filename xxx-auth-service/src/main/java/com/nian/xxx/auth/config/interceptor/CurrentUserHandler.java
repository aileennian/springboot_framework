package com.nian.xxx.auth.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.nian.auth.Constant;
import com.nian.auth.annotation.CurrentUser;
import com.nian.spring.SpringContextHolder;
import com.nian.xxx.auth.entity.User;
import com.nian.xxx.auth.service.TokenManagerService;
import com.nian.xxx.auth.service.UserService;



/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * 
 * @date 2015/7/31.
 */
@Component
@ConditionalOnClass(CurrentUserHandler.class)//判断这个类是否在classpath中存在  
public class CurrentUserHandler implements HandlerMethodArgumentResolver {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果参数类型是User并且有CurrentUser注解则支持
		if (parameter.getParameterType().isAssignableFrom(User.class)
				&& parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 取出鉴权时存入的登录用户Id,该值由AuthInterceptor时置入
		Long currentUserId = (Long) webRequest.getAttribute(Constant.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
		if (currentUserId != null) {
			// 从数据库中查询并返回
			if (userService==null){
				userService= SpringContextHolder.getBean(UserService.class);
			}
			User user = userService.findByUserId(currentUserId);
			if (user!=null) user.setPassword("");
			return user;
		}
//		else{
//			String authtoken = webRequest.getHeader(Constant.AUTHORIZATION);
//			if (authtoken != null) {			
//				UserMode mode = TokenMgr.checkToken(authtoken).getDate();		
//				// 从数据库中查询并返回
//				User user = userService.findOne(mode.getUserId());
//				user.setPassword("");
//				return user;				
//			}
//		}
		throw new MissingServletRequestPartException(Constant.CURRENT_USER_ID+","+Constant.AUTHORIZATION);
	}	
}
