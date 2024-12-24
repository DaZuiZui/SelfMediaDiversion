package com.example.demo.controller;

import com.example.demo.service.WebDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Set;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private WebDriverService webDriverService;

    @PostMapping("/me")
    public void gome() {
        WebDriver driver = webDriverService.getDriver();
        driver.get("https://www.douyin.com/user/self?from_tab_name=main&showTab=like");
    }

    @PostMapping("/gofriend")
    public void gofriend() {
        WebDriver driver = webDriverService.getDriver();
        driver.get("https://www.douyin.com/friend");
    }

    @GetMapping("/loginForDouYin")
    public String loginForDouYin() {
        WebDriver driver = webDriverService.getDriver();
        System.err.println(driver);
        try {
            // 打开目标页面
            driver.get("https://www.douyin.com");

            // 加载 Cookie（如果需要）
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cookies.dat"))) {
                Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
                for (Cookie cookie : cookies) {
                    driver.manage().addCookie(cookie);
                }
                driver.navigate().refresh();
            } catch (Exception e) {
                System.err.println("未加载 Cookie：" + e.getMessage());
            }

            // 登录逻辑（如果未登录）
            try {
                WebElement loginButton = driver.findElement(By.xpath("//button//p[contains(text(), '登录')]"));
                loginButton.click();
                Thread.sleep(3000); // 等待 3 秒
                WebElement passwordTab = driver.findElement(By.xpath("//li[@aria-label='密码登录']"));
                passwordTab.click();
                Thread.sleep(3000); // 等待 3 秒
                WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='手机号']"));
                phoneInput.sendKeys("13418962569");


                WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='请输入密码']"));
                passwordInput.sendKeys("Bryan20020225..");
                WebElement loginButton1 = driver.findElement(By.xpath("//button[@type='submit']"));
                loginButton1.click();

                //todo 如果需要验证码
                System.err.println("可以保存了");

            } catch (Exception e) {
                System.err.println("已登录，无需重复登录：" + e.getMessage());
            }

            // 保存 Cookie（如果需要）
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cookies.dat"))) {
                oos.writeObject(driver.manage().getCookies());
            }

            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            return "登录过程出错：" + e.getMessage();
        }

        return "登录完成！";
    }

    @GetMapping("/quitDriver")
    public String quitDriver() {
        webDriverService.quitDriver();
        return "浏览器已退出";
    }

}
