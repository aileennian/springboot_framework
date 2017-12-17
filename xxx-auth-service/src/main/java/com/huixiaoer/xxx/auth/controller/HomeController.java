package com.huixiaoer.xxx.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixiaoer.api.ResultVo;

import io.swagger.annotations.Api;


@Api("Welcome")
@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	 //Eureka客户端
  /*  @Autowired
    private EurekaClient eurekaClient;*/
    
    //服务发现客户端
    @Autowired
    private DiscoveryClient discoveryClient;
    
	/**
	 * 返回json串
	 * @returns
	 */
	@RequestMapping("/")
	@ResponseBody
	public ResultVo<String> index(){
		String v = "helloworld";
		ResultVo result = new ResultVo<String>(v);
		return result;
	}
	
	/**
	 * 返回对像，自动转换
	 * @return
	 */
	@RequestMapping("/hello")
	public ResultVo<String> hello(){
		String v = "登录验证";
		return new ResultVo<String>(v);
	}
	
 
    /**
     * 本地服务实例信息
     * */
    @GetMapping("/instance-info")
    public ServiceInstance showInfo(){
       ServiceInstance service = discoveryClient.getLocalServiceInstance();
       //ServiceInstance localServiceInstance = this.discoveryClient.getInstanceRemoteStatus();
        return service;
    }
}
