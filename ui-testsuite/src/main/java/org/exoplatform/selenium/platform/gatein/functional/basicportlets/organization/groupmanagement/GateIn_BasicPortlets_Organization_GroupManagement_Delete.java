package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization.groupmanagement;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;

/**
 *@author HangNTT
 *@date: 20/09/2012
 */
public class GateIn_BasicPortlets_Organization_GroupManagement_Delete extends PlatformBase {
	
	
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;
	String GROUP_NAME = "FNC_GTN_PLT_ORG_03_04_004"; 
	String GROUP_LABEL= "FNC_GTN_PLT_ORG_03_04_004"; 
	String GROUP_DESC = "FNC_GTN_PLT_ORG_03_04_004"; 

	String GROUP_NAME1 = "Platform"; 
	String USER_NAME = "demo";
	String MEMBERSHIP = "member"; 
	boolean SELECT = true; 
	By upLevel = By.xpath("//a[@title='Up Level']");

	public String CAN_NOT_DELETE_GROUP_MANDATORY = "You can't delete this group because it (or its child) is mandatory.";
	
	
	
	@BeforeMethod()
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
	}
	
	@Test(groups={"later"})
	public void test04_checkExistingOfUserInDeteledGroup () {
		info("--login portal--");
		account.signIn("root", "gtn");
		
		info("--Go to User and group--");
		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();
		
		//Choose group tab
		info("--Choose group tab--");
		userGroup.chooseGroupTab();
		
		//Add new group
		info("--Add new group --");
		userGroup.addGroup(GROUP_NAME, GROUP_LABEL, GROUP_DESC, true);
		waitForTextPresent(GROUP_NAME);
		
		//Add new user into group
		info("--Add user into group--");
		userGroup.addUsersToGroup(USER_NAME, MEMBERSHIP, SELECT, true);
		waitForTextPresent(USER_NAME);
		waitForTextPresent(MEMBERSHIP);
		
		// Go to User Management Tab
		info("--Check membership of user in a group--");
		userGroup.chooseUserTab();
		
		//Search new user
		info("--Search user--");
		userGroup.searchUser(USER_NAME, "User Name");
		
		//Edit new user
		info("--Click edit user--");
		userGroup.editUser(USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		click(ELEMENT_CANCEL_BUTTON);
		
		//Back to Group Management
		info("--back to Group management--");
		userGroup.chooseGroupTab();
		
		//Delete group
		info("--Delete group--");
		userGroup.selectGroup(GROUP_NAME);
		userGroup.deleteGroup(GROUP_NAME, true, 120000);
		
		//Back to User management tab
		info("--back to User management--");
		userGroup.chooseUserTab();
		
		//Check after delete group
		info("--Search user--");
		userGroup.searchUser(USER_NAME, "User Name");
		info("--Click edit user--");
		userGroup.editUser(USER_NAME);
		click(userGroup.ELEMENT_USER_MEMBERSHIP_TAB);
		info("--verify groupname--");
		waitForElementNotPresent(GROUP_NAME);
		click(ELEMENT_CANCEL_BUTTON);
	}
	
	@Test
	public void test10_deleteMandatoryGroup () {
		info("--login portal--");
		account.signIn("root", "gtn");
		
		info("--Go to User and group--");
		navigation.goToUsersAndGroupsManagement();
		//goToGroupsManagement();
		
		info("--Choose group tab--");
		userGroup.chooseGroupTab();
		userGroup.selectGroup(GROUP_NAME1);
		
		//Delete group is mandatory
		userGroup.deleteGroup(GROUP_NAME1, false);
		waitForMessage(CAN_NOT_DELETE_GROUP_MANDATORY);
		closeMessageDialog();
	}

	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}