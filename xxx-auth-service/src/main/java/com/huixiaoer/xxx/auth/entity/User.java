package com.huixiaoer.xxx.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huixiaoer.mybatis.base.BaseEntity;


@Entity
@Table(name="blog_user")
public class User extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    @Id
    @Column(name = "USER_ID")
	private Long userId;
    
    @Column(name = "USER_NAME")
    private String userName;
    private String password;	
    private Date lastLoginTime;
    private String loginClientIp;
    
    @Transient
    private String roleIds;
    
    
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLoginClientIp() {
		return loginClientIp;
	}
	public void setLoginClientIp(String loginClientIp) {
		this.loginClientIp = loginClientIp;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
