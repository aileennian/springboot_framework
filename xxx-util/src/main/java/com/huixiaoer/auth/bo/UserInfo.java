package com.huixiaoer.auth.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author admin
 *
 */
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
   // @GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
    private String userName;
    private Date lastLoginTime;
    private String loginClientIp;
 
    
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getLoginClientIp() {
		return loginClientIp;
	}
	public void setLoginClientIp(String loginClientIp) {
		this.loginClientIp = loginClientIp;
	}
	 
	
}
