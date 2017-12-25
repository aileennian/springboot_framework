package com.nian.xxx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nian.auth.Constant;
import com.nian.spring.SpringContextHolder;
import com.nian.xxx.web.AppWebXxx;

/**
 * springboot 使用MockMvc对controller进行测试
 * 
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppWebXxx.class)
@WebAppConfiguration
public class TokenClientControllerTest extends BaseTest {

	String tokens="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTM1MjA0ODksInN1YiI6IntcInVzZXJJZFwiOjQ1LFwibG9naW5OYW1lXCI6XCJhYVwiLFwicm9sZUlkc1wiOm51bGwsXCJ1dWlkXCI6XCJhNDI5YTk5Njc4MTE0MzYzODA2ODRjNDNmZWUxY2U0MFwifSIsImV4cCI6MTUxMzYwNjg4OX0.JUwJxH6ZQBeDQsF6LY8MVw6tvnGwAjq4cabRcZs3fAY";
	/**
	 * 测试springcoud接口
	 * 
	 * @throws Exception
	 */
//	@Test
//	@Before
//	public void getLogin() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.post("/token/login")
//				.param("username", "aa")
//				.param("password", "111")
//				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
//				
//		System.out.println(MockMvcResultMatchers.content().toString());
//				//.andExpect(MockMvcResultMatchers.content().string(Matchers.contains("SUCCESS")));
//	}
//	
//	
	/**
	 * 测试springcoud接口
	 * 
	 * @throws Exception
	 */
	@Test
	public void get() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/token/getToken")
				 .header(Constant.AUTHORIZATION,tokens)
				 .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
				//.andExpect(MockMvcResultMatchers.content().string(Matchers.contains("SUCCESS")));
	}
}
