package com.huixiaoer.redis.lock;

import java.util.Random;

import com.huixiaoer.redis.impl.RedisClient;



public class RedisLock {
	/** 加锁标志 */
	protected static final String LOCKED = "TRUE";
	/** 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒 */
	protected static final long MILLI_NANO_CONVERSION = 1000 * 1000L;
	/** 默认超时时间（毫秒） */
	protected static final long DEFAULT_TIME_OUT = 500;
	protected final Random RANDOM = new Random();
	/** 锁的超时时间（秒），过期删除 */
	protected static final int EXPIRE = 30;
	// 锁状态标志
	protected boolean locked = false;
	
	private RedisClient redisClient;
	public RedisLock(RedisClient redisClient){
		this.redisClient = redisClient;
	}

	/**
	 * This creates a RedisLock
	 * 
	 * @param key
	 *            key
	 * @param shardedJedisPool
	 *            数据源
	 */

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout, String key) {

		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (redisClient.setnx(getLockKey(key), LOCKED) == 1) {
					redisClient.expire(getLockKey(key), EXPIRE);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间
	 * @param expire
	 *            锁的超时时间（秒），过期删除
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout, int expire, String key) {
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (redisClient.setnx(getLockKey(key), LOCKED) == 1L) {
					redisClient.expire(getLockKey(key), expire);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @return 成功或失败标志
	 */
	public boolean lock(String key) {
		return lock(DEFAULT_TIME_OUT, key);
	}

	/**
	 * 解锁 无论是否加锁成功，都需要调用unlock 应该以： lock(); try { doSomething(); } finally {
	 * unlock()； } 的方式调用
	 */
	public void unlock(String key) {
		if (this.locked) {
			redisClient.del(getLockKey(key));
		}
	}

	private String getLockKey(String key) {
		return key + "_lock";
	}
}
