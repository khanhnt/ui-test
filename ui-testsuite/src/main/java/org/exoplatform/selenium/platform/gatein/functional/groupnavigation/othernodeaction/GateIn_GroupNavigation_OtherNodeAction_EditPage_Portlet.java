package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.othernodeaction;

import java.util.HashMap;
import java.util.Map;



import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.GroupNavigation;

/**
 *@author HangNTT
 * @date: 26/09/2012
 */
public class GateIn_GroupNavigation_OtherNodeAction_EditPage_Portlet extends PlatformBase {
	/**
	 * @param args
	 */
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	By ELEMENT_EDIT_NAV_GROUP = null;
	By UP_LEVEL = By.xpath("//a[@title='Up Level']");
	By ELEMENT_EDIT_PAGE_PAGE_BODY_COMPONENT = By.id("UIPage");

	public String EDIT_NODE_PAGE_LINK = "Edit Node's Page";
	
	@BeforeMethod()
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		naviManage = new NavigationManagement(driver);
		page = new PageManagement(driver);
		groupNavi = new GroupNavigation(driver);
		ELEMENT_EDIT_NAV_GROUP = By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
	}

	//Add New Page By Wizard
	@Test(groups={"later"})
	public void test18_CheckFinishFunctionAfterEditPorletWithSavingPage () {
		String NODE_NAME = "GROUPNAV_26_02_018"; 
		String DISPLAY_NAME = "GROUPNAV_26_02_018";		
		String LANGUAGE = "English";	
		Map<String, String> PORTLET_IDS = new HashMap<String, String>();
		PORTLET_IDS.put("Administration/ApplicationRegistryPortlet","");
		String CATEGORY_TITLE = "Administration";

		info("Main program");	  
		account.signIn("root", "gtn");
		//Add new page by wizard
		navigation.goToManagePages();
		navigation.goToAddPageEditor();
		click(UP_LEVEL);   
		page.addNewPageEditor(NODE_NAME, DISPLAY_NAME, LANGUAGE, CATEGORY_TITLE, PORTLET_IDS, true);
		// Show form to edit portlet when edit node's page
		navigation.goToGroupSites();
		click(ELEMENT_EDIT_NAV_GROUP);
		info("Right click on new node");
		rightClickOnElement(groupNavi.ELEMENT_NODE_GROUP_LINK.replace("{$navigation}", NODE_NAME));
		info("edit node's page");
		waitForElementPresent(By.linkText(EDIT_NODE_PAGE_LINK));
		click(By.linkText(EDIT_NODE_PAGE_LINK));
		// Edit portlet
		mouseOver(ELEMENT_PORTLET_CONTAINER, true);
		click(ELEMENT_EDIT_PORTLET_ICON);
		// Choose Window Settings tab
		click(ELEMENT_WINDOW_SETTINGS_TAB);
		info("--Edit current title with valid value--");
		type(ELEMENT_WINDOWS_TITLE, "test18_ChangePortlet", true);
		click(By.id("Save"));
		mouseOver(ELEMENT_PORTLET_CONTAINER, true);
		waitForTextPresent("test18_ChangePortlet");
		//Check Finish
		click(ELEMENT_FINISH_ICON);
		save();
		//Go to Group Navigation
		click(ELEMENT_EDIT_NAV_GROUP);
		// Delete node
		naviManage.deleteNode("Administration","Administration","GROUPNAV_26_02_018",true);
		
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP, NODE_NAME);
	}
	
	//Add New Page By Wizard
	@Test(groups={"later","pending"})
	public void test23_CheckFinishFunctionOnEditingPageAfterEditedPagePortletLayout () {
		String NODE_NAME = "GROUPNAV_26_02_023"; 
		String DISPLAY_NAME = "GROUPNAV_26_02_023";		
		String LANGUAGE = "English";	
		Map<String, String> PORTLET_IDS = new HashMap<String, String>();
		PORTLET_IDS.put("Administration/ApplicationRegistryPortlet","");
		String CATEGORY_TITLE = "Administration";
		String ELEMENT_DASHBOARD_CATEGORY = ELEMENT_EDIT_PAGE_CATEGORY_MENU.replace("${categoryLabel}", "Dashboard");
		By ELEMENT_APPLICATION_DASHBOARD_PORTLET = By.id("dashboard/DashboardPortlet");

		info("Main program");	  
		account.signIn("root", "gtn");
		//Add new page by wizard
		navigation.goToApplicationRegistry();
		navigation.goToAddPageEditor();
		click(UP_LEVEL);   
		page.addNewPageEditor(NODE_NAME, DISPLAY_NAME, LANGUAGE, CATEGORY_TITLE, PORTLET_IDS, true);
		navigation.goToGroupSites();
		click(ELEMENT_EDIT_NAV_GROUP);
		info("Right click on new node");
		rightClickOnElement(groupNavi.ELEMENT_NODE_GROUP_LINK.replace("{$navigation}", NODE_NAME));

		info("edit node's page");

		waitForElementPresent(By.linkText(EDIT_NODE_PAGE_LINK));
		click(By.linkText(EDIT_NODE_PAGE_LINK));

		//EditNodePage("GROUPNAV_26_02_023");
		info("--View layout of portal before change portlet layout--");
		captureScreen("case23_BeforeChange");
		info("--Select application tab on edit inline composer --");
		waitForTextPresent("Administration");
		click(ELEMENT_DASHBOARD_CATEGORY);
		dragAndDropToObject(ELEMENT_APPLICATION_DASHBOARD_PORTLET, ELEMENT_EDIT_PAGE_PAGE_BODY_COMPONENT);
		click(ELEMENT_FINISH_ICON);
		save();
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON);
		
		click(By.linkText("GROUPNAV_26_02_023"));
		captureScreen("GROUPNAV_26_02_023");
		//Delete node
		navigation.goToGroupSites();
		click(ELEMENT_EDIT_NAV_GROUP);
		naviManage.deleteNode("Administration","Administration","GROUPNAV_26_02_023",true);
		
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP, NODE_NAME);
	}

	@AfterMethod()
	public void afterTest(){
		account.signOut();
		driver.quit();
	}
}