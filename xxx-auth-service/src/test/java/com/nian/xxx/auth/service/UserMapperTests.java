package com.nian.xxx.auth.service;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.nian.xxx.auth.AppProviderUser;
import com.nian.xxx.auth.entity.User;
import com.nian.xxx.auth.mapper.UserMapper;
import com.nian.xxx.auth.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppProviderUser.class)
@WebAppConfiguration
public class UserMapperTests {
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private UserMapper userMapper;

//	@Test
//	public void testInsert() throws Exception {
////		User person = new User();
////		person.setUserName("aa");
////		userService.insertUser(person);
////		Assert.assertNotNull(person.getUserId());
////		logger.debug(JSON.toJSONString(person));
//	}


//	@Test
//	public void testFindByPage() {
//		Page<User> persons = userService.findAllByPage(1, 1);
//		// 需要把Page包装成PageInfo对象才能序列化。该插件也默认实现了一个PageInfo
//		PageInfo<User> pageInfo = persons.toPageInfo();		
//		Assert.assertNotNull(persons);
//		logger.debug(pageInfo.toString());
//		logger.debug(JSON.toJSONString(pageInfo));
//	}
	
	
	@Test
	public void findByUsername() {
		User persons = userService.findByLoginName("aa");
		
		User persons1 = userService.findByUserId(45L);
	
		
		Assert.assertNotNull(persons);
		logger.debug(persons.toString());
		
		//org.apache.ibatis.type.JdbcType.
	}

//	@Test
//	public void testCacheByPage() {
////		long begin = System.currentTimeMillis();
////		List<User> persons = userMapper.findAll();
////		long ing = System.currentTimeMillis();
////		userMapper.findAll();
////		long end = System.currentTimeMillis();
////		logger.debug("第一次请求时间：" + (ing - begin) + "ms");
////		logger.debug("第二次请求时间:" + (end - ing) + "ms");
////
////		Assert.assertNotNull(persons);
////		logger.debug(persons.toString());
////		logger.debug(JSON.toJSONString(persons));
//	}


}
