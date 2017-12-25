package com.nian.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

public interface RedisList  extends IRedisClient{
	public Jedis getJedis();
	
	/**
	 * List长度
	 * 
	 * @param String
	 *            key
	 * @return 长度
	 * */
	public long llen(String key);
	/**
	 * 增加集合
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月17日
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> long rpushByLock(String key, List<T> list);

	/**
	 * 重写指定位置的值
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月17日
	 * @param key
	 * @param index
	 * @param c
	 * @return
	 */
	public <T> String lset(String key, int index, T c);

	

 
	/**
	 * 添加数据，加锁
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月17日
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> long rpush(String key, List<T> list);



	/**
	 * 获取指定范围的记录，可以做为分页使用
	 * 
	 * @param String
	 *            key
	 * @param long start
	 * @param long end
	 * @return List
	 * */
	public List<String> lrange(String key, long start, long end);

	/**
	 * 返回所有的结果集
	 * 
	 * @param String
	 *            key
	 * @param long start
	 * @param long end
	 * @return List
	 * */
	public List<String> lrange(String key);

  
}
