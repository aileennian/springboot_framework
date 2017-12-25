package com.nian.api;

public enum ResultStatus {
	   	SUCCESS(100, "成功"),
	    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
	    USER_NOT_FOUND(-1002, "用户不存在"),
	    USER_NOT_LOGIN(-1003, "用户未登录"),
		USER_CREATE_TOKEN(-1004, "创建TOKEN失败"),
		USER_VALIDATION_CODE(-1005, "验证码错误"),
	    JWT_LOGIN_EXPIRE(-1006,"登录超时"),
	    JWT_AUTH_FAIL(-1007,"权限不成功！"),
	    JWT_FAIL(-1008,"验证失败！"),
	    TOKEN_REDIS_EXPIRE(-1009,"登录超时!")
		;

	    /**
	     * 返回码
	     */
	    private int code;

	    /**
	     * 返回结果描述
	     */
	    private String message;

	    ResultStatus(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        this.code = code;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
}
