package com.huixiaoer.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.huixiaoer.redis.RedisCount;
import com.huixiaoer.redis.base.BaseKeyConstant;


/**
 * 我们使用缓存（Tair或者Redis）实现分布式锁，具体代码如下：
 * @author admin
 *
 */
public class ThreadLockLock {
	@Autowired
	@Qualifier("redisClient")
	private RedisCount redis;

    private ThreadLocal<Long> lockerBeanThreadLocal = new ThreadLocal<>();

    public void init(long userid) {
        lockerBeanThreadLocal.remove();
        lockerBeanThreadLocal.set(userid);
    }

    public void updateLock(String keyprefix) {
        String lockKey = keyprefix + lockerBeanThreadLocal.get();
        redis.incrBy(lockKey, BaseKeyConstant.COUNT_EXPIRE);
    }

    public void invalidLock(String keyprefix) {
        String lockKey = keyprefix + lockerBeanThreadLocal.get();
        redis.del(lockKey);
    }


}
