package com.yf.annotations;


import java.lang.annotation.*;

/**
 * 配置locator的来源
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
public @interface LocatorSource {

    String filePath();
}
