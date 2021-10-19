package com.yf.listener;

import com.yf.BaseTest;
import com.yf.annotations.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class DataSourceListener implements IAnnotationTransformer {
    private static final Logger log = LoggerFactory.getLogger(DataSourceListener.class);

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        if (iTestAnnotation == null || method == null) {
            return;
        }
        setDataProvider(iTestAnnotation, method);
        if(iTestAnnotation.getDataProviderClass()==null){
            iTestAnnotation.setDataProviderClass(BaseTest.class);
        }else{
            iTestAnnotation.getDataProviderClass();
        }
    }

    private void setDataProvider(ITestAnnotation iTestAnnotation, Method method) {
        if (method.isAnnotationPresent(DataSource.class)) {
            if("".equals(iTestAnnotation.getDataProvider())){
                iTestAnnotation.setDataProvider("getData");
            }else{
                iTestAnnotation.getDataProvider();
            }

        } else if(!method.isAnnotationPresent(DataSource.class)&&!"".equals((iTestAnnotation.getDataProvider()))){
            log.info("not DataSource but has DataProvider");
        }
    }
}
