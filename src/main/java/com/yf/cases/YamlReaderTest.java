package com.yf.cases;

import com.yf.annotations.DataSource;
import org.testng.annotations.Test;

import java.util.Map;

public class YamlReaderTest {
    @Test
    @DataSource(filePath = "/data/data.yml")
    public void test(Map map){
        System.out.println(map.toString());
    }


}
