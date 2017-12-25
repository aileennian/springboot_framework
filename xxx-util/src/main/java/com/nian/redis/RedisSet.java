package com.nian.redis;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public interface RedisSet  extends IRedisClient{
	public Jedis getJedis();
	
	   /**
     * 向Set添加一条记录，如果member已存在返回0,否则返回1
     *
     * @param String
     *            key
     * @param String
     *            member
     * @return 操作码,0或1
     * */
    public long sadd(String key, String member) ;
    
    /**
     * 向Set添加多条记录
     *
     * @param String
     *            key
     * @param String
     *            member
     * @return 操作码,0或1
     * */
    public long sadd(String key, String... member);

    /**
     * 获取给定key中元素个数
     *
     * @param String
     *            key
     * @return 元素个数
     * */
    public long scard(String key) ;

    
 

    /**
     * 确定一个给定的值是否存在
     *
     * @param String
     *            key
     * @param String
     *            member 要判断的值
     * @return 存在返回1，不存在返回0
     * **/
    public boolean sismember(String key, String member);
    /**
     * 返回集合中的所有成员
     *
     * @param String
     *            key
     * @return 成员集合
     * */
    public Set<String> smembers(String key) ;

    
    /**
     * 不重复保存数据
     * @author nianxiaoling[14110310]
     * @date 2015年7月13日
     * @param key
     * @param list
     * @return
     */
	public <T> long sadd(String key, List<T> list);
	
	
	 	
    
    /**
     * 随机返回并删除名称为key的set中一个元素
     *
     * @param String
     *            key
     * @return 被删除的成员
     * */
    public String spop(String key) ;

    /**
     * 从集合中删除指定成员
     *
     * @param String
     *            key
     * @param String
     *            member 要删除的成员
     * @return 状态码，成功返回1，成员不存在返回0
     * */
    public long srem(String key, String member);

   /**
    * 其他方法另外添加 
    */
}
