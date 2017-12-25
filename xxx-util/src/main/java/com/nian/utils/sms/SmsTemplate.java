package com.nian.utils.sms;

/***
 * 短信通知模板
 * 
 * @todo TODO
 * @author litt
 * @date 2016年6月24日上午10:54:20
 *
 */
public enum SmsTemplate {

	ORDER_PAYMENT_SUCCESSFUL_NOTICE("order.payment.successful.notice","96914", "您有一笔新的订单请查看，订单号v1，订单金额v2元，请尽快发货。"),
	SMS_VERIFICATION_CODE("sms.verification.code","96885", "验证码:v1，v2分钟内输入有效。为了保证您的安全,请勿将此信息透漏给其他人。");
	

	private final String type;
	private final String templateId;//来自于融联云短信模版配置
	private final String content;

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public String getTemplateId() {
		return templateId;
	}

	private SmsTemplate(String type, String templateId, String content) {
		this.type = type;
		this.templateId = templateId;
		this.content = content;
	}
	
	

}
