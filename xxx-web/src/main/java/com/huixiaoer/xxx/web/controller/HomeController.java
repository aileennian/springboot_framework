package com.huixiaoer.xxx.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huixiaoer.api.ApiResultVo;
import com.huixiaoer.api.ResultVo;
import com.huixiaoer.xxx.web.config.ConfigProperties;

import io.swagger.annotations.ApiOperation;

/**
 * @Value使用补充 读取配置文件的值
 * @author admin
 *
 */
@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

	@Autowired
	private ConfigProperties configProperties;
	

	@RequestMapping("/")
	@ResponseBody
	public ApiResultVo<String> home() {
    	String url=properties.getProviderUser()+"/";
//        Map<String, Object> param=new HashMap<String, Object>();  
//        param.put("userId", "测试");  
//        param.put("password", "");  
//        param.put("codeChars",validationCode);    	
    	ResultVo<String> vo = restTemplate.getForObject(url, ResultVo.class);
    	//业务处理
    	
    	ApiResultVo<String> result = new ApiResultVo(vo.getDate());   
    	return result;
	}

	
	@ApiOperation(value = "hello欢迎，返回freemarker页面实例", notes = "")
	@RequestMapping("/hello")
	public String hello(Map<String,Object> model) {
		logger.debug("调试信息");		
        model.put("appName","Welcome to "+this.configProperties.getAppName()); 
    	String url=properties.getProviderUser()+"/hello";
//        Map<String, Object> param=new HashMap<String, Object>();  
//        param.put("userId", "测试");  
//        param.put("password", "");  
//        param.put("codeChars",validationCode);    	
    	ResultVo<String> vo = restTemplate.getForObject(url, ResultVo.class);
    	//业务处理
    	ApiResultVo<String> result = new ApiResultVo("welcome="+vo.getDate());   
    	model.put("apiResultVo", result.toString());
    	return "api";
	}

	

	@RequestMapping("/toUpload")
	public String toUpload(Model model) {
		return "home/upload";
	}

	@RequestMapping("/toFormdata")
	public String formdata(Model model) {
		return "home/formdata";
	}

	@RequestMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile[] files, String name,HttpServletRequest request) {
		if(files != null){
			for(MultipartFile file : files){
				System.out.println(file.getOriginalFilename());
			}
		}
		
		/**
		 * 规范
		 */
		ApiResultVo<String> vo =  new ApiResultVo<String>();		
		return vo.ok(request.getParameter("name"));
	}

	@RequestMapping("/formdata")
	@ResponseBody
	public String formdata(@RequestParam("file") MultipartFile[] files, String name,HttpServletRequest request) {
		if(files != null){
			for(MultipartFile file : files){
				logger.info(file.getOriginalFilename());
			}
		}

		
		ApiResultVo<String> vo =  new ApiResultVo<String>();		
		return vo.ok(request.getParameter("name"));		
	}
	
	
	@RequestMapping("/error500")
	public void index() {
		int a = 1 / 0;
		System.out.println(a);
	}

	@RequestMapping("/error400/{id}")
	public Object error400(@PathVariable("id") Integer id) {
		System.out.println(id);
		return id;
	}
	
	
    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 本地服务实例信息
     * */
	@RequestMapping("/serviceinfo")
	@ResponseBody
    public String showInfo(){
    	ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        String instances = "/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId();
        logger.info(instances);
        return instances;
    }

}
