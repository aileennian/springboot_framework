package com.nian.utils.http;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统信息工具类
 * 
 * @author nianxialing
 * @date 2013-5-6
 */
public final class IPUtil {

	protected static Logger logger = LogManager.getLogger(IPUtil.class);

    private IPUtil() {
    }

    /**
     * 取到当前机器的IP地址
     * 
     * @return
     * @author piaohailin
     * @date 2013-5-6
     */
    public static String getIp() {
        String hostIp = "0.0.0.0";      
        List<String> ips = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> inteAddresses = netInterface.getInetAddresses();
                while (inteAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inteAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ips.add(inetAddress.getHostAddress());
                    }
                }
            }
            hostIp = collectionToDelimitedString(ips, ",");
        } catch (SocketException ex) {            	
           logger.error(ex.getMessage());
        }            
        
        return hostIp;
    }

    private static String collectionToDelimitedString(Collection<String> coll,
                                                      String delim) {
        if (coll == null || coll.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    public static String getHostName() {
        String hostName = "0.0.0.0";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return hostName;
    }
    
    /**
     * Title : getAddrFromTaoBao   
     * Desc  : 根据客户IP查询地理位置（使用淘宝IP地址库）
     * Author: huangwj
     * Date  : 2017年5月23日 上午10:46:35  
     * @param ip
     * @return
     */
    public static String getAddrFromTaoBao(String ip) {  
    	String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;  
    	String result = HttpUtils.get(url, "utf-8", null);
    	String region = "";
    	if (result != null) {  
    		JSONObject json = JSONObject.parseObject(result);
    		if(json.getIntValue("code") == 0){
    			JSONObject data = json.getJSONObject("data");  
    			region += data.getString("region") + " ";  
    			region += data.getString("city") + " ";
    			region += data.getString("county") + " ";
    			if(StringUtils.isEmpty(region.trim())){
    				region = "本地IP";
    			}
    		}
    	}  
    	return region;  
	}  
    
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		String address = IPUtil.getAddrFromTaoBao(ip);
		System.out.println(address);
	}  
	
}
