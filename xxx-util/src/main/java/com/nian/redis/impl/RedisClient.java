package com.nian.redis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.nian.redis.lock.RedisLock;
import com.nian.utils.json.JacksonMapper;

import redis.clients.jedis.Jedis;

/**
 * 就一个小Demo 随便写下
 * 
 * @author bigsea
 * 
 */
public class RedisClient extends BaseRedisClient{
	

	/*
	 * 获取单个对象 常用方法 add by 原涌超
	 */
	public <T> T get(final String key, Class<T> c) {
		String result = this.get(key);
		if (StringUtils.isEmpty(result)) {
			T tt = (T) JacksonMapper.jsonToBean(result, c);
			return tt;
		} else {
			return null;
		}
	}
	

	/*
	 * 返回状态码 存储单个对象 常用方法
	 */
	public <T> String set(final String key, T c) {
		
		final String value = JacksonMapper.beanToJson(c);
		return set(key, value);
    }


	/*
	 * 存储单个对象同时设置失效时间 The command is exactly equivalent to the following group
	 * of commands: SET + EXPIRE. The operation is atomic.
	 */
	public <T> String setex(final String key, T c, final int seconds) {
		Jedis jedis = null;
		final String value = JacksonMapper.beanToJson(c);
		try {
			jedis = getJedis();
			return jedis.setex(key, seconds, value); //.setex(key, value, "NX", "PX", time);
		}finally{
			closeRedis(jedis);
		}
	}
	
    public long del(Set<String> keys) {
    	if (keys==null){
    		return 0;
    	}
    	String[] aryKeys = (String[]) keys.toArray(new String[0]);    
        return del(aryKeys);
    }
	
    public long delByPrefix(String prefix){
    	if (prefix==null){
    		return 0;
    	}
    	Set<String> keys = keys(prefix+"*");
    	return del(keys);
    }
	
    
    public long del(List<String> keys) {
    	if (keys==null){
    		return 0;
    	}    	
    	String[] ary = (String[]) keys.toArray(new String[keys.size()]);
        return del(ary);
    }
    
    
    public <T> long sadd(String key, List<T> list) {
		if (list==null || list.size()<=0) return 0;
		String[] jsons = new String[list.size()];
		int i=0;
		for (T c : list) {
			final String json = JacksonMapper.beanToJson(c);
			jsons[i++] = json;
		}
		long count = sadd(key, jsons);
		return count;
	}
    
    
    /**
     * 
     * @param key
     * @param list
     * @return
     */
	public <T> long rpushByLock(String key, List<T> list) {
		long count = 0;
		if (list == null || list.size() <= 0) {
			return 0;
		}
		String[] jsons = new String[list.size()];
		int i = 0;
		for (T c : list) {
			final String json = JacksonMapper.beanToJson(c);
			jsons[i++] = json;
		}
		
		RedisLock redisLock = new RedisLock(this);
		// 增加锁
		if (!redisLock.lock(key)) {
			return count;
		}
		try {
			del(key);
			count = rpush(key, jsons);
		} finally {
			redisLock.unlock(key);
		}
		return count;

	}
	
	
	public <T> String lset(String key, int index, T c) {
		final String json = JacksonMapper.beanToJson(c);
		
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lset(key, index, json);
		}finally{
			closeRedis(jedis);
		}	
		
	}
	
	public <T> T lindex(String key, int index, Class<T> c) {
		String result = lindex(key, index);
		if (StringUtils.isNotBlank(result)) {
			T tt = (T) JacksonMapper.jsonToBean(result, c);
			return tt;
		} else {
			return null;
		}
	}

	
	/**
	 * 获取指定范围的记录，可以做为分页使用
	 * 
	 * @param <T>
	 * @param String
	 *            key
	 * @param long start
	 * @param long end
	 * @return List
	 * */
	public <T> List<T> lrange(String key, Class<T> c, long start, long end) {
		List<String> list = lrange(key, start, end);
		if (list == null || list.size() == 0) {
			return null;
		}
		List<T> ret = new ArrayList<T>();
		for (String result : list) {
			T tt = (T) JacksonMapper.jsonToBean(result, c);
			ret.add(tt);
		}
		return ret;

	}
	
	
	/**
	 * 添加数据，加锁 ，追加数据到list头部
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月17日
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> long lpush(String key, T c) {
		final String json = JacksonMapper.beanToJson(c);

		long llen = llen(key);
		String lockKey = key + "{" + llen + "}";

		RedisLock redisLock = new RedisLock(this);		
		// 增加锁
		if (!redisLock.lock(lockKey)) {
			return 0;
		}
		long count = lpush(key, json);
		return count;
	}
	
	
	
	public <T> long rpush(String key, List<T> list) {
		if (list == null || list.size() <= 0) {
			return 0;
		}

		String[] jsons = new String[list.size()];
		int i = 0;
		for (T c : list) {
			final String json = JacksonMapper.beanToJson(c);
			jsons[i++] = json;
		}

		long llen = llen(key);
		String lockKey = key + "{" + llen + "}";

		RedisLock redisLock = new RedisLock(this);		
		// 增加锁
		if (!redisLock.lock(lockKey)) {
			return 0;
		}
		try {
			long count = rpush(key, jsons);
			return count;
		} finally {
			redisLock.unlock(lockKey);
		}
	}
	
	
	public List<String> lrange(String key) {		
		long end=llen(key);
		if (end <= 0) {
			return null;
		}
		long start = 0l;
		List<String> list = lrange(key, start, end);
		return list;
	}


 


	public <T> List<T> hmget(String key, Class<T> c, String... fields) {
		List<String> list = hmget(key, fields);
		List<T> result = new ArrayList<T>();
		if (list != null && list.size() > 0) {
			for (String s : list) {
				result.add((T) JacksonMapper.jsonToBean(s, c));
			}
		}

		return result;
	}

	public <T> List<T> hget(String key, Class<T> c,String fields) {
		String json = hget(key, fields);
		if (json == null) {
			return null;
		}
		List<T> tt = (List<T>) JacksonMapper.jsonToList(json, c);
		return tt;
	}
	
 
	public <T> T hget(String key, String fields, Class<T> c) {
		String json = this.hget(key, fields);
		T tt = null;
		if(json!=null){
			tt = (T) JacksonMapper.jsonToBean(json, c);
		}
		return tt;
	}
	
	public <T> Map<String, T> hgetAll(String key, Class<T> c) {
		Map<String, T> ret = new HashMap<String, T>();

		Map<String, String> map = hgetAll(key);
		for (String field : map.keySet()) {
			String json = map.get(field);
			T tt = (T) JacksonMapper.jsonToBean(json, c);
			ret.put(field, tt);
		}
		return ret;
	}
	
	
	public long hset(String key, String field, Object o) {
		String json = "";
		json = JacksonMapper.beanToJson(o);
		return hset(key, field, json);
	}
	
	
	public <T> String hmset(String key, Map<String, T> map) {
		Map<String, String> ret = new HashMap<String, String>();
		for (String field : map.keySet()) {
			Object c = map.get(field);
			if (c instanceof String) {
				ret.put(field, (String) c);
			} else {
				String json = JacksonMapper.beanToJson(c);
				ret.put(field, json);
			}
		}
		if (ret.size() > 0) {
			String s = hmset(key, ret);
			return s;
		} else {
			return null;
		}
	}





 

 



	 

	 

}
