package org.exoplatform.selenium.platform.gatein.functional.globalsettings;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.UserGroupManagement;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author VuNA
 *@date: 26/09/2012
 */
public class GateIn_GlobalSettings_LanguageSettings extends PlatformBase{
	UserGroupManagement userGroup;
	ManageAccount account;
	NavigationToolbar navigation;
	
	public By ELEMENT_APPLY_FRENCH_BUTTON = By.linkText("Appliquer");
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*-- Case ID 002
	 *-- Change language
	 * --*/
	@Test
	public void test02_ChangeLanguage() {

		account.signIn("root", "gtn");

		info("-- Step 1: Show form to change language --");
		navigation.goToChangeLanguageForUserInterface();
		waitForTextPresent(navigation.TEXT_CHANGE_LANGUAGE);

		info("-- Step 2: Change language --");
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}", "French"));
		apply();
		waitForTextNotPresent("Home");
		waitForTextPresent("Accueil"); 

		info("-- Step 3: Check displaying language when sign out --");
		signOutInFrench(); 
		/*-- Verify that portal is displayed in language of current using portal not new selected language at step 2 --*/
		captureScreen("portalSettings_languageSettings_changeLanguage_caseID02_step3");

		info("-- Step 4: Check displaying language when sign in again--");
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		/*-- Verify that portal is displayed in new selected language at step 2 --*/
		waitForTextPresent("Accueil");
		captureScreen("portalSettings_languageSettings_changeLanguage_caseID02_step4");

		info("-- Restore Original data values after testing --");
		goToChangeLanguageForUserInterfaceInFrench();
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}", "Anglais"));
		applyInFrench();
		waitForTextPresent("Home");

		info("-- Sign Out --");	
		waitForTextPresent("Root Root");
		account.signOut();	
	}

	/*-- Case ID 003  
	 *-- Check display language of portal in public mode
	 */
	@Test
	public void test03_CheckDisplayLanguageOfPortalInPublicMode(){
		
		info("-- Step 2: Show form to change language --");
		click(ELEMENT_GO_TO_ACME);
		click(ELEMENT_PORTAL_CHANGE_LANGUAGE);

		info("-- Step 3: Change language --");
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","French"));
		apply();
		waitForTextNotPresent("Change language");
		waitForTextPresent("Changer de langue");

		info("-- Restore Original data values after testing --");
		click(ELEMENT_PORTAL_CHANGE_LANGUAGE);
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","Anglais"));
		applyInFrench();
		waitForTextPresent("Change language");
	}

	/*-- Case ID 004  
	 *-- Check display language of portal in private mode with demo account 
	 *-- 
	 *--*/
	@Test
	public void test04_CheckDisplayLanguageOfPortalInPrivateModeWithDemoAccount(){
		info("-- Step 1: Check display after login to portal --");
		account.signIn("root", "gtn");
		waitForTextPresent("Home");
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID04_step1");
		account.signOut();
		deleteCookieTest(driver);
		driver.close();

		info("-- Step 2: Check displaying language when language of browser don't support by portal with user account demo --");
		// choose a browser's language that portal doesn't support this language: e.g, Vietnamese
		WebDriver driverTest = initNewDriverSeleniumTest(Language.vi);
		driverTest.get(baseUrl);
		account = new ManageAccount(driverTest);
		account.signIn("root", "gtn");
		waitForTextPresent("Home");
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID04_step2");
		navigation = new NavigationToolbar(driverTest);
		info("-- Step 3: Check when change language--");
		navigation.goToChangeLanguageForUserInterface();
		waitForTextPresent(navigation.TEXT_CHANGE_LANGUAGE);
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","French"));
		apply();
		waitForTextNotPresent("Home");
		waitForTextPresent("Accueil");
		signOutInFrench(); 
		driverTest.close();

		info("-- Restore Original data values after testing --");
		//set a browser's language to default: English
		WebDriver driverOriginal = initNewDriverSeleniumTest(Language.en);
		driverOriginal.get(baseUrl);
		account = new ManageAccount(driverOriginal);
		navigation = new NavigationToolbar(driverOriginal);
		account.signIn("root", "gtn");
		goToChangeLanguageForUserInterfaceInFrench();
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","Anglais"));
		applyInFrench();
		waitForTextPresent("Home");

		info("-- Sign Out --");	
		waitForTextPresent("Root Root");
		account.signOut();	
	}

	/*-- Case ID 005
	 *-- Check display language of portal in private mode with new account 
	 *-- 
	 *--*/
	@Test(groups={"later"})
	public void test05_CheckDisplayLanguageOfPortalInPrivateModeWithNewAccount(){
		String username = "useratexoplatformeu"; 
		String password = "exoplatform"; 
		String confirmPassword = "exoplatform"; 
		String firstName = "first"; 
		String lastName = "last name"; 
		String email = "testaccount@platform.com"; 
		String userNameGiven = ""; 
		String language = "English"; 
		boolean verify = true;

		info("-- Step 1: Register new account --");
		account.signIn("root", "gtn");
		navigation.goToNewStaff();
		account.addNewUserAccount(username,password,confirmPassword,firstName,
				lastName,email,userNameGiven,language,verify);
		account.signOut();

		info("-- Step 2: Check display after login to portal --");
		driver.get(baseUrl);
		account.signIn(username, password);
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID05_step2");
		account.signOut();
		deleteCookieTest(driver);
		driver.close();

		info("-- Step 3: Check displaying language when language of browser don't support by portal with new account --");
		WebDriver driverTest = initNewDriverSeleniumTest(Language.vi);
		driverTest.get(baseUrl);
		account = new ManageAccount(driverTest);
		navigation = new NavigationToolbar(driverTest);
		account.signIn(username, password);
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID05_step3");

		info("-- Step 4: Check when change language --");
		navigation.goToChangeLanguageForUserInterface();
		waitForTextPresent(navigation.TEXT_CHANGE_LANGUAGE);
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","French"));
		apply();
		waitForTextNotPresent("Home");
		waitForTextPresent("Accueil");
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID05_step4");
		goToChangeLanguageForUserInterfaceInFrench();
		click(navigation.ELEMENT_LANGUAGE_LINK.replace("{$language}","Anglais"));
		applyInFrench();
		waitForTextPresent("Home");
		account.signOut(); 
		driverTest.close();

		info("-- Restore Original data values after testing --");
		//set a browser's language to default: English
		WebDriver driverOriginal = initNewDriverSeleniumTest(Language.en);
		driverOriginal.get(baseUrl);
		account = new ManageAccount(driverOriginal);
		navigation = new NavigationToolbar(driverOriginal);
		userGroup = new UserGroupManagement(driverOriginal);
		account.signIn("root", "gtn");
		//delete user demo
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(username);

		info("-- Sign Out --");	
		waitForTextPresent("Root Root");
		account.signOut();	
	}

	/*-- Case ID 006
	 *-- Check display language of portal after change language in Organization portlet 
	 *--*/
	@Test(groups={"later"})
	public void test06_CheckDisplayLanguageOfPortalAfterChangeLanguageInOrganizationPortlet(){
		account.signIn("root", "gtn");

		info("-- Step 1: Show form to edit a specific user --");
		navigation.goToUsersAndGroupsManagement();
		userGroup.editUser("demo");
		
		waitForTextPresent("User Name");
		type(ELEMENT_INPUT_EMAIL,"demo@localhost.com",true);
		click(ELEMENT_USER_PROFILE_TAB);

		info("-- Step 2: Change displaying language for user --");
		waitForTextPresent("Language");
		select(ELEMENT_SELECT_LANGUAGE, "French");
		save();
		waitForMessage(account.MSG_UPDATE_USER);
		closeMessageDialog();
		account.signOut();

		info("-- Step 3: Check when change language --");
		//Sign In portal by account which edited above
		driver.get(baseUrl);
		account.signIn("demo", "gtn");
		waitForTextNotPresent("Home");
		waitForTextPresent("Accueil");
		signOutInFrench();

		info("-- Restore Original data values after testing --");
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		navigation.goToUsersAndGroupsManagement();
		userGroup.editUser("demo");
		waitForTextPresent("User Name");
		click(ELEMENT_USER_PROFILE_TAB);
		waitForTextPresent("Language");
		select(ELEMENT_SELECT_LANGUAGE, "English");
		save();
		waitForMessage(account.MSG_UPDATE_USER);
		closeMessageDialog();

		info("-- Sign Out --");
		waitForTextPresent("Root Root");
		account.signOut();	
	}

	/*-- Case ID 007
	 *-- Check display language of portal with new account is created in public mode
	 *-- 
	 *--*/
	@Test(groups={"later"})
	public void test07_CheckDisplayLanguageOfPortalWithNewAccountIsCreatedInPublicMode(){
		/*-- Prepare data for test case
	    --   Disable use captcha
		--*/
		String username = "useratexoplatformvn";
		String password = "exoplatform"; 
		String confirmPassword = "exoplatform"; 
		String firstName = "Exo";
		String lastName = "Sea";
		String email = "exosea@platform.com";

		account.signIn("root", "gtn");

		navigation.goToRegisterPageInPublicMode(driver);
		navigation.goToEditPageEditor();
		setUseCaptcha(false, false);
		account.signOut();
		deleteCookieTest(driver);
		driver.close();

		// choose a browser's language: French
		WebDriver driverTest = initNewDriverSeleniumTest(Language.fr);
		//Start a test case
		info("-- Step 1: Create new account in public mode --");

		click(account.ELEMENT_REGISTER_LINK);
		account = new ManageAccount(driverTest);
		navigation = new NavigationToolbar(driverTest);
		
		info("-- Step 2: Complete adding new account with valid values --");
		account.addNewUserAccountInPublicMode(username, password, confirmPassword, firstName, lastName, email, false);
		waitForMessage("MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT_INFRENCH");
		closeMessageDialog();

		info("-- Step 3: Check display after login to portal --");
		driverTest.get(baseUrl);
		account.signIn(username,password);
		waitForTextPresent("Accueil");
		captureScreen("portalSettings_languageSettings_checkDisplayLanguage_caseID07_step3");
		signOutInFrench();

		info("-- Restore Original data values after testing --");
		driverTest.get(baseUrl);
		account.signIn("root", "gtn");
		navigation.goToRegisterPageInPublicMode(driverTest);
		navigation.goToEditPageEditor();
		setUseCaptcha(true, false);
		deleteCookieTest(driverTest);
		driverTest.close();

		//Delete user demo 
		account.signIn("root", "gtn");
		navigation.goToUsersAndGroupsManagement();
		userGroup.deleteUser(username);

		//Finish a test case
		info("-- Sign Out--");
		waitForTextPresent("Root Root");
		account.signOut();
	}
	/**
	 * update: thuntn
	 */
	/*----- Auxiliary functions -----*/
	public void signOutInFrench(){
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK,true);
		mouseOverAndClick(By.linkText("Se Déconnecter"));	
		pause(500);
	}
	/**
	 * update: thuntn
	 */
	public void goToChangeLanguageForUserInterfaceInFrench(){
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		mouseOverAndClick(By.linkText("Changer de Langue"));	
		pause(500);
	}

	//Go to edit page layout in french
	/*public void goToEditPageLayoutInFrench(){
		info("--Go to edit page layout--");
		waitForElementPresent(ELEMENT_LINK_EDITOR_INFRENCH);
		mouseOver(ELEMENT_LINK_EDITOR_INFRENCH, true);
		pause(500);
		mouseOver(ELEMENT_LINK_EDITOR_PAGE, true);
		pause(500);
		mouseOverAndClick(ELEMENT_LINK_EDITOR_EDIT_LAYOUT_INFRENCH);
		pause(500);
	}*/

	public void applyInFrench(){
		waitForAndGetElement(ELEMENT_APPLY_FRENCH_BUTTON);
		click(ELEMENT_APPLY_FRENCH_BUTTON);
	}	

	public static enum Language{
		en, fr, vi;
	}

	public WebDriver getDriver(Language language){
		String locale = language.toString();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", locale);
		driver = new FirefoxDriver(profile);
		return driver;
	}

	public WebDriver initNewDriverSeleniumTest(Language language){
		driver = getDriver(language);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		return driver;
	}

	public static void deleteCookieTest(WebDriver driverTest){
		driverTest.manage().deleteCookieNamed("LR_COOKIE_SESSION_START");
		driverTest.manage().deleteCookieNamed("JSESSIONIDSSO");
		driverTest.manage().deleteCookieNamed("JSESSIONID");
	}
}