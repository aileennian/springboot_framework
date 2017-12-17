package com.huixiaoer.mybatis.multi;

public enum DynamicDataSourceType {
    read("read", "从库"), write("write", "主库"), writeForce("writeForce", "强制读");
	
	/**
	 * writeForce为强制读
	 */
   
    private String type;
   
    private String name;

    DynamicDataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

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
