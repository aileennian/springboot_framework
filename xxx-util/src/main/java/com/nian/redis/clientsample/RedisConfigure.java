package com.nian.redis.clientsample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nian.redis.base.BaseRedisConfiguration;
import com.nian.redis.config.JedisProperties;

import redis.clients.jedis.JedisPool;

@Configuration
@EnableConfigurationProperties(JedisProperties.class)
//@ConditionalOnClass(RedisClient.class)//判断这个类是否在classpath中存在  
public class RedisConfigure extends BaseRedisConfiguration {

	protected Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private JedisProperties jedisProperties;

	@Bean	
	//@ConditionalOnMissingBean(JedisPool.class) // 容器中如果没有RedisClient这个类,那么自动配置这个RedisClient
	public JedisPool jedisPool() {
		return super.getJedisPool(jedisProperties);
	}

	@Bean
	//@ConditionalOnMissingBean(RedisClient.class) // 容器中如果没有RedisClient这个类,那么自动配置这个RedisClient
	public RedisClient redisClient(@Autowired @Qualifier("jedisPool") JedisPool pool) {
		RedisClient rc = new RedisClient();
		rc.setJedisPool(pool);
		return rc;
	}

}
