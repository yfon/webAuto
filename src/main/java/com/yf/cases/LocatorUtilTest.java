package com.yf.cases;

import com.yf.beans.Locator;
import com.yf.utils.LocatorUtil;
import org.testng.annotations.Test;

public class LocatorUtilTest {
    @Test
    public void getUrl(){
        String url= LocatorUtil.getURL("/locator/locator.xml","Login");
        System.out.println(url);
    }
    @Test
    public void getLocator(){
        Locator locator=LocatorUtil.getLocator("/locator/locator.xml","Login","用户名");
        System.out.println(locator.toString());
    }

}
