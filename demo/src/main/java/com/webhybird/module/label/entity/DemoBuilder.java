package com.webhybird.module.label.entity;

public class DemoBuilder {
    private int param;
    private int param4;
    private int param2;
    private int param3;
    private int param5;

    public DemoBuilder setParam(int param) {
        this.param = param;
        return this;
    }

    public DemoBuilder setParam4(int param4) {
        this.param4 = param4;
        return this;
    }

    public DemoBuilder setParam2(int param2) {
        this.param2 = param2;
        return this;
    }

    public DemoBuilder setParam3(int param3) {
        this.param3 = param3;
        return this;
    }

    public DemoBuilder setParam5(int param5) {
        this.param5 = param5;
        return this;
    }

    public Demo createDemo() {
        return Demo.createDemo(param,param2,param3,param4,param5);
    }
}
