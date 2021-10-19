package com.yf.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Page implements Serializable {
    private String pageName;
    private String url;
    private Map<String,Locator> locatorMap=new HashMap<>();
    public Page(){};
    public Page(String pageName,String url,Map<String,Locator> locatorMap){
        this.pageName=pageName;
        this.url=url;
        this.locatorMap=locatorMap;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLocatorMap(Map<String, Locator> locatorMap) {
        this.locatorMap = locatorMap;
    }

    public String getPageName() {
        return pageName;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Locator> getLocatorMap() {
        return locatorMap;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageName='" + pageName + '\'' +
                ", url='" + url + '\'' +
                ", locatorMap=" + locatorMap +
                '}';
    }
}
