package com.nian.xxx.spider.httpclient.main;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nian.xxx.spider.entity.JdModel;
import com.nian.xxx.spider.httpclient.util.URLFecter;

import io.swagger.annotations.Api;

@Api("京东图书爬虫主程序")
public class JdongMain {

	static final Logger logger = LogManager.getLogger(JdongMain.class);

	public static void main(String[] args) throws Exception {
		// 初始化一个httpclient
		HttpClient client = new DefaultHttpClient();
		// 我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
		String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
		// 抓取的数据
		List<JdModel> bookdatas = URLFecter.URLParser(client, url);
		// 循环输出抓取的数据
		for (JdModel jd : bookdatas) {
			logger.info("编号:" + jd.getBookID() + "\t" + "bookPrice:" + jd.getBookPrice() + "\t" + "bookName:"
					+ jd.getBookName());
		}

		//logger.info("bookdatas="+bookdatas);
		// 将抓取的数据插入数据库
		// MYSQLControl.executeInsert(bookdatas);
	}

}
