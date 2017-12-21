package com.huixiaoer.xxx.spider.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huixiaoer.xxx.spider.config.ConfigProperties;

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
	public String home(Map<String,Object> model) {
		logger.debug("调试信息");		
        model.put("appName","Welcome to "+this.configProperties.getAppName()); 
        
        return "home/index";//返回的内容就是templetes下面文件的名称
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
		System.out.println(request.getParameter("name"));
		return name;
	}

	@RequestMapping("/formdata")
	@ResponseBody
	public String formdata(@RequestParam("file") MultipartFile[] files, String name,HttpServletRequest request) {
		if(files != null){
			for(MultipartFile file : files){
				logger.info(file.getOriginalFilename());
			}
		}
		logger.info(request.getParameter("name"));
		return name;
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
}
