package com.yf.cases;

import com.yf.BaseTest;

public class Login extends BaseTest {
    public Login(){
        locate("Login");
        open();
        input("账号", "yufei");
        input("密码", "1qaz@2wsx");
        click("登录按钮");
   }


}

