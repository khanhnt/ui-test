package org.exoplatform.selenium.platform.users;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * <li>This class creates default users in the database</li>
 * @author vuna2
 *
 */
public class EXOGTN_DefaultUsers extends PlatformBase{

	//WebElements
	public final By ELEMENT_AGREEMENT_CHECKBOX = By.xpath("//*[@id = 'agreement']");
	//By.id("agreement");
	public final By ELEMENT_FIRSTNAME_ACCOUNT = By.name("firstNameAccount");
	public final By ELEMENT_LASTNAME_ACCOUNT = By.name("lastNameAccount");
	public final By ELEMENT_EMAIL_ACCOUNT = By.name("emailAccount");
	public final By ELEMENT_CONFIRM_PASS_ACCOUNT = By.name("confirmUserPasswordAccount");
	public final By ELEMENT_ROOT_PASS_ACCOUNT = By.name("adminPassword");
	public final By ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT = By.name("confirmAdminPassword");

	public final String USER_PASS = "gtngtn";
	public final String USER_ADMIN = "john";
	public final String USER_AUTHOR = "james";
	public final String USER_PUBLISHER = "mary";
	public final String USER_DEVELOPER = "jack";

	Button button;
	ManageAccount magAcc;
	NavigationToolbar nav;
	UserGroupManagement userGrp;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		nav = new NavigationToolbar(driver);
		userGrp = new UserGroupManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@Test
	public void test00_createDefaultUser(){
		info("-- Checking the terms and conditions agreement... --");
		if (isElementPresent(ELEMENT_AGREEMENT_CHECKBOX)) {
			click(ELEMENT_AGREEMENT_CHECKBOX, 2);
			click(button.ELEMENT_CONTINUE_BUTTON);
		}else{
			info("-- Terms and conditions agreement have been accepted... --");
		}
		waitForTextNotPresent("terms and conditions agreement");

		info("-- Creating an Admin account: John... --");
		if (isElementPresent(ELEMENT_ROOT_PASS_ACCOUNT)){
			type(ELEMENT_INPUT_USERNAME, USER_ADMIN, true);
			type(ELEMENT_FIRSTNAME_ACCOUNT, "John", true);
			type(ELEMENT_LASTNAME_ACCOUNT, "Smith", true);
			type(ELEMENT_EMAIL_ACCOUNT, "john@exoplatform.com", true);	
			type(ELEMENT_INPUT_PASSWORD, USER_PASS, true);
			type(ELEMENT_CONFIRM_PASS_ACCOUNT, USER_PASS, true);	
			type(ELEMENT_ROOT_PASS_ACCOUNT, USER_PASS, true);
			type(ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT, USER_PASS, true);
			click(button.ELEMENT_SUBMIT_BUTTON);
			waitForTextNotPresent("Create your account");
			click(button.ELEMENT_START_BUTTON);
		}
		Utils.pause(25000);
		waitForElementPresent(ELEMENT_ACCOUNT_NAME_LINK);
		info("-- Administrator account (John) has been created successfully... --");       
	}

	/**
	 * Create an user for the group Development: eg, Jack Miller
	 */
	@Test
	public void test01_createDefaultUser(){
		info("-- Creating an user for the group Development --");
		String username = "jack"; 
		String password = USER_PASS; 
		String confirmPassword = USER_PASS; 
		String firstName = "Jack"; 
		String lastName = "Miller"; 
		String email = "jack@exoplatform.com"; 
		String userNameGiven = ""		; 
		String language = "English"; 
		boolean verify = true     ;

		magAcc.signIn(USER_ADMIN, USER_PASS);

		nav.goToNewStaff();
		magAcc.addNewUserAccount(username, password, confirmPassword, firstName,
				lastName, email, userNameGiven, language, verify);

		nav.goToUsersAndGroupsManagement();
		userGrp.chooseGroupTab();
		userGrp.selectGroup("Development");
		userGrp.addUsersToGroup(username, "member", true, true);	
		info("-- Developer account (Jack) has been created successfully... --");
	}

	/**
	 * Create an user for the group Content Management: eg, Mary Williams (publisher/editor)
	 * James David (redactor)
	 */
	@Test
	public void test02_createDefaultUser(){
		String username = "james"; 
		String password = USER_PASS; 
		String confirmPassword = USER_PASS; 
		String firstName = "James"; 
		String lastName = "David"; 
		String email = "james@exoplatform.com"; 
		String userNameGiven = ""		; 
		String language = "English"; 
		boolean verify = true     ;

		magAcc.signIn(USER_ADMIN, USER_PASS);

		nav.goToNewStaff();

		magAcc.addNewUserAccount(username, password, confirmPassword, firstName,
				lastName, email, userNameGiven, language, verify);

		magAcc.addNewUserAccount("mary", password, confirmPassword, "Mary",
				"Williams", "mary@exoplatform.com", userNameGiven, language, verify);

		nav.goToUsersAndGroupsManagement();

		userGrp.chooseGroupTab();
		userGrp.selectGroup("Platform/Content Management");
		userGrp.addUsersToGroup(username, "redactor", true, true);
		userGrp.addUsersToGroup("mary", "editor", true, true);
		userGrp.addUsersToGroup("mary", "publisher", true, true);
		info("-- Author and Publisher account have been created successfully... --");
	}
}