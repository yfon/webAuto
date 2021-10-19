package com.yf;

import com.alibaba.fastjson.JSONObject;
import com.yf.annotations.DataSource;
import com.yf.annotations.LocatorSource;
import com.yf.beans.Locator;
import com.yf.utils.LocatorUtil;
import com.yf.utils.WebDriverUtil;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    public static WebDriver driver;
    private static String pageName;
    /** 浏览器导航栏对象，封装导航栏方法用 */
    private static WebDriver.Navigation navigation;
    private static Map data;
    private static JSONObject jsonObject;
    private static  String json;
    private static Actions actions;
    @DataProvider(name="getData")
    public static Object[][] getData(Method method){
        DataSource dataSource = null;

        Yaml yaml = new Yaml();
        if(method.isAnnotationPresent(DataSource.class )){
            dataSource=method.getAnnotation(DataSource.class);
        }else{
            log.error("未指定@DataSource注解却初始化了dataProvider");
        }
        try (InputStream in = BaseTest.class.getResourceAsStream(dataSource.filePath());){
            data = yaml.load(in);
          jsonObject = JSONObject.parseObject(JSONObject.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[][]{{jsonObject}};


//
//        DataSource dataSource = null;
//        Iterator<Object[]> object =null;
//        //Object[][] object=null;
//        if(method.isAnnotationPresent(DataSource.class )){
//            dataSource=method.getAnnotation(DataSource.class);
//        }else{
//            log.error("未指定@DataSource注解却初始化了dataProvider");
//        }
//        if(DataSourceType.YAML.equals(dataSource.dataSourceType())){
//            object= YamlReaderUtil.getYamlData(dataSource.filePath());
//        }
//        return  object;
    }
//    @AfterSuite
//    protected void afterSuite(){}
    @BeforeClass
    protected void beforeClass(){
        driver = WebDriverUtil.getDriver();
        // 窗口最大化
        driver.manage().window().maximize();
        navigation = driver.navigate();
        actions=new Actions(driver);
    }
    @AfterClass
    protected  void afterClass(){
        actions.release().perform();
//        driver.quit();
    }


    protected void locate(String pageName){
        this.pageName=pageName;
        System.out.println("------------------");
        System.out.println(pageName);
    }

    /**
     * 浏览器操作方法
     */
    protected void open(){
        String url= LocatorUtil.getURL(new GetLocatorFilePath().invok().getFilePath(),pageName);
        navigation.to(url);
    }
    protected void refresh(){
        driver.navigate().refresh();
    }
    protected void back(){
        driver.navigate().back();
    }
    protected void forward(){
        driver.navigate().forward();
    }
    protected void close(){
        driver.close();
    }
    /**
     * 线程等待 sleep()
     * 隐式等待 implicitlyWait()
     * @param millis
     */
    protected void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void implicitlyWait(Long time){
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }
    /**
     *  元素操作
     */
    protected void click(String locatorName){
        getWebElement(locatorName).click();
    }
    protected void input(String locatorName,String inputString){
        getWebElement(locatorName).sendKeys(inputString);
    }
    protected void clear(String locatorName){
        getWebElement(locatorName).clear();
    }

    /**
     * ifram操作
     * @param locatorName
     */
    protected void switchToIframe(String locatorName){
        WebElement ifram=getWebElement(locatorName);
        driver.switchTo().frame(ifram);
    }
    protected void exitToIframe(){
        driver.switchTo().defaultContent();
    }
    /**
     *
     */
    protected void switchWindow(){
        String oneWindow=driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for(String handle:handles){
            if(oneWindow.equals(handle)==false){
                driver.switchTo().window(handle);
            }
        }
    }
    /**
     * 鼠标操作
     */
    protected void rightClick(String locatorName){
        actions.contextClick(getWebElement(locatorName)).perform();
    }
    protected void doubleClick(String locatorName){
        actions.doubleClick(getWebElement(locatorName)).perform();
         }
    /**
     *  断言操作
     */
    protected void assertTextContain(String expected, String actual){
            Assert.assertTrue(expected.contains(actual),expected+" not contains "+actual);
    }
    protected void asserEqual(T expected, T actual){
        Assert.assertEquals(expected, actual);
    }

    /**
     * 内部工具类，用来获取LocatorSource注解中的locator filePath
     * */

    private class GetLocatorFilePath{
        private String filePath;
        ITestNGMethod iTestNGMethod = Reporter.getCurrentTestResult().getMethod();
        Method method = iTestNGMethod.getMethod();
        LocatorSource locatorSource = method.getAnnotation(LocatorSource.class);

        /**
         * 调用
         * */
        public GetLocatorFilePath invok() {
            filePath = locatorSource.filePath();
            return this;
        }
        /**
         * 获取LocatorSource注解中的locator filePath
         * */
        public String getFilePath() {
            return filePath;
        }

    }

    /**
     * 获取locator属性
     */
    protected WebElement getWebElement(String locatorName) {
        String filePath = new GetLocatorFilePath().invok().getFilePath();
        return getWebElement(filePath, locatorName);
    }

    protected WebElement getWebElement(String filePath,String name){
        Locator locator=LocatorUtil.getLocator(filePath,pageName,name);
        String by=locator.getBy();
        String value=locator.getValue();
        int timeOut=locator.getTimeOut();

        try {
            Method byMethod= By.class.getDeclaredMethod(by,String.class);
            try {
                By byClass=(By)byMethod.invoke(null, value);
                WebDriverWait webDriverWait=new WebDriverWait(driver, timeOut);
                //显示等待
                WebElement webElement=webDriverWait.until(new ExpectedCondition<WebElement>() {
                    @NullableDecl
                    @Override
                    public WebElement apply(@NullableDecl WebDriver webDriver) {
                        return webDriver.findElement(byClass);
                    }
                });
                return webElement;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        return null;
    }

}
