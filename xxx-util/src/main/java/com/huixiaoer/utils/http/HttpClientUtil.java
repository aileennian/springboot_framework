/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: HttpClient.java
 * Author:   L.J.W
 * Date:     2013-8-7 下午4:53:58
 * Description: Passport Client
 * History: 
 * <author>      <time>      <version>    <desc>
 * L.J.W         20130807        1.0.0      Passport Client
 */
package com.huixiaoer.utils.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



/**
 * A utility class for HttpClient.
 *
 * @author <a href="mailto:ljw79618@gmail.com">L.J.W</a>
 */
@SuppressWarnings("rawtypes")
public final class HttpClientUtil {

	protected static Logger logger = LogManager.getLogger(HttpClientUtil.class);

    private static final int HTTP_RESPONSE_CODE_FLAG = 300;

    private static final String IBM_JSSE2_SOCKETFACTORY_CLASS = "com.ibm.jsse2.SSLSocketFactoryImpl";

    private static SSLSocketFactory jsse2SslSocketFactory;

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";

    private static final String HTTP_METHOD_POST = "POST";

    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

    private static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
    
    private static final String USER_AGENT = "User-Agent";
    
    private static final String FAKE_USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";

    static {
        if (System.getProperty("java.vm.vendor").toLowerCase().startsWith("ibm")) {
            try {
                Class jsse2SslSocketFactoryClass = Class.forName(IBM_JSSE2_SOCKETFACTORY_CLASS);
                jsse2SslSocketFactory = (SSLSocketFactory) jsse2SslSocketFactoryClass.newInstance();
            } catch (Exception e) {
                logger.warn( "Error when create jsse2SslSocketFactory.", e);
            }
        }
    }

    /**
     * Contacts the remote URL and returns the response.
     *
     * @return the response.
     */
    public static String getResponseFromServer(final String url, int connectionTimeout, int readTimeout,
                                               boolean trustAllCerts) throws IOException {
        URLConnection conn = new URL(url).openConnection(Proxy.NO_PROXY);
        conn.setRequestProperty(USER_AGENT, FAKE_USER_AGENT);
        initSslSetting(conn, trustAllCerts);
        if (connectionTimeout >= 0) {
            conn.setConnectTimeout(connectionTimeout);
        }
        if (readTimeout >= 0) {
            conn.setReadTimeout(readTimeout);
        }
        if (conn instanceof HttpURLConnection) {
            validateResponse((HttpURLConnection) conn);
        }
        return readResult(conn.getInputStream());
    }

    private static void initSslSetting(URLConnection conn, boolean trustAllCerts) {
        if (conn instanceof HttpsURLConnection) {
            if (trustAllCerts) {
                try {
                    SSLContext sslCtx = SSLContext.getInstance("TLS");
                    sslCtx.init(null, new TrustManager[]{new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                            // do nothing
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                            // do nothing
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }}, new SecureRandom());
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslCtx.getSocketFactory());
                    ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    });
                } catch (Exception ex) {
                    logger.warn("Error when set sslCtx.", ex);
                }
            } else {
                if (System.getProperty("java.vm.vendor").toLowerCase().startsWith("ibm")
                        && jsse2SslSocketFactory != null) {
                    ((HttpsURLConnection) conn).setSSLSocketFactory(jsse2SslSocketFactory);
                }
                ((HttpsURLConnection) conn).setHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
            }
        }
    }

    public static String getResponseViaPost(final String url, String param, String contentType,
                                            int connectionTimeout, int readTimeout,
                                            boolean trustAllCerts) throws IOException {
        URLConnection connection = new URL(url).openConnection(Proxy.NO_PROXY);
        initSslSetting(connection, trustAllCerts);
        HttpURLConnection con = (HttpURLConnection) connection;
        if (connectionTimeout >= 0) {
            con.setConnectTimeout(connectionTimeout);
        }
        if (readTimeout >= 0) {
            con.setReadTimeout(readTimeout);
        }
        con.setInstanceFollowRedirects(true);
        con.setDoOutput(true);
        con.setRequestMethod(HTTP_METHOD_POST);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
        con.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH,
                Integer.toString(param.getBytes(Charset.defaultCharset()).length));
        con.setRequestProperty(USER_AGENT, FAKE_USER_AGENT);
        final DataOutputStream printout = new DataOutputStream(con.getOutputStream());
        printout.write(param.getBytes("UTF-8"));
        printout.flush();
        printout.close();
        validateResponse(con);
        return readResult(con.getInputStream());
    }

    private static void validateResponse(HttpURLConnection con) throws IOException {
        if (con.getResponseCode() >= HTTP_RESPONSE_CODE_FLAG) {
            try {
                InputStream es = con.getErrorStream();
                if (es != null) {
                    logger.warn("Did not receive successful HTTP response," + "response content is "
                            + readResult(es));
                }
            } catch (Exception ex) {
                logger.warn("Exception occur when process errorStream", ex);
            }
            throw new IOException("Did not receive successful HTTP response: status code = " + con.getResponseCode()
                    + ", status message = [" + con.getResponseMessage() + "]");
        }
    }

    private static String readResult(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            StringBuilder temp = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                temp.append(line);
                line = reader.readLine();
            }
            return temp.toString();
        } finally {
            reader.close();
        }
    }
//	public static String getUrlContent(String weburl, String encoding) {
//		StringBuffer content = new StringBuffer();
//		InputStream Is;
//		try {
//			URL url = new URL(weburl);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			
////			httpConn.setConnectTimeout(20000);
//
//			Is = httpConn.getInputStream();
//			byte[] buffer = new byte[4096];
//			int bytes_read;
//			while ((bytes_read = Is.read(buffer)) != -1) {
//				String str = new String(buffer, 0, bytes_read, encoding);
//				content.append(str);
//			}
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			Is=null;
//		}
//		return content.toString();
//	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = "";
            if(param==null||param.equals(""))
            	urlNameString = url;
            else
            	urlNameString = url + "?" + param;
            	
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
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
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
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
	public static void main(String[] args) {
		String cc=sendGet("https://www.oschina.net/event/2241348?origin=zx02?from=20170618","utf-8");
		System.out.println(cc);
	}
}
