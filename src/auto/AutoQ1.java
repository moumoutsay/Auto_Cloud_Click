package auto;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;


public class AutoQ1 {
	private static WebDriver driver;
	private static final String BASE_URL = "https://15619project.org/";
	private static final String DNS_NAME = "ec2-54-173-158-25.compute-1.amazonaws.com";
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
				System.out.println("Can submit now");
			    driver.get(BASE_URL + SCORE_URI);
			    driver.findElement(By.id("nav_submit")).click();
			    driver.findElement(By.id("nav_submit_")).click();
			    driver.findElement(By.id("URL")).sendKeys(DNS_NAME);
			    new Select(driver.findElement(By.id("Duration"))).selectByVisibleText("1"); // 1 min
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
					if (timeToSleep < 10) { timeToSleep = 10; }
 				} 
				System.out.println("time remain" + timeToSleep);
				mySleep(1000*timeToSleep);
			}
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
