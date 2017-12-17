package com.huixiaoer.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import com.huixiaoer.utils.json.JacksonMapper;

public class ApiParameter {
	public static HttpEntity<String> getHttpEntity(Map<String,Object> param){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);        
        String paramJson = JacksonMapper.beanToJson(param);        
        HttpEntity<String> requestEntity = new HttpEntity(paramJson,headers);
        return requestEntity;
	}

	
	public static HttpEntity<String> getHttpEntity(Map<String,Object> param, HttpHeaders headers){
        if (headers==null){
		  headers = new HttpHeaders();
        }
        headers.setContentType(MediaType.APPLICATION_JSON);        
        String paramJson = JacksonMapper.beanToJson(param);        
        HttpEntity<String> requestEntity = new HttpEntity(paramJson,headers);
        return requestEntity;
	}
	
	public static HttpEntity<String> getHttpEntity(HttpHeaders headers){
        if (headers==null){
		  headers = new HttpHeaders();
        }
        headers.setContentType(MediaType.APPLICATION_JSON);        
        HttpEntity<String> requestEntity = new HttpEntity(null,headers);
        return requestEntity;
	}

	public static  <T> T getResultData(Object vo,Class cls) throws RuntimeException{
		if (vo==null ){
			return null;
		}
		if (vo instanceof LinkedHashMap
				|| vo instanceof TreeMap
				|| vo instanceof LinkedTreeMap
				){
			ObjectMapper objectMapper = new ObjectMapper();
			T tt = (T) objectMapper.convertValue(vo, cls);
			return tt;
		}else if(vo instanceof String){
			T tt = (T) JacksonMapper.jsonToBean((String) vo, cls);
			return tt;
		}
		throw new RuntimeException("对象转换失败！"+vo);
	}
}
