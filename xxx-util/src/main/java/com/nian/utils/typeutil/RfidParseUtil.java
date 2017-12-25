/**
 * 
 */
package com.nian.utils.typeutil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangzhao <br>
 *
 * 2016年4月4日下午12:47:27
 */
public class RfidParseUtil {	

	//截取三级类目
	public static String getthirdmenu(String epc){
		int epcsrc=Integer.parseInt(epc.substring(0,4));
        String thirdmenu=epcsrc+"";        
        return thirdmenu;

    }	
	
	//通过epc获取当前日期
	public static Date getepcdate(String epc){
		Integer days=Integer.parseInt(epc.substring(14,18));		
	    return getProductDate(days);
	 }
	
	 //获取当前年的第一天的日期 如20160101
    public static Date getProductDate(int day){ 
    	Date d= getCurrYearFirst();
  	   	Calendar now =Calendar.getInstance();  
  	   	now.setTime(d);  
  	   	now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
  	   	return now.getTime();  
  } 
	
    
	public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

      public static Date getCurrYearFirst(){
          Calendar currCal=Calendar.getInstance();  
          int currentYear = currCal.get(Calendar.YEAR);
          return getYearFirst(currentYear);
     }
      
      //获取一个当前日期后几天的日期
      public static Date getDateAfter(Date d,int day){  
    	   Calendar now =Calendar.getInstance();  
    	   now.setTime(d);  
    	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
    	   return now.getTime();  
    } 
      

}
