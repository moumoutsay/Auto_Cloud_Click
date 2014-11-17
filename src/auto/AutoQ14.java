package auto;
import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;

/**
 * Submit job automatically for Q1 to Q2 of Phase 2 
 * Note: all are MySQL !!! 
*/
public class AutoQ14 {
	private static WebDriver driver;
	private static String baseUrl;
	public static final String DNS_NAME = "myLB-511064448.us-east-1.elb.amazonaws.com";

	public static void main(String[] args) {
		// setup env
		final FirefoxProfile profile = 
				  new FirefoxProfile(new File("/Users/moumoutsay/Documents/course/Cloud/Profile"));
		driver = new FirefoxDriver(profile);
	    baseUrl = "https://15619project.org/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	    Random rand = new Random();
	    int myRand = rand.nextInt(4) + 1;
		while (true) {
			String curStatus = null;
			
			// go to submission page 
			try {
				driver.get(baseUrl + "submissions/1/7/");
				WebElement element =
						driver.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[17]"));
				
				curStatus = element.getText();
				System.out.println(curStatus);
			}	catch (Exception e) { }

			// check if can submit not 
			if (curStatus.equals("DONE") || curStatus.equals("FAILED") || curStatus.equals("CANCELED")) {
				switch (myRand) {
				case 1:
					submitQ1(driver);
					break;
				case 2:
					submitQ2(driver);
					break;
				case 3:
					submitQ3(driver);
					break;
				case 4:
					submitQ4(driver);
					break;
				}
//				if (myRand++ == 4) {
//					myRand = 1; // wrap around
//				}
				myRand = rand.nextInt(4) + 1;
			} else {  // not not submit, check the sleep time 
				int timeToSleep = 1;
				try {
					WebElement element =
							driver.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[15]"));
					curStatus = element.getText();
				} catch (Exception e) {}
				if (isNumeric(curStatus)) {
					timeToSleep = Integer.parseInt(curStatus);
					if ( timeToSleep < 3) timeToSleep = 3;
					if ( timeToSleep > 100) timeToSleep = 50;
 				} 
				System.out.println("time remain" + timeToSleep);
				mySleep(1000*timeToSleep);
			}
		}
//		mySleep(10000000);
// 		driver.close();
	}
	
	public static boolean isNumeric(String str) {
	  try {
	    Integer.parseInt(str);  
	  } catch(NumberFormatException nfe) {
	    return false;  
	  }  
	  return true;  
	}
	
	public static void mySleep(int timeToSleep) {
		try {
			Thread.sleep(timeToSleep);
		} catch ( Exception e) {}
	}
	
	public static Integer getRunTime(WebDriver inDriver) {
		String score = null;
		Random rand = new Random();
		if (rand.nextInt(50) > 10 ) {  // this is for live test, so we only care 1 min
			return 1;
		}
		
		try {
			WebElement element =
				driver.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[13]"));
			score = element.getText();
		} catch ( Exception e) {}
		if (isDouble(score)) {
			System.out.println("cur score"  + score);
			double realVal = Double.parseDouble(score);
			if (realVal > 90.0) {
				System.out.println("return 10"  + realVal);
				return 10;
			}
			if (realVal > 70.0) {
				System.out.println("return 5"  + realVal);
				return 5;
			}
			if (realVal > 40.0) {
				System.out.println("return 3"  + realVal);
				return 3;
			}
		} 
		return 1;
	}
	
	public static boolean isDouble(String str) {  
	  try {
	    Double.parseDouble(str);  
	  } catch(NumberFormatException nfe) {  
	    return false;  
	  }
	  return true;  
	}

	public static void submitQ1(WebDriver inDriver) {
		System.out.println("Can submit now");
	    inDriver.get(baseUrl + "/scoreboard/1/7/");
	    inDriver.findElement(By.id("nav_submit")).click();
	    inDriver.findElement(By.id("nav_submit_")).click();
	    inDriver.findElement(By.id("URL")).sendKeys(DNS_NAME);
	    new Select(inDriver.findElement(By.id("Duration"))).selectByVisibleText("1"); // 1 min
	    inDriver.findElement(By.name("proceed")).click();
	    mySleep(1000);
	}

	public static void submitQ2(WebDriver inDriver) {
		Integer runTime = getRunTime(inDriver);
		System.out.println("Can submit now");
	    inDriver.get(baseUrl + "/scoreboard/1/7/");
	    inDriver.findElement(By.id("nav_submit")).click();
	    mySleep(300);
	    inDriver.findElement(By.linkText("Query 2")).click();
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("DbType"))).selectByVisibleText("MySQL");
	    mySleep(300);
	    inDriver.findElement(By.id("URL")).clear();
	    inDriver.findElement(By.id("URL")).sendKeys(DNS_NAME);
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("Duration"))).selectByVisibleText(runTime.toString());
	    mySleep(300);
	    inDriver.findElement(By.name("proceed")).click();
	    mySleep(1000);
	}
	
	public static void submitQ3(WebDriver inDriver) {
		Integer runTime = getRunTime(inDriver);
		System.out.println("Can submit now");
	    inDriver.get(baseUrl + "/scoreboard/1/7/");
	    inDriver.findElement(By.id("nav_submit")).click();
	    mySleep(300);
	    inDriver.findElement(By.linkText("Query 3")).click();
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("DbType"))).selectByVisibleText("MySQL");
	    mySleep(300);
	    inDriver.findElement(By.id("URL")).clear();
	    inDriver.findElement(By.id("URL")).sendKeys(DNS_NAME);
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("Duration"))).selectByVisibleText(runTime.toString());
	    mySleep(300);
	    inDriver.findElement(By.name("proceed")).click();
	    mySleep(1000);
	}
	public static void submitQ4(WebDriver inDriver) {
		Integer runTime = getRunTime(inDriver);
		System.out.println("Can submit now");
	    inDriver.get(baseUrl + "/scoreboard/1/7/");
	    inDriver.findElement(By.id("nav_submit")).click();
	    mySleep(300);
	    inDriver.findElement(By.linkText("Query 4")).click();
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("DbType"))).selectByVisibleText("MySQL");
	    mySleep(300);
	    inDriver.findElement(By.id("URL")).clear();
	    inDriver.findElement(By.id("URL")).sendKeys(DNS_NAME);
	    mySleep(300);
	    new Select(inDriver.findElement(By.id("Duration"))).selectByVisibleText(runTime.toString());
	    mySleep(300);
	    inDriver.findElement(By.name("proceed")).click();
	    mySleep(1000);
	}
}
