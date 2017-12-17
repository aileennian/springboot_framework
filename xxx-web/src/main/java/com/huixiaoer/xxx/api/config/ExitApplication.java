package com.huixiaoer.xxx.api.config;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

 


@Controller
public class ExitApplication extends ApplicationObjectSupport implements DisposableBean, ExitCodeGenerator {
	Logger logger = LogManager.getLogger(ExitApplication.class);
	
	
	@RequestMapping("/exit")
	@ResponseBody
	public String exit(){
		SpringApplication.exit(super.getApplicationContext(), this);
		return "ok";
	}

	
	@PreDestroy  
	public void destroy() throws Exception {
		logger.info("扫许：@PreDestroy，销毁了");
	}

	@Override
	public int getExitCode() {
		logger.info("// the applicaiton end");
		return 1024;

	}
	

}
