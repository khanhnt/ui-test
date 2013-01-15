package org.exoplatform.selenium.platform.gatein.functional.basicportlets.administration;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.*;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GateIn_BasicPortlets_Administration_ApplicationRegistry_AddCategory extends PlatformBase{
	

	UserGroupManagement userGroup;
	ManageAccount account;
	NavigationToolbar navigation;
	ManageApplications app;
	String categoryName = "testNewCategory";
	String displayName = "testNewCategory";
	String categoryDescription = "adding a new category";
	String messageDuplicateCategory = "This category is existing, please enter another one!";
	boolean verify = true;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		userGroup = new UserGroupManagement(driver);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		app = new ManageApplications(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*-- Case ID 001
	 *-- Add new category with valid value  
	 * --*/
	@Test(groups={"later"})
	public void test01_AddNewCategoryWithValidValue(){
		boolean publicMode = false;
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(userGroup.GROUP_PLATFORM_ADMIN, "*");

		account.signIn("root", "gtn");

		info("-- Step 1: Show Application Registry form --");
		navigation.goToApplicationRegistry();
		waitForTextPresent("Categories");

		info("-- Step 2 & 3: Show Add new category form and Add new category with valid value -- ");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, publicMode, permissions, verify);
		
		info("-- Delete data after testing --");
		app.deleteCategoryAtManageApplications(categoryName, verify);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 007
	 *-- Add same name categories 
	 * --*/
	@Test(groups={"later"})
	public void test07_AddSameNameCategories(){
		boolean publicMode = false;
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(userGroup.GROUP_PLATFORM_ADMIN, "member");

		account.signIn("root", "gtn");

		info("-- Step 1: Add the first category --");
		navigation.goToApplicationRegistry();
		waitForTextPresent("Categories");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, publicMode, permissions, verify);

		info("-- Step 2: Add same name category --");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, false);
		waitForMessage(messageDuplicateCategory);
		closeMessageDialog();
		
		info("-- Delete data after testing --");
		app.deleteCategoryAtManageApplications(categoryName, verify);
		
		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}
	
	/*-- Case ID 008
	 *-- Add new category with category name the same with existing but different by lower/upper case
	 * --*/
	@Test(groups={"later"})
	public void test08_AddNewCategoryWithCategoryNameTheSameWithExistingButDifferentByLowerUpperCase(){
		boolean publicMode = false;
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(userGroup.GROUP_PLATFORM_ADMIN, "member");

		account.signIn("root", "gtn");

		info("-- Step 1: Show Application Registry form --");
		navigation.goToApplicationRegistry();
		waitForTextPresent("Categories");
		
		info("-- Step 2 & 3: Show Add new category form and Add the first category with valid value -- ");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, publicMode, permissions, verify);
		
		info("-- Step 4: Create new same Category name --");
		app.addNewCategoryAtManageApplications("TESTNEWCATEGORY", "TESTNEWCATEGORY", categoryDescription, true, null, verify);
		
		info("-- Delete data after testing --");
		app.selectCategoryAtManageApplications(categoryName);
		app.deleteCategoryAtManageApplications(categoryName, verify);
		app.selectCategoryAtManageApplications("TESTNEWCATEGORY");
		app.deleteCategoryAtManageApplications("TESTNEWCATEGORY", verify);
		
		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}
	
	/*-- Case ID 014
	 *-- Add public category in Application registry
	 * --*/
	@Test(groups={"later"})
	public void test014_AddPublicCategoryInApplicationRegistry(){
		boolean publicMode = true;
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(userGroup.GROUP_PLATFORM_ADMIN, "member");

		account.signIn("root", "gtn");

		info("-- Step 1: Show Application Registry form --");
		navigation.goToApplicationRegistry();
		waitForTextPresent("Categories");
		
		info("-- Step 2 & 3 & 4: Show Add new category form and Check when choose public option for new category and Save -- ");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, publicMode, null, verify);
		
		info("-- Delete data after testing --");
		app.deleteCategoryAtManageApplications(categoryName, verify);
		
		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}
	
	/*-- Case ID 015
	 *-- Add private category in Application registry
	 * --*/
	@Test(groups={"later"})
	public void test015_AddPrivateCategoryInApplicationRegistry(){
		boolean publicMode = false;
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put(userGroup.GROUP_PLATFORM_ADMIN, "member");

		account.signIn("root", "gtn");

		info("-- Step 1: Show Application Registry form --");
		navigation.goToApplicationRegistry();
		waitForTextPresent("Categories");
		
		info("-- Step 2 & 3: Show Add new category form and Check form to select group/membership for permission -- ");
		info("-- Step 4 & 5: Check after select group/membership to assign right on new category and Save --");
		app.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, publicMode, permissions, verify);
	
		info("-- Delete data after testing --");
		app.deleteCategoryAtManageApplications(categoryName, verify);
		
		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}	
}