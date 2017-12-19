package com.huixiaoer.xxx.web.config.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huixiaoer.api.ApiParameter;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.Constant;
import com.huixiaoer.auth.UserMode;
import com.huixiaoer.auth.annotation.Login;
import com.huixiaoer.spring.SpringContextHolder;
import com.huixiaoer.utils.json.JacksonMapper;
import com.huixiaoer.xxx.web.config.ConfigProperties;

/**
 * @ 自定义拦截器， 判断此次请求是否有权限[是否已登录]
 * 
 * @author admin
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	protected ConfigProperties properties;

	@Autowired
	protected RestTemplate restTemplate;

	/**
	 * 鉴权成功，将值存入request中
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
				//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Header's Authorization的值为空!");

			}
			return true;
		}
        
//		Map<String, Object> param=new HashMap<String, Object>();  
//        param.put("authtoken", authtoken);  
//        param.put("useId")
		if (properties==null){
			properties= SpringContextHolder.getBean(ConfigProperties.class);
		}
        String url=properties.getProviderUser()+"/tokens/checkToken";
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.AUTHORIZATION, authtoken);;
        HttpEntity<String> httpEntity = ApiParameter.getHttpEntity(headers);
        
        if (restTemplate==null){
        	restTemplate = SpringContextHolder.getBean(RestTemplate.class);
        }
        ResponseEntity<String> entity = restTemplate.exchange(url,HttpMethod.GET, httpEntity,String.class);		
		ResultVo<String> vo = JacksonMapper.jsonToBean(entity.getBody(), ResultVo.class);
		if (vo.isSuccess()){
			UserMode userMode = ApiParameter.getResultData(vo.getDate(), UserMode.class);
			request.setAttribute(Constant.CURRENT_USER_ID, userMode.getUserId());
			request.setAttribute(Constant.CURRENT_USER_MODE,userMode);
			return true;
		}
		//为了防止以某种直接在写入key，将其设为null
	    request.setAttribute(Constant.CURRENT_USER_ID, null);
	    request.setAttribute(Constant.CURRENT_USER_MODE, null);
	    
		// 如果验证token失败，并且方法注明了Authorization，返回401错误
		if (method.getAnnotation(Login.class) != null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return true;
	}
}
