package com.yf.utils;

public enum DataSourceType {
    EXCEL("excel"),
    CSV("csv"),
    POSTGRESQL("postgresql"),
    YAML("yaml");


    private String value;
    private DataSourceType(String value){
    this.value=value;
    }
    public String value(){
        return this.value;
    }

}
