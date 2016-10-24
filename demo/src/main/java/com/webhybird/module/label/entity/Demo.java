/**
 *
 */
package com.webhybird.module.label.entity;

/**
 * ***********************************************************
 *
 * @类名 ：Demo.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/10/14
 * ***********************************************************
 */
public class Demo {

    private int param;

    private int param2;

    private int param3;

    private int param4;

    private int param5;

    public Demo() {
    }

    public Demo(int param) {
        this.param = param;
    }

    public Demo(int param, int param4) {
        this.param = param;
        this.param4 = param4;
    }

    private Demo(int param, int param2, int param3, int param4, int param5) {
        this.param = param;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public static Demo createDemo(int param, int param2, int param3, int param4, int param5) {
        return new Demo(param, param2, param3, param4, param5);
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public int getParam3() {
        return param3;
    }

    public void setParam3(int param3) {
        this.param3 = param3;
    }

    public int getParam4() {
        return param4;
    }

    public void setParam4(int param4) {
        this.param4 = param4;
    }

    public int getParam5() {
        return param5;
    }

    public void setParam5(int param5) {
        this.param5 = param5;
    }
}
