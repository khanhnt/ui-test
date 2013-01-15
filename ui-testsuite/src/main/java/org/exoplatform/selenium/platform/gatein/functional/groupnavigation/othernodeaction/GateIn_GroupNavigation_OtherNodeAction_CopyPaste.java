package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.othernodeaction;

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
 * @date: 20/09/2012	
 */
public class GateIn_GroupNavigation_OtherNodeAction_CopyPaste extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	//Define data
	public By UP_LEVEL = By.xpath("//a[contains(@title,'Up Level')]");
	public By ELEMENT_PAGE_MANAGEMENT = By.xpath("//a[@title='Page Management']");
	public By ELEMENT_APPLICATION_REGIS = By.xpath("//a[@title='Application Registry']");
	public By PORTAL_ADMINISTRATION = By.xpath("//a[@title='Portal Administration']");
	public String CHILD_NODE = "//div[@class='childrenContainer']//a[@class = 'NodeIcon DefaultPageIcon' and @title='Page Management']";
	public By CLOSE_NAVIGATION = By.xpath("//a[contains(@title,'Close Window')]");
	public By ELEMENT_NEW_STAFF = By.linkText("New Staff");
	
	@BeforeMethod()
	public void beforeTest()
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
	
	//Copy/Paste a node into another node in the same navigation
	@Test()
	public void test01_CopyPasteNodeInSameNavigation()
	{
		By ADMIN_EDIT_NAVI = By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}","Administration"));
		//Goto Group Sites
		navigation.goToGroupSites();
		
		//Select Edit Navigation of Content Management
		click(ADMIN_EDIT_NAVI);
		
		//Copy Node on Content Explorer
		copyNode(ELEMENT_PAGE_MANAGEMENT);
		
		//Paste Node on Content Administration
		pasteNode(ELEMENT_APPLICATION_REGIS);
		
		//Save
		save();
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON);
		//Select Edit Navigation of Content Management
		click(ADMIN_EDIT_NAVI);
		click(ELEMENT_APPLICATION_REGIS);
		
		//Verify Copy/Paste result and Reset data
		waitForElementPresent(CHILD_NODE);
		rightClickOnElement(CHILD_NODE);
		click(ELEMENT_NODE_DELETE);
		waitForConfirmation(naviManage.MSG_DELETE_NODE);
		waitForElementNotPresent(CHILD_NODE);
		save();
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON);
	}
	
	//Copy/Paste a node into another node in different navigation
	@Test(groups={"later"})
	public void test02_CopyPasteNodeInDiffirentNavigation()
	{
		By ADMIN_EDIT_NAVI = By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}","Administration"));
		//Goto Group Sites
		navigation.goToGroupSites();
		
		//Select Edit Navigation of Administration
		waitForElementPresent(ADMIN_EDIT_NAVI);
		click(ADMIN_EDIT_NAVI);
		
		//Right click and select Copy Node on Administration
		copyNode(ELEMENT_PAGE_MANAGEMENT);
		
		//Close Content Management Navigation
		waitForElementPresent(CLOSE_NAVIGATION);
		click(CLOSE_NAVIGATION);
		
		//Select Edit Navigation of Administration
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}","Executive Board"));
		
		//Right click and confirm Paste Node not exist on Node Portal Administration
		rightClickOnElement(ELEMENT_NEW_STAFF);
		waitForElementNotPresent(ELEMENT_PASTE_NODE);
		
		//Close Content Management Navigation
		waitForElementPresent(CLOSE_NAVIGATION);
		click(CLOSE_NAVIGATION);
	}
	
	//Copy/Paste a node into the same place
	@Test()
	public void test03_CopyPasteNodesInSamePlace()
	{
		By ADMIN_EDIT_NAVI = By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}","Administration"));
		//Goto Group Sites
		navigation.goToGroupSites();
		
		//Select Edit Navigation of Administrators
		waitForElementPresent(ADMIN_EDIT_NAVI);
		click(ADMIN_EDIT_NAVI);
		
		//Copy Node on Page management
		copyNode(ELEMENT_PAGE_MANAGEMENT);
		
		//Paste Node on Application registry
		pasteNode(ELEMENT_APPLICATION_REGIS);
		
		//Save
		save();
		waitForElementNotPresent(ELEMENT_PAGE_MANAGEMENT);
		
		//Select Edit Navigation of Administrators
		waitForElementPresent(ADMIN_EDIT_NAVI);
		click(ADMIN_EDIT_NAVI);
		
		click(ELEMENT_APPLICATION_REGIS);
		copyNode(By.xpath(CHILD_NODE));
		pasteNode(ELEMENT_APPLICATION_REGIS);
		
		//Verify display message to notice that Node already existed
		waitForTextPresent(naviManage.MSG_ADD_SAME_NODE);
		click(ELEMENT_OK_BUTTON);
		
		rightClickOnElement(CHILD_NODE);
		click(ELEMENT_NODE_DELETE);
		waitForConfirmation(naviManage.MSG_DELETE_NODE);
		waitForElementNotPresent(CHILD_NODE);
		save();
		waitForElementNotPresent(ELEMENT_APPLICATION_REGIS);
	}
	
	@AfterMethod
	public void afterTest(){
		account.signOut();
		driver.quit();
	}
}