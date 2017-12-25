package com.nian.xxx.auth.config.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nian.api.ResultVo;
import com.nian.auth.Constant;
import com.nian.auth.UserMode;
import com.nian.auth.annotation.Login;
import com.nian.spring.SpringContextHolder;
import com.nian.xxx.auth.service.TokenManagerService;

/**
 * @ 自定义拦截器， 判断此次请求是否有权限[是否已登录]
 * 
 * @author admin
 *
 */
@Component
@ConditionalOnClass(LoginAuthInterceptor.class)//判断这个类是否在classpath中存在  
public class LoginAuthInterceptor extends HandlerInterceptorAdapter {
	protected static final Logger logger = LogManager.getLogger(LoginAuthInterceptor.class);

	@Autowired
	private TokenManagerService tokenManagerService;

	/**
	 * 鉴权成功，将值存入request中
	 * 
	 * @throws Exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		// 从header中得到token,从头信息中得到授权信息
		String authtoken = request.getHeader(Constant.AUTHORIZATION);
		if (authtoken == null || authtoken.equals("")) {
			if (method.getAnnotation(Login.class) != null) {
				logger.error("请求头header上Authorization值为空!");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Header's Authorization的值为空!");
				// return false;
			}
			return true;
		}

		/**
		 * 解析token对象，通过客户端判断，再通过redis判断一次
		 */
		if (this.tokenManagerService==null){
			tokenManagerService = SpringContextHolder.getBean(TokenManagerService.class);
		}
		
		ResultVo<UserMode> resultVo = tokenManagerService.checkToken(authtoken);
		if (resultVo.getDate() != null) {
			UserMode userMode = resultVo.getDate();
			// 如果token验证成功，将token对应的用户id存在requesttokenManagerService中，便于之后注入
			request.setAttribute(Constant.CURRENT_USER_ID, userMode.getUserId());
			request.setAttribute(Constant.CURRENT_USER_MODE, userMode);
			return true;
		}
		// 为了防止以某种直接在写入key，将其设为null
		request.setAttribute(Constant.CURRENT_USER_ID, null);
		request.setAttribute(Constant.CURRENT_USER_MODE, null);
		
		
		
		// 如果验证token失败，并且方法注明了Authorization，返回401错误
		if (method.getAnnotation(Login.class) != null) {
			logger.error("resultVo.getMessage()="+resultVo.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resultVo.getMessage());
			return false;
		}
		return true;
	}

	
	
}
