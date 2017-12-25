package com.nian.mybatis.base;

import java.io.Serializable;

import javax.persistence.Transient;

public class BaseEntity implements Serializable{
 
    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;
    
    
 

 
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
