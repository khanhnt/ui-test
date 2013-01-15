package org.exoplatform.selenium.platform.gatein.functional.registration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;

import org.exoplatform.selenium.platform.ManageAccount;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author VuNA
 *@date: 01/10/2012
 */
public class GateIn_Registration_PublicMode extends PlatformBase {

	
	UserGroupManagement userGroup;
	ManageAccount account;
	NavigationToolbar navigation;
	PageManagement page;
	NavigationManagement naviManage;
	String password = "exoplatform"; 
	String confirmPassword = "exoplatform"; 
	String firstName = "Exo";
	String lastName = "Sea";
	String email = "exosea@platform.com";
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

		String username = "userexocase01";

		// Disable use captcha to run automated test case
		account.signIn("root", "gtn");
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		setUseCaptcha(false, false);
		account.signOut();

		//Start a test case
		info("-- Step 1: Show Account register form --");
		click(account.ELEMENT_REGISTER_ACCOUNT_LINK);
		//update later
		waitForTextPresent(navigation.ELEMENT_REGISTER_TEXT);

		info("-- Step 2: Complete adding new account with valid values --");
		account.addNewUserAccountInPublicMode(username, password, confirmPassword, firstName, lastName, email, true);

		info("-- Step 3: Check registered accounts list after adding new --");
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		navigation.goToUsersAndGroupsManagement();
		userGroup.searchUser(username, searchOption);

		info("-- Restore Original data values after testing --");
		userGroup.deleteUser(username);
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		setUseCaptcha(true, false);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 003
	 *-- Create new account with user name the same with existing but different by lower/upper case
	 *-- Case N/A
	 * */

	/*-- Case ID 008
	 *-- Create new account with User name the same with existing
	 * */
	@Test(groups={"later"})
	public void test08_CreateNewAccountWithUserNameTheSameWithExisting(){
		String username = "userexocase02";

		// Disable use captcha to run automated test case
		account.signIn("root", "gtn");
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		setUseCaptcha(false, false);
		account.signOut();

		info("-- Step 1: Create the first account --");
		click(account.ELEMENT_REGISTER_ACCOUNT_LINK);
		//will update later
		waitForTextPresent(navigation.ELEMENT_REGISTER_TEXT);
		account.addNewUserAccountInPublicMode(username, password, confirmPassword, firstName, lastName, email, true);

		info("-- Step 2: Create same User name account --");
		account.addNewUserAccountInPublicMode(username, password, confirmPassword, firstName, lastName, "email@exo.com", false);
		waitForMessage(account.MESSAGE_DUPLICATE_ACCOUNT);
		closeMessageDialog();

		info("-- Restore Original data values after testing --");
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(username);
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		setUseCaptcha(true, false);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 012
	 *-- Create new account when copy from Password and paste to Confirm Password field.
	 * */
	@Test(groups={"later"})
	public void test12_CreateNewAccountWhenCopyFromPasswordAndPasteToConfirmPasswordField(){

		String username = "userexocase12";

		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		// Disable use captcha to run automated test case
		setUseCaptcha(false, false);

		info("-- Step 2: Create new account --");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		copyPaste(ELEMENT_INPUT_PASSWORD, password, account.ELEMENT_INPUT_CONFIRM_PASSWORD_PUBLIC_MODE);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(account.ELEMENT_INPUT_EMAIL_PUBLIC_MODE, email, true);
		click(account.ELEMENT_SUBSCRIBE_BUTTON);
		waitForMessage(account.MESSAGE_ALERT_PASSWORD);
		closeMessageDialog();
		click(account.ELEMENT_RESET_BUTTON);

		info("-- Restore Original data values after testing --");
		navigation.goToEditPageEditor();
		setUseCaptcha(true, false);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}

	/*-- Case ID 024
	 *-- Create new account with invalid format Email address 
	 */
	@Test(groups={"later"})
	public void test24_CreateNewAccountWithInvalidFormatEmailAddress(){
		String username = "userexocase24";

		account.signIn("root", "gtn");

		info("-- Step 1: Show register new account form --");
		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		// Disable use captcha to run automated test case
		setUseCaptcha(false, false);

		info("-- Add new account with invalid format Email address --");
		account.addNewUserAccountInPublicMode(username, password, confirmPassword, firstName, lastName, "exo.com", false);
		waitForMessage(account.MESSAGE_INVALID_EMAIL_ADDRESS);
		closeMessageDialog();
		click(account.ELEMENT_RESET_BUTTON);

		info("-- Restore Original data values after testing --");
		navigation.goToEditPageEditor();
		setUseCaptcha(true, false);

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();
	}
}