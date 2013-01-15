package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.edit.editnavigation.othernodeaction;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.exoplatform.selenium.TestLogger.*;

public class GateIn_PortalNavigation_Edit_EditNavigation_OtherNodeActions_Move extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	NavigationManagement naviManage;
	public String EDIT_classic_NAVIGATION = "//div[text()='classic']/following::a[text()='Edit Navigation']";
	public String MOVE_UP_LINK = "Move Up";
	public String MOVE_DOWN_LINK = "Move Down";
	public String HOME_POSITION = "//div[2]/div[1]/a";
	public String SPACES_POSITION = "//div[23]/div/a";
	public String WIKI_OLD_POSITION = "//div[4]/div/a[contains(text(),'Wiki')]";
	public String WIKI_NEW_POSITION = "//div[3]/div/a[contains(text(),'Wiki')]";
	public String REGISTER_OLD_POSITION = "//div[18]/div/a[contains(text(),'Register')]";
	public String REGISTER_NEW_POSITION = "//div[19]/div/a[contains(text(),'Register')]";
	public String EDIT_INTRANET_NAVIGATION = ELEMENT_EDIT_NAVIGATION.replace("${navigation}", "intranet");
	
	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		account.signIn("root", "gtn");
	}
	
	//Change order of node in case Move Up or Move Down
	@Test()
	public void test01_ChangeNodeOrder()
	{	
		String nodeHome = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Home");
		String nodeSite = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Wiki");
		String nodeSpaces = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Spaces");
		String nodeRegister = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Register");
		
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		info("-START test01_ChangeNodeOrder");
		
		//Goto Edit Navigation
		info("---Goto Edit Navigation");
		navigation.goToPortalSites();
		
		//Click on Edit Navigation of classic
		info("---Click on Edit Navigation of Classic");
		click(EDIT_INTRANET_NAVIGATION);
		
		info("---Verify can't Move Up order of first node");
		
		//Verify position of node Home 
		info("-----Verify position of node Home before Move Up");
		waitForElementPresent(HOME_POSITION);
		
		info("-----Click on Move Up item of node Home");
		//Right click on node Home
		rightClickOnElement(nodeHome);
		
		//Click on Move Up item
		click(By.linkText(MOVE_UP_LINK));
		
		//Verify nothing happens ~ position of node Home is not changed
		info("-----Verify position of node Home is not changed after Move Up");
		waitForElementPresent(HOME_POSITION);
				
		info("---Verify can't Move Down order of last node");
		
		//Verify position of node ACCOUNT 
		info("-----Verify position of node NEW ACCOUNT before Move Down");
		waitForElementPresent(SPACES_POSITION);
		
		info("-----Click on Move Down of node NEW ACCOUNT");
		//Right click on node
		rightClickOnElement(nodeSpaces);
		
		//Click on Move Down item
		click(By.linkText(MOVE_DOWN_LINK));
		
		//Verify nothing happens ~ position of node New ACCOUNT is not changed
		info("-----Verify position of node site map is not changed after Moving Down");
		waitForElementPresent(SPACES_POSITION);
		
		info("---Verify can Move Up node is not first node");
		
		//Verify position of node Wiki
		info("-----Verify position of node Wiki before Moving Up");
		waitForElementPresent(WIKI_OLD_POSITION);
		
		info("-----Click on Move Up item of node Wiki");
		//Right click on node Wiki
		rightClickOnElement(nodeSite);
		
		//Click on Move Up item of node Wiki
		click(By.linkText(MOVE_UP_LINK));
		
		//Verify position of Wiki is changed
		info("-----Verify position of node Wiki is changed after Moving Up");
		waitForElementPresent(WIKI_NEW_POSITION);
		
		info("---Verify can Move Down node which is not last node");
		
		//Verify position of node Register
		info("-----Verify position of node Register before Moving Down");
		waitForElementPresent(REGISTER_OLD_POSITION);
		
		info("-----Click on Move Down item of node Register");
		//Right click on node Register
		rightClickOnElement(nodeRegister);
		
		//Click on Move Down item of node Register
		click(By.linkText(MOVE_DOWN_LINK));
		
		//Verify position of Register is changed
		info("-----Verify position of node Register is changed after Moving Down");
		waitForElementPresent(REGISTER_NEW_POSITION);
		
		info("-END test01_ChangeNodeOrder");
	}
	
	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}