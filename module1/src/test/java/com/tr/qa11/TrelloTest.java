package com.tr.qa11;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrelloTest {
    ChromeDriver wd;
    
    @BeforeMethod
    public void setUp() throws Exception {
        wd = new ChromeDriver();
        //wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        openSite();
    }

    @DataProvider
    public Iterator<Object[]> invalidLogin() throws IOException {
        List<Object[]>list = new ArrayList<Object[]>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/loginNegative.csv")));
        String line = reader.readLine();
        while(line!=null){
            String[] split = line.split(";");
            list.add(new Object[]{new UserData().setEmail(split[0]).setPwd(split[1])});

            line = reader.readLine();
        }
        return list.iterator();
    }
    @DataProvider
    public Iterator<Object[]> validLogin() throws IOException {
        List<Object[]>list = new ArrayList<Object[]>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/login.csv")));
        String line = reader.readLine();
        while(line!=null){
            String[] split = line.split(";");
            list.add(new Object[]{new UserData().setEmail(split[0]).setPwd(split[1])});

            line = reader.readLine();
        }
        return list.iterator();
    }
    
    @Test(dataProvider="validLogin", enabled = false)
    public void TrelloTest(UserData user) {


        wd.findElement(By.xpath(".//*[@href='/login?returnUrl=%2F']")).click();
        login(user);
        submitLogin();
        Assert.assertTrue(isElementPresent(By.cssSelector("span.member-initials")));
//        enterEmail();
//        enterPwd();

        wd.findElement(By.cssSelector("a.board-tile.mod-add")).click();
        fillBoardForm();
        wd.findElement(By.xpath("//div[5]/div/div[2]/div/div/div/form/input[3]")).click();
    }

    @Test(dataProvider="invalidLogin")
    public void TrelloTestNegative(UserData user) {


        wd.findElement(By.xpath(".//*[@href='/login?returnUrl=%2F']")).click();
        login(user);
        submitLogin();
        Assert.assertTrue(isElementPresent(By.cssSelector("div#error p.error-message")));
//        enterEmail();
//        enterPwd();


    }

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void login(UserData userLogin) {
        wd.findElement(By.id("user")).click();
        wd.findElement(By.id("user")).clear();
        wd.findElement(By.id("user")).sendKeys(userLogin.getEmail());
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).clear();
        wd.findElement(By.id("password")).sendKeys(userLogin.getPwd());
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
