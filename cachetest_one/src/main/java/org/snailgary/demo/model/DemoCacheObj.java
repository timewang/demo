/**
 *
 */
package org.snailgary.demo.model;

/**
 * ***********************************************************
 *
 * @类名 ：DemoCacheObj.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/28
 * ***********************************************************
 */
public class DemoCacheObj {

    private String id;

    private String value;

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

    @Override public String toString() {
        return "DemoCacheObj{" + "id='" + id + '\'' + ", value='" + value + '\'' + '}';
    }
}
