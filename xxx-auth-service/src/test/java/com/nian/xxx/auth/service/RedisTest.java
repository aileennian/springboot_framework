package com.nian.xxx.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nian.redis.RedisString;
import com.nian.xxx.auth.AppProviderUser;



/**
 * Service层测试
 * @author admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppProviderUser.class)
@WebAppConfiguration
public class RedisTest {
	
	@Autowired
	@Qualifier("redisClient")
	private RedisString redis;
	@Test
	public void testFindByPage(	) {
		redis.set("aa","v");
		System.out.println("redis="+redis.get("aa"));
	}
	
}
