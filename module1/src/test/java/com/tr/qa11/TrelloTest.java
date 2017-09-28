package com.tr.qa11;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TrelloTest {
    ChromeDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new ChromeDriver();
        //wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void TrelloTest() {
        openSite();
        wd.findElement(By.xpath(".//*[@href='/login?returnUrl=%2F']")).click();
        enterEmail();
        enterPwd();
        submitLogin();
        wd.findElement(By.cssSelector("a.board-tile.mod-add")).click();
        fillBoardForm();
        wd.findElement(By.xpath("//div[5]/div/div[2]/div/div/div/form/input[3]")).click();
    }

    public void fillBoardForm() {
        wd.findElement(By.id("boardNewTitle")).click();
        wd.findElement(By.id("boardNewTitle")).clear();
        wd.findElement(By.id("boardNewTitle")).sendKeys("new1");
    }

    public void submitLogin() {
        wd.findElement(By.id("login")).click();
    }

    public void enterPwd() {
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).clear();
        wd.findElement(By.id("password")).sendKeys("Lena1975");
    }

    public void enterEmail() {
        wd.findElement(By.id("user")).click();
        wd.findElement(By.id("user")).clear();
        wd.findElement(By.id("user")).sendKeys("etarnovskaya@gmail.com");
    }

    public void openSite() {
        wd.get("https://trello.com/");
    }

    @AfterMethod
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
