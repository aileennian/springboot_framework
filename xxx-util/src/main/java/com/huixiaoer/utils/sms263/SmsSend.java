package com.huixiaoer.utils.sms263;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;

import com.huixiaoer.utils.json.JSONHelper;
import com.huixiaoer.utils.sms263.util.WebUtils;

/**
 * Created by xxg on 17/5/6
 */
public class SmsSend {
//	云服务器管理平台地址 http://gic.yun-idc.com  
//		用户名:  yhcs
//		密码 :     111qqq```
//	用户名：SD-hyxx    密码：Net789sd  
    private static String account = "SD-hyxx";//你的账号
    private static String password = "Net789sd";//你的密码
    private static String prodId = "";//你的产品ID

    public static String sendSms(String mobile, String msg) {
        return null;
    }
    public static String sendSms2(String mobile, String msg) {
    	if(!msg.contains("一号车市")){
        	msg="【一号车市】"+msg;
    	}
    	
    	String ret=sendSms263(mobile, msg);
    	String r=JSONHelper.json2String(ret, "result");
    	//这个供应商出问题了，在调用2次发送//2017-6-26
    	if(!r.equals("0")){
    		 ret=sendSms263(mobile, msg);
    	     r=JSONHelper.json2String(ret, "result");
    	     if(!r.equals("0")){
        		 ret=sendSms263(mobile, msg);
        	     r=JSONHelper.json2String(ret, "result");
        	     if(!r.equals("0")){
            		//TODO 调用其他供应商
            	}
        	}
    	}
    	return r;
    }
    
    /**
     * 返回值
     * 其中0表示成功，其他表示失败
     * 
     * {"result":0,"msgid":"1560518101454346100","ts":"20170518101454"}
     * @param mobile
     * @param msg
     */
    public static String sendSms263(String mobile, String msg) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String timestamp = format.format(new Date());

        Map<String, String> param = new HashMap<>();
        param.put("account", account);
        param.put("ts", timestamp);
        param.put("pswd", DigestUtils.md5Hex(account + password + timestamp));
        param.put("mobile", mobile);
        param.put("msg", msg);
        param.put("needstatus", "true");
        param.put("product", prodId);
        param.put("resptype", "json");
        try {
            String smsUrl = "http://120.27.244.164/msg/HttpBatchSendSM";
            String resp = WebUtils.doPost(smsUrl, param, "UTF-8", 3600, 3600);
            //{"result":0,"msgid":"1560518101454346100","ts":"20170518101454"}
//        	String r=JSONHelper.json2String(resp, "result");
            System.out.println(resp);
            
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        sendSms("13683678434", "您本次操作的短信验证码为3333，切勿告诉他人！");//yidong
//        sendSms("18601106333", "【263云通信】加密接口测试");//nian
//    	sendSms("18010969256", "【263云通信】加密接口测试");//zhanglong  dianxin
//        sendSms("18618325809", "【263云通信】加密接口测试");//liantong
//    	String s="{\"result\":0,\"msgid\":\"1560518101454346100\",\"ts\":\"20170518101454\"}";
//    	String r=JSONHelper.json2String(s, "result");
//    	System.out.println(r);
    }
}
