package com.huixiaoer.utils.mail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.huixiaoer.utils.PropertiesUtil;

import net.sf.json.JSONObject;




public class MessageUtil {

	public static final String channelCode = "xxx";
	public static final String authorcode = "xxx";
	protected static Logger logger = LogManager.getLogger(MessageUtil.class);

	
	/**
	 * @param phoneNum
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static JSONObject sendPhoneMessage(String phoneNum, String message) throws UnsupportedEncodingException {
		String url = "http://www.xxx.com/api/xxxcommon/sendSms";
		String parmsign = "channelCode="+channelCode+"&mobile="+phoneNum;
		String sign = encryption(authorcode + URLEncoder.encode(parmsign, "utf-8") +
			authorcode);
		String content = URLEncoder.encode(URLEncoder.encode(message, "utf-8"), "utf-8");
		String parmstr = parmsign + "&content=" + content + "&sign=" + sign;
		
		//返回值ret格式：{"data":"","code":"1","msg":""}
		//code:1操作成功，其他出错。
		String ret = sendPhoneGet(url, parmstr);
		JSONObject jsonObject = JSONObject.fromObject(ret);
		return jsonObject;
	}
	/**
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	private static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendPhoneGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

	
	/***短信保存时间**/
	public final static long MAIL_SAVE_TIME = 300000L;
	/**
	 * 获得随机密码 
	 * @param lenth
	 * @return
	 */
	public static String getRandomPwd(int lenth){
		StringBuffer buffer = new StringBuffer(
	     "0123456789abcdefghijklmnopqrstuvwxyz");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < lenth; i++) {
		   //生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
		   sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
//	/***
//	 * 获得发送信息的部分内容 ： 服务器，发件人，密码等
//	 */
////	public static final String MAIL_SEND_PARAM = "mail_send_param";
//	
//	public static void sendShortMessage(String toMobile, String content)
//{
//		ShortMessageSender sms = new ShortMessageSender();
//		sms.init();//xxg
//		
//		content = content.replaceAll("&", "%26");
//		java.util.Map params = new java.util.HashMap();
//		params.put(ShortMessageSender.MSG_TO, toMobile);
//		params.put(ShortMessageSender.MSG_CONTENT, content);
//		try {
//			sms.sendMessage(params);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 发送信息到Email
	 * @param mailTo 收件人
	 * @param title 标题
	 * @param message 信息主题
	 * @param isHtml 是否发送的是html
	 * @throws Exception
	 */
	public static void sendEmailMessage(String mailTo,String title,String message,boolean isHtml)throws Exception{
		MailUtilDto dto = new MailUtilDto();//ParamUtil.getParamForMail(MAIL_SEND_PARAM);
		dto.setMailTo(mailTo);
		dto.setMailTitle(title);
		dto.setMailsession(message);
		sendEmailMessage(dto,isHtml);
	}
	
	/**
	 * 发送信息到Email
	 * @param mailTo 收件人
	 * @param title 标题
	 * @param message 信息主题
	 * @param isHtml 是否发送的是html
	 * @throws Exception
	 */
	public static void sendEmailMessage(MailUtilDto dto,String mailTo,String title,String message,boolean isHtml)throws Exception{
//		MailUtilDto dto = new MailUtilDto();//ParamUtil.getParamForMail(MAIL_SEND_PARAM);
		dto.setMailTo(mailTo);
		dto.setMailTitle(title);
		dto.setMailsession(message);
		sendEmailMessage(dto,isHtml);
	}
	/**
	 * 发送信息到Email
	 * @param mailTo 收件人
	 * @param title 标题
	 * @param message 信息主题
	 * @throws Exception
	 */
	public static void sendEmailMessage(String mailTo,String title,String message)throws Exception{
		sendEmailMessage(mailTo,title,message,false);
	}
	
	/**
	 * 发送信息到Email
	 * @param dto 发信所用的基本信息
	 * @param isHtml 是否发送的是html
	 */
	private static void  sendEmailMessage(MailUtilDto dto,boolean isHtml) throws Exception{
		try {  
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties p = new Properties(); //Properties p = System.getProperties();  
            p.put("mail.smtp.auth", "true");  
            p.put("mail.transport.protocol", "smtp");  
            p.put("mail.smtp.host", dto.getMailServer());
            String port=PropertiesUtil.getValue("mail.port");
//            port="465";
            if(port==null||port.equals(""))
            	port="25";
            else if(port.equals("465")){//smtp.exmail.qq.com(使用SSL，端口号465) xxg add
            	p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            }
            logger.info(port);
            p.put("mail.smtp.port", port);  
            //建立会话  
            Session session = Session.getInstance(p);  
            Message msg = new MimeMessage(session); //建立信息  
            msg.setFrom(new InternetAddress(dto.getMailFrom(),"一号车市")); //发件人  
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dto.getMailTo())); //收件人  
            msg.setSentDate(new Date()); // 发送日期  
            msg.setSubject(dto.getMailTitle()); // 主题  
            if(isHtml){
            	msg.setContent(dto.getMailsession(),"text/html;charset=utf8");
            }else{
            	msg.setText(dto.getMailsession()); //内容  
            }
            
            
            // 邮件服务器进行验证  
            Transport tran = session.getTransport("smtp");  
            tran.connect(dto.getMailServer(), dto.getMailFrom(), dto.getPassWord());  
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送  
   
        } catch (Exception e) { 
        	e.printStackTrace();
           // throw new CommonException(Messages.getString("memberMsg.sendMailError"));
        }  
	}
	public static void main(String[] args) {
		String mailTo="123476097@qq.com";
		String title="xxg修改密码";
//		String message=mailTo;
		String basePath="";
		String jsp = basePath+"forWordAction.action?date=";
		String url = "\r\n\t <html><head><title></title></head><body><a href=\""+jsp+"&email="+(mailTo)+"\">立刻点此</a></body></html>";
		
		String message="忘记密码了？"+url+"	找回吧!(十分钟内有效，过期请重发)";
//		boolean isHtml=true;
		try {
//			String mailServer="smtp.163.com";  //发信所用的服务器
//			String mailFrom="xxx@163.com";    //发件人
//			String passWord="xxx";    //发件人密码
			
			sendEmailMessage(mailTo, title, message);
//			MailUtilDto dto=new MailUtilDto(mailServer, mailFrom, passWord);
//			sendEmailMessage(dto,mailTo, title, message,isHtml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
