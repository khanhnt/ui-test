package org.exoplatform.selenium.platform.gatein.functional.others;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationManagement;
import static org.exoplatform.selenium.TestLogger.*;

/**
 * 
 * @author thaopth
 * date: 26/09/2012
 */

public class GateIn_Others_Sitemap extends PlatformBase {
	
	ManageApplications app;
	ManageAccount account;
	NavigationToolbar navigation;
	PageManagement page;
	NavigationManagement naviManage;
	
	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		page = new PageManagement(driver);
		app = new ManageApplications(driver);
		naviManage = new NavigationManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.quit();
	}
	
	/* Case 01: Check site map portlet
	 * Import all applications
	 * Add page with site map portlet
	 * Check site map portlet
	 */
	@Test(groups={"later"})
	public void test01_CheckSiteMapPortlet () {
		String DATA_NODE_NAME = "Sitemap1";
		String DATA_LANGUAGE = "English";
		String DATA_CATEGORY_TITLE = "Navigation";
		Map<String, String> portletIds = new HashMap<String, String>();
		portletIds.put("Navigation/local._web.SiteMapPortlet", "");
		By SITEMAP_PORTLET = By.className("UIRowContainer");
		account.signIn("root", "gtn");
		navigation.goToApplicationRegistry();
		info("Import all apps");
		click(app.ELEMENT_IMPORT_APPLICATION);
		waitForConfirmation("This action will automatically create categories and import all the gadgets and portlets on it.");
		pause(2000);
		
		info("Add page");
		navigation.goToAddPageEditor();
		
		info("Add new page");
		page.addNewPageEditor(DATA_NODE_NAME, DATA_NODE_NAME, DATA_LANGUAGE, DATA_CATEGORY_TITLE, portletIds, true);
		waitForElementPresent(SITEMAP_PORTLET);
		
		info("Click on a page");
		click(By.xpath("//div[@class='ClearFix']/a[text()='Home']"));
		pause(1000);
		
		info("Verify home page is displayed");
		waitForElementPresent("//*[@id='composerInput']");
		
		//----Reset data-------
		
		info("Delete page");
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP,DATA_NODE_NAME);
		navigation.goToGroupSites();
		naviManage.deleteNode("Administration", "Application Registry", DATA_NODE_NAME, false);
		
	}
}