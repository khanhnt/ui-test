package org.exoplatform.selenium.platform.gatein.functional.basicportlets.administration;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GateIn_BasicPortlets_Administration_ApplicationRegistry_EditCategory extends PlatformBase
{
	
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;
	PageEditor pageEditor;
	ManageApplications app;
	//Define Data
	public String CATE_NAME_01 = "PLT_APPR_05_02_CATE1";
	public String CATE_DISP_NAME_01 = "PLT_APPR_05_02_CATE1";
	public String CATE_NAME_02 = "PLT_APPR_05_02_CATE2";
	public String CATE_DISP_NAME_02 = "PLT_APPR_05_02_CATE2";
	public String APP_ACCOUNT_PORTLET = "Account Portlet";
	public String APP_REGISTRY = "Application Registry";

	public String DATA_USER_NAME_01 = "user10";
	public String DATA_USER_NAME_02 = "user03";
	public String DATA_PASSWORD = "123456";
	public String DATA_FIRST_NAME = "FirstName";
	public String DATA_LAST_NAME = "LastName";
	public String DATA_EMAIL03 = DATA_USER_NAME_01+"@exoplatform.com";
	public String DATA_EMAIL04 = DATA_USER_NAME_02+"@exoplatform.com";
	public String DATA_LANGUAGE = "English";
	public String PLATFORM_ADMIN_DATA_GROUP = "Platform/Administration";
	public String MANAGER_DATA_MEMBER_SHIP = "manager";

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		account.signIn("root", "gtn");
		userGroup = new UserGroupManagement(driver);
		pageEditor = new PageEditor(driver);
		app = new ManageApplications(driver);
	}

	//Change access right on category from public to be limited by group(s)
	@Test(groups={"later"})
	public void test03_ChangeAccessRightFromPublicToLimited()
	{	
		//Add new user belongs to group Plaform/Administrator with membership is member 
		navigation.goToNewStaff();
		account.addNewUserAccount(DATA_USER_NAME_01, DATA_PASSWORD, DATA_PASSWORD, DATA_FIRST_NAME, DATA_LAST_NAME, DATA_EMAIL03, DATA_FIRST_NAME, DATA_LANGUAGE, true);

		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();

		//Select group tab
		userGroup.chooseGroupTab();

		//Select group
		userGroup.selectGroup(PLATFORM_ADMIN_DATA_GROUP);
		//		selectGroup("Administration");

		//Add user to group
		userGroup.addUsersToGroup(DATA_USER_NAME_01, "member", false, true);

		//Go to Application Registry page > setting access right for page is public
		navigation.goToApplicationRegistry();
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		navigation.goToEditPageEditor();
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForElementPresent(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		waitForElementPresent(ELEMENT_CHECKBOX_PUBLIC_MODE);
		//		check(ELEMENT_CHECKBOX_PUBLIC_MODE);
		app.makeItPublic(true);
		save();

		//Setting access right for portlet is public
		mouseOver(ELEMENT_PORTLET_CONTAINER, false);
		click(ELEMENT_EDIT_PORTLET_ICON);
		waitForElementPresent(app.ELEMENT_ACCESS_PERMISSION_TAB);
		click(app.ELEMENT_ACCESS_PERMISSION_TAB);
		waitForElementPresent(ELEMENT_CHECKBOX_PUBLIC_MODE);
		app.makeItPublic(true);
		save();
		close();
		click(ELEMENT_FINISH_ICON);

		//Add new category
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		app.addNewCategoryAtManageApplications(CATE_NAME_01, CATE_DISP_NAME_01, "", true, null, true);

		//Add Apps into added Category
		app.addApplicationToCategory(CATE_NAME_01, false, "Account", "Portlet", APP_ACCOUNT_PORTLET, true, null, null);
		app.addApplicationToCategory(CATE_NAME_01, false, "Application registry", "Portlet", APP_REGISTRY, true, null, null);

		//Edit permission of added Category from Public --> Limited 	
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(PLATFORM_ADMIN_DATA_GROUP, MANAGER_DATA_MEMBER_SHIP);
		app.editCategoryAtManageApplications(CATE_NAME_01, CATE_DISP_NAME_01, "", false, permissions, false);

		//Sign out and Sign in as added user
		waitForTextPresent("Root Root");
		account.signOut();
		account.signIn(DATA_USER_NAME_01, DATA_PASSWORD);

		//Go to Application Registry page 
		navigation.goToApplicationRegistry();

		//Verify added user can't see above edited Category 
		waitForElementNotPresent(CATE_NAME_01);

		//Sign out and Sign in as root
		account.signOut();
		account.signIn("root", "gtn");

		//Go to Application Registry page 
		navigation.goToApplicationRegistry();

		//Reset default data

		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		navigation.goToEditPageEditor();
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForElementPresent(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		waitForElementPresent(ELEMENT_CHECKBOX_PUBLIC_MODE);
		uncheck(ELEMENT_CHECKBOX_PUBLIC_MODE);
		waitForElementPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		//		click(ELEMENT_ADD_PERMISSION_BUTTON);
		setViewPermissions(PLATFORM_ADMIN_DATA_GROUP, "*");
		save();
		pause(500);
		click(ELEMENT_FINISH_ICON);

		//Delete data
		navigation.goToApplicationRegistry();
		app.deleteCategoryAtManageApplications(CATE_NAME_01, true);

		//Delete user
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(DATA_USER_NAME_01);
	}

	//Change access right on category from limited by group(s) to be public
	@Test(groups={"later"})
	public void test04_ChangeAccessRightFromLimitedToPublic()
	{
		By bName02= By.xpath("//a[contains(@title,'"+CATE_NAME_02+"')]");
		//Add new user belongs to group Plaform/Administrator with membership is member 
		navigation.goToNewStaff();
		account.addNewUserAccount(DATA_USER_NAME_02, DATA_PASSWORD, DATA_PASSWORD, DATA_FIRST_NAME, DATA_LAST_NAME, DATA_EMAIL04, DATA_FIRST_NAME, DATA_LANGUAGE, true);

		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();

		//Select group tab
		userGroup.chooseGroupTab();

		//Select group
		userGroup.selectGroup(PLATFORM_ADMIN_DATA_GROUP);

		//Add user to group
		userGroup.addUsersToGroup(DATA_USER_NAME_02, "manager", false, true);

		//Go to Application Registry page > setting access right user
		navigation.goToApplicationRegistry();
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		navigation.goToEditPageEditor();
		waitForElementPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		waitForElementPresent(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		waitForElementPresent(ELEMENT_CHECKBOX_PUBLIC_MODE);
		app.makeItPublic(false);
		setViewPermissions(PLATFORM_ADMIN_DATA_GROUP, MANAGER_DATA_MEMBER_SHIP);
		save();
		click(ELEMENT_FINISH_ICON);
		pause(500);
		//Go to Application Registry page 
		//		goToApplicationRegistry();

		//Add new category
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Customers", "*");
		app.addNewCategoryAtManageApplications(CATE_NAME_02, CATE_DISP_NAME_02, "", false, permissions, true);

		//Sign out and Sign in as added user
		account.signOut();
		account.signIn(DATA_USER_NAME_02, DATA_PASSWORD);
		//Go to Application Registry page check no Catetory CATE_NAME_02
		navigation.goToApplicationRegistry();
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		navigation.goToEditPageEditor();
		waitForElementNotPresent(bName02);
		click(ELEMENT_FINISH_ICON);
		pause(500);
		//Sign out and Sign in as root user
		account.signOut();
		account.signIn("root", "gtn");
		navigation.goToApplicationRegistry();
		//Edit permission of added Category from Limited --> public
		app.editCategoryAtManageApplications(CATE_NAME_02, CATE_DISP_NAME_02, "", true, null, false);

		//Sign out and Sign in as added user to check can see category
		account.signOut();
		account.signIn(DATA_USER_NAME_02, DATA_PASSWORD);
		//Verify added user can see above edited Category 
		navigation.goToApplicationRegistry();
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		navigation.goToEditPageEditor();
		waitForElementPresent(bName02);
		click(pageEditor.ELEMENT_CLOSE_WINDOWS_BUTTON);

		//Log in as root to edit category
		//Sign out and Sign in as root
		account.signOut();
		account.signIn("root", "gtn");

		//Go to Application Registry page 
		navigation.goToApplicationRegistry();

		//Verify root can see above edited Category 
		waitForElementPresent(bName02);

		//Delete data
		app.deleteCategoryAtManageApplications(CATE_NAME_02, true);

		//Delete user
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(DATA_USER_NAME_02);
	}

	@AfterMethod()
	public void afterTest()
	{
		driver.quit();
	}
}