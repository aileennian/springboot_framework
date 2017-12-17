package com.huixiaoer.utils.sms;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
 

public class SmsUtils {
	protected static Logger logger = LogManager.getLogger("SmsUtils");
	
	public static Boolean sendSms( String mobile,SmsTemplate smsTemplate, String businessStr) {
		if (StringUtils.isBlank(mobile)) {
			logger.info("手机号码为空!");
			return false;
		}
		String[] strlist = businessStr.split(",");
		return toSendSms(mobile, smsTemplate,strlist);
	}
	
	private static Boolean toSendSms(String mobile,SmsTemplate smsTemplate, String[] params){
		HashMap<String, Object> result = null;
		//初始化SDK
		//以下是容联云的sdk
		
//		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//		restAPI.init("app.cloopen.com", "8883");
//		restAPI.setAccount("8a48b55150e162370150f071ec856b8c", "03fc1006867545b0863f4da50ef74e56");
//		restAPI.setAppId("8aaf0708559171f101559a2533f00002");
//		result = restAPI.sendTemplateSMS(mobile,smsTemplate.getTemplateId() ,params);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				logger.info(key +" = "+object);
			}
			return true;
		}else{
			//异常返回输出错误码和错误信息
			logger.error("短信错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return false;
		}
	}
	
//	public static void main(String[] args) {
//		sendSms("15810615303",SmsTemplate.SMS_VERIFICATION_CODE, "888888,20");
//		sendSms("15810615303",SmsTemplate.ORDER_PAYMENT_SUCCESSFUL_NOTICE, "201604161117430008,50.5");
//	}

}
