package com.nian.api;

import java.io.Serializable;

import com.nian.utils.json.JacksonMapper;

public class ResultVo<T>  implements Serializable{
	private boolean isSuccess;
	private T date=(T) new String();
	private int errcode;
	private String message;
	
	public ResultVo() {

	}
 
	public ResultVo(T v){
		this.date=v;
		this.isSuccess=true;
	}
	
	public ResultVo(int code,String errMsg){
		this.isSuccess=false;
		this.errcode=code;
		this.message=errMsg;
	}

	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}
	
	
	

	public void setError(int code, String err) {
		this.errcode = code;
		this.message = err;
		this.isSuccess = false;
		this.date = null;
	}


	public void setError(ResultStatus error) {
		setError(error.getCode(), error.getMessage());
	}
		
	
	public String error(int code, String message) {
		setError(code, message);
		return JacksonMapper.beanToJson(this);
	}

	public String error(String message) {
		setError(500, message);
		return JacksonMapper.beanToJson(this);
	}

	
	
	public void setOk(T t) {
		this.isSuccess = true;
		this.date = t;
		this.errcode=0;
	}
	
	/**
	 * 反正JSON串
	 * 
	 * @param t
	 * @return
	 */
	public String ok(T t) {
		setOk(t);
		return JacksonMapper.beanToJson(this);
	}

	@Override
	public String toString() {
		String v= JacksonMapper.beanToJson(this);
		return v;
	}
	
	

}
