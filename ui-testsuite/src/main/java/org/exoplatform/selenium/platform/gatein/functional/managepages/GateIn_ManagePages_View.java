package org.exoplatform.selenium.platform.gatein.functional.managepages;

import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.info;

/**
 *@author VuNA2
 *@date: 18/09/2012
 **/
public class GateIn_ManagePages_View extends PlatformBase{
	ManageAccount account;
	NavigationToolbar navigation;
	PageManagement page;
	/*-- Data for test case --*/
    By ELEMENT_PORTAL_TOP_CONTAINER = By.id("PortalNavigationTopContainer");
	String ELEMENT_CURRENT_NAVIGATION = "intranet";
	//update later
	By ELEMENT_SITE_MENU = null;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		page = new PageManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.quit();
	}
	
	/*--Case 02 Portal\Manage Pages\View
	 *  Check displaying page created in pages management
	 * --*/
	@Test(groups={"later"})
	public void test09_CheckDisplayingPageCreatedInPagesManagement(){
		PageType type = PageType.PORTAL; 
		String pageName = "demoPage"; 
		String pageTitle = "demoPage";
	   	 boolean publicMode = true;
	    	Map<String, String> permissions = null;   
	    	String groupId = "Platform/Administration"; 
	    	String membership = "manager";
	    
		info("-- Starting Case 09: Check displaying page created in pages management --");
		
		account.signIn("root", "gtn");
		
		info("-- Step 1: Show pages list --");
		navigation.goToManagePages();
		
		info("-- Step 2: Create new page for portal --");
		page.addNewPageAtManagePages(type, pageName, pageTitle, publicMode, 
				                permissions, groupId, membership);
		
		info("-- Step 3: View created page from navigation --");
		account.signOut();
		account.signIn("root", "gtn");
		goToMySitesAndVerifyPage(pageName);
		
		info("-- Step 4: View created page in Edit Page & Navigation --");
		navigation.goToPortalSites();
		editNavigation(ELEMENT_CURRENT_NAVIGATION);
		waitForTextNotPresent(pageName);
		save();
		waitForTextPresent(ELEMENT_CURRENT_NAVIGATION);
		
		info("-- Delete date after testing--");
		navigation.goToManagePages();
		page.deletePage(type, pageTitle);
		waitForTextNotPresent(pageTitle);
		
		info("-- End of test case 09: SignOut --");
		account.signOut();
	}
	
	/*-- Auxiliary functions --*/
	public boolean goToMySitesAndVerifyPage(String pageName){
		//update later
		mouseOver(ELEMENT_SITE_MENU, true);
	    	waitForTextPresent("Intranet");
	    	mouseOver(By.linkText("Intranet"), true);
	    	mouseOver(By.linkText("Home"), true);
	    	waitForTextNotPresent(pageName);
	    	mouseOver(By.linkText("Wiki"),true);
	    	waitForTextNotPresent(pageName);
	    	return false; 
	}
}