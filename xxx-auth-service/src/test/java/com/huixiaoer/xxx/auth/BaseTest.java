package com.huixiaoer.xxx.auth;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.huixiaoer.spring.SpringContextHolder;

public class BaseTest {
	protected MockMvc mvc;
	@Autowired
	protected WebApplicationContext wac;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();// 建议使用这种
		SpringContextHolder.setContext(wac);
	}
   
}
