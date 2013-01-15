package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization.membershipmanagement;

import org.openqa.selenium.Alert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;

public class GateIn_BasicPortlets_Organization_MembershipManagement_Delete extends PlatformBase{
	
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;
	
	String GROUP_NAME1 = "Platform"; 
	String USER_NAME = "demo";
	String MEMBERSHIP = "member"; 
	boolean SELECT = true; 
	@Test(groups={"later"})
	public void test05_checkExistingOfUserInGroupAfterHisRoleInThatGroupWasDeleted(){
		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		navigation.goToUsersAndGroupsManagement();

		info("Go to Memebrship tab");
		userGroup.chooseMembershipTab();

		info("Add new membership");
		userGroup.addMembership("Test_POR_04_04_005", "test membership type", true);

		info("Go to Group management");
		userGroup.chooseGroupTab();

		info("Add user into group");
		userGroup.selectGroup(GROUP_NAME1);
		userGroup.addUsersToGroup("demo", "Test_POR_04_04_005", SELECT, true);

		info("Go to Memebrship tab");
		userGroup.chooseMembershipTab();

		info("Delete membership");
		userGroup.deleteMembership("Test_POR_04_04_005", true);

		info("Back to Group Management");
		userGroup.chooseGroupTab();
		userGroup.selectGroup(GROUP_NAME1);
		waitForElementNotPresent(USER_NAME);
		waitForElementNotPresent("Test_POR_04_04_005");
	}
	@Test(groups={"later"})
	public void test06_checkMembershipInformationOfUserAfterRoleOfHimInAGroupWasDeleted(){
		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		navigation.goToUsersAndGroupsManagement();

		info("Go to Memebrship tab");
		userGroup.chooseMembershipTab();

		info("Add new membership");
		userGroup.addMembership("test", "test membership type", true);

		info("Go to Group management");
		userGroup.chooseGroupTab();

		info("Add user into group");
		userGroup.selectGroup(GROUP_NAME1);
		userGroup.addUsersToGroup("demo", "test", SELECT, true);

		info("Go to User Management");
		userGroup.chooseUserTab();

		info("--Search user--");
		userGroup.searchUser("demo", "User Name");

		info("--Click edit user--");
		userGroup.editUser(USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		isElementPresent(USER_NAME);
		isElementPresent("test");
		click(ELEMENT_CANCEL_BUTTON);

		info("Go to Memebrship tab");
		userGroup.chooseMembershipTab();

		info("Delete membership");
		userGroup.deleteMembership("test", true);

		info("Check membership information of user");
		userGroup.chooseUserTab();

		info("--Search user--");
		userGroup.searchUser("demo", "User Name");

		info("--Click edit user--");
		userGroup.editUser(USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		waitForElementNotPresent(USER_NAME);
		waitForElementNotPresent("test");
		click(ELEMENT_CANCEL_BUTTON);
	}
	@Test(groups={"later"})
	public void test07_deleteMandatoryMembership(){
		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		navigation.goToUsersAndGroupsManagement();

		info("Go to Memebrship tab");
		userGroup.chooseMembershipTab();
		String deleteIcon = ELEMENT_MEMBERSHIP_DELETE_ICON.replace("${membership}", "member");
		click(deleteIcon);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitForTextPresent(userGroup.MSG_DELETE_MANDATORY_MEMBERSHIP);
		closeMessageDialog();
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
