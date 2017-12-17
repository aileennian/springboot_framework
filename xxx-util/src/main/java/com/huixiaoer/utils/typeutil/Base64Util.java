/**
 * 
 */
package com.huixiaoer.utils.typeutil;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * @author Administrator
 *
 */
public class Base64Util {
	// 加密
		public static String getBase64(String str) {
			byte[] b = null;
			String s = null;
			try {
				b = str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b != null) {
				s = new BASE64Encoder().encode(b);
			}
			return s;
		}

		// 解密
		public static String getFromBase64(String s) {
			byte[] b = null;
			String result = null;
			if (s != null) {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					b = decoder.decodeBuffer(s);
					result = new String(b, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		/**
		 * 对byte[]进行加密
		 * @param bytes
		 * @return
		 */
		public static String base64Encode(byte[] bytes){  
			 return new BASE64Encoder().encode(bytes);  
		 }
		/**
		 * 对加密之后解密
		 * @param base64Code
		 * @return
		 * @throws Exception
		 */
		public static byte[] base64Decode(String base64Code) throws Exception{
			return  new BASE64Decoder().decodeBuffer(base64Code);
		}
		/*public static void main(String[] args) {
			byte [] buffer=null;
			try {
				File file =new File("C:/Users/zq/AppData/Roaming/Tencent/QQ/Temp/ULCJ@_~BZC(}~%DE$8JJ~5X.jpg");
				FileInputStream fis=new FileInputStream(file);
				ByteArrayOutputStream bos=new ByteArrayOutputStream();
				byte [] b=new byte[1000];
				int n;
				while((n=fis.read(b))!=-1){
					bos.write(b,0,n);
				}
				fis.close();
				bos.close();
				buffer=bos.toByteArray();
				String encode =Base64Util.base64Encode(buffer);
				System.out.println("转换后："+ encode);
				byte[] decode=Base64Util.base64Decode(encode);
				System.out.println("解码后:"+decode);
				String burl=RemoteImageServ.upLoadIImg(decode,"test.png");
				System.out.println(burl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
}
