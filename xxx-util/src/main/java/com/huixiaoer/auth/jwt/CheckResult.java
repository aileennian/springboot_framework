package com.huixiaoer.auth.jwt;

import com.huixiaoer.auth.UserMode;

import io.jsonwebtoken.Claims;

public class CheckResult {
	private boolean success; 
	private String errCode;
	private Claims claims;
	private UserMode subject; 
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Claims getClaims() {
		return claims;
	}
	public void setClaims(Claims claims) {
		this.claims = claims;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public UserMode getSubject() {
		return subject;
	}
	public void setSubject(UserMode subject) {
		this.subject = subject;
	}
	
	
	
}
