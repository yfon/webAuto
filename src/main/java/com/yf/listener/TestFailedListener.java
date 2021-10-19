package com.yf.listener;

import com.yf.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestFailedListener extends TestListenerAdapter {
    public void onTestFailure(ITestResult iTestResult) {
        screenShot();
    }
    @Attachment(value = "Page screenshot",type = "image/png")
    public byte[] screenShot() {
        return ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.BYTES);
    }
}
