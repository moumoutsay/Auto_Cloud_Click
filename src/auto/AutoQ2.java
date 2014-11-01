package auto;
import java.io.File;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;


public class AutoQ2 {
	private static WebDriver driver;
	private static String baseUrl;

	public static void main(String[] args) {
		// setup env
		final FirefoxProfile profile = 
				  new FirefoxProfile(new File("/Users/moumoutsay/Documents/course/Cloud/Profile"));
		driver = new FirefoxDriver(profile);
	    baseUrl = "https://15619project.org/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    
		int i = 0;
		while (true) {
			// control how many time we want to try 
			if (i++ > 100) {
				break;
			}
			String curStatus = null;
			
			// go to submission page 
			try {
				driver.get(baseUrl + "submissions/1/3/");
				WebElement element =
						driver.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[17]"));
				
				curStatus = element.getText();
				System.out.println(curStatus);
			}	catch (Exception e) { }
			
			// check if can submit not 
			if (curStatus.equals("DONE") || curStatus.equals("FAILED") || curStatus.equals("CANCELED")) {
				System.out.println("Can submit now");
			    driver.get(baseUrl + "/scoreboard/1/3/");
			    driver.findElement(By.id("nav_submit")).click();
			    mySleep(300);
			    //driver.findElement(By.id("nav_submit_")).click();
			    driver.findElement(By.linkText("Query 2")).click();
			    mySleep(300);
			    new Select(driver.findElement(By.id("DbType"))).selectByVisibleText("MySQL");
			    mySleep(300);
			    driver.findElement(By.id("URL")).clear();
			    driver.findElement(By.id("URL")).sendKeys("ec2-54-172-82-31.compute-1.amazonaws.com");
			    mySleep(300);
			    new Select(driver.findElement(By.id("Duration"))).selectByVisibleText("1");
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
					if ( timeToSleep < 10) timeToSleep = 10;
 				} 
				System.out.println("time remain" + timeToSleep);
				mySleep(1000*timeToSleep);
			}
		}
		mySleep(10000000);
 		driver.close();
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
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
}