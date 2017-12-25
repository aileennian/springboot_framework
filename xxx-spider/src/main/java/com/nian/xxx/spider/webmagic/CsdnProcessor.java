package com.nian.xxx.spider.webmagic;


import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nian.xxx.spider.entity.CsdnBlog;

import io.swagger.annotations.Api;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author admin
 *
 */
@Api(value="CSDN博客爬虫",tags="webmagic")
public class CsdnProcessor implements PageProcessor {
	public static Logger logger = LogManager.getLogger(CsdnProcessor.class);
	
    private static String username = "";// 设置csdn用户名
    private static int size = 0;// 共抓取到的文章数量	

	private Site site = Site.me().setSleepTime(0).setCycleRetryTimes(3);

	/**
	 * http://www.ccgp-xuzhou.gov.cn/Home/HomeList?category_id=331
	 * csdn uri 后缀
	 */
	public static final String CSDN_URI = "zhengyong15984285623";

	@Override
	public void process(Page page) {
	      // 列表页
        if (!page.getUrl().regex("http://www\\.ccgp-xuzhou\\.gov\\.cn/Home/HomeList?category_id=331\\d+").match()) {
            // 添加所有文章页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links()// 限定文章列表获取区域
                    .regex("/" + username + "/article/details/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
            // 添加其他列表页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links()// 限定其他列表页获取区域
                    .regex("/" + username + "/article/list/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
            // 文章页
        } else {
            size++;// 文章数量加1
            // 用CsdnBlog类来存抓取到的数据，方便存入数据库
            CsdnBlog csdnBlog = new CsdnBlog();
            // 设置编号
            csdnBlog.setId(Integer.parseInt(
                    page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/(\\d+)").get()));
            // 设置标题
            csdnBlog.setTitle(
                    page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()").get());
            // 设置日期
            csdnBlog.setDate(
                    page.getHtml().xpath("//div[@class='article_r']/span[@class='link_postdate']/text()").get());
            // 设置标签（可以有多个，用,来分割）
            csdnBlog.setTags(listToString(page.getHtml()
                    .xpath("//div[@class='article_l']/span[@class='link_categories']/a/allText()").all()));
            // 设置类别（可以有多个，用,来分割）
            csdnBlog.setCategory(
                    listToString(page.getHtml().xpath("//div[@class='category_r']/label/span/text()").all()));
            // 设置阅读人数
            csdnBlog.setView(Integer.parseInt(page.getHtml().xpath("//div[@class='article_r']/span[@class='link_view']")
                    .regex("(\\d+)人阅读").get()));
            // 设置评论人数
            csdnBlog.setComments(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='article_r']/span[@class='link_comments']").regex("\\((\\d+)\\)").get()));
            // 设置是否原创
            csdnBlog.setCopyright(page.getHtml().regex("bog_copyright").match() ? 1 : 0);
            // 把对象存入数据库
           // new CsdnBlogDao().add(csdnBlog);
            // 把对象输出控制台
            System.out.println(csdnBlog);
        }

	}
	
	
	 // 把list转换为string，用,分割
    public static String listToString(List<String> stringList) {
    	DOMConfigurator.configure("/Users/zhengyong/crawl/log4j.xml");//加载.xml文件
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }
    
    @Override
    public Site getSite() {
        return site;
    }
    
    
    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        // 从用户博客首页开始抓，开启5个线程，启动爬虫
        Spider.create(new CsdnProcessor()).addUrl("http://blog.csdn.net/" + username).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }
    
}
