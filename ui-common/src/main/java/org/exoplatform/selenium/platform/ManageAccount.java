package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ManageAccount extends PlatformBase {

	//[Create a New Account] Screen (Public Mode)
	public final By ELEMENT_SUBSCRIBE_BUTTON = By.xpath(".//*[@id='UIRegisterForm']/div[2]/div/div/a[1]");
	public final By ELEMENT_RESET_BUTTON = By.xpath(".//*[@id='UIRegisterForm']/div[2]/div/div/a[2]");
	public final By ELEMENT_REGISTER_LINK = By.xpath("//b[contains(text(),'Register')]");
	public final By ELEMENT_INPUT_CONFIRM_PASSWORD_PUBLIC_MODE = By.id("confirmPassword");
	public final By ELEMENT_INPUT_EMAIL_PUBLIC_MODE = By.id("emailAddress");
	public final By ELEMENT_REGISTER_ACCOUNT_LINK = By.xpath("//b[contains(text(),'Register')]");

	public  final String MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT = "You have successfully registered a new account!";
	public  final String MESSAGE_DUPLICATE_ACCOUNT = "This username already exists, please enter another one.";
	public  final String MESSAGE_ALERT_PASSWORD = "Password and Confirm Password must be the same.";
	public  final String MESSAGE_INVALID_EMAIL_ADDRESS = "Your email address is invalid. Please enter another one.";
	public  final String MSG_SIGN_IN_FAILED ="Sign in failed. Wrong username or password.";
	public  final String MSG_UPDATE_USER = "The user profile has been updated.";
	

	public ManageAccount(WebDriver dr){
		driver = dr;
	}

	//Sign-in function for eXoGTN
	public void signIn(String username, String password) {
		info("--Sign in as " + username + "--");
		if (waitForAndGetElement(ELEMENT_GO_TO_PORTAL,30000,0) != null ){
			click(ELEMENT_GO_TO_PORTAL);		
		}
		click(ELEMENT_SIGN_IN_LINK);
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		click(ELEMENT_SIGN_IN_CONFIRM_BUTTON);
		waitForElementNotPresent(ELEMENT_SIGN_IN_CONFIRM_BUTTON);
	}

	//Sign-out for eXoGTN
	public void signOut(){
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		click(ELEMENT_SIGN_OUT_LINK);
		pause(1000);
		if ( ExpectedConditions.alertIsPresent() != null ){
			acceptAlert();
		}
		//driver.get(baseUrl);
	}

	// Edit user in My Account
	// Hover [user name] -> My Account
	public void editUserInMyAccount(String firstName, String lastName, String email, String currentPassword, String newPassword,
			String confirmNewPassword){
		info("-- Edit user in My Account --");

		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(ELEMENT_INPUT_EMAIL, email, true);
		click(ELEMENT_CHANGE_PASSWORD_TAB);
		waitForTextPresent("Current Password:");
		
		type(ELEMENT_INPUT_CURRENTPASSWORD, currentPassword, true);
		type(ELEMENT_INPUT_NEW_PASSWORD_MYACCOUNT, newPassword, true);
		type(ELEMENT_INPUT_NEW_CONFIRM_PASSWORD_MYACCOUNT, confirmNewPassword, true);
		click(ELEMENT_ACCOUNT_PROFILE_TAB);

		save();	
		waitForMessage("The account information has been updated.");
		closeMessageDialog();
		close();
	}

	// Add a new user account
	// setting -> user -> add users
	public void addNewUserAccount(String username, String password, String confirmPassword, String firstName, 
			String lastName, String email, String userNameGiven, String language, boolean verify) {

		info("--Create a new user using \"New Staff\" portlet--");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		type(ELEMENT_INPUT_CONFIRM_PASSWORD, confirmPassword, true);
		type(ELEMENT_INPUT_FIRSTNAME_ADD, firstName, true);
		type(ELEMENT_INPUT_LASTNAME_ADD, lastName, true);
		type(ELEMENT_INPUT_EMAIL_ADD, email, true);
		click(ELEMENT_USER_PROFILE_TAB);
		waitForTextPresent("Given Name:");
		type(ELEMENT_INPUT_USER_NAME_GIVEN, userNameGiven, true);
		select(ELEMENT_SELECT_USER_LANGUAGE, language);
		click(ELEMENT_ACCOUNT_SETTING_TAB);
		save();

		if (verify) {
			waitForMessage("You have registered a new account.");
			closeMessageDialog();
		}
	}

	//Add a new user account in public mode
	public void addNewUserAccountInPublicMode(String username, String password, String confirmPassword, String firstName,
			String lastName, String email, boolean verify){

		info("-- Add new user account in public mode --");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		type(ELEMENT_INPUT_CONFIRM_PASSWORD_PUBLIC_MODE, confirmPassword, true);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(ELEMENT_INPUT_EMAIL_PUBLIC_MODE, email, true);
		click(ELEMENT_SUBSCRIBE_BUTTON);
		if (verify) {
			waitForMessage(MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT);
			closeMessageDialog();
		}
	}
}
