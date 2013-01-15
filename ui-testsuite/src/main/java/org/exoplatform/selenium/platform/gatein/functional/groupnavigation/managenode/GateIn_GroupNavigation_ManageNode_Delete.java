package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.managenode;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 24/09/2012
 */

public class GateIn_GroupNavigation_ManageNode_Delete extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	//Define data
	public By ELEMENT_APPLICATION_REGIS = By.xpath("//a[@title='Application Registry']");
	public By NODE_ADDED = By.xpath("//a[text()='POR_GRNAVIGATION_25_06']");
	public String NODE_NAME = "POR_GRNAVIGATION_25_06";
	public String PAGE_SELECTOR_NAME = "POR_GRNAVIGATION_25_06_PAGE";
	
	@BeforeMethod
	public void beforeMethods() 
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		naviManage = new NavigationManagement(driver);
		page = new PageManagement(driver);
		groupNavi = new GroupNavigation(driver);
		account.signIn("root", "gtn");
	}
	
	//Delete node with deleting confirmation
	@Test()
	public void test01_DeleteNodeWithConfirmation()
	{
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		
		//Goto Setting > Portal > Group Sites
		navigation.goToGroupSites();
		
		//Click Edit Navigation of Employees
//		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		
		//Add Node for test
		groupNavi.addNodeForGroup("Administration", "Application Registry", false, NODE_NAME, true, languages, NODE_NAME, PAGE_SELECTOR_NAME, PAGE_SELECTOR_NAME, true, true);
		
		//Verify added data
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		click(ELEMENT_APPLICATION_REGIS);
		waitForElementPresent(NODE_ADDED);
		
		//Delete data
		naviManage.deleteNode("Administration", "Application Registry", NODE_NAME, false);
		
		//Verify page selector still exists
		navigation.goToManagePages();
		page.searchPageByTitle(PageType.GROUP, PAGE_SELECTOR_NAME);
		
		//Delete data
		page.deletePage(PageType.GROUP, PAGE_SELECTOR_NAME);
	}
	
	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}