package com.example.demo.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * loginForDouYin
     * 模拟账号密码登录抖音
     * @return
     */
    @GetMapping("/loginForDouYin")
    public String loginForDouYin() {
        // 设置 GeckoDriver 路径（请将此路径替换为你的 geckodriver 绝对路径）
        System.setProperty("webdriver.gecko.driver", "/Users/yangyida/Downloads/geckodriver");

        // 初始化 FirefoxOptions（可以选择无头模式）
        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("-private"); // 启用隐私模式（无痕模式）
//        options.addArguments("--disable-application-cache"); // 禁用应用缓存

        // 如果需要指定 Firefox 的二进制路径（例如，如果安装在非默认位置）
        options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");

        // 创建 FirefoxDriver 实例
        WebDriver driver = new FirefoxDriver(options);
        System.err.println("创建成功");

        try {
            // 打开抖音登录页面
            driver.get("https://www.douyin.com/?recommend=1");
            System.err.println("打开成功");

            // 设置隐式等待时间
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.err.println("等待加载元素进程完成30秒");

            // 点击登录按钮
            WebElement loginButton = driver.findElement(By.xpath("//button//p[contains(text(), '登录')]"));
            loginButton.click();
            System.err.println("进入登录页面");

            WebElement passwordTab = driver.findElement(By.xpath("//li[@aria-label='密码登录']"));
            passwordTab.click();
            System.err.println("进去");

            // 输入手机号
            WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='手机号']"));
            phoneInput.sendKeys("13418962569");

// 输入密码
            WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='请输入密码']"));  // 假设有一个类似的密码输入框
            passwordInput.sendKeys("Bryan20020225..");

// 点击登录按钮
            WebElement loginButton1 = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton1.click();


            Thread.sleep(500000);
        } catch (Exception e) {
            e.printStackTrace();
            return "登录过程出错：" + e.getMessage();
        }
            driver.quit();


        return "";
    }
}
