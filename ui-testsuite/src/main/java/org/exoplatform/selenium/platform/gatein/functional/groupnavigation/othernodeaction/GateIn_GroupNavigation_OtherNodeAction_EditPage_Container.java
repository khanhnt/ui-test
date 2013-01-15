package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.othernodeaction;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;

import org.exoplatform.selenium.platform.ManageAccount;

/**
 *@author HangNTT
 * @date: 26/09/2012
 */
public class GateIn_GroupNavigation_OtherNodeAction_EditPage_Container extends PlatformBase {
	/**
	 * @param args
	 */
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	PageEditor pageEditor;
	By UP_LEVEL = By.xpath("//a[@title='Up Level']");
	By ELEMENT_EDIT_PAGE_PAGE_BODY_COMPONENT = By.id("UIPage");
	By ELEMENT_ROW_PAGE_CONFIGS = By.xpath("//a[contains(text(),'Row Page Configs')]");
	By ELEMENT_PAGE_CONFIGS = By.xpath("//div[@id='UIDropDownPageTemp']/div/div/div/div/div/div/div");
	By ELEMENT_ROWS_LAYOUT = By.linkText("Rows Layout");
	By ELEMENT_ONE_ROW_LAYOUT = By.id("oneRow");
	By ELEMENT_EDIT_CONTAINER_ICON = By.xpath("//div[@class='UIRowContainer']/div/div/div[2]/div/div[2]/div/a[1]");
	String ELEMENT_EDIT_PAGE_COMPONENT = "//div[@class='UIRowContainer']/div[${portletNumber}]/div";
	String ELEMENT_EDIT_CONTAINER = ELEMENT_EDIT_PAGE_COMPONENT.replace("${portletNumber}", "1");
	By ELEMENT_INPUT_WIDTH = By.id("width");
	By ELEMENT_INPUT_HEIGHT = By.id("height");
	public String EDIT_NODE_PAGE_LINK = "Edit Node's Page";
	
	
	//Add new page with layout
	public void addNewPageWithLayout (String nodeName, String displayName, String language, boolean extendedLabelMode){

		type(ELEMENT_INPUT_NODE_NAME, nodeName, true);
		WebElement element = waitForAndGetElement(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
		if (extendedLabelMode){
			Assert.assertTrue(element.isSelected());
			select(ELEMENT_SELECT_LANGUAGE, language);

		}else {
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
			type(ELEMENT_INPUT_PAGE_DISPLAY_NAME, displayName, true);
		}

		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);
		waitForTextPresent("Page Configs");
		click(ELEMENT_PAGE_CONFIGS);
		mouseOverAndClick(ELEMENT_ROW_PAGE_CONFIGS);
		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent("Page Editor");
	}

	@BeforeMethod()
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		naviManage = new NavigationManagement(driver);
		page = new PageManagement(driver);
		pageEditor = new PageEditor(driver);
		groupNavi = new GroupNavigation(driver);
	}

	//Add New Page By Wizard
	@Test(groups={"later"})
	public void test04_CheckFinishFunctionOnEditingPageAfterEditedPagePortletLayout () {
		String NODE_NAME = "GROUPNAV_26_03_004"; 
		String DISPLAY_NAME = "GROUPNAV_26_03_004";		
		String LANGUAGE = "English";	
		Map<String, String> PORTLET_IDS = new HashMap<String, String>();

		PORTLET_IDS.put("Administration/ApplicationRegistryPortlet","");
		String CATEGORY_TITLE = "Administration";

		info("main program");	  
		account.signIn("root", "gtn");
		//Add new page by wizard
		navigation.goToManagePages();
		navigation.goToAddPageEditor();
		click(UP_LEVEL);   
		page.addNewPageEditor(NODE_NAME, DISPLAY_NAME, LANGUAGE, CATEGORY_TITLE, PORTLET_IDS, true);	
		navigation.goToGroupSites();
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		info("Right click on new node");
		rightClickOnElement(By.linkText("GROUPNAV_26_03_004"));
		info("Edit node's page");
		waitForElementPresent(By.linkText(EDIT_NODE_PAGE_LINK));
		click(By.linkText(EDIT_NODE_PAGE_LINK));
		//Choose Container tab
		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(ELEMENT_ROWS_LAYOUT);
		click(ELEMENT_ROWS_LAYOUT);

		info("--Move container to the new palce--");
		dragAndDropToObject(ELEMENT_ONE_ROW_LAYOUT, ELEMENT_EDIT_PAGE_PAGE_BODY_COMPONENT);
		info("--Edit container of page--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(ELEMENT_EDIT_CONTAINER, true);
		mouseOver(ELEMENT_EDIT_CONTAINER_ICON, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		waitForTextPresent("Container Setting");	
		type(ELEMENT_INPUT_WIDTH, "900px", true);
		type(ELEMENT_INPUT_HEIGHT, "600px", true);
		save();
		waitForTextNotPresent("Container Setting");
		info("--Verify that the changes on container is saved--");
		captureScreen("container");
		click(ELEMENT_FINISH_ICON);
		// close navigation form
		save();
		//Delete node
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		naviManage.deleteNode("Administration","Administration","GROUPNAV_26_03_004",true);
		
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP, NODE_NAME);
	}
	
	//Add New Page By Wizard
	@Test(groups={"later"})
	public void test09_CheckFinishFunctionAfterChangeNodePageContainerLayout () {

		String NODE_NAME = "GROUPNAV_26_03_009"; 
		String DISPLAY_NAME = "GROUPNAV_26_03_009";		
		String LANGUAGE = "English";	
		By ELEMENT_INPUT_TITLE = By.id("title");

		info("main program");	  
		account.signIn("root", "gtn");
		//Add new page by wizard
		navigation.goToManagePages();
		navigation.goToAddPageEditor();
		click(UP_LEVEL);  
		addNewPageWithLayout(NODE_NAME, DISPLAY_NAME, LANGUAGE,true);
		// go to Edit navigation of group
		navigation.goToGroupSites();
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		//click edit node's page
		info("Right click on new node");
		rightClickOnElement(By.linkText("GROUPNAV_26_03_009"));
		info("edit node's page");
		waitForElementPresent(By.linkText(EDIT_NODE_PAGE_LINK));
		click(By.linkText(EDIT_NODE_PAGE_LINK));
		//Choose Container tab
		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(ELEMENT_ROWS_LAYOUT);
		click(ELEMENT_ROWS_LAYOUT);
		info("--Move container to the new palce--");
		//drag and drop container
		dragAndDropToObject(ELEMENT_ONE_ROW_LAYOUT, ELEMENT_EDIT_PAGE_PAGE_BODY_COMPONENT);
		info("--Edit container of page--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(ELEMENT_EDIT_CONTAINER, true);
		mouseOver(ELEMENT_EDIT_CONTAINER_ICON, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		waitForTextPresent("Container Setting");	
		// change information for container
		type(ELEMENT_INPUT_TITLE,"GROUPNAV_TITLE",true);
		type(ELEMENT_INPUT_WIDTH, "900px", true);
		type(ELEMENT_INPUT_HEIGHT, "600px", true);
		save();
		waitForTextNotPresent("Container Setting");
		info("--Verify that the changes on container is saved--");
		captureScreen("GROUPNAV_TITLE");
		click(ELEMENT_FINISH_ICON);
		// close navigation form
		save();
		//Delete node
		click(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
		naviManage.deleteNode("Administrators","Administration","GROUPNAV_26_03_009",true);
		
		navigation.goToManagePages();
		page.deletePage(PageType.GROUP, NODE_NAME);
	}

	@AfterMethod()
	public void afterTest(){
		account.signOut();
		driver.quit();
	}
}