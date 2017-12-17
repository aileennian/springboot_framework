package com.huixiaoer.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

public interface IRedisClient {
	public Jedis getJedis();

	/**
	 * 设置key的过期时间，以秒为单位
	 *
	 * @param String
	 *            key
	 * @param 时间
	 *            ,已秒为单位
	 * @return 影响的记录数
	 */
	public long expire(String key, long seconds);
	
 
 
//    {
//    	return this.redisService.expire(key);
//    }

    /**
     * 查询key的过期时间
     *
     * @param String
     *            key
     * @return 以秒为单位的时间表示
     * */
    public long getExpire(String key);
//    {
//        return this.redisService.ttl(key);
//    }

    /**
     * 取消对key过期时间的设置
     *
     * @param key
     * @return 影响的记录数
     * */
    public long persist(String key) ;
//    {
//      return this.redisService.persist(key);
//    }
    
    
    /**
     * 删除keys对应的记录,可以是多个key
     *
     * @param String
     *            ... keys
     * @return 删除的记录数
     * */
    public long del(String... keys);
//    {
//        return redisService.del(keys);
//    }
    
    /**
     * 删除key
     * @author nianxiaoling[14110310]
     * @date 2015年7月27日
     * @param key
     * @return
     */
    public long del(String key);
//    {
//        return redisService.del(key);
//    }
    
    /**
     * 删除一个集合的key
     * @author nianxiaoling[14110310]
     * @date 2015年3月22日
     * @param keys
     * @return
     */
//    public long del(Set<String> keys)
//    ;{
//    	if (keys==null){
//    		return new long(0);
//    	}
//    	String[] aryKeys = (String[]) keys.toArray(new String[0]);    
//        return redisService.del(aryKeys);
//    }
    
    /**
     * 删除某个前缀的key
     * @author nianxiaoling[14110310]
     * @date 2015年3月23日
     * @param prefix
     * @return
     */
    public long delByPrefix(String prefix);//{
//    	if (prefix==null){
//    		return new long(0);
//    	}
//    	Set<String> keys = redisService.keys(prefix+"*");
//    	return del(keys);
//    }
    
 //   public long del(List<String> keys);
    
    

    /**
     * 判断key是否存在
     *
     * @param String
     *            key
     * @return boolean
     * */
    public boolean exists(String key) ;

    
    /**
     * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
     *
     * @param String
     *            key
     * @return List<String> 集合的全部记录
     * **/
    public List<String> sort(String key);

    
    /**
     * 对List,Set,SortSet进行排序或limit
	  * public SortingParams by(java.lang.String pattern)
		* Sort by weight in keys.
		* Takes a pattern that is used in order to generate the key names of the weights used for sorting. 
		* Weight key names are obtained substituting the first occurrence of 
		* with the actual value of the elements on the list.
		* The pattern for a normal key/value pair is "keyname*" and for a value in a hash "keyname*->fieldname".
     * @param String
     *            key
     * @param SortingParams
     *            parame 定义排序类型或limit的起止位置.
     * @return List<String> 全部或部分记录
     * **/
    public List<String> sort(String key, SortingParams parame);

    /**
     * 返回指定key存储的类型
     *
     * @param String
     *            key
     * @return String string|list|set|zset|hash
     * **/
    public String type(String key);
    
//    /**
//     * 查找所有匹配给定的模式的键
//     *
//     * @param String
//     *  key的表达式,*表示多个，？表示一个
//     * */
//    public Set<String> keys(String pattern) ;
}
