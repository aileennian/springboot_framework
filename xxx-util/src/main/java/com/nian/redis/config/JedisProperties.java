package com.nian.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * #单机
 * 多redis连接配置
 * redis.host = 120.24.92.75
 * @author admin
 *
 */
@Component
@PropertySource(value = "classpath:/redis/redis.properties")
@ConfigurationProperties(prefix = "redis")
public class JedisProperties {
	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.password=myredis}")
	private String password;
	
	
	@Value("${redis.pool.maxTotal}")
	private int maxTotal;

	@Value("${redis.pool.maxIdle}")
	private int maxIdle;

	@Value("${redis.pool.maxWaitMillis}")
	private int maxWaitMillis;

	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	
//	@Value("${redis.pool.blockWhenExhausted}")
	private boolean blockWhenExhausted;
	
	@Value("${redis.pool.testOnReturn}")
	private boolean testOnReturn;
	

	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isBlockWhenExhausted() {
		return blockWhenExhausted;
	}

	public void setBlockWhenExhausted(boolean blockWhenExhausted) {
		this.blockWhenExhausted = blockWhenExhausted;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
