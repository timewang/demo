package com.webhybird.framework.dataquery;

/**
 * Created by wangzhongfu on 2015/5/16.
 */
public enum DateType {

    DATE_YYYY("yyyy"),
    DATE_YYYY_MM("yyyy-MM"),
    DATE_YYYY_MM_DD("yyyy-MM-dd"),
    DATETIME_HH("yyyy-MM-dd hh")
    ,DATETIME_HH_MM("yyyy-MM-dd hh:mm"),
    DATETIME_HH_MM_SS("yyyy-MM-dd hh:mm:ss");

    DateType(String dateformat) {
        this.dateformat = dateformat;
    }

    private String dateformat;

    public String getDateformat() {
        return dateformat;
    }

}
