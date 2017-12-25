package com.nian.xxx.spider.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api("京东图书爬虫数据模型")
public class JdModel {
	
	@ApiModelProperty("图书编号")
    private String bookID;
	
	@ApiModelProperty("图书名称")
    private String bookName;
	
	@ApiModelProperty("图书价格")
    private String bookPrice;
	
	
    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookPrice() {
        return bookPrice;
    }
    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
