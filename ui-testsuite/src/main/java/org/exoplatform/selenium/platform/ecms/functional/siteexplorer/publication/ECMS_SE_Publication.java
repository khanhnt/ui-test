package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publication;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: HaKT
 * @date: 10/09/2013
 */
public class ECMS_SE_Publication extends PlatformBase{

	ManageAccount magAc;
	NavigationToolbar navToolBar;
	ECMainFunction ecMain;
	SitesExplorer sitesEx;
	ActionBar actBar;
	ContentTemplate conTemp;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", "gtn"); 
		ecMain = new ECMainFunction(driver);
		sitesEx = new SitesExplorer(driver);
		actBar = new ActionBar(driver);
		conTemp =  new ContentTemplate(driver);
	}
	
	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	public static By ELEMENT_PUBLICATION = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsManagePublications']");
	public static By DRIVER_SITES_MANAGEMENT = By.xpath("//*[@class='driveLabel' and @data-original-title='Sites Management']");
	
	/**CaseId: 66269 -> Publish content when only set time in From field
	 * Create new content on site explorer
	 * Click Publication on action bar
	 * Choose staged
	 * Move to scheduled tab
	 * Choose Only From field
	 * Save
	 */
	@Test
	public void test01_PublishContentWhenOnlySetTimeInFromField(){
		
		String File_Name = "File_Case_66269";
		String File_Content = "Content of File: Publish content when only set time in From field";
		
		By VERIFY_DRAFT_IS_CHECKED = By.xpath("//li[@class='currentStatus']//p[contains(text(),'Draft')]/..//a[@class='node']");
		
		navToolBar.goToSiteExplorer();
		
		actBar.chooseDrive(DRIVER_SITES_MANAGEMENT);
		
		actBar.goToAddNewContent();
		
		conTemp.createNewFile(File_Name, File_Content, null);
		
		click(ELEMENT_PUBLICATION);
		
		waitForAndGetElement(VERIFY_DRAFT_IS_CHECKED);
		
		
		
	}
	
	
}
