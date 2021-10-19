package com.yf.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class WebDriverUtil {

    private static final Logger log= LoggerFactory.getLogger(WebDriverUtil.class);
    private static Class clazz;
    private static Object obj ;
    public static WebDriver getDriver(){
        Document document=null;
        String driverName=null;
        Element driverElement=null;
        SAXReader reader=new SAXReader();
        try{
            document=reader.read(WebDriverUtil.class.getResourceAsStream("/driver.xml"));
        }catch (DocumentException e){
            log.error("read driver.xml failed",e);
        }
        Element rootDocument=document.getRootElement();
        int driverIndex=Integer.parseInt(rootDocument.attributeValue("driverIndex"));
        List<Element> elements=rootDocument.elements("name");
        for(Element element:elements){
            if(driverIndex==Integer.parseInt(element.attributeValue("index"))){
                driverName=element.attributeValue("value");
                driverElement=element;

            }
        }
        Element propertiesElement=driverElement.element("properties");
        List<Element> propertyElements=propertiesElement.elements("property");
        for(Element propertyElement:propertyElements){
            //设置系统参数，必须
            System.setProperty(propertyElement.attributeValue("name"), formatPath(propertyElement.attributeValue("value")));
            log.info(propertyElement.attributeValue("name"), propertyElement.attributeValue("value"));

        }

        //设置chrome特有属性
        Element optionsElement=driverElement.element("options");
        List<Element> optionElements=optionsElement.elements("option");
        if(optionElements.size()!=0){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--test-type", "--ignore-certificate-errors");
            for (Element optionElement:optionElements){
                if("binary".equals(optionElement.attributeValue("type"))){
                    String binary = formatPath(optionElement.getStringValue());
                    chromeOptions.setBinary(binary);
                }
            }
           WebDriver driver = new ChromeDriver(chromeOptions);
           return driver;
        }

        try {
            clazz=Class.forName(driverName);
            obj= clazz.newInstance();
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            log.error("not found class",e);
        }
        return (WebDriver)obj;
    }
public static String formatPath(String path){
        if(path.contains("{default.driver.path}")){
            path=path.replace("{default.driver.path}", "src/main/resources");
        }
        return  path;
}

}
