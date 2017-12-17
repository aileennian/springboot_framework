package com.huixiaoer.redis;

public interface RedisString  extends IRedisClient{

    
	
	public String set(String key, String value);
	public String get(String key);
	public String set(String key, String value,long seconds);
	
	 
    /**
     * 添加有过期时间的记录
     *
     * @param String  key
     * @param int seconds 过期时间，以秒为单位
     * @param String value
     * @return String 操作状态
     * */
    public String setex(String key, String value,int seconds);

   

    /**
     * 添加一条记录，仅当给定的key不存在时才插入
     * @param String key
     * @param String value
     * @return long 状态码，1插入成功且key不存在，0未插入，key存在
     * */
    public long setnx(String key, String value);

   
    /**
     * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
     * 例:String str1="123456789";<br/>
     * 对str1操作后setRange(key,4,0000)，str1="123400009";
     * @param String  key
     * @param long offset
     * @param String  value
     * @return long value的长度
     * */
    public long setRange(String key, long offset, String value) ;

    /**
     * 在指定的key中追加value
     * @param String  key
     * @param String value
     * @return long 追加后value的长度
     * **/
    public long append(String key, String value);

   

  
    /**
     * 获取并设置指定key对应的value<br/>
     * 如果key存在返回之前的value,否则返回null
     * @param String  key
     * @param String value
     * @return String 原始value或null
     * */
    public String getSet(String key, String value);

    
    
//    /**
//     * 删除key
//     * @author nianxiaoling[14110310]
//     * @date 2015年7月27日
//     * @param key
//     * @return
//     */
//    public long delete(String key); 
	
}
