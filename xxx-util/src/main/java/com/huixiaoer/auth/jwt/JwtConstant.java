package com.huixiaoer.auth.jwt;

public class JwtConstant {
	public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "huixiaoer.com";
    public static final int JWT_TTL = 60*60*1000;  //millisecond
    
    //刷新时间
    public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
    //token保存时间
    public static final int JWT_REFRESH_TTL = 24*60*60*1000;  //millisecond
    //redis服务端保存的时间
    public static final int REDIS_REFRESH_TTL = 55*60*1000;  //millisecond
    
    
    
}
