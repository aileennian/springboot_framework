package com.nian.utils.browser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * 
 * @author  向寒
 *
 */
public class BrowserUtils {
	
	protected static Logger logger = LogManager.getLogger(BrowserUtils.class);
	// 判断是否是IE
	public static boolean isIE(HttpServletRequest request) {
		return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0||
				request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0")>0)? true : false;
	}

	/**
	 * 获取IE版本
	 * 
	 * @param request
	 * @return
	 */
	public static Double getIEversion(HttpServletRequest request) {
		Double version = 0.0;
		if (getBrowserType(request, IE11)) {
			version = 11.0;
		}else if (getBrowserType(request, IE10)) {
			version = 10.0;
		}else if (getBrowserType(request, IE9)) {
			version = 9.0;
		}else if (getBrowserType(request, IE8)) {
			version = 8.0;
		}else if (getBrowserType(request, IE7)) {
			version = 7.0;
		}else if (getBrowserType(request, IE6)) {
			version = 6.0;
		}
		return version;
	}

	/**
	 * 获取浏览器类型
	 * 
	 * @param request
	 * @return
	 */
	public static BrowserType getBrowserType(HttpServletRequest request) {
		BrowserType browserType = null;
		if (getBrowserType(request, IE11)) {
			browserType = BrowserType.IE11;
		}
		if (getBrowserType(request, IE10)) {
			browserType = BrowserType.IE10;
		}
		if (getBrowserType(request, IE9)) {
			browserType = BrowserType.IE9;
		}
		if (getBrowserType(request, IE8)) {
			browserType = BrowserType.IE8;
		}
		if (getBrowserType(request, IE7)) {
			browserType = BrowserType.IE7;
		}
		if (getBrowserType(request, IE6)) {
			browserType = BrowserType.IE6;
		}
		if (getBrowserType(request, FIREFOX)) {
			browserType = BrowserType.Firefox;
		}
		if (getBrowserType(request, SAFARI)) {
			browserType = BrowserType.Safari;
		}
		if (getBrowserType(request, CHROME)) {
			browserType = BrowserType.Chrome;
		}
		if (getBrowserType(request, OPERA)) {
			browserType = BrowserType.Opera;
		}
		if (getBrowserType(request, "Camino")) {
			browserType = BrowserType.Camino;
		}
		return browserType;
	}

	private static boolean getBrowserType(HttpServletRequest request, String brosertype) {
		return request.getHeader("USER-AGENT").toLowerCase().indexOf(brosertype) > 0 ? true : false;
	}
	private final static String IE11 = "rv:11.0";
	private final static String IE10 = "MSIE 10.0";
	private final static String IE9 = "MSIE 9.0";
	private final static String IE8 = "MSIE 8.0";
	private final static String IE7 = "MSIE 7.0";
	private final static String IE6 = "MSIE 6.0";
	private final static String MAXTHON = "Maxthon";
	private final static String QQ = "QQBrowser";
	private final static String GREEN = "GreenBrowser";
	private final static String SE360 = "360SE";
	private final static String FIREFOX = "Firefox";
	private final static String OPERA = "Opera";
	private final static String CHROME = "Chrome";
	private final static String SAFARI = "Safari";
	private final static String OTHER = "其它";

	public static String checkBrowse(HttpServletRequest request) {
		String userAgent=request.getHeader("USER-AGENT");
		return checkBrowse(userAgent);
	}
	
	public static String checkBrowse(String userAgent) {
		if (regex(OPERA, userAgent))
			return OPERA;
		if (regex(CHROME, userAgent))
			return CHROME;
		if (regex(FIREFOX, userAgent))
			return FIREFOX;
		if (regex(SAFARI, userAgent))
			return SAFARI;
		if (regex(SE360, userAgent))
			return SE360;
		if (regex(GREEN, userAgent))
			return GREEN;
		if (regex(QQ, userAgent))
			return QQ;
		if (regex(MAXTHON, userAgent))
			return MAXTHON;
		if (regex(IE11, userAgent))
			return IE11;
		if (regex(IE10, userAgent))
			return IE10;
		if (regex(IE9, userAgent))
			return IE9;
		if (regex(IE8, userAgent))
			return IE8;
		if (regex(IE7, userAgent))
			return IE7;
		if (regex(IE6, userAgent))
			return IE6;
		return OTHER;
	}

	public static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	/**
	 * Title : getUserAgentOS   
	 * Desc  : 获取操作系统名称
	 * Author: huangwj
	 * Date  : 2017年6月9日 下午3:01:11  
	 * @return
	 */
	public static String getUserAgentOS(HttpServletRequest request){
		String  userAgentString = request.getHeader("User-Agent");
        return getUserAgentOS(userAgentString);
	}
	
	/**
	 * Title : getUserAgentOS   
	 * Desc  : 获取操作系统名称
	 * Author: huangwj
	 * Date  : 2017年6月9日 下午3:01:11  
	 * @return
	 */
	public static String getUserAgentOS(String userAgentString){
		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);  
        OperatingSystem os = userAgent.getOperatingSystem();
        return os.getName();
	}
	
	/**
	 * Title : getUserAgentOSDeviceModel   
	 * Desc  : 获取手机设备型号，若不是手机返回浏览器类型 
	 * Author: huangwj
	 * Date  : 2017年6月9日 下午3:28:25  
	 * @param request
	 * @return
	 */
	public static String getUserAgentOSDeviceModel(HttpServletRequest request){
		String  userAgentString = request.getHeader("User-Agent");
		return getUserAgentOSDeviceModel(userAgentString);
	}
	/**
	 * Title : getUserAgentOSDeviceModel   
	 * Desc  : 获取手机设备型号，若不是手机返回浏览器类型
	 * Author: huangwj
	 * Date  : 2017年6月9日 下午3:08:14  
	 * @param userAgentString
	 * @return
	 */
	public static String getUserAgentOSDeviceModel(String userAgentString){
		logger.info("userAgentString : "+ userAgentString);
		
		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);  
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        DeviceType deviceType = os.getDeviceType();
        String model = "";
        String browserName = browser.getName();
    	Version version = browser.getVersion(userAgentString);
    	if(version!=null){
    		browserName += " " + version.toString();
    	}
        if("mobile".equalsIgnoreCase(deviceType.getName())){
            Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");  
            Matcher matcher = pattern.matcher(userAgentString);  
            if (matcher.find()) {  
                model = matcher.group(1).trim();
                if(model.contains(";")){
                	String[] ms = model.split(";");
                	if(ms.length>1){
                		model = ms[1].trim();
                	}else{
                		model = ms[0].trim();
                	}
                	
                }
            }
            
            if(StringUtils.containsIgnoreCase(os.getName(), "mac")){
            	String[] str = userAgentString.split("[()]+");  
                String res="iphone OS"+str[1].split("OS")[1].split("like")[0]; 
            	model = res;
            }
        }
     	model = model + " " + browserName;
      	return model.trim();
	}
	
    public static void main(String[] args) {  
        String[] agents = new String[]{
        		"Mozilla/5.0 (Linux; Android 6.0.1; vivo X9 Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.81 Mobile Safari/537.36 LieBaoFast/4.43.2",
        		"userAgentString : Mozilla/5.0 (iPhone; CPU iPhone OS 10_0_2 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Mobile/14A456 Safari/602.1",
        		"User-Agent : Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
        		"User-Agent : Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_2 like Mac OS X) AppleWebKit/603.2.4 (KHTML, like Gecko) Mobile/14F89 SogouMobileBrowser/5.5.18",
        		"User-Agent : Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36",
        		"User-Agent : Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
        		"User-Agent : Mozilla/5.0 (Linux; U; Android 6.0.1; zh-cn; LEX720 Build/WAXCNFN5902304261S) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/7.4 Mobile Safari/537.36",
        		"User-Agent : Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
        		"User-Agent : Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36",
        		"User-Agent : Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 V1_AND_SQ_5.0.0_146_YYB_D QQ/5.0.0.2215",
        		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.3 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/535.19 (KHTML, like Gecko) Version/1.0 Chrome/18.0.1025.308 Mobile Safari/535.19",
        		"Mozilla/5.0 (Linux; U; Android 4.3; zh-CN; SM-N9009 Build/JSS15J) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-CN; Coolpad 5891 Build/JZO54K) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.3.478 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; Coolpad 5891 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 V1_AND_SQ_5.0.0_146_YYB2_D QQ/5.0.0.2215",
        		"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53",
        		"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257 QQ/5.0.0.165",
        		"Mozilla/5.0 (Linux; Android 4.3; zh-cn; SAMSUNG-GT-I9308_TD/1.0 Android/4.3 Release/11.15.2013 Browser/AppleWebKit534.30 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 SogouMSE,SogouMobileBrowser/3.2.3",
        		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/5.0.3.10 (Baidu; P1 4.2.2)",
        		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0",
        		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36 OPR/24.0.1565.82529",
        		"Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; Nexus 4 Build/KTU84P) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/5.2.3.0 (Baidu; P1 4.0.4)",
        		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.3 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; Android 4.0.4; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/535.19 (KHTML, like Gecko) Version/4.0 LieBaoFast/2.12.0 Mobile Safari/535.19",
        		"Opera/9.80 (Android; Opera Mini/7.0.31907/34.2499; U; zh) Presto/2.8.119 Version/11.10 ",
        		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HW-HUAWEI_C8825D/C8825DV100R001C92B943SP01; 480*800; CTC/2.0) AppleWebKit/534.30 Mobile Safari/534.30",
        		"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; SGP521 Build/17.1.2.A.0.314) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; Android 4.4.2; SGP521 Build/17.1.2.A.0.314) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-CN; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.8.5.442 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        		"Mozilla/5.0 (Linux; Android 4.4.2; zh-cn; SAMSUNG-SM-N9009 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/1.5 Chrome/28.0.1500.94 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-CN; HTC HTL22 Build/JDQ39) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1", 
        		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; AMOI A920W Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 ", 
        		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36",
        		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; GT-N7100 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
        		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        		"Huawei U8800    Android 2.3.3   Baidu 2.2   Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.2 Version/4.0 Mobile Safari/530.17        有用(0)",
        		"Huawei U8800    Android 2.3.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31        有用(0)",
        		"Meizu MX M031   Android 4.0.3   Chrome 18   Mozilla/5.0 (Linux; Android 4.0.3; M031 Build/IML74K) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19      有用(0)",
        		"Meizu MX M031   Android 4.0.3   Opera 12.1  Opera/9.80 (Android 4.0.3; Linux; Opera Mobi/ADR-1210241511) Presto/2.11.355 Version/12.10      有用(0)",
        		"Meizu MX M031   Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30         有用(0)",
        		"Meizu MX M031   Android 4.0.3   Baidu 2.2   Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.2 Version/4.0 Mobile Safari/530.17        有用(0)",
        		"Meizu MX M031   Android 4.0.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31      有用(0)",
        		"Meizu M9    Android 4.0.3   QQ 3.7  MQQBrowser/3.7/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30    Normal Mode     有用(0)",
        		"Meizu M9    Android 4.0.3   QQ 3.5  MQQBrowser/3.5/Adr (Linux; U; 4.0.3; zh-cn; M9 Build/Flyme 1.0.1;640*960)   Speed Mode  有用(0)",
        		"Meizu M9    Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30       有用(0)",
        		"Meizu M9    Android 4.0.3   QQ 3.5  MQQBrowser/3.5/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30    Normal Mode     有用(0)",
        		"Meizu MX M031   Android 4.0.3   Maxthon 2.7     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30         有用(0)",
        		"Huawei U8800    Android 2.3.3   Maxthon 2.7     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1         有用(0)",
        		"Huawei U8800    Android 2.3.3   QQ 3.7  MQQBrowser/3.7/Adr (Linux; U; 2.3.5; zh-cn; U8800 Build/U8800V100R001C00B528G002;480*800)   Speed Mode  有用(0)",
        		"Huawei U8800    Android 2.3.3   Dolphin 9.1     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1         有用(0)",
        		"Huawei U8800    Android 2.3.3   QQ 3.7  MQQBrowser/3.7/Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1  Normal Mode     有用(0)",
        		"Huawei U8800    Android 2.3.3   -built-in *     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
        		"Samsung P6200(GALAXY Tab)   Android 3.2     -built-in *     Mozilla/5.0 (Linux; U; Android 3.2; zh-cn; GT-P6200 Build/HTJ85B) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13      有用(0)",
        		"Huawei U8800    Android 2.3.3   Maxthon 4.0     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1         有用(0)",
        		"Meizu MX M031   Android 4.0.3   Baidu 2.3   Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.3 Version/4.0 Mobile Safari/530.17 baidubrowser/023_1.41.3.2_diordna_069_046/uzieM_51_3.0.4_130M/1200a/963E77C7DAC3FA587DF3A7798517939D%7C408994110686468/1        有用(0)",
        		"Huawei U8800    Android 2.3.3   Baidu 2.3   Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.3 Version/4.0 Mobile Safari/530.17 baidubrowser/042_1.6.3.2_diordna_008_084/IEWAUH_01_5.3.2_0088U/1001a/BE44DF7FABA8768B2A1B1E93C4BAD478%7C898293140340353/1         有用(0)",
        		"Huawei U8800    Android 2.3.3   Dolphin 9.2     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1         有用(0)",
        		"HTC S720e(One X)    Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HTC S720e Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30        有用(1)",
        		"HTC S720e(One X)    Android 4.0.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HTC S720e Build/IMM76D) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31         有用(0)",
        		"Meizu MX M031   Android 4.0.3   Dolphin Min 2.3     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30         有用(0)",
        		"Meizu MX M031   Android 4.0.3   QQ 4.0  MQQBrowser/4.0/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/533.1 (KHTML, like Gecko) Mobile Safari/533.1        有用(0)",
        		"Meizu M9    Android 4.0.3   QQ 3.7  MQQBrowser/3.7/Adr (Linux; U; 4.0.3; zh-cn; M9 Build/Flyme 1.0.1;640*960)",
        		"Meizu MX2 M040  Android 4.1     UC 9.4  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; M040 Build/JRO03H) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.4.1.362 U3/0.8.0 Mobile Safari/533.1      有用(0)",
        		"Meizu MX2 M040  Android 4.1     Chrome 31   Mozilla/5.0 (Linux; Android 4.1.1; M040 Build/JRO03H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36       有用(0)",
        		"Meizu MX2 M040  Android 4.1     猎豹 2.8  Mozilla/5.0 (Linux; Android 4.1.1; M040 Build/JRO03H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.64 Mobile Safari/537.36       有用(0)",
        		"Meizu MX2 M040  Android 4.1     Baidu 4.1   Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/4.2.4.0 (Baidu; P1 4.1.1)       有用(0)",
        		"Meizu MX M031   Android 4.1     -built-in *     Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M031 Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30         有用(1)",
        		"Meizu MX M031   Android 4.1     UC 8.8  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; M031 Build/JRO03H) AppleWebKit/534.31 (KHTML, like Gecko) UCBrowser/8.8.3.278 U3/0.8.0 Mobile Safari/534.31        有用(0)",
        		"Meizu MX2 M040  Android 4.1     QQ 4.1  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/4.1 Mobile Safari/533.1         有用(0)",
        		"Meizu MX2 M040  Android 4.1     -built-in *     Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30         有用(2)",
        		"Samsung P6200(GALAXY Tab)   Android 3.2     QQ HD 2.1   Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16       有用(0)",
        		"Samsung P6200(GALAXY Tab)   Android 3.2     UC HD 2.3   Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3) AppleWebKit/534.31 (KHTML, like Gecko) Chrome/17.0.558.0 Safari/534.31 UCBrowser/2.3.1.257",
        		"Mozilla/5.0 (Linux; U; Android 5.1.1; zh-CN; SM-G920V Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.5.5.943 Mobile Safari/537.36"
        		,"Mozilla/5.0 (Android 5.1; Mobile; rv:52.0) Gecko/52.0 Firefox/52.0",
        		"Mozilla/5.0 (Linux; U; Android 5.1.1; zh-CN; SM-G920V Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.5.5.943 Mobile Safari/537.36"
        };
    	for (String agent : agents) {
    		String os = BrowserUtils.getUserAgentOS(agent);
    		String model = BrowserUtils.getUserAgentOSDeviceModel(agent);
    		System.out.println(os +" -> " + model);
		}
    	
    	
//    	String userAgent="User-agent==Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_2 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13F69 Safari/601.1";  
//        String[] str=userAgent.split("[()]+");  
//        String res="iphone"+str[1].split("OS")[1].split("like")[0];  
//        System.out.println(res); 
    }
	
    
}
