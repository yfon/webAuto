package com.yf.annotations;


import com.yf.utils.DataSourceType;

import java.lang.annotation.*;

/**
 * 配置文件类型，数据来源的注解
 * */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {

    DataSourceType dataSourceType() default DataSourceType.YAML;
    String filePath() default "";


}

