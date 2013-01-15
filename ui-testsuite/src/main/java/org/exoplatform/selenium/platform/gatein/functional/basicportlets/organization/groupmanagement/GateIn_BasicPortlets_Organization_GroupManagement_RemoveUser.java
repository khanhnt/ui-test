package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization.groupmanagement;

import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;

/**
 *@author HangNTT
 *@date: 24/09/2012
 */
public class GateIn_BasicPortlets_Organization_GroupManagement_RemoveUser extends PlatformBase {
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;

	public String ELEMENT_GROUP_NAME = "platform" ;
	public String ELEMENT_GROUP_LABEL = "Platform" ;
	public String ELEMENT_USER_NAME = "mary";
	public String ELEMENT_MEMBER_SHIP = "manager"; 
	boolean SELECT = true; 
	By VERIFY_USER_AND_ROLE_IN_GROUP = By.xpath("//td/div[@title='mary']/following::td/div[@title='manager']");
	By VERIFY_USER = By.xpath("//td/div[@title='/platform']/following::td/div[@title='manager']");
	
	@BeforeMethod()
	public void beforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
	}

	@Test(groups={"later"})
	public void test01_RemoveUserFromExistingGroup () {
		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();

		//Choose group tab
		info("--Choose group tab--");
		userGroup.chooseGroupTab();

		//Add new user into group
		info("--Add user into group--");
		userGroup.selectGroup("Platform");
		userGroup.addUsersToGroup(ELEMENT_USER_NAME, ELEMENT_MEMBER_SHIP, SELECT, true);
		waitForElementPresent(VERIFY_USER_AND_ROLE_IN_GROUP);

		//Delete user in current group
		userGroup.deleteUserInGroup(ELEMENT_GROUP_NAME, ELEMENT_GROUP_LABEL,ELEMENT_USER_NAME);
	}	

	@Test(groups={"later"})
	public void test03_CheckMembershipOfUserInUserProfileAfterRemovedFromSpecificGroup() {

		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();

		//Choose group tab
		info("--Choose group tab--");
		userGroup.chooseGroupTab();

		//Add new user into group
		info("--Add user into group--");
		userGroup.selectGroup("Platform");
		userGroup.addUsersToGroup(ELEMENT_USER_NAME, ELEMENT_MEMBER_SHIP, SELECT, true);
		waitForElementPresent(VERIFY_USER_AND_ROLE_IN_GROUP);

		//Check membership of user
		info("Go to User Managemt");
		userGroup.chooseUserTab();
		//Search new user
		info("--Search user--");
		userGroup.searchUser(ELEMENT_USER_NAME, "User Name");

		//Edit new user
		info("--Click edit user--");
		userGroup.editUser(ELEMENT_USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		waitForElementPresent(VERIFY_USER);
		click(ELEMENT_CANCEL_BUTTON);

		//Back to Group Management
		info("--back to Group management--");
		userGroup.chooseGroupTab();

		//Delete user into group
		userGroup.deleteUserInGroup(ELEMENT_GROUP_NAME, ELEMENT_GROUP_LABEL,ELEMENT_USER_NAME);

		//Check after delete user into group
		userGroup.chooseUserTab();
		//Search new user
		info("--Search user--");
		userGroup.searchUser(ELEMENT_USER_NAME, "User Name");

		//Edit new user
		info("--Click edit user--");
		userGroup.editUser(ELEMENT_USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		waitForElementNotPresent(VERIFY_USER);
		click(ELEMENT_CANCEL_BUTTON);
	}

	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}