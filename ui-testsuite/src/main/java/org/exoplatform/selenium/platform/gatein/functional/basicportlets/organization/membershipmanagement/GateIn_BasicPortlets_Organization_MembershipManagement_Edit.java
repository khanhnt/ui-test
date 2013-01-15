package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization.membershipmanagement;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GateIn_BasicPortlets_Organization_MembershipManagement_Edit extends PlatformBase {
	ManageAccount account;
	NavigationToolbar navigation;
	UserGroupManagement userGroup;

	public String ELEMENT_GROUP_NAME = "organization" ;
	public String ELEMENT_GROUP_LABEL = "Organization" ;
	public String USER_NAME = "demo";
	public String ELEMENT_MEMBER_SHIP= "manager"; 
	
	
	boolean SELECT = true; 
	By VERIFY_BEFORE_EDIT_ROLE = By.xpath("//td/div[@title='mary']/following::td/div[@title='manager']");

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
	public void test01_EditMembershipForUserFromExistingGroup () {
		info("--login portal--");
		account.signIn("root", "gtn");

		info("--Go to User and group--");
		//Go to user and group management page
		navigation.goToUsersAndGroupsManagement();

		//Choose group tab
		info("--Choose group tab--");
		userGroup.chooseGroupTab();

		//Add user into group
		info("--Add user into group--");
		userGroup.selectGroup(userGroup.GROUP_PLATFORM_ADMIN);
		userGroup.addUsersToGroup(USER_NAME, ELEMENT_MEMBER_SHIP, SELECT, true);
		waitForElementPresent(userGroup.ELEMENT_MEMBERSHIP_TEXT.replace("{$user}","demo").replace("{$membership}", "manager"));

		// Edit user into group
		click(userGroup.ELEMENT_EDIT_MEMBER);

		// change membership
		select(ELEMENT_SELECT_MEMBERSHIP, "member");
		save();
		waitForElementPresent(userGroup.ELEMENT_MEMBERSHIP_TEXT.replace("{$user}","demo").replace("{$membership}", "member"));

		// Delete user in group
		userGroup.deleteUserInGroup(ELEMENT_GROUP_NAME,ELEMENT_GROUP_LABEL,USER_NAME);
	}

	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}
