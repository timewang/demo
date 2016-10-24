package com.webhybird.module.tree.constants;

/**
 * Created by wangzhongfu on 2015/5/27.
 */
public enum ZtreeEnum {
    rootId("0"),typeOrg("org"),targetBlank("_blank")
    ;

    private String enumName;

    ZtreeEnum(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumName() {

        return enumName;
    }
}
