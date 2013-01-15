package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.othernodeaction;

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
 * @author NhungVT
 * @date: 24/09/2012	
 */

public class GateIn_GroupNavigation_OtherNodeAction_CutPaste extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	//Define data
	public By ADMINISTRATION_LINK = By.xpath("//div[@class='node']//a[@title='Administration']");
	public By ELEMENT_APPLICATION_REGIS = By.xpath("//a[@title='Application Registry']");
	public By IDE_LINK = By.xpath("//a[@title='IDE']");
	public By CHILD_NODE = By.xpath("(//a[contains(text(),'POR_GRNAVIGATION_25_05_002')])");
	public By CLOSE_NAVIGATION_ICON = By.xpath("//a[contains(@title,'Close Window')]");
	public String NODE_NAME = "POR_GRNAVIGATION_25_05_002";
	public By EXECUTIVE_EDIT_NAVI_LINK = By.xpath("//td/div[text()='Executive Board']/ancestor::tr/td/a[text()='Edit Navigation']");
	public By ELEMENT_NEW_STAFF = By.linkText("New Staff");
	public By ELEMENT_ADMIN_EDIT_NAVI = null;

	//Product Messages

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		naviManage = new NavigationManagement(driver);
		page = new PageManagement(driver);
		groupNavi = new GroupNavigation(driver);
		account.signIn("john", "gtn");
		ELEMENT_ADMIN_EDIT_NAVI = By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
	}
	
	//Cut/Paste node to same place
	@Test()
	public void test01_CutPasteNodeInSamePlace()
	{
		//Goto Setting > Portal > Group Sites
		navigation.goToGroupSites();
		
		//Click Edit navigation of Administration 
		click(ELEMENT_ADMIN_EDIT_NAVI);
		
		//Copy Node Portal Administration 
		cutNode(ADMINISTRATION_LINK);
		
		//Paste to Portal Administration
		pasteNode(ADMINISTRATION_LINK);
		
		//Verify message confirmation
		waitForMessage(naviManage.MSG_SAME_SOURCE);
		click(ELEMENT_OK_BUTTON);
	}
	
	//Cut/Paste a node to new place in the same navigation
	@Test()
	public void test02_CutPasteNodeInSameNavigation()
	{
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		
		//Goto Setting > Portal > Group Sites
		navigation.goToGroupSites();
		
		//Click Edit navigation of Administration 
		click(ELEMENT_ADMIN_EDIT_NAVI);
		
		//Add child node for Group Navigation
		groupNavi.addNodeForGroup("Administration", "Administration", false, NODE_NAME, true, languages, NODE_NAME, NODE_NAME, NODE_NAME, true, true);
		
		//Click Edit navigation of Administration
		click(ELEMENT_ADMIN_EDIT_NAVI);
		
		//Copy added child node
		if (waitForElementPresent(groupNavi.ELEMENT_NODE_GROUP_LINK.replace("{$navigation}", NODE_NAME),5000,0) == null )
		click(ADMINISTRATION_LINK);
		
		cutNode(By.xpath("//div[@class='node']//a[@title='"+NODE_NAME+"']"));
		
		//Paste to Node Sites Management 
		click(ELEMENT_APPLICATION_REGIS);
		pasteNode(ELEMENT_APPLICATION_REGIS);
		
		//Save
		save();
		pause(1000);
		
		//Click Edit navigation of Administration
		click(ELEMENT_ADMIN_EDIT_NAVI);
		waitForElementPresent(ELEMENT_APPLICATION_REGIS);
		click(ELEMENT_APPLICATION_REGIS);
		waitForElementPresent(CHILD_NODE);
		
		//Delete test data
		naviManage.deleteNode("Administration", "Application Registry", NODE_NAME, false);
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP, NODE_NAME);
	}
	
	//Cut/Paste a node to new place in different navigation
	@Test(groups={"later"})
	public void test03_CutPasteNodeInDiffNavigation()
	{
		//Goto Setting > Portal > Group Sites
		navigation.goToGroupSites();
		
		//Click Edit navigation of Administration 
		waitForElementPresent(ELEMENT_ADMIN_EDIT_NAVI);
		click(ELEMENT_ADMIN_EDIT_NAVI);
		
		//Copy Node Portal Administration 
		cutNode(ADMINISTRATION_LINK);
		waitForElementPresent(CLOSE_NAVIGATION_ICON);
		click(CLOSE_NAVIGATION_ICON);
		
		//Click Edit navigation of Development
		click(EXECUTIVE_EDIT_NAVI_LINK);
		rightClickOnElement(ELEMENT_NEW_STAFF);
		waitForElementNotPresent(ELEMENT_PASTE_NODE);
		click(CLOSE_NAVIGATION_ICON);
	}
	
	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}