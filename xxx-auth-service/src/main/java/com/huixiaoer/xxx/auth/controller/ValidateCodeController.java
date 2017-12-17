package com.huixiaoer.xxx.auth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huixiaoer.utils.validatecode.ValidateCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("获取验证码")
public class ValidateCodeController {
	// 图形验证码的字符集，系统将随机从这个字符串中选择一些字符作为验证码
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getValidCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ApiOperation(value="获得图形验证码", notes="")
	public void getValidCode(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession();

		ValidateCode vCode = new ValidateCode(120, 40, 5, 100);
		/**
		 * sessionId需要修改
		 */
		session.setAttribute("code", vCode.getCode());
		vCode.write(response.getOutputStream());
	}

}
