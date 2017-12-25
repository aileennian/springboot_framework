package com.nian.redis.base;

import org.apache.logging.log4j.Logger;

import com.nian.redis.config.JedisProperties;

import org.apache.logging.log4j.LogManager;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public abstract class BaseRedisConfiguration {
	protected Logger logger = LogManager.getLogger(this.getClass());

	
	public JedisPool getJedisPool(JedisProperties  jedisProperties) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(jedisProperties.getMaxTotal());
		config.setMaxIdle(jedisProperties.getMaxIdle());
		config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
		config.setTestOnReturn(jedisProperties.isTestOnReturn());
		//config.setBlockWhenExhausted(jedisProperties.isBlockWhenExhausted());
		config.setTestOnBorrow(jedisProperties.isTestOnBorrow());
		
		JedisPool jedisPool = new JedisPool(config, jedisProperties.getHost(), jedisProperties.getPort(), Protocol.DEFAULT_TIMEOUT, jedisProperties.getPassword(), Protocol.DEFAULT_DATABASE, null);
		logger.debug("jedisPool.getResource()",jedisPool.getResource());
		return jedisPool;
	}
	
	
//	public JedisPoolConfig getJedisPoolConfig(JedisProperties  jedisProperties) {		
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(jedisProperties.getMaxTotal());
//		config.setMaxIdle(jedisProperties.getMaxIdle());
//		config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
//		config.setTestOnReturn(jedisProperties.isTestOnReturn());
//		//config.setBlockWhenExhausted(jedisProperties.isBlockWhenExhausted());
//		config.setTestOnBorrow(jedisProperties.isTestOnBorrow());
//		
//		return config;
//	}

	
}
