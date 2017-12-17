package com.huixiaoer.xxx.service.register;

/**
 * 服务器配置
 * @author admin
 *
 */
public enum EurekaProfiles {	
	MASTER("peer1", "从库"), BACKUP("peer2", "备份机");
	
	EurekaProfiles(String type, String name) {
        this.type = type;
        this.name = name;
    }
	
	private String type;
	private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
}
