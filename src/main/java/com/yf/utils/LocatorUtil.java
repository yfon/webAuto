package com.yf.utils;

import com.yf.beans.Locator;
import com.yf.beans.Page;
import org.apache.commons.collections.map.HashedMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocatorUtil {
    private static final Logger log = LoggerFactory.getLogger(LocatorUtil.class);
    private static String filePath;
    private static Map<String, Map<String, Page>> pageMaps = new HashMap<>();
    public LocatorUtil(){}
    public LocatorUtil(String filePath){
        this.filePath=filePath;
    }
    private static void initXML(){
        if(pageMaps.get(filePath)==null){
            loadXML();
        }
    }

    /**
     * 解析locator文件
     */
    private static void loadXML(){
        SAXReader saxReader=new SAXReader();
        Document document=null;
        try {
            document=saxReader.read(LocatorUtil.class.getResourceAsStream(filePath));
        }catch (DocumentException e) {
            log.error("not found locator file");
        }
        Element rootElement=document.getRootElement();
        List<Element> pageElements=rootElement.elements("page");
        Map<String,Page> pageMap=new HashMap<>();
        for(Element pageElement:pageElements){
            String pageName=pageElement.attributeValue("pageName");
            String url=pageElement.attributeValue("url");
            String defaultTimeOut=pageElement.attributeValue("defaultTimeOut");
            List<Element> locatorElements=pageElement.elements("locator");
            Map<String, Locator> locatorMap = new HashedMap();
            for(Element locatorelement:locatorElements){
                String name=locatorelement.attributeValue("name");
                String by=locatorelement.attributeValue("by");
                String value=locatorelement.attributeValue("value");
                String timeOut=locatorelement.attributeValue("timeOut");
                if(timeOut==null||"".equals(timeOut)){
                    timeOut=defaultTimeOut;
                }
                locatorMap.put(name,new Locator(name,by,value,Integer.parseInt(timeOut)));
            }
            pageMap.put(pageName, new Page(pageName,url,locatorMap));
        }
        pageMaps.put(filePath,pageMap);


    }
    public static String getURL(String filePath,String pageName){
        new LocatorUtil(filePath);
        initXML();
        String url=LocatorUtil.pageMaps.get(filePath).get(pageName).getUrl();
        return url;
    }
    public static Locator getLocator(String filePath,String pageName,String name){
        new LocatorUtil(filePath);
        initXML();
        Locator locator=LocatorUtil.pageMaps.get(filePath).get(pageName).getLocatorMap().get(name);
        return locator;
    }



}
