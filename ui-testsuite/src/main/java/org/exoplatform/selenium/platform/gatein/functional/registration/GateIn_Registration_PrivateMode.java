package org.exoplatform.selenium.platform.gatein.functional.registration;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.UserGroupManagement;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PortalManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author VuNA
 *@date: 26/09/2012
 */
public class GateIn_Registration_PrivateMode extends PlatformBase{

	UserGroupManagement userGroup;
	ManageAccount account;
	NavigationToolbar navigation;
	PageManagement page;
	NavigationManagement naviManage;
	PortalManagement portal;
	String password = "exoplatform"; 
	String confirmPassword = "exoplatform"; 
	String firstName = "first"; 
	String lastName = "last name"; 
	String email = "testaccount5@platform.com"; 
	String userNameGiven = ""; 
	String language = "English"; 
	boolean verify = true;
	String searchOption = "User Name";

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		portal = new PortalManagement(driver);
		userGroup = new UserGroupManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*-- Case ID 001
	 *-- Create new account with valid values  
	 * --*/
	@Test(groups={"later"})
	public void test01_CreateNewAccountWithValidValues(){
		String username = "valid_value"; 

		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToNewStaff();

		info("-- Step 2: Complete adding new account with valid values --");
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,verify);

		info("-- Step 3: Check registered accounts list after adding new --");
		navigation.goToUsersAndGroupsManagement();
		userGroup.searchUser(username, searchOption);

		info("-- Delete data after testing--");
		userGroup.deleteUser(username);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 003
	 *-- Create new account with user name the same with existing but different by lower/upper case  
	 *-- Case N/A
	 * --*/

	/*-- Case ID 008
	 *-- Create new account with User name the same with existing  
	 * --*/
	@Test(groups={"later"})
	public void test08_CreateNewAccountWithUserNameTheSameWithExisting(){
		String username = "existing_account2"; 
		account.signIn("root", "gtn");

		info("-- Step 1: Register the first account --");
		navigation.goToNewStaff();
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,verify);

		info("-- Step 2: Create same User name account --");
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,false);
		waitForMessage(account.MESSAGE_DUPLICATE_ACCOUNT);
		closeMessageDialog();

		info("-- Delete data after testing--");
		navigation.goToUsersAndGroupsManagement();
		userGroup.searchUser(username, searchOption);
		userGroup.deleteUser(username);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 012
	 *-- Create new account when copy from Password and paste to Confirm Password field  
	 * --*/
	@Test
	public void test12_CreateNewAccountWhenCopyFromPasswordAndPasteToConfirmPasswordField(){
		String username = "copy_paste_pass"; 
		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToNewStaff();

		info("-- Step 2: Create new User account --");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		copyPaste(ELEMENT_INPUT_PASSWORD, password, ELEMENT_INPUT_CONFIRM_PASSWORD);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(ELEMENT_INPUT_EMAIL, email, true);
		save();
		waitForMessage(account.MESSAGE_ALERT_PASSWORD);
		closeMessageDialog();

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 024
	 *-- Create new account with invalid format Email address 
	 * --*/
	@Test(groups={"later"})
	public void test24_CreateNewAccountWithInvalidFormatEmailAddress(){
		String username = "invalidemail"; 
		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToNewStaff();

		info("-- Step 2: Create new User account --");
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,"email.platform",userNameGiven,language,false);
		waitForMessage(account.MESSAGE_INVALID_EMAIL_ADDRESS);
		closeMessageDialog();

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 028
	 *-- Create new account with Email address existing 
	 * --*/
	@Test(groups={"later"})
	public void test28_CreateNewAccountWithEmailAddressExisting(){
		String username = "existing_email";
		String usernameTest = "new_existing_email";
		String messageDuplicateEmailAddress = "This email already exists, please enter a different address.";
		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToNewStaff();

		info("-- Step 2: Add new account --");
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,verify);

		info("-- Step 3: Add new account with Email existing --");
		account.addNewUserAccount(usernameTest,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,false);
		waitForMessage(messageDuplicateEmailAddress);
		closeMessageDialog();

		info("-- Delete data after testing--");
		navigation.goToUsersAndGroupsManagement();
		userGroup.searchUser(username, searchOption);
		userGroup.deleteUser(username);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}
}