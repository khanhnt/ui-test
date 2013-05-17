package org.exoplatform.selenium.platform.social.functional.space;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.ManageAccount.*;
import static org.exoplatform.selenium.platform.social.SpaceManagement.*;
import org.exoplatform.selenium.platform.social.ManageMember;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author: hangNTT
 * Date: 21/11/2012
 * @Manage Member: Leave space
 */
public class SOC_SPA_MemberManagement_Leave extends ManageMember{

	public static String DATA_USER1 = "john";
	public static String DATA_USER2 = "James";
	public static String DATA_PASS = "gtn";

	public static String SPACE_NAME1 ="space1";
	public static String SPACE_NAME2 ="space2";
	public static String SPACE_NAME3 ="space3";
	public static String SPACE_NAME4 ="space4";
	public static String SPACE_NAME5 ="space5";
	public static String SPACE_NAME7 ="space7";
	public static String SPACE_NAME8 ="space8";
	public static String SPACE_NAME9 ="space9";
	
	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		actions = new Actions(driver);
		signIn(DATA_USER1, DATA_PASS);
	}
	
	//Members leave the space (by invited)
	@Test
	public void test01_memberLeaveTheSpace(){
		info("-- Creating a new space --");
		
		goToMySpacePage();

		addNewSpace(SPACE_NAME1, "description of space1",120000);

		info("Invite a user to space");

		goToSettings();

		goToMembers();

		inviteSingleUser(userType.AUTHOR);

		userAcceptInvitationToJoinSpace(true, userType.AUTHOR, SPACE_NAME1);

		doAction("Leave", SPACE_NAME1);

		waitForTextPresent("Spaces Found: 0.");

		signOut();

		signIn(DATA_USER1, DATA_PASS);

		restoreData(SPACE_NAME1, 180000);
	}
	
	//Root leave the space
	@Test
	public void test02_rootLeaveTheSpace(){
		info("-- Creating a new space --");

		goToMySpacePage();

		addNewSpace(SPACE_NAME2, "description of space2",60000);

		info("Invite a user to space");

		goToSettings();

		goToMembers();

		inviteSingleUser(userType.ROOT);

		signOut();

		signIn("root", "gtn");

		goToMySpacePage();

		doAction("Leave", SPACE_NAME2);

		waitForTextPresent("Spaces Found: 1. Results displayed in order of alphabet.");

		restoreData(SPACE_NAME2,120000);
	}
	
	//Manager of space leaves space when only has one member in space
	@Test
	public void test03_managerOfSpaceLeaveTheSpaceWhenOnlyHasOneMemberInSpace(){
		info("-- Creating a new space --");

		goToMySpacePage();

		addNewSpace(SPACE_NAME3, "description of space3",60000);

		goToMySpacePage();

		doAction("Leave", SPACE_NAME3);

		waitForTextPresent(VERIFY_MESSAGE);

		closeMessageDialog();

		restoreData(SPACE_NAME3,180000);
	}
	
	//Make a user to become the manager of the space
	@Test
	public void test04_makeAUserToBecomesTheManagerOfTheSpace(){
		info("-- Creating a new space --");

		goToMySpacePage();

		addNewSpace(SPACE_NAME4, "description of space4",60000);

		info("Invite a user to space");

		goToSettings();

		goToMembers();

		inviteSingleUser(userType.AUTHOR);

		userAcceptInvitationToJoinSpace(true, userType.AUTHOR, SPACE_NAME4);

		signOut();

		signIn(DATA_USER1, DATA_PASS);

		grantManagerForUser(SPACE_NAME4,DATA_USER2);

		signOut();

		signIn("james", "gtn");

		goToMySpacePage();

		doAction("Edit", SPACE_NAME4);

		waitForElementPresent(ELEMENT_SETTINGS);

		waitForElementPresent(ELEMENT_ACCESS_AND_EDIT_TAB);

		waitForElementPresent(ELEMENT_MEMBERS_TAB);

		waitForElementPresent(ELEMENT_APPLICATIONS_TAB);

		waitForElementPresent(ELEMENT_NAVIGATION_TAB);

		info("Delete space");

		restoreData(SPACE_NAME4,180000);
	}
	
	//Remove A Manager
	@Test
	public void test05_removeAManager(){
		info("-- Creating a new space --");

		goToMySpacePage();

		addNewSpace(SPACE_NAME5, "description of space5",60000);

		info("Invite a user to space");

		goToSettings();

		goToMembers();

		inviteSingleUser(userType.AUTHOR);

		userAcceptInvitationToJoinSpace(true, userType.AUTHOR, SPACE_NAME5);

		signOut();

		signIn(DATA_USER1, DATA_PASS);

		grantManagerForUser(SPACE_NAME5, DATA_USER2);
		
		signOut();

		signIn("james", "gtn");

		goToMySpacePage();

		doAction("Edit", SPACE_NAME5);

		waitForElementPresent(ELEMENT_SETTINGS);

		waitForElementPresent(ELEMENT_ACCESS_AND_EDIT_TAB);

		waitForElementPresent(ELEMENT_MEMBERS_TAB);

		waitForElementPresent(ELEMENT_APPLICATIONS_TAB);

		waitForElementPresent(ELEMENT_NAVIGATION_TAB);

		signOut();

		info("Remove manager of user");

		signIn(DATA_USER1, DATA_PASS);

		revokeManagerForUser (SPACE_NAME5, DATA_USER2);
		
		info("Delete space");

		restoreData(SPACE_NAME5,120000);
	}

	//Sends request after invitation
	@Test
	public static void test07_sendRequestAfterInvitation(){    
		
		goToMySpacePage();

		addNewSpace(SPACE_NAME7, "description of space7", 60000);

		//Get and store all cookies of current browser
		Set<Cookie> cookies1 = getBrowserCookies();

		//Open new browser by Java script
		String handlesBefore = driver.getWindowHandle();

		openNewBrowser();

		//Sign with new user to remove connection
		signIn("james", DATA_PASS);

		goToAllSpaces();

		//Set cookies for browser 2
		Set<Cookie> cookies2 = driver.manage().getCookies();
		String handlesAfter = driver.getWindowHandle();

		//Add cookies back to previous browser: 1
		backToPreviousBrowser(cookies1, handlesBefore);

		goToSettings();

		goToMembers();

		inviteSingleUser(userType.AUTHOR);

		//Add cookies back to previous browser: 2
		backToPreviousBrowser(cookies2, handlesAfter);

		requestToJoin(SPACE_NAME7);
		
		doAction("Accept", SPACE_NAME7);
		
		click(By.linkText(SPACE_NAME7));
		
		verifyUserJoinedSpace(userType.AUTHOR);

		//Add cookies back to previous browser: 1
		backToPreviousBrowser(cookies1, handlesBefore);

		restoreData(SPACE_NAME7,120000);
	}
	
	//Sends invitation after request
	@Test
	public static void test08_sendInvatationAfterRequests(){

		goToMySpacePage();

		addNewSpace(SPACE_NAME8, "description of space8",60000);

		goToSettings();

		goToMembers();

		selectUser("James");

		//Get and store all cookies of browser1
		Set<Cookie> cookies1 = getBrowserCookies();

		//Open new browser by Java script
		String handlesBefore = driver.getWindowHandle();

		//At browser2
		openNewBrowser();

		//Sign with new user to remove connection
		signIn("james", DATA_PASS);

		goToAllSpaces();

		requestToJoin(SPACE_NAME8);
		
		//Add cookies back to previous browser
		backToPreviousBrowser(cookies1, handlesBefore);
		
		click(ELEMENT_INVITE_MEMBER_BUTTON);

		waitForElementPresent(ELEMENT_PENDING_TABLE);

		restoreData(SPACE_NAME8,120000);
	}
	
	//User Leave space
	@Test
	public void test09_memberLeaveSpace(){

		info("-- Creating a new space --");

		goToMySpacePage();

		addNewSpace(SPACE_NAME9, "description of space9",60000);

		signOut();

		info("Remove manager of user");

		signIn("james", DATA_PASS);

		userRequestToJoinSpace(userType.AUTHOR, SPACE_NAME9);
		
		//signOut();

		info("Login with user which created space at step 1");

		//signIn(DATA_USER1, DATA_PASS);

		managerAcceptRequestFromUser(true, userType.ADMIN, userType.AUTHOR, SPACE_NAME9);
		
		signOut();

		info("Login with user is member of space");

		signIn("james", DATA_PASS);

		goToMySpacePage();

		info("member leave space");

		doAction("Leave", SPACE_NAME9);

		signOut();

		info("Delete space");

		signIn(DATA_USER1, DATA_PASS);

		restoreData(SPACE_NAME9,180000);
	}
	
	@AfterMethod
	public void afterMethods(){
		info("-- Logout --");
		signOut();
		driver.quit();
		actions = null;
	}
}