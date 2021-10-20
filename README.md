# webAuto
web页面自动化框架搭建
===
使用技术：Java+testng+allure，maven管理依赖包</br>
数据驱动：yaml文件</br>
testing.xml监听自定义注解，获取@DataProvider标识的数据源</br>

封装模式
----
采用po模式封装。</br>
page,locator两个bean定义界面元素，page包括：pagename：指定进入的page，url；页面访问地址。locator：name：指定定位的元素，by：定位的方式，value：定位的值，time：显示等待的时间</br>
locator.xml对界面元素进行封装</br>
<pages>
    <!-- 登录界面 -->
    <page pageName="Login" url="https://172.18.2.113:8443/ui-infra" defaultTimeOut="1">
        <!--<locate name="" by="" value="" timeOut=""/>-->
        <locator name="账号" by="id" value="username" timeOut="1"/>
        <locator name="密码" by="xpath" value="//*[@id=&quot;ccs-app&quot;]/div[1]/div/tempalte/div[3]/div/input" timeOut="3"/>
        <locator name="登录按钮" by="xpath" value="//*[@id=&quot;ccs-app&quot;]/div[1]/div/button" timeOut=""/>
    </page>
</pages>


