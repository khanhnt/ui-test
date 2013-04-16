package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * <li>This class creates default users in the database</li>
 * @author vuna2
 *
 */
public class TermAndConditionAgreement extends PlatformBase{
	//WebElements
	public final By ELEMENT_AGREEMENT_CHECKBOX = By.xpath("//*[@id = 'agreement']");
	//By.id("agreement");
	public final By ELEMENT_FIRSTNAME_ACCOUNT = By.name("firstNameAccount");
	public final By ELEMENT_LASTNAME_ACCOUNT = By.name("lastNameAccount");
	public final By ELEMENT_EMAIL_ACCOUNT = By.name("emailAccount");
	public final By ELEMENT_CONFIRM_PASS_ACCOUNT = By.name("confirmUserPasswordAccount");
	public final By ELEMENT_ROOT_PASS_ACCOUNT = By.name("adminPassword");
	public final By ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT = By.name("confirmAdminPassword");

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

		info("-- Creating an Admin account: FQA... --");
		if (isElementPresent(ELEMENT_ROOT_PASS_ACCOUNT)){
			type(ELEMENT_INPUT_USERNAME, "fqa", true);
			type(ELEMENT_FIRSTNAME_ACCOUNT, "FQA", true);
			type(ELEMENT_LASTNAME_ACCOUNT, "VN", true);
			type(ELEMENT_EMAIL_ACCOUNT, "fqa@exoplatform.com", true);	
			type(ELEMENT_INPUT_PASSWORD, "gtngtn", true);
			type(ELEMENT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);	
			type(ELEMENT_ROOT_PASS_ACCOUNT, "gtngtn", true);
			type(ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);
			click(button.ELEMENT_SUBMIT_BUTTON);
			waitForTextNotPresent("Create your account");
			click(button.ELEMENT_START_BUTTON);
		}
		Utils.pause(25000);
		waitForElementPresent(ELEMENT_ACCOUNT_NAME_LINK);
		info("-- Administrator account (FQA) has been created successfully... --");       
	}
}