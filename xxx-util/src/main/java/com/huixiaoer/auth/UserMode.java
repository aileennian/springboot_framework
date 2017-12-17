package com.huixiaoer.auth;

import java.io.Serializable;

public class UserMode  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7101934416104721555L;
	
	private Long userId;
	private String loginName;
	private String roleIds;
	private String token;
	

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
 

	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
