package com.nian.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
/*	public static void main(String[] args) {
	       String s=encryption("test");
	       System.out.println(s);
	}*/
	public static String encryption(String plain) { 
        String re_md5 = new String(); 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            md.update(plain.getBytes()); 
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
 
}
