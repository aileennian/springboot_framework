package com.huixiaoer.xxx.auth.entity;

import java.io.Serializable;
import java.util.Date;

import com.huixiaoer.mybatis.base.BaseEntity;

public class Message extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2128346393707857300L;

	private Integer id;
	private String nickName;
	private String ip;
	private Date insertTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
