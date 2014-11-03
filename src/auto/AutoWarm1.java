package auto;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;


public class AutoWarm1 {
	private static WebDriver driver;
	private static String baseUrl;
	public static final String DNS_NAME = "MySQLq2-1162405540.us-east-1.elb.amazonaws.com";
	
	public static void main(String[] args) {
		// setup env
		final FirefoxProfile profile = 
				  new FirefoxProfile(new File("/Users/moumoutsay/Documents/course/Cloud/Profile"));
		driver = new FirefoxDriver(profile);
	    baseUrl = "http://ec2-54-172-235-249.compute-1.amazonaws.com/q2?userid=1728883435&tweet_time=2014-03-21+15:10:30";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		while (true) {
			String curStatus = null;
			
			// go to submission page 
			try {
				driver.get(baseUrl);
				mySleep(5);
			}	catch (Exception e) { }
	
		}
		//mySleep(10000000);
 		//driver.close();
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
}
