package com.nian.utils.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;  

public class GsonUtil {
	 private GsonUtil(){}    
     
	    /**   
	     * 对象转换成json字符串   
	     * @param obj    
	     * @return    
	     */    
	    public static String toJson(Object obj) {    
	        try{  
	              
	              Gson gson = new Gson();    
	              return gson.toJson(obj);    
	        }catch(Exception e){  
	            e.printStackTrace();  
	              
	        }  
	        return null;  
	    }    
	      
	    /**   
	     * 对象转换成json字符串   
	     * @param obj    
	     * @return    
	     */    
	    public static String toJson(String key ,Object obj) {    
	        try{  
	              Map map = new HashMap();  
	              map.put(key, obj);  
	              Gson gson = new Gson();    
	              return gson.toJson(map);    
	        }catch(Exception e){  
	            e.printStackTrace();  
	              
	        }  
	        return null;  
	    }    
	    
	    /**   
	     * json字符串转成对象   
	     * @param str     
	     * @param type   
	     * @return    
	     */    
	    public static Object fromJsonToObject(String str, Type type) {    
	        try{  
	             Gson gson = new Gson();    
	             return gson.fromJson(str, type);    
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	       return null;  
	    }    
	    
	    /**   
	     * json字符串转成对象   
	     * @param str     
	     * @param type    
	     * @return    
	     */    
	    public static Object fromJsonToObject(String str, Class type) {    
	        try{  
	              Gson gson = new Gson();    
	              return gson.fromJson(str, type);    
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }    
	    /**  
	     * json转List  
	     * */  
	    private void test(){  
	        String json = "";  
	        String result = new Gson().fromJson(json,new TypeToken<List<Object>>() {}.getType());//转换为集合   
	    }  
	    
	    public static void main(String[] arg) throws JsonParseException, JsonMappingException, IOException{
	    	String value = "<ResultVo><date></data><errcode>1</errcode><message>Required String parameter 'username' is not present</message><success>false</success></ResultVo>";
	    	
	    	ObjectMapper mapper = new ObjectMapper(); 
	    	//mapper.acceptJsonFormatVisitor(type, visitor);
	    	//ResultVo vo = mapper.readValue(value,ResultVo.class);
	    	
	    	//ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(value.getBytes()); 
	    	Reader reader  = new StringReader(value);
	    	
			//JsonReader jr = new JsonReader(reader);
			//jr.setLenient(true);
			//jr.toString()
			
			//Gson gson = new Gson();  
			//ResultVo vo = gson.fromJson(jr, ResultVo.class);
			
			//System.out.println("aaa="+jr.toString());
			
//			String json1=gson.toJson(json);//转换成json数据  s
			 
			//gson.fromJson(reader, typeOfT)
			
			
			JsonParser jsonParser = new JsonParser();
			JsonObject j = jsonParser.parse(value).getAsJsonObject();
	    	
	    }
	    
}
