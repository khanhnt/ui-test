package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.manageportal;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PortalManagement;

import static org.exoplatform.selenium.TestLogger.info;

/**
 *@author VuNA2
 *@date: 18/09/2012
 **/
public class GateIn_PortalNavigation_DeletePortal extends PlatformBase{

	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	NavigationManagement naviManage;
	PortalManagement portal;
	public static final By ELEMENT_PORTAL_TOP_CONTAINER = By.id("PortalNavigationTopContainer");
	public static final By ELEMENT_HOME_LINK = By.xpath("//img[@alt='Home']");
	String portalName = "demoPortal"; 
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		portal = new PortalManagement(driver);
		driver.get(baseUrl);
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterTest(){
		driver.quit();
	}
	
	/*--Case 02 Portal\Portal Navigation\Delete
	 *  Delete portal by legal user
	 * --*/
	@Test(groups={"later"})
	public void test02_DeletePortalByLegalUser(){
		/*-- Data for test case --*/	
		String portalLocale = "English";
		String portalSkin = "Default"; 
		String portalSession = "On Demand";
		//boolean publicMode = true;
		Map<String, String> permission = new HashMap<String, String>();
	    permission.put("Platform/Administration", "member");  
	    String editGroupId = "Platform/Administration"; 
	    String editMembership = "manager" ;
		
	    info("-- Case 02: Delete portal by legal user --");
		account.signIn("john", "gtn");
		
		info("-- Go to the portal sites --");
		navigation.goToPortalSites();
		
		info("-- Step 1: create a new portal --");
		portal.addNewPortal(portalName, portalLocale, portalSkin,portalSession, 
						false, permission, editGroupId, editMembership);
		account.signOut();
		
		info("-- Step 3: Delete portal --");
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		info("-- Step 2: Check existing portals use list");
		waitForTextPresent(portalName);
		hoverMySites();
		
		
		portal.deletePortal(portalName);
//		waitForTextNotPresent(portalName);
		
		info("-- End of test case 02: SignOut --");
		account.signOut();
	}
	
	/*-- Auxiliary functions --*/
	public void hoverMySites(){
		//will update later
	    waitForElementPresent(By.linkText(portalName));
	}
}