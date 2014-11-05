//package com.example.tests;
package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Cloud_Example1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @Before
  public void setUp() throws Exception {
	final FirefoxProfile profile = 
			  new FirefoxProfile(new File("/Users/moumoutsay/Documents/course/Cloud/Profile"));
	driver = new FirefoxDriver(profile);
    
    baseUrl = "https://15619project.org/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testNew() throws Exception {
      
    driver.get(baseUrl + "home/");
    assertEquals("15-319/619 Cloud Computing", driver.getTitle());
    Thread.sleep(1000);
    
    driver.findElement(By.linkText("15619 Project")).click();
    driver.findElement(By.linkText("Phase 2")).click();
    
    Thread.sleep(1000);
    assertEquals("15-319/619 Cloud Computing", driver.getTitle());
    driver.findElement(By.id("nav_submissions")).click();
    Thread.sleep(1000);
    assertEquals("15-319/619 Cloud Computing", driver.getTitle());
    driver.findElement(By.id("nav_summary")).click();
    Thread.sleep(1000);
    assertEquals("15-319/619 Cloud Computing", driver.getTitle());
    driver.findElement(By.id("nav_submissions")).click();
    assertEquals("15-319/619 Cloud Computing", driver.getTitle());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
