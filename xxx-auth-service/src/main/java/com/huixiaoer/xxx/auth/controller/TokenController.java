package com.huixiaoer.xxx.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huixiaoer.api.ResultStatus;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.auth.Constant;
import com.huixiaoer.auth.UserMode;
import com.huixiaoer.auth.annotation.CurrentUser;
import com.huixiaoer.auth.annotation.Login;
import com.huixiaoer.auth.bo.UserInfo;
import com.huixiaoer.xxx.auth.entity.User;
import com.huixiaoer.xxx.auth.service.TokenManagerService;
import com.huixiaoer.xxx.auth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 */
@Api("Token相关操作")
@RestController
@RequestMapping("/tokens")
public class TokenController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenManagerService tokenManager;

	/**
	 * 
	 * 利用401状态码和响应头WWW-Authenticate来搞一搞，玩一下，但是这种方式很不安全···base64太容易就被破解了。
	 * 需要知道的是，在第一次认证的时候，初次响应是没有完整的响应头的；在认证过后，浏览器在会话存活之间，每一次请求都会带着请求头Authorization
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "登录", notes = "在发送请求时，请将信息保存在Header的Authorization里。登录成功，返回token串")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "validationCode", value = "验证码", required = false, dataType = "String"), })
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<String> login(@RequestBody(required = true) String param,
			// @RequestParam(required=true) String password,
			// @RequestParam(required=false) String validationCode,
			HttpServletRequest req, HttpServletResponse res) {

		// eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTM1MDQwODAsInN1YiI6IntcInVzZXJJZFwiOjQ1LFwibG9naW5OYW1lXCI6XCJhYVwiLFwicm9sZUlkc1wiOm51bGwsXCJ1dWlkXCI6XCIxOWU2MWI4N2QxZTY0ODE2YjkxYTc5MzkwODljMjJiZFwifSIsImV4cCI6MTUxMzU5MDQ4MH0.-nckR57IBQ-Gp0LJq5Wv-IrAZJ20m6ktSgLbrvEUOlE
		JsonParser jp = new JsonParser();
		JsonObject jsonObj = jp.parse(param).getAsJsonObject();

		String username = jsonObj.get("username").getAsString();
		String password = jsonObj.get("password").getAsString();

		Assert.notNull(username, "用户名称不能为空！");
		Assert.notNull(password, "密码不能为空！");
		// username = "aa";
		// password = "111";

		// HttpSession session = req.getSession();
		// //sessionId需要修订
		// String validation_code =
		// (String)session.getAttribute("validation_code");
		// if(!validationCode.equalsIgnoreCase(validation_code)){
		// return new ResponseEntity<>(new
		// String().error(ResultStatus.USER_VALIDATION_CODE),HttpStatus.UNAUTHORIZED);
		// }

		ResultVo<UserMode> vo = new ResultVo<UserMode>();// .error(ResultStatus.USER_CREATE_TOKEN)

		User user = userService.findByLoginName(username);
		if (user == null || user.getPassword() == null || password == null || // 未注册
				!user.getPassword().equals(password)) { // 密码错误

			vo.setError(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
			return new ResponseEntity<String>(vo.toString(), HttpStatus.UNAUTHORIZED);
		}

		// String encoding = Base64Encoder.encode ("test1:test1");
		String token = null;
		try {
			// 生成一个token，保存用户登录状态
			token = tokenManager.createToken(user);
			res.setHeader(Constant.AUTHORIZATION, token);
			req.authenticate(res);
			req.setAttribute(Constant.CURRENT_USER_ID, user.getUserId());
			// req.setAttribute(Constant.CURRENT_USER_MODE, token);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.trace(e);
			vo.setError(ResultStatus.USER_CREATE_TOKEN);
			return new ResponseEntity<>(vo.toString(), HttpStatus.UNAUTHORIZED);
		}
		UserMode userMode = new UserMode();
		userMode.setLoginName(username);
		userMode.setUserId(user.getUserId());
		userMode.setToken(token);
		userMode.setRoleIds(user.getRoleIds());
		vo.setOk(userMode);
		return new ResponseEntity<>(vo.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.DELETE, RequestMethod.GET })
	@Login
	@ApiOperation(value = "登出", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.AUTHORIZATION, value = "token", required = false, dataType = "string", paramType = "header")
	})
	public ResponseEntity<String> logout(HttpServletRequest req, HttpServletResponse res) {
		String userId = (String) req.getAttribute(Constant.CURRENT_USER_ID);
		if (userId!=null){
			tokenManager.deleteToken(Long.valueOf(userId));
		}
		ResultVo<String> vo = new ResultVo<String>();// .error(ResultStatus.USER_CREATE_TOKEN)
		vo.setSuccess(true);
		return new ResponseEntity<>(vo.toString(), HttpStatus.OK);
	}


	@ApiOperation(value = "得到当前用户", notes = "")
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = Constant.AUTHORIZATION, required = true, dataType = "string", paramType = "header") })
	@Login
	public String getCurrentUser(@CurrentUser User user) {
		ResultVo<UserInfo> vo = new ResultVo<UserInfo>();// .error(ResultStatus.USER_CREATE_TOKEN)
		UserInfo userinfo = new UserInfo();
		userinfo.setLoginClientIp(user.getLoginClientIp());
		userinfo.setUserName(user.getUserName());
		userinfo.setUserId(user.getUserId());
		vo.setOk(userinfo);
		return vo.toString();
	}

	@ApiOperation(value = "检测token的有效性，并返回UserMode", notes = "")
	@RequestMapping(value = "/checkToken", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = Constant.AUTHORIZATION, required = true, dataType = "string", paramType = "header") })
	@Login
	public ResultVo<UserMode> checkToken(@CurrentUser User user,HttpServletRequest request, HttpServletResponse response) {

		//System.out.println("User="+user.toString());
		ResultVo<UserMode> vo = new ResultVo<UserMode>();// .error(ResultStatus.USER_CREATE_TOKEN)
		String authtoken = request.getHeader(Constant.AUTHORIZATION);
		if (authtoken == null || authtoken.equals("")) {
			logger.error("请求头header上Authorization值为空!");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			//vo.setError(HttpServletResponse.SC_UNAUTHORIZED,  "Header's Authorization的值为空!");
			return vo;
		}
		vo = tokenManager.checkToken(authtoken);
		return vo;
	}

}
