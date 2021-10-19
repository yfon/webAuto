package com.yf.cases;

import com.yf.utils.WebDriverUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class WebDriverTest {
    @Test
    public void webDriver(){
        WebDriver driver= WebDriverUtil.getDriver();
        driver.navigate().to("http://www.baidu.com/");
    }
}
