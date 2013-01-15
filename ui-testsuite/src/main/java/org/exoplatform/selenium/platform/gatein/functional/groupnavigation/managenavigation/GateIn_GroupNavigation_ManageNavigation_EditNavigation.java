package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.managenavigation;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 20/09/2012
 */

public class GateIn_GroupNavigation_ManageNavigation_EditNavigation extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	
	//Define data
	public By ADMIN_EDIT_PROPERTIES_LINK = By.xpath("//td/div[text()='Administration']/ancestor::tr/td/a[text()='Edit Properties']");
	public By PRIORITY_SELECT = By.xpath("//select[@name='priority']");
	public By OWNER_TYPE_INPUT = By.xpath("//input[@id='ownerType' and @readonly='' or @readonly ='readonly']");
	public By OWNER_ID_INPUT = By.xpath("//input[@id='ownerId' and @readonly='' or @readonly ='readonly']");
	public By PRIORITY_OPTION_1 = By.xpath("//option[@value='2' and @selected='selected']");
	public By PRIORITY_OPTION_2 = By.xpath("//option[@value='6' and @selected='selected']");
	public By ADMINISTRATION_OLD_POSTION = By.xpath("//div[@id='UIGroupNavigationGrid']//table[2]//div[@title='/platform/administrators']");
	public By ADMINISTRATION_NEW_POSTION = By.xpath("//div[@id='UIGroupNavigationGrid']//table[5]//div[@title='/platform/administrators']");
	public By ELEMENT_GROUP_PRIORITY_SELECT = By.name("priority");
	
	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		account.signIn("root", "gtn");
	}

	//Edit properties of group navigation
	@Test()
	public void test01_EditPropertiesOfGroupnavigation()
	{
		//Goto Group Sites
		navigation.goToGroupSites();

		//Verify position of Administration before change order
		waitForElementPresent(ADMINISTRATION_OLD_POSTION);
		captureScreen("POR_GRNAVIGATION_24_03_GroupSites_BeforeChangeOrder");

		//Select Edit Properties of Administration
		waitForElementPresent(ADMIN_EDIT_PROPERTIES_LINK);
		click(ADMIN_EDIT_PROPERTIES_LINK);

		//Verify OwnerType & OwnerId can not be changed
		waitForElementPresent(OWNER_TYPE_INPUT);
		waitForElementPresent(OWNER_ID_INPUT);
		click(ELEMENT_GROUP_PRIORITY_SELECT);
		waitForElementPresent(PRIORITY_OPTION_1);

		//Change number of priority
		select(PRIORITY_SELECT, "6");
		save();

		//Verify position of Administration after changing order
		waitForElementNotPresent(ADMINISTRATION_OLD_POSTION);
		waitForElementPresent(ADMINISTRATION_NEW_POSTION);
		captureScreen("POR_GRNAVIGATION_24_03_GroupSites_AfterChangeOrder");

		//Verify priority is changed
		waitForElementPresent(ADMIN_EDIT_PROPERTIES_LINK);
		click(ADMIN_EDIT_PROPERTIES_LINK);
		click(ELEMENT_GROUP_PRIORITY_SELECT);
		waitForElementPresent(PRIORITY_OPTION_2);

		//Sign out and Sign in again
		account.signOut();
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		
		//Goto Group Sites
		navigation.goToGroupSites();

		//Verify position of Administration after Signing out and Signing in again  
		waitForElementNotPresent(ADMINISTRATION_OLD_POSTION);
		waitForElementPresent(ADMINISTRATION_NEW_POSTION);

		//Reset order of navigation list
		waitForElementPresent(ADMIN_EDIT_PROPERTIES_LINK);
		click(ADMIN_EDIT_PROPERTIES_LINK);
		waitForElementPresent(PRIORITY_OPTION_2);
		select(PRIORITY_SELECT, "2");
		save();

		//Verify data is reset
		waitForElementPresent(ADMINISTRATION_OLD_POSTION);
		waitForElementNotPresent(ADMINISTRATION_NEW_POSTION);
	}

	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}