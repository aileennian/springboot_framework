package com.huixiaoer.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public interface RedisSortSet  extends IRedisClient{
	public Jedis getJedis();
	
	   /**
     * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
     *
     * @param String
     *            key
     * @param double score 权重
     * @param String
     *            member 要加入的值，
     * @return 状态码 1成功，0已存在member的值
     * */
    public long zadd(String key, double score, String member) ;


    /**
     * 获取集合中元素的数量
     *
     * @param String
     *            key
     * @return 如果返回0则集合不存在
     * */
    public long zcard(String key);

    /**
     * 获取指定权重区间内集合的数量
     *
     * @param String
     *            key
     * @param double min 最小排序位置
     * @param double max 最大排序位置
     * */
    public long zcount(String key, double min, double max);

    
   

    
    /**
     * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
     *
     * @param String
     *            key
     * @param int start 开始位置(包含)
     * @param int end 结束位置(包含)
     * @return Set<String>
     * */
    public Set<String> zrange(String key, int start, int end);

 

    /**
     * 获取指定值在集合中的位置，集合排序从低到高
     *
     * @see zrevrank
     * @param String
     *            key
     * @param String
     *            member
     * @return long 位置
     * */
    public long zrank(String key, String member) ;

    /**
     * 获取指定值在集合中的位置，集合排序从高到低
     *
     * @see zrank
     * @param String key
     * @param String member
     * @return long 位置
     * */
    public long zrevrank(String key, String member);

    /**
     * 从集合中删除成员
     *
     * @param String key
     * @param String member
     * @return 返回1成功
     * */
    public long zrem(String key, String member);

   



    /**
     * 获取给定区间的元素，原始按照权重由高到低排序
     *
     * @param String  key
     * @param int start
     * @param int end
     * @return Set<String>
     * */
    public Set<String> zrevrange(String key, int start, int end) ;

    
    
    /**
     * 获取给定值在集合中的权重
     * (权重参数score，使得集合中的元素能够按score进行有序排列)
     * @param String  key
     * @param memeber
     * @return double 权重
     * */
    public double zscore(String key, String memebr);
}
