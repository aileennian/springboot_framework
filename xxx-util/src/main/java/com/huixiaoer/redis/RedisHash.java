package com.huixiaoer.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public interface RedisHash  extends IRedisClient{
	public Jedis getJedis();
	
	/**
	 * 从hash中删除指定的存储
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid 存储的名字
	 * @return 状态码，1成功，0失败
	 * */
	public long hdel(String key, String field);

	/**
	 * 测试hash中指定的存储是否存在
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            fieid 存储的名字
	 * @return 1存在，0不存在
	 * */
	public boolean hexists(String key, String field);

	/**
	 * 以Map的形式返回hash中的存储和值
	 * 
	 * @param String
	 *            key
	 * @return Map<Strinig,String>
	 * */
	public Map<String, String> hgetAll(String key);

	/**
	 * 获取hash中value的集合
	 *
	 * @param String
	 *            key
	 * @return List<String>
	 * */
	public List<String> hvals(String key);

	/**
	 * 获取hash中存储的个数，类似Map中size方法
	 *
	 * @param String
	 *            key
	 * @return long 存储的个数
	 * */
	public long hlen(String key);

	/**
	 * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
	 *
	 * @param String
	 *            key
	 * @param String
	 *            ... fieids 存储位置
	 * @return List<String>
	 * */
	public List<String> hmget(String key, String... fields) ;

	/**
	 * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
	 * 
	 * @param <T>
	 *
	 * @param String
	 *            key
	 * @param String
	 *            ... fieids 存储位置
	 * @param Class
	 *            <T> Class<T> c
	 * @return List<T>
	 * */
	public <T> List<T> hmget(String key, Class<T> c, String... fields);

	/**
	 * 得到单个对象
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月22日
	 * @param key
	 * @param t
	 * @param fields
	 * @return
	 */
	public <T> T hget(String key, String fields, Class<T> c);

	public String hget(String key, String fields);

	public <T> List<T> hget(String key, Class<T> c,String fields) ;

	/**
	 * 以Map的形式返回hash中的存储和值
	 * 
	 * @param String
	 *            key
	 * @return Map<Strinig,String>
	 * */
	public <T> Map<String, T> hgetAll(String key, Class<T> c);

	
	/**
	 * hash自增长度设置 Increment the number stored at field in the hash
	 * 
	 * @param key
	 * @param field
	 * @param value
	 *            (步长)
	 * @return
	 */
	public long hincrBy(String key, String field, long value);

	/**
	 * 添加对应关系，如果对应关系已存在，则覆盖
	 *
	 * @param Strin
	 *            key
	 * @param Map
	 *            <String,String> 对应关系
	 * @return 状态，成功返回OK
	 * */
	public long hset(String key, String field, Object o);
	/**
	 * 一次保存多个数值
	 * 
	 * @author nianxiaoling[14110310]
	 * @param <T>
	 * @date 2015年3月22日
	 * @param key
	 * @param map
	 * @return
	 */
	public <T> String hmset(String key, Map<String, T> map);
}
