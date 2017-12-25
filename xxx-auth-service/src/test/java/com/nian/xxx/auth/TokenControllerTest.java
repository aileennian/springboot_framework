package com.nian.xxx.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nian.auth.Constant;
import com.nian.xxx.auth.AppProviderUser;

/**
 * springboot 使用MockMvc对controller进行测试
 * 
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppProviderUser.class)
@WebAppConfiguration
public class TokenControllerTest extends BaseTest {
	
	String tokens="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTM1MjA0ODksInN1YiI6IntcInVzZXJJZFwiOjQ1LFwibG9naW5OYW1lXCI6XCJhYVwiLFwicm9sZUlkc1wiOm51bGwsXCJ1dWlkXCI6XCJhNDI5YTk5Njc4MTE0MzYzODA2ODRjNDNmZWUxY2U0MFwifSIsImV4cCI6MTUxMzYwNjg4OX0.JUwJxH6ZQBeDQsF6LY8MVw6tvnGwAjq4cabRcZs3fAY";

	


	/**
	 * 测试springcoud接口
	 * 
	 * @throws Exception
	 */
	@Test
	public void get() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tokens/currentUser")
				 .header(Constant.AUTHORIZATION,tokens)
				 //.param("userId", "45")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
				//.andExpect(MockMvcResultMatchers.content().string(Matchers.contains("SUCCESS")));
	}
}
