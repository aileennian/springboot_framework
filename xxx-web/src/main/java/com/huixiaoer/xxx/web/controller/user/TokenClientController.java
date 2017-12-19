package com.huixiaoer.xxx.web.controller.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixiaoer.api.ApiResultVo;
import com.huixiaoer.api.ApiParameter;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.Constant;
import com.huixiaoer.auth.UserMode;
import com.huixiaoer.auth.annotation.CurrentUser;
import com.huixiaoer.auth.annotation.Login;
import com.huixiaoer.auth.bo.UserInfo;
import com.huixiaoer.utils.json.JacksonMapper;
import com.huixiaoer.xxx.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("Token相关操作")
@Controller
@RequestMapping("/token")
public class TokenClientController extends BaseController {
	/**
	 * 
	 * 利用401状态码和响应头WWW-Authenticate来搞一搞，玩一下，但是这种方式很不安全···base64太容易就被破解了。
	 * 需要知道的是，在第一次认证的时候，初次响应是没有完整的响应头的；在认证过后，浏览器在会话存活之间，每一次请求都会带着请求头Authorization
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@RequestMapping(value="/login",method = {RequestMethod.POST,RequestMethod.GET})
	@ApiOperation(value="登录", notes="在发送请求时，请将信息保存在Header的Authorization里。登录成功，返回token串")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
        @ApiImplicitParam(name = "password", value = "密s码", required = true, dataType = "String"),
        @ApiImplicitParam(name = "codeChars", value = "验证码", required = false, dataType = "String"),
	})
	public ResponseEntity<ApiResultVo<UserMode>> login(
			@RequestParam(required=true) String username, 
			@RequestParam(required=true) String password,
			@RequestParam(required=false) String validationCode,
			HttpServletRequest req,
			HttpServletResponse res
			//HttpServletResponse response
			) throws IOException, ServletException, JSONException {
	
		// MultiValueMap<String,String> param = new LinkedMultiValueMap<String, String>();//参数放入一个map中，restTemplate不能用hashMap
		String url=properties.getProviderUser()+"/tokens/login";
		Map<String,Object> param=new HashMap<String,Object>();  
        param.put("username", username);  
        param.put("password", password); 
        HttpEntity<String> requestEntity = ApiParameter.getHttpEntity(param);
           
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity, String.class);
		String json = responseEntity.getBody();
		
		ApiResultVo<UserMode> apiVo = new ApiResultVo();
		
		
		ResultVo<?> vo = JacksonMapper.jsonToBean(json,ResultVo.class);
		if (vo.isSuccess()){
			UserMode userMode = ApiParameter.getResultData(vo.getDate(),UserMode.class);		
			
			res.setHeader(Constant.AUTHORIZATION, userMode.getToken());
			req.authenticate(res);
			req.setAttribute(Constant.CURRENT_USER_ID, userMode.getUserId());
			req.setAttribute(Constant.CURRENT_USER_MODE, userMode);
			
			apiVo.setOk(userMode);
			
			return new ResponseEntity<>(apiVo,HttpStatus.OK);
		}else{
			apiVo.setError(HttpStatus.UNAUTHORIZED.value(),"");
		return new ResponseEntity<>(apiVo,HttpStatus.UNAUTHORIZED);
		}		
	}

	@ApiOperation(value = "登出", notes = "请求token保存在Header的Authorization里")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.AUTHORIZATION, value = "token", required = false, dataType = "string", paramType = "header"), })
	@Login
	@RequestMapping(value = "/logout", method = { RequestMethod.DELETE})
	public ApiResultVo<String> logout(ModelMap model, HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		String authtoken = request.getHeader(Constant.AUTHORIZATION);
		if (authtoken == null || authtoken.equals("")) {
			logger.error("请求头header上Authorization值为空!");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Header头"+Constant.AUTHORIZATION+"信息值为空!");
		}
	
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.AUTHORIZATION, authtoken);
        HttpEntity<String> requestEntity = ApiParameter.getHttpEntity(headers);
           
        String url = properties.getProviderUser() + "/tokens/logout";
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity, String.class);
		
		ResultVo<?> vo = JacksonMapper.jsonToBean(responseEntity.getBody(),ResultVo.class);
		
		/**
		 * 返回值处理
		 */
		ApiResultVo<String> apiVo = new ApiResultVo<String>();
		if (vo.isSuccess()){
			apiVo.setOk("");
		}else{
			apiVo.setErrcode(1);
			apiVo.setSuccess(false);
		}
		return apiVo;
	}
	
	
	@ApiOperation(value = "校验未返回token", notes = "请求token保存在Header的Authorization里")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.AUTHORIZATION, value = "token", required = false, dataType = "string", paramType = "header"), })
	@Login
	@RequestMapping(value = "/getToken", method = {RequestMethod.GET })
	public ApiResultVo<UserMode> getToken(@CurrentUser UserInfo user, ModelMap model, HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("User="+user);
		ApiResultVo<UserMode> apiVo = new ApiResultVo<UserMode>();
		
		UserMode userMode = (UserMode) request.getAttribute(Constant.CURRENT_USER_MODE);
		if (userMode == null) {
			logger.error("请求头header上Authorization值为空!");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return apiVo;
		}
		apiVo.setOk(userMode);
		return apiVo;
	}

	

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@RequestMapping(value = "/userInstance")
	@ResponseBody
	public ServiceInstance userInstance() {
		String providerUserUrls = "provider-user";
		ServiceInstance serviceInstance = this.loadBalancerClient.choose("service-provider-user");
		// loadBalancerClient.reconstructURI(instance, original);
		ServiceInstance instance = loadBalancerClient.choose(providerUserUrls);
		return instance;
	}

}
