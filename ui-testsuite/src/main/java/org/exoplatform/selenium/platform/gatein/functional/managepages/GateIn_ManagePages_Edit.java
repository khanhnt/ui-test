package org.exoplatform.selenium.platform.gatein.functional.managepages;

import static  org.exoplatform.selenium.TestLogger.debug;

/**
 * @author thaopth
 * @date: 18/09/2012
 */

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import  org.exoplatform.selenium.platform.UserGroupManagement;

public class GateIn_ManagePages_Edit extends PlatformBase {
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	UserGroupManagement userGroup;
	
	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		userGroup = new UserGroupManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		account.signOut();
		driver.quit();
	}

	@Test(groups={"later"})
	/* FNC_GTN_POR_MNP_22_019: Change edit right on portal page while editing portal page's properties
	 * Add new user
	 * Add user into group Platform/Administration with membership is "member"
	 * Create page for portal
	 * Change "edit permission" of page
	 * Login as user who belongs to group has edit permission
	 * Verify
	 */
	public void test19_ChangeEditRightOnPortalPage ()
	{
		String USER_NAME= "mimi";
		String PAGE_NAME = "FNC_GTN_POR_MNP_22_019";
		String PAGE_TITLE = "FNC_GTN_POR_MNP_22_019";

		account.signIn("root", "gtn");

		/*navigation.goToNewStaff();
		account.addNewUserAccount(USER_NAME, "123456", "123456", USER_NAME, "test", "mimi@exoplatform.com", USER_NAME, "English", true);
		//Go to user and group management page
*/		debug("Go to user and group management page");
		navigation.goToUsersAndGroupsManagement();

		//Select group tab
		debug("Select group tab");
		userGroup.chooseGroupTab();

		//Select group
		debug("Select group");
		userGroup.selectGroup("Platform");
		userGroup.selectGroup("Administration");

		//Add user to group
		debug("Add user to group");
		userGroup.addUsersToGroup(USER_NAME, "member", false, true);

		debug("Go to manage page");
		navigation.goToManagePages();

		debug("page management");
		page.editPageAtManagePages(PageType.GROUP, "Page Management");

		//edit access right for page management
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		waitForElementPresent(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_PERMISSION_SETTING_TAB);

		debug("Set view permission for page management");
		setViewPermissions("Platform/Administration", "*");


		//Change edit permission of page

		waitForElementPresent(ELEMENT_EDIT_PERMISSION_SETTING);
		click(ELEMENT_EDIT_PERMISSION_SETTING);

		debug("Set edit permission for page management");
		setEditPermissions("Platform/Administration", "member");

		debug("Save edit page form");
		save();

		debug("click finish button");
		waitForElementPresent(ELEMENT_FINISH_ICON);
		click(ELEMENT_FINISH_ICON);

		//Add new page for portal
		debug("Add new page for portal");
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Administration", "manager");
		waitForTextPresent(PAGE_NAME);

		debug("Edit new page");
		page.editPageAtManagePages(PageType.PORTAL, PAGE_TITLE);

		debug("Change edit permission of new page");
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		waitForElementPresent(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_PERMISSION_SETTING_TAB);

		waitForElementPresent(ELEMENT_EDIT_PERMISSION_SETTING);
		click(ELEMENT_EDIT_PERMISSION_SETTING);

		debug("Set edit permission for new page");
		setEditPermissions("Platform/Administration", "member");

		save();
		waitForElementPresent(ELEMENT_FINISH_ICON);
		click(ELEMENT_FINISH_ICON);
		waitForElementNotPresent(ELEMENT_FINISH_ICON);

		account.signOut();

		//Login new user and view edit page form
		account.signIn(USER_NAME, "123456");
		navigation.goToManagePages();
//		mouseOver(ELEMENT_EDIT_PAGE_LINK, true);
//		mouseOver(ELEMEMT_PAGE_MENU, true);
//		mouseOverAndClick(ELEMENT_PAGE_LAYOUT);
		navigation.goToEditPageEditor();
		waitForTextPresent("View Page properties");

		//Abort edit page form
		click(ELEMENT_ABORTEDIT_BUTTON);
		waitForElementNotPresent(ELEMENT_ABORTEDIT_BUTTON);
		account.signOut();

		account.signIn("root", "gtn");

		//Delete data
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(USER_NAME);
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, PAGE_NAME);
		waitForTextNotPresent(PAGE_NAME);

	}

	@Test(groups={"later"})
	/* FNC_GTN_POR_MNP_22_021: Check change after edit page properties with finish
	 * Create new page
	 * Edit page properties: page title
	 * Verify new title of page
	 * Delete data
	 */
	public void test22_CheckChangesAfterEditPagePropertiesWithFinish () 
	{
		//Define data
		String PAGE_NAME = "FNC_GTN_POR_MNP_22_021";
		String PAGE_TITLE = "FNC_GTN_POR_MNP_22_021";

		account.signIn("root", "gtn");

		debug("Go to manage pages");
		navigation.goToManagePages();

		debug("Add new page");
		page.addNewPageAtManagePages(PageType.PORTAL, PAGE_NAME, PAGE_TITLE, true, null, "Platform/Administration", "*");
		waitForTextPresent(PAGE_TITLE);

		debug("Edit page");
		page.editPageAtManagePages(PageType.PORTAL, PAGE_TITLE);

		//Go to view page properties
		debug("Click view page properties");
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		//Change page title
		debug("Change page title");
		type(pageEditor.ELEMENT_VIEWPAGE_PAGETITLE, "FNC_GTN_POR_MNP_22_021_test", true);

		debug("Save edit properties form");
		save();

		debug("click finish button");
		waitForElementPresent(ELEMENT_FINISH_ICON);
		click(ELEMENT_FINISH_ICON);

		//Open edit page form again and verify new page title
		page.editPageAtManagePages(PageType.PORTAL, "FNC_GTN_POR_MNP_22_021_test");

		debug("Click view page properties");
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForElementPresent(By.xpath("//input[@id='title' and @value='FNC_GTN_POR_MNP_22_021_test']"));

		debug("Cancel and abort edit page form");
		click(ELEMENT_CANCEL_BUTTON);
		click(ELEMENT_ABORTEDIT_BUTTON);

		//Delete data
		page.deletePage(PageType.PORTAL, "FNC_GTN_POR_MNP_22_021_test");
		waitForTextNotPresent("FNC_GTN_POR_MNP_22_021_test");
	}
}