package com.nian.redis;

import redis.clients.jedis.Jedis;

/**
 * 计数器
 * 
 * @author admin
 *
 */
public interface RedisCount extends IRedisClient{
	public Jedis getJedis();
 

	/**
     * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
     *
     * @param String
     *            key
     * @param long number 要减去的值
     * @return long 减指定值后的值
     * */
    public long decrBy(String key, long number);

	/**
     * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
     *
     * @param String
     *            key
     * @param long number 要减去的值
     * @return long 减指定值后的值
     * */
    public long incrBy(String key, long number) ;

	/**
    * 减1
    * @param key
    * @return
    */
    public long decr(String key);

	/**
     * 计数加1
     * @param key
     * @return
     */
    public long incr(String key);

}
