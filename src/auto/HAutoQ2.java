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

public class HAutoQ2 {
	private static WebDriver driver;

	private static final String BASE_URL = "https://15619project.org/";
	private static final String DNS_NAME = "ec2-54-174-14-215.compute-1.amazonaws.com";
	private static final String SUBMIT_URI = "submissions/1/7/"; // phase 3
	private static final String SCORE_URI = "scoreboard/1/7/";   // phase 3
	
	public static void main(String[] args) {
		// setup env
		final FirefoxProfile profile = 
				  new FirefoxProfile(new File("/Users/moumoutsay/Documents/course/Cloud/Profile"));
		driver = new FirefoxDriver(profile);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		while (true) {
			String curStatus = null;
			
			// go to submission page 
			try {
				driver.get(BASE_URL + SUBMIT_URI);
				WebElement element =
						driver.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[17]"));
				
				curStatus = element.getText();
				System.out.println(curStatus);
			}	catch (Exception e) { }

			// check if can submit not 
			if (curStatus.equals("DONE") || curStatus.equals("FAILED") || curStatus.equals("CANCELED")) {
				Integer runTime = getRunTime(driver);
				System.out.println("Can submit now");
			    driver.get(BASE_URL + SCORE_URI);
			    driver.findElement(By.id("nav_submit")).click();
			    mySleep(300);
			    //driver.findElement(By.id("nav_submit_")).click();
			    driver.findElement(By.linkText("Query 2")).click();
			    mySleep(300);
			    new Select(driver.findElement(By.id("DbType"))).selectByVisibleText("HBase");
			    mySleep(300);
			    driver.findElement(By.id("URL")).clear();
			    driver.findElement(By.id("URL")).sendKeys(DNS_NAME);
			    mySleep(300);
			    new Select(driver.findElement(By.id("Duration"))).selectByVisibleText(runTime.toString());
			    mySleep(300);
			    driver.findElement(By.name("proceed")).click();
			    mySleep(1000);
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
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
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
		if (rand.nextInt(50) > 15 ) {
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
	
	public static boolean isDouble(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
