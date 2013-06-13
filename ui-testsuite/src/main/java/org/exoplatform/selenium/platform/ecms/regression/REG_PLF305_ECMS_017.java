package org.exoplatform.selenium.platform.ecms.regression;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.ecms.ActionBar.goToAddNewContent;
import static org.exoplatform.selenium.platform.ecms.ContentTemplate.createNewContentFolder;
import static org.exoplatform.selenium.platform.ecms.ContentTemplate.createNewFreeLayoutWebContent;
import static org.exoplatform.selenium.platform.ecms.SiteExplorer.chooseDrive;

import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class REG_PLF305_ECMS_017 extends EcmsBase {
	/*-- Data for these test cases --*/
	public String DATA_USER = "john";
	public String DATA_PASS = "gtn";
	By DRIVER_SITES_MANAGEMENT = By.xpath("//a[@class='DriveLabel' and @title = 'Sites Management']");
	
	@BeforeMethod
	public void beforeMethods(){
		info("\n\nBEGIN Test case REG_PLF305_ECMS_017 - Manage Publications \n\n");
		initSeleniumTest();
		driver.get(baseUrl);
		actions = new Actions(driver);
		info("Login ECMS with " + DATA_USER);
		loginEcms(DATA_USER, DATA_PASS);
	}
	
	@AfterMethod
	public void afterMethods(){
		info("Logout ECMS");
		info("\n\nEND Test case REG_PLF305_ECMS_017 \n\n");
		driver.quit();
		actions = null;
	}
	
	private String getTomorrowDate(){
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		String tomorrowAsString = dateFormat.format(tomorrow);

		return tomorrowAsString;		
	}
	
	/*
	 * Steps:
		- In Sites Explorer, create new Free Layout web content
		- Go to Manage Publications on Action Bar
		- Choose a 'To' date value in Scheduled group
		- Save => an Unknown Error message is thrown
	 */
	@Test
	public void test17_ManagePublications(){
		// Go to Sites Explorer
		info("\n=== Go Sites Explorer ===");
		goToSiteExplorer();
		chooseDrive(DRIVER_SITES_MANAGEMENT);
		waitForElementPresent(By.xpath("//div[contains(text(),'Sites Management')]"));
		
		// Go to acme folder
		info("\n=== Go to acme folder ===");
		goToNodeByPath("acme");
		pause(4000);
		
		// Create new folder REG_PLF305_ECMS_017 - OK
		info("\n=== Create new folder REG_PLF305_ECMS_017 ===");
		createNewContentFolder("REG_PLF305_ECMS_017", "regplf305ecms017");
		pause(1000);
		waitForElementPresent(By.xpath("//div[contains(text(),'REG_PLF305_ECMS_017')]"));
		
		// Go into new folder
		info("\n=== Go into new folder REG_PLF305_ECMS_017 ===");
		goToNodeByPath("REG_PLF305_ECMS_017");
		pause(4000);
		
		// Create new Free layout web content, named "FLWC1"
		info("\n=== Create new Free layout webcontent, named FLWC1 ===");
		goToAddNewContent();
		createNewFreeLayoutWebContent("FLWC1", "flwc1", "First content", "", "", "", "");
		pause(5000);
		
		// Click on Publications
		info("\n=== Click on Publications ===");
		waitForElementPresent(By.linkText("Publications"));
		mouseOver(By.linkText("Publications"),false);
		click(By.linkText("Publications"));
		
		// Choose To date 
		info("\n=== Choose To date ===");
		String TOMORROW_DATE = getTomorrowDate();		
		type(By.id("UIPublicationPanelEndDateInput"), TOMORROW_DATE, false);
		pause(2000);
		info("\n=== Click Save ===");
		save();
		
		// Check that there is no warning/unknown error
		waitForElementPresent(By.xpath("//div[@id='UITabContent']/p[contains(text(),'First content')]"));
		info("\n=== There is no warning/unknown error ==> OK !!! ===");
		pause(2000);
		
		// Delete data
		info("\n=== Return to acme folder ===");
		goToNodeByPath("acme");
		pause(4000);
		info("\n=== Delete data in REG_PLF305_ECMS_017 folder ===");
		deleteData(By.xpath("//div[contains(text(),'REG_PLF305_ECMS_017')]"));
	}

}
