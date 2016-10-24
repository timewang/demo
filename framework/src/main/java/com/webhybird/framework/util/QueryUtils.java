package com.webhybird.framework.util;

import org.springframework.util.StringUtils;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
public class QueryUtils {

    /**
     * 包装 like 的查询参数
     * @param searchWord
     * @return
     */
    public static String getSearchString(String searchWord){
        return StringUtils.isEmpty(searchWord)? "" : "%" + searchWord + "%";
    }

}
