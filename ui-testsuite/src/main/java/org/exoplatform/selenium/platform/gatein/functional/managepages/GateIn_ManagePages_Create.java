package org.exoplatform.selenium.platform.gatein.functional.managepages;

import static org.exoplatform.selenium.TestLogger.*;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;

/**
 * @author NhungVT, ThaoPTH
 * @date: 18/09/2012	
 */

public class GateIn_ManagePages_Create extends PlatformBase 
{
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	WebElement ELEMENT = null;
	//Define data
	public String INTRANET_LINK = "";
	public String PAGE_NAME = "";
	public String PAGE_TITLE = "";
	
	public String ADMINISTRATION_LINK = "//a[text()='Administration']";
	public String MANAGEMENT_LINK = "//a[text()='Management']";
	public String HOME_LINK = "//a[@title='Home']";
	public String EMPTY_LAYOUT = "//div[text()='Empty Layout']";
	
	//Messages
	public String BLANK_NODE_NAME_MESSAGE = "The field \"Node Name\" is required.";
	public String NODE_NAME_START_NUMBER_MESSAGE = "The \"Node Name\" field must start with a letter and must not contain special characters.\"";
	public String NODE_NAME_START_SPECIAL_MESSAGE = "Only alpha, digit, dash and underscore characters allowed for the field \"Node Name\".";
	public String PAGE_NAME_EXIST_MESSAGE = "This page name already exists.";
	public By ELEMENT_APPLICATION_REGISTRY_CREATE = By.linkText("Page Management");
	//update later
	public String SERVICE_MANAGEMENT_LINK = "";
	public String ELEMENT_OWNER_ID_INTRANET = "//input[@name='ownerId' and @value='intranet']";
	public String ADMIN_LINK = "";
	public String SITE_EXPORT_IMPORT_LINK = "";
	public String ELEMENT_SITE_MENU = "";
	public String ELEMENT_CLASSIC_MENU = "";
	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		account.signIn("root", "gtn");
	}
	
	/*--------------- Manage Page - Create #1 ---------------*/
	
	//Check showing add new page form (Portal, Group)
	@Test(groups={"later"})
	public void test01_CheckShowNewPageForm()
	{
		info("-START test01_CheckShowNewPageForm");
		
		//Check Showing on Portal's page
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		waitForElementPresent(HOME_LINK);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		//Check Showing on Group's page
		
		//Goto Settings > Administration > Management
		navigation.goToApplicationRegistry();
		
		//Goto Edit >  Page > Add Page of Group
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		//will update later
		waitForElementPresent(ELEMENT_APPLICATION_REGISTRY_CREATE);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		info("-END test01_CheckShowNewPageForm");
	}
	
	//Check showing pages list of selected navigation at step 1 of Create page  form
	@Test(groups={"later"})
	public void test06_CheckShowPageListOfSelectedNavigation()
	{
		info("-START test06_CheckShowPageListOfSelectedNavigation");
		
		//Check Showing on Group's page
		
		//Goto Settings > Administration > Management
		navigation.goToApplicationRegistry();
		
		navigation.goToAddPageEditor();
		
		//verify list of pages in the left
		//will update later
		waitForElementPresent(ELEMENT_APPLICATION_REGISTRY_CREATE);
		
		waitForElementPresent(SERVICE_MANAGEMENT_LINK);
		waitForElementPresent(pageEditor.PAGE_MANAGER_LINK);
		waitForElementPresent(SITE_EXPORT_IMPORT_LINK);
		click(ELEMENT_APPLICATION_REGISTRY_CREATE);
		//Click on Up Level icon
		click(pageEditor.UP_LEVEL_ICON);
		
		//Verify Selected Page Node is Administration
		waitForElementPresent(ADMIN_LINK);
		click(pageEditor.UP_LEVEL_ICON);
		
		//Verify Selected Page Node is /default
				waitForElementPresent(pageEditor.DEFAULT_NODE);
		//Click Abort button
		click(ELEMENT_ABORT_BUTTON);
		
		//Check Showing on Portal's page
		
		//Goto My Sites > intranet > Home
		mouseOver(ELEMENT_SITE_MENU, true);
		pause(500);
		mouseOverAndClick(ELEMENT_CLASSIC_MENU);
		pause(500);
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Click on Home
		waitForElementPresent(HOME_LINK);
		click(HOME_LINK);
		
		//Click on Up Level icon
		waitForElementPresent(pageEditor.UP_LEVEL_ICON);
		click(pageEditor.UP_LEVEL_ICON);
		
		//Verify Selected Page Node is /default
		waitForElementPresent(pageEditor.DEFAULT_NODE);
		
		//Click Abort button
		click(ELEMENT_ABORT_BUTTON);
		
		info("-END test06_CheckShowPageListOfSelectedNavigation");
	}
	
	//Complete step 1 in Create page  with valid value
	@Test()
	public void test07_CompleteStep1WithValidValue()
	{
		info("-START test07_CompleteStep1WithValidValue");
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		waitForElementPresent(HOME_LINK);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Input valid value for all fields at Step1
		type(pageEditor.NODE_NAME_INPUT, "Page1", true);
		
		//Click Next button
		next();
		
		//Verify Step 2
		waitForTextPresent("Select a Page Layout Template");
		waitForElementPresent(EMPTY_LAYOUT);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		info("-END test07_CompleteStep1WithValidValue");
	}
	
	//Complete step 1 in Create page with blank Node Name
	@Test()
	public void test08_CompleteStep1WithBlankNodeName()
	{
		info("-START test08_CompleteStep1WithBlankNodeName");
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		waitForElementPresent(HOME_LINK);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Click Next button with Blank Node Name
		next();
		
		//Verify alert message
		waitForTextPresent(BLANK_NODE_NAME_MESSAGE);
		click(ELEMENT_OK_BUTTON);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		info("-END test08_CompleteStep1WithBlankNodeName");
	}
	
	//Complete step 1 in Create page with Node Name start with number
	@Test()
	public void test09_CompleteStep1WithNodeNameStartNumber()
	{
		info("-START test09_CompleteStep1WithNodeNameStartNumber");
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		waitForElementPresent(HOME_LINK);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Input node name start with number at Step1
		type(pageEditor.NODE_NAME_INPUT, "1Page", true);
		
		//Click Next button
		next();
		
		//Verify Step 2
		waitForTextPresent(NODE_NAME_START_NUMBER_MESSAGE);
		click(ELEMENT_OK_BUTTON);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		info("-END test09_CompleteStep1WithNodeNameStartNumber");
	}
	
	//Complete step 1 in Create page wizard with Node Name start with dot and underscore characters 
	@Test()
	public void test10_CompleteStep1WithNodeNameStartSpeacialChars()
	{		
		info("-START test10_CompleteStep1WithNodeNameStartSpeacialChars");
		
		//Goto Edit >  Page > Add Page of Portal
		navigation.goToAddPageEditor();
		
		//Verify Left panel 
		waitForElementPresent(HOME_LINK);
		
		//Verify Right panel
		waitForElementPresent(pageEditor.NODE_NAME_INPUT);
		
		//Input node name start with dash char at Step1
		type(pageEditor.NODE_NAME_INPUT, "-Page", true);
		
		//Click Next button
		next();
		
		//Verify Step 2
		waitForTextPresent(NODE_NAME_START_NUMBER_MESSAGE);
		click(ELEMENT_OK_BUTTON);
		
		//Reset Node Name value
		ELEMENT = waitForAndGetElement(By.xpath(pageEditor.NODE_NAME_INPUT));
		ELEMENT.clear();
		
		//Input node name start with dot char at Step1
		type(pageEditor.NODE_NAME_INPUT, ".Page", true);
		
		//Click Next button
		next();
		
		//Verify Step 2
		waitForTextPresent(NODE_NAME_START_SPECIAL_MESSAGE);
		click(ELEMENT_OK_BUTTON);
		
		//Click Abort button
		click(pageEditor.ELEMENT_ABORT_BUTTON);
		
		info("-END test10_CompleteStep1WithNodeNameStartSpeacialChars");
	}
	
	/*--------------- Manage Page - Create #2 ---------------*/
	
	@Test 
	/* FNC_GTN_POR_MNP_20_022
	 * Create portal page
	 * Create group page same name as portal page
	 */
	public void test22_CreateGroupPageSameNameWithPortalPage()
	{
		info("-START test22_CreateGroupPageSameNameWithPortalPage");
		PAGE_NAME = "FNC_GTN_POR_MNP_20_022";
		PAGE_TITLE = "FNC_GTN_POR_MNP_20_022";
		navigation.goToManagePages();
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);

		debug("Add new page for current portal");
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Administration", "manager");
		debug("Verify new page is create successfully");
		waitForTextPresent(PAGE_NAME);

		debug("Add new page with same name for group");
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);
		page.addNewPageAtManagePages(PageType.GROUP, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Administration", "manager");
		waitForTextPresent(PAGE_NAME);

		//Delete data
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);
		page.deletePage(PageType.GROUP, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);
		
		info("-END test22_CreateGroupPageSameNameWithPortalPage");
	}
	
	@Test
	/* FNC_GTN_POR_MNP_20_023
	 * Create page for group
	 */
	public void test23_CreatePageForGroup ()
	{
		info("-START test23_CreatePageForGroup");
		
		PAGE_NAME = "FNC_GTN_POR_MNP_20_023";
		PAGE_TITLE = "FNC_GTN_POR_MNP_20_023";
		
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Visitors", "manager");

		debug("Go to manage page");
		navigation.goToManagePages();

		debug("add new page");
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);
		page.addNewPageAtManagePages(PageType.GROUP, PAGE_NAME, PAGE_TITLE, false, permissions,"Platform/Administration", "manager");
		waitForTextPresent(PAGE_NAME);

		//Delete data
		page.deletePage(PageType.GROUP, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);
		
		info("-END test23_CreatePageForGroup");
	}
	
	@Test
	/* FNC_GTN_POR_MNP_20_024
	 * Create page for portal
	 */
	public void test24_CreatePortalPage () 
	{
		info("-START test24_CreatePortalPage");
		
		PAGE_NAME = "FNC_GTN_POR_MNP_20_024";
		PAGE_TITLE = "FNC_GTN_POR_MNP_20_024";

		debug("Go to manage page");
		navigation.goToManagePages();

		debug("add new page");
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null,"Platform/Administration", "manager");
		waitForTextPresent(PAGE_NAME);

		//Deleta data
		debug("Delete page");
		page.deletePage(PageType.PORTAL, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);
		
		info("-END test24_CreatePortalPage");
	}
	
	@Test
	/* FNC_GTN_POR_MNP_20_025
	 * Create page for portal
	 * Create page same name in one portal
	 */
	public void test25_CreatePagesSameNameInSamePortal () 
	{
		info("-START test25_CreatePagesSameNameInSamePortal");
		
		INTRANET_LINK = baseUrl+"/portal/intranet/";
		PAGE_NAME = "FNC_GTN_POR_MNP_20_025";
		PAGE_TITLE = "FNC_GTN_POR_MNP_20_025";

		debug("Go to manage page");
		navigation.goToManagePages();
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);

		debug("Add new page");
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Visitors", "*");
		waitForTextPresent(PAGE_NAME);

		navigation.goToManagePages();

		click(page.ELEMENT_ADD_PAGE_BUTTON);

		waitForTextPresent("Page Setting");

		select(page.ELEMENT_SELECT_OWNER_TYPE, "portal");
		waitForElementPresent(ELEMENT_OWNER_ID_INTRANET);

		type(page.ELEMENT_PAGE_NAME_INPUT, PAGE_NAME, true);
		type(page.ELEMENT_PAGE_TITLE_INPUT, PAGE_TITLE, true);
		save();

		waitForMessage(PAGE_NAME_EXIST_MESSAGE);
		click(ELEMENT_OK_BUTTON);
		click(ELEMENT_CANCEL_BUTTON);

		//Delete data
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, PAGE_NAME);
		
		info("-END test25_CreatePagesSameNameInSamePortal");
	}
	
	@Test
	/* FNC_GTN_POR_MNP_20_028
	 * Create page for group
	 * Create portal page with name same as group page
	 */
	public void test28_CreatePortalPageSameNameAsGroupPage ()
	{
		info("-START test28_CreatePortalPageSameNameAsGroupPage");
		
		INTRANET_LINK = baseUrl+"/portal/intranet";
		PAGE_NAME = "FNC_GTN_POR_MNP_20_028";
		PAGE_TITLE = "FNC_GTN_POR_MNP_20_028";
		
		debug("Go to manage page");
		navigation.goToManagePages();
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);

		debug("Add new page for group");
		page.addNewPageAtManagePages(PageType.GROUP, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Visitors", "*");
		waitForTextPresent(PAGE_NAME);
		
		debug("Add new page for portal");
		waitForElementPresent(page.ELEMENT_ADD_PAGE_BUTTON);
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Administration", "manager");
		waitForTextPresent(PAGE_NAME);
		
		//Delete data
		page.deletePage(PageType.PORTAL, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);
		page.deletePage(PageType.GROUP, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);		
		
		info("-END test28_CreatePortalPageSameNameAsGroupPage");
	}
	
	@AfterMethod()
	public void afterTest()
	{
		driver.quit();
	}
}