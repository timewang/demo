package com.webhybird.module.label.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webhybird.framework.base.ColAlias;

import java.util.Date;

/**
 * Created by wangzhongfu on 2015/5/13.
 */
public class LabelVO {

    private String id;
    @ColAlias("value_")
    private String value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
