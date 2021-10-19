package com.yf.beans;

import java.io.Serializable;
public class Locator implements Serializable {
    private String name;
    private String by;
    private String value;
    private int timeOut;
    public Locator(){};
    public Locator(String name,String by,String value,int timeOut){
        this.name=name;
        this.by=by;
        this.value=value;
        this.timeOut=timeOut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getName() {
        return name;
    }

    public String getBy() {
        return by;
    }

    public String getValue() {
        return value;
    }

    public int getTimeOut() {
        return timeOut;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "name='" + name + '\'' +
                ", by='" + by + '\'' +
                ", value='" + value + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }
}
