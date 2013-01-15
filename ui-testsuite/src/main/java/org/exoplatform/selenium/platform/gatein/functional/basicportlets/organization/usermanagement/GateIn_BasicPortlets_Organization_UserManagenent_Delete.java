package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization.usermanagement;

import static org.exoplatform.selenium.TestLogger.info;
import  org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GateIn_BasicPortlets_Organization_UserManagenent_Delete extends PlatformBase{
	
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;
	boolean SELECT = true;  
	@Test(groups={"later"})
	public void test01_deleteSpecificUserFromExistingUsersListInCommunityManagement(){

		String USER_NAME = "exoseaadministrator";
		String PASSWORD	= "test_por_02_04_001";
		String CONFIRM_PASSWORD = "test_por_02_04_001";
		String FIRST_NAME = "first name";
		String LAST_NAME = "last name";
		String EMAIL = "exoseatestaccount@exo.com";
		String USER_NAME_GIVEN = "";
		String LANGUAGE = "English";

		String searchOption = "User Name";
		By ELEMENT_USER_NAME = By.name("username");
		By ELEMENT_PWD = By.name("password");
		By ELEMENT_SIGNIN = By.name("signIn");

		info("--login portal--");
		account.signIn("root", "gtn");
		
		// Go to New Staff
		info("Go to New Staff");
		navigation.goToNewStaff();
		
		// Add new user
		info("Input value");
		account.addNewUserAccount(USER_NAME,PASSWORD,CONFIRM_PASSWORD,FIRST_NAME,LAST_NAME,EMAIL,USER_NAME_GIVEN,LANGUAGE,true);
		
		//Go to user and group management page
		info("Go to users and group management");
		navigation.goToUsersAndGroupsManagement();
		
		//Choose Group Management
		info("Choose Group Management");
		userGroup.chooseGroupTab();
		
		//Add new user into group
		info("Add new user into group");
		userGroup.selectGroup(userGroup.GROUP_PLATFORM_ADMIN);
		info("Add user into group");
		userGroup.addUsersToGroup(USER_NAME, "member", SELECT, true);
		isElementPresent(USER_NAME);
		isElementPresent("member");
		
		//Choose User Management tab
		info("Choose User Management");
		userGroup.chooseUserTab();
		
		//Search new user
		info("Search user");
		userGroup.searchUser(USER_NAME, searchOption);
		
		//Delete new user
		info("Delete user");
		userGroup.deleteUser(USER_NAME);
		//closeMessageDialog();
		
		info("Check group after delete user");
		info("Choose Group Management");
		userGroup.chooseGroupTab();
		userGroup.selectGroup(userGroup.GROUP_PLATFORM_ADMIN);
		waitForElementNotPresent(USER_NAME);
		waitForElementNotPresent("member");
		account.signOut();
		driver.get(baseUrl);
		
		info("Sign in by deleted account");
		//click(ELEMENT_GO_TO_PORTAL);
		account.signIn(USER_NAME, "test_por_02_04_001");
		waitForTextPresent(account.MSG_SIGN_IN_FAILED);
		type(ELEMENT_USER_NAME, "root", true);
		waitForElementPresent(ELEMENT_PWD);
		type(ELEMENT_PWD, "gtn", true);
		click(ELEMENT_SIGNIN);		
	}
	@BeforeMethod()
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
	}
	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}
