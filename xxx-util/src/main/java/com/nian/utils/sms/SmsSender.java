/**
 * 
 */
package com.nian.utils.sms;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nian.utils.typeutil.Base64Util;

/**
 * @author xianghan
 *
 * 短信发送工具
 */
public class SmsSender {
	private static SmsSender instance = new SmsSender();
	public static SmsSender getInstance() {
		return instance;
	}
	 static String userid="haier";
	 static String key="aGFpZXJoYWllcg==";
	
	/*public static void main(String[] args) {
	    String result=senderSms("13681073722");
	    System.out.println(result);
	}*/
	
	public synchronized static String senderSms(String mobiles){
		int num = randomCode();
		String msg="验证码:"+num+"，20分钟内输入有效。为了保证您的安全,请勿将此信息透漏给其他人。";
	    getInstance().sendSms(userid, Base64Util.getFromBase64(key), mobiles, msg, String.valueOf(System.currentTimeMillis()));
	    return String.valueOf(num);
	}
	
	public static String senderSms(String mobiles,String msg){
		getInstance().sendSms(userid, Base64Util.getFromBase64(key), mobiles, msg, String.valueOf(System.currentTimeMillis()));
		String result="1";
	    return result ;
	}
	private static int randomCode() {
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int num = 0;
		for(int i = 0; i < 6; i++){
		    num = num * 10 + array[i];
		}
		if (String.valueOf(num).length()!=6) {
			return randomCode();
		}
		return num;
	}

	private String getSoapSmssend(String userid,String pass,String mobiles,String msg,String time)
   {
       try 
       {
           String soap = "";
           soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
           		+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
           		+"<soap:Body>"
           		+"<SendMessages xmlns=\"http://tempuri.org/\">"
           		+"<uid>"+userid+"</uid>"
           		+"<pwd>"+pass+"</pwd>"
           		+"<tos>"+mobiles+"</tos>"
           		+"<msg>"+msg+"</msg>"
           		+"<otime>"+time+"</otime>"            		
           		+"</SendMessages>"
           		+"</soap:Body>"
           		+"</soap:Envelope>";                        
           return soap;
       } 
       catch (Exception ex)
       {
           ex.printStackTrace();
           return null;
       }
   }
	
	  
	private InputStream getSoapInputStream(String userid,String pass,String mobiles,String msg,String time)throws Exception
   {
		URLConnection conn = null;
		InputStream is = null;
       try
       {
           String soap=getSoapSmssend(userid,pass,mobiles,msg,time);            
           if(soap==null)
           {
               return null;
           }
           try{
            
           	URL url=new URL("http://service2.winic.org/Service.asmx");     
           	
           	conn=url.openConnection();
           	conn.setUseCaches(false);
               conn.setDoInput(true);
               conn.setDoOutput(true);                           
               conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
               conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
               conn.setRequestProperty("HOST","service2.winic.org");
               conn.setRequestProperty("SOAPAction","\"http://tempuri.org/SendMessages\"");

               OutputStream os=conn.getOutputStream();
               OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
               osw.write(soap);
               osw.flush();                
           }catch(Exception ex){
           	System.out.print("SmsSoap.openUrl error:"+ex.getMessage());
           }                                            
           try{
           	is=conn.getInputStream();            	
           }catch(Exception ex1){
           	System.out.print("SmsSoap.getUrl error:"+ex1.getMessage());
           }
           
           return is;   
       }
       catch(Exception e)
       {
       	System.out.print("SmsSoap.InputStream error:"+e.getMessage());
           return null;
       }
   }
	 
	//发送短信
	public String sendSms(String userid,String pass,String mobiles,String msg,String time)
   {
       String result = "-12";
		try
       {
           Document doc;
           DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
           dbf.setNamespaceAware(true);
           DocumentBuilder db=dbf.newDocumentBuilder();
           InputStream is=getSoapInputStream(userid,pass,mobiles,msg,time);
           if(is!=null){
	            doc=db.parse(is);
	            NodeList nl=doc.getElementsByTagName("SendMessagesResult");
	            Node n=nl.item(0);
	            result=n.getFirstChild().getNodeValue();
	            is.close();
           }            	
           return result;
       }
       catch(Exception e)
       {
       	System.out.print("SmsSoap.sendSms error:"+e.getMessage());
           return "-12";
       }
   } 

	 
	
	private String  getVoiceSend(String userid,String pwd,String txtPhone,String vtxt,String Svmode,byte[] buffer,String svrno,String str_time,String end_time) throws Exception
			{
		 try 
	        {
	            String soap = "";
	            soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
	            		+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
	            		+"<soap:Body>"
	            		+"<SendVoice xmlns=\"http://tempuri.org/\">"
	            		+"<uid>"+userid+"</uid>"
	            		+"<pwd>"+pwd+"</pwd>"
	            		+"<vto>"+txtPhone+"</vto>"
	            		+"<vtxt>"+vtxt+"</vtxt>"
	            		+"<mode>"+Svmode+"</mode>" 
	            		+"<FileBytes>"+buffer+"</FileBytes>" 
	            		+"<svrno>"+svrno+"</svrno>" 
	            		+"<str_time>"+str_time+"</str_time>" 
	            		+"<end_time>"+end_time+"</end_time>" 
	            		+"</SendVoice>"
	            		+"</soap:Body>"
	            		+"</soap:Envelope>";                        
	            return soap;
	              
	        } 
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	            return null;
	        }
			}
	private InputStream getVoiceInputStream(String userid,String pwd,String txtPhone,String vtxt,String Svmode,String vfbtye,String svrno,String str_time,String end_time)throws Exception
   {
		URLConnection conn = null;
		InputStream is = null;
       try
       {
       	
       	  byte[] buffer1 = null;
              
             if (Svmode=="3")
             {
                 String vPath = vfbtye;
                 if (vPath == "")
                 {
                     String xx="请选择语音文件。格式为.WAV 大小不要超过 2M";                  
                     return null;
                 }
                 else
                 {                
                     int i = vPath.lastIndexOf(".");
                       //取得文档扩展名
                     String newext = vPath.substring(i+1).toLowerCase();
                     if (!newext.equals("wav"))
                     {
                         String xx = "语音文件格式不正确";
                         return null;
                     }

                 }
                  
              //    FileStream fs = new FileStream(vPath, FileMode.Open);
             //    buffer = new byte[fs.Length];
              //   fs.Read(buffer, 0, buffer.Length);
              //   fs.Close();

             }
             else
             {
                 buffer1 = new byte[0];
             }
             
             
           String soap=getVoiceSend(userid,pwd,txtPhone,vtxt,Svmode,buffer1,svrno,str_time,end_time);            
           if(soap==null)
           {
               return null;
           }
           try{
           	
                 
           	URL url=new URL("http://service2.winic.org/Service.asmx");     
           	
           	conn=url.openConnection();
           	conn.setUseCaches(false);
               conn.setDoInput(true);
               conn.setDoOutput(true);                           
               conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
               conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
               conn.setRequestProperty("HOST","service2.winic.org");
               conn.setRequestProperty("SOAPAction","\"http://tempuri.org/SendVoice\"");

               OutputStream os=conn.getOutputStream();
               OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
               osw.write(soap);
               osw.flush();                
           }catch(Exception ex){
           	System.out.print("SmsSoap.openUrl error:"+ex.getMessage());
           }                                            
           try{
           	is=conn.getInputStream();            	
           }catch(Exception ex1){
           	System.out.print("SmsSoap.getUrl error:"+ex1.getMessage());
           }
           
           return is;   
       }
       catch(Exception e)
       {
       	System.out.print("SmsSoap.InputStream error:"+e.getMessage());
           return null;
       }
   }
	
	//发送语言
	public String sendVoice(String userid,String pwd,String txtPhone,String vtxt,String Svmode,String vfbtye1,String svrno,String str_time,String end_time)
	{ 
		 String result = "-12";
			try
	        {
	            Document doc;
	            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
	            dbf.setNamespaceAware(true);
	            DocumentBuilder db=dbf.newDocumentBuilder();
	            InputStream is=getVoiceInputStream(userid,pwd,txtPhone,vtxt,Svmode,vfbtye1,svrno,str_time,end_time);
	            if(is!=null){
		            doc=db.parse(is);
		            NodeList nl=doc.getElementsByTagName("SendVoiceResult");
		            Node n=nl.item(0);
		            result=n.getFirstChild().getNodeValue();
		            is.close();
	            }            	
	            return result;
	        }
	        catch(Exception e)
	        {
	        	System.out.print("SmsSoap.sendSms error:"+e.getMessage());
	            return "-12";
	        }
	        
	 
	}
	


}
