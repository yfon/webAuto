package com.yf.cases;

import com.alibaba.fastjson.JSONObject;
import com.yf.BaseTest;
import com.yf.annotations.DataSource;
import com.yf.annotations.LocatorSource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class role extends BaseTest {
    @BeforeClass
    @LocatorSource(filePath = "/locator/login.xml")
    private void before(){
        new Login();
    }
    @Test
    @LocatorSource(filePath = "/locator/role.xml")
    @DataSource(filePath = "/data/role.yml")
    public void createRole(JSONObject role){
        locate("role");
        open();
        click("创建角色");
        input("角色名", role.getString("rolename"));
        click("角色类型下拉框");
//        sleep(1000);
        click("角色类型");
        input("角色描述", role.getString("roledescription"));
        click("创建");
    }
}
