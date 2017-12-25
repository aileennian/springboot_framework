package com.nian.xxx.auth.config.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nian.redis.base.BaseRedisConfiguration;
import com.nian.redis.config.JedisProperties;

import redis.clients.jedis.JedisPool;

@Configuration
@EnableConfigurationProperties(JedisProperties.class)
@ConditionalOnClass(RedisClient.class)//判断这个类是否在classpath中存在  
public class RedisConfigure extends BaseRedisConfiguration {

	protected Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private JedisProperties jedisProperties;

	@Bean
	public JedisPool jedisPool() {
		return super.getJedisPool(jedisProperties);
	}

	@Bean
	public RedisClient redisClient(@Autowired @Qualifier("jedisPool") JedisPool pool) {
		RedisClient rc = new RedisClient();
		rc.setJedisPool(pool);
		return rc;
	}

}
