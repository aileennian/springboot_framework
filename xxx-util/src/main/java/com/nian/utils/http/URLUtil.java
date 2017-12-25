package com.nian.utils.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * <p>
 * Title:URL辅助工具类
 * </p>
 *
 * <p>
 * Copyright: 转载请注明出处http://blog.csdn.net/sunyujia/
 * </p>
 *
 * @author 孙钰佳
 * @main sunyujia@yahoo.cn
 * @date Sep 21, 2008 12:31:23 PM
 */
@SuppressWarnings("rawtypes")
public class URLUtil {
	/**
	 *
	 * Description:取得当前类所在的文件
	 *
	 * @param clazz
	 * @return
	 * @mail sunyujia@yahoo.cn
	 * @since：Sep 21, 2008 12:32:10 PM
	 */
	public static File getClassFile(Class clazz) {
		URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}

	/**
	 *
	 * Description:同getClassFile 解决中文编码问题
	 *
	 * @param clazz
	 * @return
	 * @mail sunyujia@yahoo.cn
	 * @since：Sep 21, 2008 1:10:12 PM
	 */
	public static String getClassFilePath(Class clazz) {
		try {
			return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 *
	 * Description:取得当前类所在的ClassPath目录
	 *
	 * @param clazz
	 * @return
	 * @mail sunyujia@yahoo.cn
	 * @since：Sep 21, 2008 12:32:27 PM
	 */
	public static File getClassPathFile(Class clazz) {
		File file = getClassFile(clazz);
		for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
			file = file.getParentFile();
		if (file.getName().toUpperCase().endsWith(".JAR!")) {
			file = file.getParentFile();
		}
		return file;
	}

	/**
	 *
	 * Description: 同getClassPathFile 解决中文编码问题
	 *
	 * @param clazz
	 * @return
	 * @mail sunyujia@yahoo.cn
	 * @since：Sep 21, 2008 1:10:37 PM
	 */
	public static String getClassPath(Class clazz) {
		try {
			return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

//	public static void main(String[] args) {
//		System.out.println(getClassFilePath(URLUtil.class));
//		System.out.println(getClassPath(URLUtil.class));
//		System.out.println(getPath(URLUtil.class));
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	static String cfgfd = "config";

	public static String getPath(Class clazz) {
		String path = getClassPath(clazz);
		String find = "";
		if (path.contains("file:"))// jar环境下
			find = "file:\\\\([\\\\a-zA-Z0-9:]{1,})";
		else// 开发环境下
			find = "bin";
		// path="C:\\Users\\zhou\\Desktop\\moduleGer\\file:\\C:\\Users\\zhou\\Desktop\\moduleGer";
		// find="file:\\\\([\\\\a-zA-Z0-9:]{1,})";
		return replace(path, find);
	}

	private static String replace(String source, String regex) {
		return source.replaceFirst(regex, cfgfd);
	}

}