package com.example.demo.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Service
public class WebDriverService {

    // 静态变量存储 WebDriver 实例，确保在整个应用程序中只创建一个 WebDriver 实例
    private static WebDriver driver;

    // 返回 WebDriver 实例，如果已经创建过则返回现有实例
    public WebDriver getDriver() {
        if (driver == null) {
            // 创建 WebDriver 实例
            initializeDriver();
        }
        return driver;
    }

    // 初始化 WebDriver
    private void initializeDriver() {
        System.setProperty("webdriver.gecko.driver", "/Users/yangyida/Downloads/geckodriver");

        // 设置 Firefox 浏览器的路径
        FirefoxBinary firefoxBinary = new FirefoxBinary(new File("/Applications/Firefox.app/Contents/MacOS/firefox"));
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);  // 设置 Firefox 浏览器的二进制文件

        // 使用已有配置文件（如果需要）
        String userProfilePath = "/Users/yangyida/Library/Application Support/Firefox/Profiles/h5tw2jp0.default-release";
        FirefoxProfile profile = new FirefoxProfile(new File(userProfilePath));
        options.setProfile(profile);

        // 初始化 Firefox WebDriver
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.err.println("浏览器已启动，加载配置文件：" + userProfilePath);
    }

    // 退出 WebDriver 实例
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // 确保下次调用时会重新创建 WebDriver
        }
    }
}
