package com.huixiaoer.xxx.auth.config.redis;

public class RedisConstant extends com.huixiaoer.redis.base.BaseKeyConstant {
	public static final String KEY_PREFIX = "xxx:";
	public final static String MIGRATION_PREFIX = KEY_PREFIX + "ROCK";	
	public static final long COUNT_EXPIRE =  60;	
	
	public static final String HASH_LOG_CUST_WILL_CHECK	 	= KEY_PREFIX + KeyType.HASH.name() + "_LOG_CUST_CHECK_";
    public static final String LIST_FUYOU_RECOMMENDS		= KEY_PREFIX + KeyType.LIST.name()+"_FUYOU_RECOMMENDS";    
    public static final String SET_FREE_PRODUCT_CHILD_IDS 	= KEY_PREFIX + KeyType.SET.name()+"_M_FREE_PRODUCT_CHILD_IDS";     
    public static final String PAGELIST_PRODUCT 			= KEY_PREFIX + KeyType.LIST.name()+"_P_";
    
 
}
