package com.nian.utils.mail;

import java.io.Serializable;

public class MailUtilDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
//	帐号：itsystem@yhcs.com
//	密码：Yhcs2017)#@#IT
	private String mailServer = "smtp.exmail.qq.com";//CommonConfig.get("email.host");  //发信所用的服务器
	private String mailFrom ="itsystem@yhcs.com";// CommonConfig.get("email.username");    //发件人
	private String passWord ="123456";//"Yhcs2017)#@#IT";// CommonConfig.get("email.password");    //发件人密码
	private String mailTo = "";      //收件人
	private String mailTitle = "" ;  //信息主题
	private String mailsession; //信息内容
	public String getMailServer() {
		return mailServer;
	}
	MailUtilDto(){
	}
	MailUtilDto(String mailServer,String mailFrom,String passWord){
		this.mailServer = mailServer;
		this.mailFrom = mailFrom;
		this.passWord = passWord;
	}
	
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public String getMailsession() {
		return mailsession;
	}
	public void setMailsession(String mailsession) {
		this.mailsession = mailsession;
	}
	
	/** 验证文件是否合法的JSON格式[]*/
	public static String validateParamForJson(String json) {
		if(!json.startsWith("[")) {
			json ="["+json ;
		}
		if(!json.endsWith("]"))
			json =json+"]" ;
		return json ;
	}
	
}
