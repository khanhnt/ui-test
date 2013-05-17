package org.exoplatform.selenium.platform.social.functional.space;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.ManageAccount.*;
import static org.exoplatform.selenium.platform.social.SpaceManagement.*;
import static org.exoplatform.selenium.platform.social.ManageMember.*;
import java.util.Set;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author vuna2
 * <li> Date: 03/12/2012 </li>
 * <li> Test cases: Social/Space/Member Community/Invite </li>
 */
public class SOC_SPA_MemberManagement_Invite extends PeopleConnection{
	public String admin = "john";
	public String pass = "gtn";

	public int timeToDeleteSpace = 120000;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		actions = new Actions(driver);
		driver.manage().window().maximize();
		signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- User.logout --");
		signOut();
		driver.quit();
		actions = null;
	}

	/**
	 * Case ID 01
	 * <li>Send invitation to user who is not member of space </li>
	 * <li>Step 1: Create a new space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Check invitation sending successfully</li>
	 */
	@Test
	public void test01_SendInvitationToUserWhoIsNotMemberOfSpace(){
		String spaceName = "MemberCommunityInvite01";

		String spaceDescription = "Send an invitation to user who is not a member of space";

		adminInviteUserAndCheckInvitation(true, false, spaceName, spaceDescription, null, userType.AUTHOR, true, "FNC_SOC_SPA_MEM_COM_INVITE_01");

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 02 
	 * <li>Invited user accept invitation</li>
	 * <li>Step 1: Create a new space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Invited user accepts invitation</li>
	 */
	@Test
	public void test02_InvitedUserAcceptInvitation(){
		String spaceName = "MemberCommunityInvite02";

		String spaceDescription = "Invited user accept the invitation";

		adminInviteUserAndUserAcceptInvitation(true, true, false, spaceName, spaceDescription, null, userType.PUBLISHER);

		// invited user was removed from the invited list to member list
		managerGoToMemberListTab(userType.ADMIN, userType.PUBLISHER, spaceName);

		// admin can see the profile of the new member: Mary	
		goToUserProfile("Mary Williams", "FNC_SOC_SPA_MEM_COM_INVITE_02");

		restoreData(spaceName, timeToDeleteSpace);		
	}

	/**
	 * Case ID 03
	 * <li>Invited user denies invitation</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Invited user denies invitation</li>
	 */
	@Test
	public void test03_InvitedUserDeniesInvitation(){
		String spaceName = "MemberCommunityInvite03";

		String spaceDescription = "Invited user denies invitation";

		//adminInviteUserAndUserIgnoreInvitation(true, false, spaceName, spaceDescription, null, userType.AUTHOR);
		adminInviteUserAndUserAcceptInvitation(false, true, false, spaceName, spaceDescription, null, userType.AUTHOR);
		
		goToAllSpaces();

		waitForTextPresent(spaceName);

		waitForElementPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));

		managerGoToMembersTab(userType.ADMIN, spaceName);

		waitForElementNotPresent(ELEMENT_INVITED_TABLE + "//td[contains(text(),'James')]");

		restoreData(spaceName, 180000);
	} 

	/**
	 * Case ID 04
	 * Manager cancels the invitation
	 */
	@Test
	public void test04_ManagerCancelsTheInvitation(){
		String spaceName = "MemberCommunityInvite04";

		String spaceDescription = "Manager cancels the invitation";

		info("-- Step 1 & 2: Create a new Space & Send invitation --");

		managerAddNewSpaceAndInviteUser(true, false, spaceName, spaceDescription, null, userType.PUBLISHER);

		//Save cookies for browser 1
		Set<Cookie> cookies1 = getBrowserCookies();

		String handlesBefore = driver.getWindowHandle();

		openNewBrowser();

		info("-- Verify that the invitation is sent successfully --");

		checkInvitation(userType.PUBLISHER, spaceName, true, "FNC_SOC_SPA_MEM_COM_INVITE_04");

		//Save cookies for browser 2
		Set<Cookie> cookies2 = getBrowserCookies();

		String handlesAfter = driver.getWindowHandle();

		info("-- Step 3: Cancel the invitation --");

		backToPreviousBrowser(cookies1, handlesBefore);

		cancelRequest("Mary Williams");

		backToPreviousBrowser(cookies2, handlesAfter);

		goToAllSpaces();

		waitForElementPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));

		backToPreviousBrowser(cookies1, handlesBefore);

		restoreData(spaceName, 180000);
	}

	/**
	 * Case ID 05
	 * <li>Send invitation from Space at Visible mode and open for registration</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>step 3: Check invitation sending successfully</li>
	 */
	@Test
	public void test05_SendInvitationFromSpaceAtVisibleModeAndOpenForRegistration(){
		String spaceName = "MemberCommunityInvite05";

		String spaceDescription = "Visible mode and open for registration";

		String[] advanceParam = {"Visible", "Open", "", ""};

		adminInviteUserAndCheckInvitation(false, true, spaceName, spaceDescription, advanceParam, userType.DEVELOPER, true, "FNC_SOC_SPA_MEM_COM_INVITE_05");

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 06
	 * <li>Send invitation from Space at Visible mode and validation for registration</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Check invitation sending successfully</li>
	 */
	@Test
	public void test06_SendInvitationFromSpaceAtVisibleModeAndValidationForRegistration(){
		String spaceName = "MemberCommunityInvite06";

		String spaceDescription = "Visible mode and validation for registration";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		adminInviteUserAndCheckInvitation(false, true, spaceName, spaceDescription, advanceParam, userType.PUBLISHER, true, "FNC_SOC_SPA_MEM_COM_INVITE_06");

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 07
	 * <li>Send invitation from Space at Visible mode and close for registration</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Check invitation sending successfully</li>
	 */
	@Test
	public void test07_SendInvitationFromSpaceAtVisibleModeAndCloseForRegistration(){
		String spaceName = "MemberCommunityInvite07";

		String spaceDescription = "Visible mode and Close for registration";

		String[] advanceParam = {"Visible", "Close", "", ""};

		adminInviteUserAndCheckInvitation(false, true, spaceName, spaceDescription, advanceParam, userType.PUBLISHER, true, "FNC_SOC_SPA_MEM_COM_INVITE_07");

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 08
	 * <li>Send invitation from Space at hidden mode and open for registration</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Check invitation sending successfully</li>
	 */
	@Test
	public void test08_SendInvitationFromSpaceAtHiddenModeAndOpenForRegistration(){
		String spaceName = "MemberCommunityInvite08";

		String spaceDescription = "Hidden mode and Open for registration";

		String[] advanceParam = {"Hidden", "Open", "", ""};

		adminInviteUserAndCheckInvitation(false, true, spaceName, spaceDescription, advanceParam, userType.AUTHOR, true, "FNC_SOC_SPA_MEM_COM_INVITE_08");

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 09
	 * Send invitation from Space at hidden mode and Validation for registration
	 * --> Refer to Case ID 08 (with the hidden mode: Registration value will not take effect)
	 */
	/*@Test
	public void test09_SendInvitationFromSpaceAtHiddenModeAndValidationForRegistration(){

	}*/

	/**
	 * Case ID 10
	 * Send invitation from Space at hidden mode and close for registration
	 * --> Refer to Case ID 08 (with the hidden mode: Registration value will not take effect)
	 */
	/*@Test
	public void test10_SendInvitationFromSpaceAtHiddenModeAndCloseForRegistration(){

	}*/

	/**
	 * Case ID 11
	 * Send invitation to Root
	 */
	@Test
	public void test11_SendInvitationToRoot(){
		String spaceName = "MemberCommunityInvite11";

		String spaceDescription = "Send an invitation to user Root";

		info("-- Step 1 & 2: Create a new Space & Send invitation--");

		managerAddNewSpaceAndInviteUser(true, false, spaceName, spaceDescription, null, userType.ROOT);

		info("-- Step 3: Check user's list--");

		click(ELEMENT_MEMBERS_TAB_IN_SPACE_MENU);

		waitForTextPresent("Root Root");

		captureScreen("FNC_SOC_SPA_MEM_COM_INVITE_11");

		restoreData(spaceName, timeToDeleteSpace);
	}

	/**
	 * Case ID 12
	 * <li>Send invitation to a member of space</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 */
	@Test
	public void test12_SendInvitationToMemberOfSpace(){
		String spaceName = "MemberCommunityInvite12";

		String spaceDescription = "Send an invitation to member of space";

		info("-- User: Jack accept the invitation to join this space --");

		adminInviteUserAndUserAcceptInvitation(true, true, false, spaceName, spaceDescription, null, userType.DEVELOPER);

		info("-- Admin: John select the user (Jack) who is a member of the space --");

		managerReInviteUser(userType.ADMIN, spaceName, false, "Jack");

		waitForMessage(MESSAGE_USER_EXISTED_IN_SPACE.replace("${username}", "demo"));

		closeMessageDialog();

		restoreData(spaceName, timeToDeleteSpace);
	}

	/**
	 * Case ID 13
	 * <li>Send invitation to users who are existing in inviting list</li>
	 * <li>Step 1: Create the private space</li>
	 * <li>Step 2: Send invitation</li>
	 * <li>Step 3: Send invitation to users who are existing in inviting list</li>
	 */
	@Test
	public void test13_SendInvitationToUsersWhoAreExistingInInvitingList(){
		String spaceName = "MemberCommunityInvite13";

		String spaceDescription = "users exist in inviting list";

		adminInviteUserAndCheckInvitation(true, false, spaceName, spaceDescription, null, userType.PUBLISHER, true, "FNC_SOC_SPA_MEM_COM_INVITE_13");

		info("-- Admin: John select the user (Mary) who is in inviting list of the space --");

		managerReInviteUser(userType.ADMIN, spaceName, false, "Mary");

		waitForMessage(MESSAGE_USER_EXISTED_IN_INVITING_LIST.replace("${username}", "mary"));

		closeMessageDialog();

		restoreData(spaceName, timeToDeleteSpace);
	}

	/**
	 * Case ID 22
	 * <li>User, who is not member of the space, sends request</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Users send request</li>
	 * <li>Step 3: Check request sending successfully</li>
	 */
	@Test
	public void test22_UserWhoIsNotMemberOfTheSpaceSendsRequest(){
		String spaceName = "MemberCommunityInvite22";

		String spaceDescription = "user is not member of the space";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		managerAddNewSpaceAndUserSendRequest(false, true, spaceName, spaceDescription, advanceParam, userType.AUTHOR);

		managerGoToMembersTab(userType.ADMIN, spaceName);

		waitForElementPresent(ELEMENT_PENDING_TABLE + "//td[contains(text(),'James')]");

		restoreData(spaceName, timeToDeleteSpace);
	}

	/**
	 * Case ID 23
	 * <li>The Manager of Space declines the request</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Users send request</li>
	 * <li>Step 3: The Manager of Space declines the request</li>
	 */
	@Test
	public void test23_TheManagerOfSpaceDeclinesTheRequest(){
		String spaceName = "MemberCommunityInvite23";

		String spaceDescription = "Manager of Space declines the request";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		managerAddNewSpaceAndUserSendRequest(false, true, spaceName, spaceDescription, advanceParam, userType.AUTHOR);

		//managerDeclineRequestFromUser(userType.ADMIN, userType.AUTHOR, spaceName);
		managerAcceptRequestFromUser(false, userType.ADMIN, userType.AUTHOR, spaceName);

		userGoToAllSpacesPage(userType.AUTHOR);

		waitForElementPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 24
	 * <li>The Manager of Space validates the request</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Users send request</li>
	 * <li>Step 3: The Manager of Space Accepts the request</li>
	 * <li>Step 4: Check accept request</li>
	 */
	@Test
	public void test24_TheManagerOfSpaceValidatesTheRequest(){
		String spaceName = "MemberCommunityInvite24";

		String spaceDescription = "Manager of Space validates the request";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		managerAddNewSpaceAndUserSendRequest(false, true, spaceName, spaceDescription, advanceParam, userType.PUBLISHER);

		managerAcceptRequestFromUser(true, userType.ADMIN, userType.PUBLISHER, spaceName);

		userGoToAllSpacesPage(userType.PUBLISHER);

		waitForElementPresent(ELEMENT_INVITATION_LEAVE_LINK_IN_ALL_SPACES.replace("${spaceName}", spaceName));

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 25
	 * User cancel request
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Users send request</li>
	 * <li>Step 3: User cancel request</li>
	 */
	@Test
	public void test25_UserCancelRequest(){
		String spaceName = "MemberCommunityInvite25";

		String spaceDescription = "User cancel request";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		managerAddNewSpaceAndUserSendRequest(false, true, spaceName, spaceDescription, advanceParam, userType.PUBLISHER);

		managerGoToMembersTab(userType.ADMIN, spaceName);

		waitForElementPresent(ELEMENT_PENDING_TABLE + "//td[contains(text(),'Mary')]");

		userCancelRequest(userType.PUBLISHER, spaceName);

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 26
	 * <li>Send request to Space at Visible mode and open for registration</li>
	 * <li>Step 1: create a new space at the visible and open mode</li>
	 * <li>Step 2: Send request</li>
	 * <li>Step 3: Check request sending successfully</li>
	 */
	@Test
	public void test26_SendRequestToSpaceAtVisibleModeAndOpenForRegistration(){
		String spaceName = "MemberCommunityInvite26";

		String spaceDescription = "Visible mode and open for registration";

		String[] advanceParam = {"Visible", "Open", "", ""};

		managerAddNewOpenSpaceAndUserJoinSpace(false, true, spaceName, spaceDescription, advanceParam, userType.DEVELOPER);

		managerGoToMemberListTab(userType.ADMIN, userType.DEVELOPER, spaceName);

		goToUserProfile("Jack Miller", "FNC_SOC_SPA_MEM_COM_INVITE_26");

		restoreData(spaceName, timeToDeleteSpace);		
	}

	/**
	 * Case ID 27
	 * <li>Send request to Space at Visible mode and Validation for registration</li>
	 * <li>Step 1: create a new space at the visible and Validation mode</li>
	 * <li>Step 2: Send request</li>
	 * <li>Step 3: Check request sending successfully</li>
	 */
	@Test
	public void test27_SendRequestToSpaceAtVisibleModeAndValidationForRegistration(){
		String spaceName = "MemberCommunityInvite27";

		String spaceDescription = "Visible mode and Validation for registration";

		String[] advanceParam = {"Visible", "Validation", "", ""};

		managerAddNewSpaceAndUserSendRequest(false, true, spaceName, spaceDescription, advanceParam, userType.AUTHOR);

		managerGoToMembersTab(userType.ADMIN, spaceName);

		waitForElementPresent(ELEMENT_PENDING_TABLE + "//td[contains(text(),'James')]");

		restoreData(spaceName, timeToDeleteSpace);
	}

	/**
	 * Case ID 28
	 * <li>Send request to Space at Visible mode and Close for registration</li>
	 * <li>Step 1: create a new space at the visible and Close mode</li>
	 * <li>Step 2: Send request</li>
	 */
	@Test
	public void test28_SendRequestToSpaceAtVisibleModeAndCloseForRegistration(){
		String spaceName = "MemberCommunityInvite28";

		String spaceDescription = "Visible mode and Close for registration";

		goToMySpacePage();

		addNewSpace(spaceName, spaceDescription, "Visible", "Close", "", "");

		userGoToAllSpacesPage(userType.DEVELOPER);

		waitForElementNotPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));

		managerInviteUserToJoinSpace(userType.ADMIN, spaceName, userType.DEVELOPER);

		userGoToAllSpacesPage(userType.DEVELOPER);

		waitForElementPresent(ELEMENT_INVITATION_ACCEPT_LINK_IN_ALL_SPACES.replace("${spaceName}", spaceName));

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 29
	 * <li>Send request to Space at hidden mode and open for registration</li>
	 * <li>Step 1: create a new space at the hidden and open mode</li>
	 * <li>Step 2: Send request</li>
	 */
	@Test
	public void test29_SendRequestToSpaceAtHiddenModeAndOpenForRegistration(){
		String spaceName = "MemberCommunityInvite29";

		String spaceDescription = "Hidden mode and Open for registration";

		goToMySpacePage();

		addNewSpace(spaceName, spaceDescription, "Hidden", "Open", "", "");

		userGoToAllSpacesPage(userType.AUTHOR);

		waitForTextNotPresent(spaceName);

		waitForElementNotPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));

		resetDataMemberManagementInvite(spaceName);
	}

	/**
	 * Case ID 30
	 * <li>Send request to Space at hidden mode and validation for registration</li>
	 * --> Refer to Case ID 29 (with the hidden mode: Registration value will not take effect)
	 */
	/*@Test
	public void test30_SendRequestToSpaceAtHiddenModeAndValidationForRegistration(){

	}*/

	/**
	 * Case ID 31
	 * <li>Send request to Space at hidden mode and close for registration</li>
	 * --> Refer to Case ID 29 (with the hidden mode: Registration value will not take effect)
	 */
	/*@Test
	public void test31_SendRequestToSpaceAtHiddenModeAndCloseForRegistration(){

	}*/

	/**
	 * Case ID 32
	 * <li>Add user into Space group</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Add new page with organization management portlet</li>
	 * <li>Step 3: Add user into space group</li>
	 */
	@Test
	public void test32_AddUserIntoSpaceGroup(){
		String spaceName = "membercommunityinvite32";

		String spaceDescription = "Add user into Space group";

		managerAddNewSpaceAndAddUserInToSpace(true, false, spaceName, spaceDescription, null, "mary", "member");

		userGoToAllSpacesPage(userType.PUBLISHER);

		waitForElementPresent(ELEMENT_INVITATION_LEAVE_LINK_IN_ALL_SPACES.replace("${spaceName}", spaceName));

		resetDataMemberManagementInvite(spaceName);		
	}

	/**
	 * Case ID 33
	 * <li>Add member with membership is manager, into space group</li>
	 * <li>Step 1: Create a new Space</li>
	 * <li>Step 2: Add new page with organization management portlet</li>
	 * <li>Step 3: Add member with membership is manager, into space group</li>
	 */
	@Test
	public void test33_AddMemberWithMembershipIsManagerIntoSpaceGroup(){
		String spaceName = "membercommunityinvite33";

		String spaceDescription = "Add member with membership is manager";

		managerAddNewSpaceAndAddUserInToSpace(true, false, spaceName, spaceDescription, null, "james", "manager");

		//James becomes the manager of the space
		userGoToAllSpacesPage(userType.AUTHOR);

		waitForElementPresent(ELEMENT_INVITATION_LEAVE_LINK_IN_ALL_SPACES.replace("${spaceName}", spaceName));

		restoreData(spaceName, timeToDeleteSpace);		
	}

	//-----  Common code (only) for these cases -----//
	public void goToUserProfile(String userName, String imageFileName){
		click(By.linkText(userName));
		waitForTextPresent("Basic information");
		captureScreen(imageFileName);
	}

	public void resetDataMemberManagementInvite(String spaceName){
		signOut();
		signIn(admin, pass);
		restoreData(spaceName, 180000);
	}
}
