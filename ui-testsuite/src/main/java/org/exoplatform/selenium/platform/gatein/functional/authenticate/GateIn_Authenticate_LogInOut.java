package org.exoplatform.selenium.platform.gatein.functional.authenticate;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.info;

public class GateIn_Authenticate_LogInOut extends PlatformBase{
	
	ManageAccount account;
	public String MESSAGE_FAILED = "Sign in failed. Wrong username or password.";
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
	}
	
	@AfterMethod
	public void afterTest(){
		driver.quit();		
	}
	
	/*-- tests cases of : Portal\Login_out\Sign In --*/	
	//Check displaying of portal after signing in successfully by admin account
	@Test
	public void test02_SignInByAdminAccount(){
			info("--Login by Admin account--");
			account.signIn("root", "gtn");
			waitForTextPresent("Root Root");
			account.signOut();		
	}
	
	//Check displaying portal after signing in successfully by normal account
	@Test
	public void test03_SignInByNormalAccount(){
		info("--Login by Normal account--");
		
		//Sign in as Jack Miller
		account.signIn("demo", "gtn");
		waitForTextPresent("Demo gtn");
		waitForTextNotPresent("Edit");
		account.signOut();
		
		//Sign in as Mary
		account.signIn("mary", "gtn");
		waitForTextPresent("Mary Kelly");
		waitForTextNotPresent("Edit");
		account.signOut();	
	}
	
	//Sign in with blank User name/Password
	@Test
	public void test04_SignInWithBlankUserNameAndPassword(){
		info("--Sign in with blank password");
		account.signIn("root","");
		waitForTextPresent(MESSAGE_FAILED);
		driver.get(baseUrl);
		info("--Sign in with blank username");
		account.signIn("","gtn");
		waitForTextPresent(MESSAGE_FAILED);	
	}
	
	//Sign in by unregistered User
	@Test
	public void test05_SignInByUnRegisteredUsername(){
		info("--Sign in with unregistered account--");
		account.signIn("exogtn", "gtn");
		waitForTextPresent(MESSAGE_FAILED);	
	}
}